// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.RobotConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Joystick;

public class BetterArcadeDriveCommand extends EntechCommandBase {
    private final DriveSubsystem m_drive;
    private final VisionSubsystem m_vision;
    private final Joystick m_stick;
    private final PIDController m_PID;

    /**
     * Creates a new ArcadeDrive. This command will drive your robot according to the joystick
     * This command does not terminate.
     *
     * @param drive The drive subsystem on which this command will run
     * @param stick Driver joystick object
     */
    public BetterArcadeDriveCommand(DriveSubsystem drive, VisionSubsystem vision, Joystick stick) {
        super(drive, vision);
        m_drive = drive;
        m_vision = vision;
        m_stick = stick;
        m_PID = new PIDController(-0.004, 0, 0);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_PID.setSetpoint(0);
        m_PID.setTolerance(3);
    }
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double adjustment = -m_stick.getX();
        if ((m_stick.getRawButton(RobotConstants.DRIVER_STICK.VISIONDRIVE)) &&
            (m_vision.isBallFound())) {
            adjustment = m_PID.calculate( m_vision.getBallX() - 0);
            if ((adjustment > 0) && (adjustment < 0.25)) {
                adjustment = 0.25;
            }
            if ((adjustment < 0) && (adjustment > -0.25)) {
                adjustment = -0.25;
            }
        }
        m_drive.arcadeDrive(m_stick.getY(), adjustment);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_drive.setCoast();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    // Returns true if command should run when robot is disabled.
    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
