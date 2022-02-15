// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.RobotConstants;

public class HookSubsystem extends EntechSubsystem {
  private VictorSPX m_motor;
  private DigitalInput m_UpLimit;
  private DigitalInput m_DownLimit;
    public enum HookMode{
      idle, up, down
    }
    private HookMode currentMode = HookMode.idle;
  /** Creates a new ExampleSubsystem. */
  public HookSubsystem() {
  }

  // Entech does all the creation work in the initialize method
  @Override
  public void initialize() {
    // Create the internal objects here
    m_motor = new VictorSPX(RobotConstants.CAN.HOOK_MOTOR);
    m_UpLimit = new DigitalInput(RobotConstants.DIGITAL_IO.HOOK_UP_LIMIT)
    m_DownLimit = new DigitalInput(RobotConstants.DIGITAL_IO.HOOK_DOWN_LIMIT)
  }

  @Override
  public void periodic() {
    if (currentMode == HookMode.idle){
      m_motor.set(ControlMode.PercentOutput, 0.0);
    } else if (currentMode == HookMode.up) {
      m_motor.set(ControlMode.PercentOutput, 1.0)
    } else if (currentMode == HookMode.down) {
      m_motor.set(ControlMode.PercentOutput, -1.0)
    }
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
