// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.RobotConstants;

public class BeltSubsystem extends EntechSubsystem {
  private TalonSRX m_motor;

  public enum BeltMode{
    stop, in, out, auto
  }
  private BeltMode currentMode = BeltMode.stop;

  public BeltSubsystem(){
  }

  // Entech does all the creation work in the initialize method
  @Override
  public void initialize(){
    m_motor = new TalonSRX(RobotConstants.CAN.BELT_MOTOR);
    currentMode = BeltMode.stop;
    // Create the internal objects here 
  }

  @Override
  public void periodic(){
    if (currentMode == BeltMode.stop){
      logger.log("belt state", "stop");
      m_motor.set(ControlMode.PercentOutput,0.0);
    } else if (currentMode == BeltMode.in){
      logger.log("belt state", "in");
      m_motor.set(ControlMode.PercentOutput, -1.0);
    } else if (currentMode == BeltMode.out){
      logger.log("belt state", "out");
      m_motor.set(ControlMode.PercentOutput, 1.0);
    }
    logger.log("belt current",m_motor.getSupplyCurrent());
  }
  
  public void intake(){
    currentMode = BeltMode.in;
  }
  public void out(){
    currentMode = BeltMode.out;
  }
  public void stop(){
    currentMode = BeltMode.stop;
  }

  @Override
  public void simulationPeriodic(){

  }
}
