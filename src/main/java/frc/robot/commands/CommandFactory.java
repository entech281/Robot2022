package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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
        return new beltOutCommand(sm.getBeltSubsystem());
    }
    public Command getbeltStopCommand(){
        return new beltStopCommand(sm.getBeltSubsystem());
    }
    public Command getHookUpCommand(){
        return new HookUpCommand(sm.getHookSubsystem());
    }
    public Command getHookDownCommand(){
        return new HookDownCommand(sm.getHookSubsystem());
    }
    public Command getVisionDriveCommand(){
        return new visionDriveCommand(sm.getDriveSubsystem(), sm.getVisionSubsytem());
    }
    public Command getDriveStraightVisionCommand(){
        return new DriveStraightVisionCommand(sm.getDriveSubsystem(), sm.getVisionSubsytem(), 24, 0.5);
    }
    public Command getDriveTillBallPickUpCommand() {
        return new DriveTillBallPickUpCommand(sm.getDriveSubsystem(), sm.getVisionSubsytem(), sm.getColorSensorSubsystem(), -0.5);
    }
    public Command getAutonomousCommand(){
        //return getDriveTillBallPickUpCommand();
        return getAutonomousCommand1(); 
    }
    public Command getAutonomousCommand1(){
        return new SequentialCommandGroup(
            getbeltInCommand(),
            new WaitCommand(1.0),
            getbeltStopCommand(),
            getTurnByAngleCommand(180),
            getDriveTillBallPickUpCommand(),
            getbeltInCommand(),
            new WaitCommand(5),
            getbeltStopCommand());
    }
}
