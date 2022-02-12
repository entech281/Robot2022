package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.DriveSubsystem;

public class DriveStraightGyroCommand extends EntechCommandBase {
    private final DriveSubsystem m_drive;
    private final double m_speed;
    private final double m_initalAngle;
    private PIDController m_PID;

    public DriveStraightGyroCommand(DriveSubsystem drive, double inches, double speed){
        super(drive);
        m_speed = speed;
        m_drive = drive;
        m_PID = new PIDController(0.015, 0, 0);
        m_initalAngle = m_drive.getAngle();
    }

    public void initialize(){
    m_drive.arcadeDrive(0, 0);
    m_drive.resetEncoders();
    m_PID.setSetpoint(0);
    m_PID.setTolerance(5);
    }

    public void execute(){
        double currentAngle = m_drive.getAngle();
        double adjustment = m_PID.calculate( m_initalAngle - currentAngle);
        m_drive.arcadeDrive(m_speed, adjustment);
    }

    public boolean isFinished(){
        if ( m_PID.atSetpoint()){
            return true;
        }
        return false;
    }
    public void end(boolean interrupted){
        m_drive.arcadeDrive(0, 0);
    }
}
