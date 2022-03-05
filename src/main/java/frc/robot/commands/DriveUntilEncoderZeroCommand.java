package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.DriveSubsystem;

public class DriveUntilEncoderZeroCommand extends EntechCommandBase {
    private final DriveSubsystem m_drive;
    private double m_speed;
    private double m_distance;
    private PIDController m_PID;

    public DriveUntilEncoderZeroCommand(DriveSubsystem drive, double speed){
        super(drive);
        m_speed = Math.abs(speed);
        m_drive = drive;
        m_PID = new PIDController(0.025,0 ,0 );
    }

    public void initialize(){
        m_distance = Math.abs((m_drive.getLeftDistance() + m_drive.getRightDistance()) / 2);
        if (m_distance > 0) {
            m_speed = -m_speed;
        }
        m_drive.arcadeDrive(0, 0);
        m_drive.setBrake();
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
        if ((m_distance > 0) && (EDistance < 0 )){
            return true;
        }
        if ((m_distance < 0) && (EDistance > 0)){
            return true;
        }
        return false;
    }
    public void end(boolean interrupted){
        m_drive.arcadeDrive(0, 0);
        m_drive.setCoast();
    }
}
