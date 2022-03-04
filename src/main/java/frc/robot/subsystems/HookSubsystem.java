// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.RobotConstants;

public class HookSubsystem extends EntechSubsystem {
  private TalonSRX m_motor;
  private DigitalInput m_UpLimit;
  private DigitalInput m_DownLimit;
  private final double motorSpeed = 1.0;
  public enum HookMode{
    idle, up, down
  }
  private HookMode currentMode = HookMode.idle;
  
  public HookSubsystem() {
  }

  // Entech does all the creation work in the initialize method
  @Override
  public void initialize() {
    // Create the internal objects here
    m_motor = new TalonSRX(RobotConstants.CAN.HOOK_MOTOR);
    m_motor.setNeutralMode(NeutralMode.Brake);
    // m_motor.enableCurrentLimit(true);
    // m_motor.configContinuousCurrentLimit(40);
    // m_motor.configPeakCurrentLimit(0);
    m_UpLimit = new DigitalInput(RobotConstants.DIGITAL_IO.HOOK_UP_LIMIT);
    m_DownLimit = new DigitalInput(RobotConstants.DIGITAL_IO.HOOK_DOWN_LIMIT);
  }

  @Override
  public void periodic() {
    // TODO: the checks against the limit switches below are reversed since there are currently
    //   no limit siwtches on the robot and they read true by default.  Fix this or remove limit switches.
    if ((currentMode == HookMode.up) && (m_UpLimit.get() == false)) {
      currentMode = HookMode.idle;
    }
    if ((currentMode == HookMode.down) && (m_DownLimit.get() == false)) {
      currentMode = HookMode.idle;
    }
    if (currentMode == HookMode.idle){
      m_motor.set(ControlMode.PercentOutput, 0.0);
    } else if (currentMode == HookMode.up) {
      m_motor.set(ControlMode.PercentOutput, motorSpeed);
    } else if (currentMode == HookMode.down) {
      m_motor.set(ControlMode.PercentOutput, -motorSpeed);
    }
    // logger.log("hook current",m_motor.getSupplyCurrent());
  }

  public void up() {
    currentMode = HookMode.up;
  }

  public void down() {
    currentMode = HookMode.down;
  }  

  public void idle() {
    currentMode = HookMode.idle;
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
