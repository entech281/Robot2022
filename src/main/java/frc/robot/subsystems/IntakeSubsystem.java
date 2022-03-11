// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.controllers.TalonPositionController;
import frc.robot.controllers.TalonSettings;
import frc.robot.controllers.TalonSettingsBuilder;
import frc.robot.BallDetector;
import frc.robot.RobotConstants;

public class IntakeSubsystem extends EntechSubsystem implements BallDetector {
  private TalonSRX m_armMotor;
  private TalonSRX m_rollerMotor;

  private Timer m_timer;
  private Timer m_armMoveTimer;
  private TalonPositionController armMotorController = null;
  private final double timerIgnoreUntil = 0.2;
  private final double timerAverageUntil = 0.7;
  private final double ballDetectedThreshold = 1.05;
  private double avgCurrent;
  private int avgCount;
  private boolean ballDetected = false;
  private boolean intakeHomed = false;
  private double armUpPosition = 0.0;
  private double armDownPosition = 53000.0;
  private double refDownPosition = 53000.0;
  private double downIncrement = 400.0;
  private double armDesiredPosition = 0.0;
  private double upSpeed = 0.45;
  private double downSpeed = -0.35;


  public enum RollerMode{
    off, in, out
  }
  private RollerMode currentRollerMode;
  private final double rollerSpeed = 1.0;

  public enum ArmMode {
    down, up
  }
  private ArmMode currentArmMode;
  public IntakeSubsystem() {
  }

  public static TalonSettings INTAKEARM = TalonSettingsBuilder.defaults()
          .withCurrentLimits(20, 15, 200)
          .brakeInNeutral()
          .withDirections(true, true)
          .noMotorOutputLimits()
          .noMotorStartupRamping()
          .usePositionControl()
          .withGains(0, 0.02, 0, 0)
          .withMotionProfile(20000, 20000, 400)
          .enableLimitSwitch(true)
          .build();

  // Entech does all the creation work in the initialize method
  @Override
  public void initialize() {
    m_armMotor = new TalonSRX(RobotConstants.CAN.ARM_MOTOR);
    m_rollerMotor = new TalonSRX(RobotConstants.CAN.ROLLER_MOTOR);
    m_armMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
    m_armMotor.setInverted(true);
    m_armMotor.setNeutralMode(NeutralMode.Brake);
    m_timer = new Timer();
    m_armMoveTimer = new Timer();
    m_armMoveTimer.stop();
    m_armMoveTimer.reset();
    currentRollerMode = RollerMode.off;
    m_armMotor.getSensorCollection().setQuadraturePosition(0, 100); 
  }
   
  @Override
  public void periodic() {

    // Manage the Arm
    if (! knowsHome()){
      armGoToHome();
    }
    // else{
    //   armMotorController.setDesiredPosition(armDesiredPosition);
    // }
    // if (DriverStation.isEnabled() && (m_armMoveTimer.get() > 1.0) && (currentArmMode == ArmMode.up) && (isLimitSwitchHit() != true)) {
    //   m_armMotor.set(ControlMode.PercentOutput, upSpeed);    
    //   armUpPosition -= downIncrement;
    //   armDesiredPosition = armUpPosition;
    // }

    if (currentArmMode == ArmMode.up) {
      if (isLimitSwitchHit()) {
        m_armMotor.set(ControlMode.PercentOutput, 0.0);
      } else {
         m_armMotor.set(ControlMode.PercentOutput, upSpeed);
      }
    }
    if (currentArmMode == ArmMode.down) {
      if (m_armMoveTimer.get() < 0.2) {
         m_armMotor.set(ControlMode.PercentOutput, downSpeed);
      } else {
        m_armMotor.set(ControlMode.PercentOutput, 0.0);
      }    
    }

    // Calculate average running current of the roller
    double current;
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
    // logger.log("Intake Arm Current Position:", m_armMotor.getSensorCollection().getQuadraturePosition());
    // logger.log("Intake Arm Desired Position:", armMotorController.getDesiredPosition());
    // logger.log("Intake Arm Actual Position", armMotorController.getActualPosition());
    logger.log("Intake Arm Up Position:", armUpPosition);
    logger.log("Intake Arm Down Position:", armDownPosition);
    logger.log("Intake Arm Limit Switch:", isLimitSwitchHit());
    logger.log("Intake Knows Home:", knowsHome());
  }

  public void armsDown() {
    armDesiredPosition = armDownPosition;
    currentArmMode = ArmMode.down;
    m_armMoveTimer.stop();
    m_armMoveTimer.reset();
    m_armMoveTimer.start();
  }


  public void armsUp() {
    armDesiredPosition = armUpPosition;
    currentArmMode = ArmMode.up;
    m_armMoveTimer.stop();
    m_armMoveTimer.reset();
    m_armMoveTimer.start();
  }

  public void armGoToHome(){
    if ((intakeHomed != true) && isLimitSwitchHit()){
      intakeHomed = true;
      m_armMotor.set(ControlMode.PercentOutput, 0.);
      // armMotorController = new TalonPositionController(m_armMotor, INTAKEARM, true);
      // armMotorController.configure();
      reset();
      currentArmMode = ArmMode.up;
    } else {
       m_armMotor.set(ControlMode.PercentOutput, upSpeed);
    }
}
  
  public void nudgeArmDown() {
    if (currentArmMode == ArmMode.down) {
      armDownPosition += downIncrement;
      armDownPosition = Math.min(1.5*refDownPosition, armDownPosition);
      armDesiredPosition = armDownPosition;
    }
  }


  public void nudgeArmUp() {
    if (currentArmMode == ArmMode.down) {
      armDownPosition -= downIncrement;
      armDownPosition = Math.max(armUpPosition + downIncrement, armDownPosition);
      armDesiredPosition = armDownPosition;
    }
  }
  public Boolean knowsHome() {
    return intakeHomed;
  }

  public boolean isLimitSwitchHit(){
    return m_armMotor.getSensorCollection().isFwdLimitSwitchClosed();
  }

  public void reset(){
    // armMotorController.resetPosition();
    armUpPosition = 0;
    armDownPosition = refDownPosition;
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
