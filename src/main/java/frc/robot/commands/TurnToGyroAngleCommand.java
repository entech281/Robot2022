package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.DriveSubsystem;

public class TurnToGyroAngleCommand extends EntechCommandBase {
    private final DriveSubsystem m_drive;
    private double m_finalAngle;
    private double m_initalAngle;
    private double angle;
    private PIDController m_PID;
    private double m_minTurnSpeed = 0.3;
    private double m_maxTurnSpeed = 0.7;

    public TurnToGyroAngleCommand(DriveSubsystem drive, double angle){
        super(drive);
        m_drive = drive;
        this.angle = angle;
        m_PID = new PIDController(0.015, 0, 0);
    }
    
    public void initialize(){
        m_initalAngle = m_drive.getAngle();
        m_finalAngle = angle;
        m_drive.arcadeDrive(0, 0);
        m_drive.resetEncoders();
        m_drive.setBrake();
        m_PID.setSetpoint(0);
        m_PID.setTolerance(3);  // IN DEGREES
    }

    public void execute(){
        double currentAngle = m_drive.getAngle();
        double adjustment = m_PID.calculate( m_finalAngle - currentAngle);
        if ((adjustment > 0) && (adjustment < m_minTurnSpeed)) {
            adjustment = m_minTurnSpeed;
        }
        if ((adjustment < 0) && (adjustment > -m_minTurnSpeed)) {
            adjustment = -m_minTurnSpeed;
        }
        if ((adjustment > 0) && (adjustment > m_maxTurnSpeed)) {
            adjustment = m_maxTurnSpeed;
        }
        if ((adjustment < 0) && (adjustment < -m_maxTurnSpeed)) {
            adjustment = -m_maxTurnSpeed;
        }
        m_drive.arcadeDrive( 0, adjustment);
    }

    public boolean isFinished(){
        if (m_PID.atSetpoint()){
            return true;
        }
        return false;
    }
    public void end(boolean interrupted){
        m_drive.arcadeDrive(0, 0);
        m_drive.setCoast();
    }
}