package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class TurnUntilBallSeenCommand extends EntechCommandBase {
    private final DriveSubsystem m_drive;
    private PIDController m_PID;
    private VisionSubsystem m_vision;
    private double m_turnSpeed = 0.4;
    private int m_tolerance = 10;

    public TurnUntilBallSeenCommand(DriveSubsystem drive, VisionSubsystem vision){
        super(drive);
        m_drive = drive;
        m_vision = vision;
        m_PID = new PIDController(0.015, 0, 0);
    }
    
    public void initialize(){
        m_drive.arcadeDrive(0, 0);
        m_drive.resetEncoders();
        m_drive.setBrake();
        m_PID.setSetpoint(0);
        m_PID.setTolerance(m_tolerance);
    }

    public void execute(){
        m_drive.arcadeDrive( 0, m_turnSpeed);
    }

    public boolean isFinished(){
        if ((Math.abs(m_vision.getBallX()) > 0) && (Math.abs(m_vision.getBallX()) <= 5)) {
            return true;
        }
        return false;
    }
    public void end(boolean interrupted){
        m_drive.arcadeDrive(0, 0);
        m_drive.setCoast();
    }
}