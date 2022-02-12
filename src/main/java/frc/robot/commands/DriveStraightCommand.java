package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.DriveSubsystem;

public class DriveStraightCommand extends EntechCommandBase {
    private final DriveSubsystem m_drive;
    private final double m_speed;
    private PIDController m_PID;

    public DriveStraightCommand(DriveSubsystem drive, double inches, double speed){
        super(drive);
        m_speed = speed;
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
        if (m_PID.atSetpoint()){
            return true;
        }
        return false;
    }
    public void end(boolean interrupted){
        m_drive.arcadeDrive(0, 0);
    }
}
