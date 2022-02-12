package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SubsystemManager;
/**
 *
 * @author dcowden
 */
public class CommandFactory {

    private final SubsystemManager sm;
    public CommandFactory(SubsystemManager subsystemManager){
        this.sm = subsystemManager;
    }
    public Command getDriveStraightCommand(double distance, double speed){
        return new DriveStraightCommand(sm.getDriveSubsystem(), distance, speed);
    }
    public Command getDriveStraightGyroCommand(double distance, double speed){
        return new DriveStraightGyroCommand(sm.getDriveSubsystem(), distance, speed);
    }
    public Command getTurnByAngleCommand(double angle){
        return new TurnByAngleCommand(sm.getDriveSubsystem(), angle);
    }

    public Command getAutonomousCommand(){
        return null;
    }
}
