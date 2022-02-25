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
        return new beltInCommand(sm.getBeltSubsystem());
    }
    public Command getbeltStopCommand(){
        return new beltInCommand(sm.getBeltSubsystem());
    }
    public Command getAutonomousCommand(){
        return getAutonomousCommand1();
    }
    public Command getAutonomousCommand1(){
        return new SequentialCommandGroup(
            getDriveStraightGyroCommand(48, 0.5),
            getTurnByAngleCommand(180),
            getDriveStraightGyroCommand(48, 0.5),
            getbeltInCommand(),//.withTimeout(10),
            new WaitCommand(5),
            getDriveStraightGyroCommand(36, 0.5)
        );
    }
}
