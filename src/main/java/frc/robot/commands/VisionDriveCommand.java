package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;


public class VisionDriveCommand extends EntechCommandBase {
    private final DriveSubsystem m_drive;
    private final VisionSubsystem m_vision;
    private PIDController m_PID;

    public VisionDriveCommand(DriveSubsystem drive, VisionSubsystem vision){
        super(drive, vision);
        m_vision = vision;
        m_drive = drive;
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
        if ((adjustment > 0) && (adjustment < 0.25)) {
            adjustment = 0.25;
        }
        if ((adjustment < 0) && (adjustment > -0.25)) {
            adjustment = -0.25;
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