// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.RobotConstants;

public class IntakeSubsystem extends EntechSubsystem {
  private TalonSRX m_armMotor;
  private TalonSRX m_rollerMotor;
  /** Creates a new ExampleSubsystem. */
  public IntakeSubsystem() {
  }

  // Entech does all the creation work in the initialize method
  @Override
  public void initialize() {
    m_armMotor = new TalonSRX(RobotConstants.CAN.ARM_MOTOR);
    m_rollerMotor = new TalonSRX(RobotConstants.CAN.ROLLER_MOTOR);
    // Create the internal objects here
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void armsDown() {
    // TODO: inpliment this
  }

  public void armsUp() {
    // TODO: inpliment this
  }

  public void rollersIn() {
    m_rollerMotor.set(ControlMode.PercentOutput, 1.0);
  }

  public void rollersOut() {
    m_rollerMotor.set(ControlMode.PercentOutput, -1.0);
  }

  public void rollersStop() {
    m_rollerMotor.set(ControlMode.PercentOutput, 0.0);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
