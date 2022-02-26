package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class DriveStraightVisionCommand extends EntechCommandBase {
    private final DriveSubsystem m_drive;
    private final double m_speed;
    private final double m_inches;
    private double m_initalAngle;
    private PIDController m_PID;
    private VisionSubsystem m_vision;

    public DriveStraightVisionCommand(DriveSubsystem drive, VisionSubsystem vision, double inches, double speed){
        super(drive,vision);
        m_speed = -speed;
        m_drive = drive;
        m_inches = Math.abs(inches);
        m_vision = vision;
        m_PID = new PIDController(-0.004, 0, 0);
    }

    public void initialize(){
        m_drive.arcadeDrive(0, 0);
        m_drive.resetEncoders();
        m_drive.setBrake();
        m_PID.setSetpoint(0);
        m_PID.setTolerance(2);
        
    }

    public void execute(){
        double adjustment = m_PID.calculate( m_vision.getBallX() - 0);
        if ((adjustment > 0) && (adjustment < 0.25)) {
            adjustment = 0.25;
        }
        if ((adjustment < 0) && (adjustment > -0.25)) {
            adjustment = -0.25;
        }
        m_drive.arcadeDrive(m_speed, adjustment);
    }

    public boolean isFinished(){
        double EDistance = Math.abs((m_drive.getLeftDistance() + m_drive.getRightDistance()) / 2);
        if (EDistance > m_inches){
            return true;
        }
        if (m_vision.isBallFound()== false){
            return true;
        }
        return false;
    }
    public void end(boolean interrupted){
        m_drive.arcadeDrive(0, 0);
        m_drive.setCoast();
    }
}
