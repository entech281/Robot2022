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

    public Command getbeltInCommand(){
        return new beltInCommand(sm.getBeltSubsystem());
    }
    public Command getbeltOutCommand(){
        return new beltInCommand(sm.getBeltSubsystem());
    }
    public Command getbeltStopCommand(){
        return new beltInCommand(sm.getBeltSubsystem());
    }

    public Command getHookUpCommand(){
        return new HookUpCommand(sm.getHookSubsystem());
    }
    public Command getHookDownCommand(){
        return new HookDownCommand(sm.getHookSubsystem());
    }

    public Command getAutonomousCommand(){
        return null;
    }
}
