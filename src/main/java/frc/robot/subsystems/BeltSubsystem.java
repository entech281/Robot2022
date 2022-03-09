// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.RobotConstants;

public class BeltSubsystem extends EntechSubsystem {
  private TalonSRX m_motor;
  private Timer m_timer;
  public Double beltInTimer = -1.0;

  public enum BeltMode{
    stop, in, out
  }
  private BeltMode currentMode = BeltMode.stop;
  private final double motorSpeed = 1.0;

  public BeltSubsystem(){
  }

  // Entech does all the creation work in the initialize method
  @Override
  public void initialize(){
    m_motor = new TalonSRX(RobotConstants.CAN.BELT_MOTOR);
    m_timer = new Timer();
    currentMode = BeltMode.stop;
  }

  @Override
  public void periodic(){
    if ((beltInTimer > 0) && (m_timer.get() > beltInTimer)){
      currentMode = BeltMode.stop;
      beltInTimer = -1.0;
    }
    switch (currentMode) {
      case stop:
        logger.log("belt state", "stop");
        m_motor.set(ControlMode.PercentOutput, 0.0);
        break;
      case in:
        logger.log("belt state", "in");
        m_motor.set(ControlMode.PercentOutput, -motorSpeed);
        break;
      case out:
        logger.log("belt state", "out");
        m_motor.set(ControlMode.PercentOutput, motorSpeed);
        break;
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
  public void intakeFor(double time){
    currentMode = BeltMode.in;
    m_timer.stop();
    m_timer.reset();
    m_timer.start();
    beltInTimer = time;
  }

  @Override
  public void simulationPeriodic(){

  }
}
