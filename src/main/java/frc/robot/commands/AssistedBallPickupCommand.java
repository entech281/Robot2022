package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;

public class AssistedBallPickupCommand extends EntechCommandBase {
    private final DriveSubsystem m_drive;
    private final double m_distance;
    private final double m_speed;
    private final double m_degrees;
    private double Edistance;

    public AssistedBallPickupCommand(DriveSubsystem drive, double inches, double speed, double degrees){
        super(drive);
        m_distance = inches;
        m_speed = speed;
        m_drive = drive;
        m_degrees = degrees;
        addRequirements(drive);
    }

    public void initialize(){
        m_drive.arcadeDrive(0, 0);
        m_drive.resetEncoders();
    }

    public void execute(){
        double leftEncoder = m_drive.getLeftDistance();
        double rightEncoder = m_drive.getRightDistance();
        double adjustment = 0.025 * ( rightEncoder - leftEncoder);
        m_drive.arcadeDrive(m_speed, adjustment);
        Edistance = (leftEncoder + rightEncoder) / 2;

    }

    public boolean isFinished(){
        if (Edistance > m_distance) {
            return true;
        }
        return false;
    }
}

