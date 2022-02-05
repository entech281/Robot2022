package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SubsystemManager;
import frc.robot.subsystems.DriveSubsystem;
/**
 *
 * @author dcowden
 */
public class CommandFactory {

    private final SubsystemManager sm;
    public CommandFactory(SubsystemManager subsystemManager){
        this.sm = subsystemManager;
    }

    public Command getDriveStraigtCommand(double distance,double speed){
        return new DriveStraightCommand(sm.getDriveSubsystem(), distance, speed);
    }

    public Command getAutonomousCommand(){
        return null;
    }
}
