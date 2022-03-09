// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.RobotConstants;

public class HookSubsystem extends EntechSubsystem {
  private TalonSRX m_motor;
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
    // Configure limit switches for normally open
    m_motor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
    m_motor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
    m_motor.overrideLimitSwitchesEnable(true);
  }

  public boolean isLimitSwitchHit(){
    return m_motor.getSensorCollection().isFwdLimitSwitchClosed();
  }

  @Override
  public void periodic() {
    // check limit switches -- either Fwd or Rev
    boolean downLimitSwitchHit = m_motor.getSensorCollection().isFwdLimitSwitchClosed();
    if ((currentMode == HookMode.down) && downLimitSwitchHit) {
      currentMode = HookMode.idle;
    }
    if (currentMode == HookMode.idle){
      m_motor.set(ControlMode.PercentOutput, 0.0);
    } else if (currentMode == HookMode.up) {
      m_motor.set(ControlMode.PercentOutput, -motorSpeed);
    } else if (currentMode == HookMode.down) {
      m_motor.set(ControlMode.PercentOutput, motorSpeed);
    }
    logger.log("hook current",m_motor.getSupplyCurrent());
    logger.log("hook limit switch:", isLimitSwitchHit());
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
