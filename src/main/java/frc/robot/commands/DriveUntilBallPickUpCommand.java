package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.BallDetector;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;


public class DriveUntilBallPickUpCommand extends EntechCommandBase {
    private final double m_speed;
    private final DriveSubsystem m_drive;
    private final VisionSubsystem m_vision;
    private final BallDetector m_detector;
    private PIDController m_PID;

    public DriveUntilBallPickUpCommand(DriveSubsystem drive, VisionSubsystem vision, BallDetector detector, double speed){
        super(drive, vision);
        m_vision = vision;
        m_drive = drive;
        m_detector = detector;
        m_speed = speed;
        m_PID = new PIDController(-0.004, 0, 0);
    }

    public void initialize(){
        m_drive.arcadeDrive(0, 0);
        m_drive.resetEncoders();
        m_drive.setBrake();
        m_PID.setSetpoint(0);
        m_PID.setTolerance(4);  // IN PIXELS
    }

    public void execute(){
        double adjustment = m_PID.calculate( m_vision.getBallX() - 0);
        if ((adjustment > 0) && (adjustment < 0.30)) {
            adjustment = 0.3;
        }
        if ((adjustment < 0) && (adjustment > -0.3)) {
            adjustment = -0.3;
        }
       m_drive.arcadeDrive( m_speed, adjustment);
    }

    public boolean isFinished(){
        if (m_detector.isBallPresent()){
            return true;
        }
        return false;
    }
    public void end(boolean interrupted){
        m_drive.arcadeDrive(0, 0);
        m_drive.setCoast();
    }
}

