// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.subsystems.ColorSensorSubsystem;

import frc.robot.RobotConstants;

public class BeltSubsystem extends EntechSubsystem {
private VictorSPX m_motor;
private String sensorColor;
private ColorSensorSubsystem color = new ColorSensorSubsystem();

  public enum BeltMode{
  stop, in, out, auto
}
  private BeltMode currentMode = BeltMode.stop;

  public BeltSubsystem(){
  }

  // Entech does all the creation work in the initialize method
  @Override
  public void initialize(){
    m_motor = new VictorSPX(RobotConstants.CAN.BELT_MOTOR);
    // Create the internal objects here
  }

  @Override
  public void periodic(){
    sensorColor = color.getSensorColor();

    if (currentMode == BeltMode.stop){
      m_motor.set(ControlMode.PercentOutput,0.0);
    } else if (currentMode == BeltMode.in){
      m_motor.set(ControlMode.PercentOutput, 1.0);
    } else if (currentMode == BeltMode.out){
      m_motor.set(ControlMode.PercentOutput, -1.0);
    }
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic(){
    // This method will be called once per scheduler run during simulation
  }
}
