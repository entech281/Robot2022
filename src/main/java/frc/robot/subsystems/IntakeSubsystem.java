// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.controllers.TalonPositionController;
import frc.robot.controllers.TalonSettings;
import frc.robot.controllers.TalonSettingsBuilder;
import frc.robot.RobotConstants;

public class IntakeSubsystem extends EntechSubsystem {
  private TalonSRX m_armMotor;
  private TalonSRX m_rollerMotor;

  private Timer m_timer;
  private TalonPositionController armMotorController;
  private final double timerIgnoreUntil = 0.2;
  private final double timerAverageUntil = 0.7;
  private final double ballDetectedThreshold = 1.1;
  private double avgCurrent;
  private int avgCount;
  private boolean ballDetected = false;
  private boolean intakeHomed = false;

  public enum RollerMode{
    off, in, out
  }
  private RollerMode currentRollerMode;
  private final double rollerSpeed = 1.0;

  public enum ArmMode {
    up, down, deploying, retracting 
  }
  private ArmMode currentArmMode;

  public IntakeSubsystem() {
  }

  public static TalonSettings INTAKEARM = TalonSettingsBuilder.defaults()
          .withCurrentLimits(20, 15, 200)
          .brakeInNeutral()
          .withDirections()
          .noMotorOutputLimits()
          .noMotorStartupRamping()
          .usePositionControl()
          .enableLimitSwitch(true).
          build();

  // Entech does all the creation work in the initialize method
  @Override
  public void initialize() {
    m_armMotor = new TalonSRX(RobotConstants.CAN.ARM_MOTOR);
    m_rollerMotor = new TalonSRX(RobotConstants.CAN.ROLLER_MOTOR);
    armMotorController = new TalonPositionController(m_armMotor, INTAKEARM, true);
    armMotorController.configure();
    m_armMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
    m_timer = new Timer();
    currentRollerMode = RollerMode.off;
    currentArmMode = ArmMode.up;
  }

  public void armGoToHome(){
    return m_armMotor.set(ControlMode.PercentOutput, 0.15);
  }
  
  public Boolean isatHome() {
    return intakeHomed;
  }

  public boolean isLimitSwitchHit(){
    return m_armMotor.getSensorCollection().isRevLimitSwitchClosed();
  }

  public void reset(){
    armMotorController.resetPosition();
  }

   
  @Override
  public void periodic() {

     if(isLimitSwitchHit()){
       reset();
       intakeHomed = true;
     }
     if (!isatHome()){
       armGoToHome();
     }

    double current;
    // Manage the Arm
    //   TODO: Implement arm management



    // Calculate average running current of the roller
    current = m_rollerMotor.getSupplyCurrent();
    if ((m_timer.get() > timerIgnoreUntil) && (m_timer.get() < timerAverageUntil)) {
      avgCount += 1;
      avgCurrent += current;
    } else if (m_timer.get() > timerAverageUntil) {
      if (avgCount > 0) {
        avgCurrent = avgCurrent / avgCount;
        avgCount = 0;
      }
      if (current > ballDetectedThreshold * avgCurrent) {
        ballDetected = true;
      }
    }
    // Manage the Roller
    switch (currentRollerMode) {
      case in:
        m_rollerMotor.set(ControlMode.PercentOutput, -rollerSpeed);
        break;
      case out:
        m_rollerMotor.set(ControlMode.PercentOutput, rollerSpeed);
        break;
      case off:
        m_rollerMotor.set(ControlMode.PercentOutput, 0.0);
        break;
    }
    logger.log("roller current", current);
    logger.log("roller detected ball", isBallPresent());
  }

  public void armsDown() {
    if (currentArmMode != ArmMode.down) {
      currentArmMode = ArmMode.deploying;
    }
  }

  public void armsUp() {
    if (currentArmMode != ArmMode.up) {
      currentArmMode = ArmMode.retracting;
    }
  }

  public void rollersIn() {
    currentRollerMode = RollerMode.in;
    m_timer.stop();
    m_timer.reset();
    m_timer.start();
    avgCount = 0;
    avgCurrent = 0.0;
    ballDetected = false;
  }

  public void rollersOut() {
    currentRollerMode = RollerMode.out;
  }

  public void rollersStop() {
    currentRollerMode = RollerMode.off;
  }

  public boolean isBallPresent() {
    return ballDetected;
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
