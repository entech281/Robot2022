package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.DriveSubsystem;

public class TurnByAngleCommand extends EntechCommandBase {
    private final DriveSubsystem m_drive;
    private double m_finalAngle;
    private double m_initalAngle;
    private double angle;
    private PIDController m_PID;

    public TurnByAngleCommand(DriveSubsystem drive, double angle){
        super(drive);
        m_drive = drive;
        this.angle = angle;
        m_PID = new PIDController(0.015, 0, 0);
    }
    
    public void initialize(){
        m_initalAngle = m_drive.getAngle();
        m_finalAngle = m_initalAngle + angle;
        m_drive.arcadeDrive(0, 0);
        m_drive.resetEncoders();
        m_PID.setSetpoint(0);
        m_PID.setTolerance(5);  // IN DEGREES
    }

    public void execute(){
        double currentAngle = m_drive.getAngle();
        //double adjustment = 0.025 * ( m_initalAngle - currentAngle);
        double adjustment = m_PID.calculate( m_finalAngle - currentAngle);
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
    }
}