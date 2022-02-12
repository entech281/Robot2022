package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;

public class DriveStraightGyroCommand extends EntechCommandBase {
    private final DriveSubsystem m_drive;
    private final double m_distance;
    private final double m_speed;
    private final double m_initalAngle;
    private double m_currentDistance;

    public DriveStraightGyroCommand(DriveSubsystem drive, double inches, double speed){
        super(drive);
        m_distance = inches;
        m_speed = speed;
        m_drive = drive;
        m_currentDistance = 0;
        m_initalAngle = m_drive.getAngle();
    }
    

    public void initialize(){
    m_drive.arcadeDrive(0, 0);
    m_drive.resetEncoders();
    }

    public void execute(){
        double currentAngle = m_drive.getAngle();
        double adjustment = 0.025 * ( m_initalAngle - currentAngle);
        m_drive.arcadeDrive(m_speed, adjustment);
        m_currentDistance = (m_drive.getLeftDistance() + m_drive.getRightDistance()) / 2;
    }

    public boolean isFinished(){
        if ( m_currentDistance > m_distance) {
            return true;
        }
        return false;
    }
}
