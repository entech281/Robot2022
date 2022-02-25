package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.DriveSubsystem;

public class DriveStraightCommand extends EntechCommandBase {
    private final DriveSubsystem m_drive;
    private final double m_speed;
    private final double m_inches;
    private PIDController m_PID;

    public DriveStraightCommand(DriveSubsystem drive, double inches, double speed){
        super(drive);
        m_inches = Math.abs(inches);
        m_speed = speed * -1
        ;
        m_drive = drive;
        m_PID = new PIDController(0.025,0 ,0 );
    }

    public void initialize(){
    m_drive.arcadeDrive(0, 0);
    m_drive.resetEncoders();
    m_PID.setSetpoint(0);
    m_PID.setTolerance(5);
    }

    public void execute(){
        double leftEncoder = m_drive.getLeftDistance();
        double rightEncoder = m_drive.getRightDistance();
        double adjustment = m_PID.calculate(rightEncoder - leftEncoder);
        m_drive.arcadeDrive(m_speed, adjustment);
    }

    public boolean isFinished(){
        double EDistance = Math.abs((m_drive.getLeftDistance() + m_drive.getRightDistance()) / 2);
        if (EDistance > m_inches){
            return true;
        }
        return false;
    }
    public void end(boolean interrupted){
        m_drive.arcadeDrive(0, 0);
    }
}
