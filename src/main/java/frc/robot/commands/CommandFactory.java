package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
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
    public Command getTurnToGyroAngleCommand(double angle){
        return new TurnToGyroAngleCommand(sm.getDriveSubsystem(), angle);
    }

    public Command getBeltInCommand(){
        return new BeltInCommand(sm.getBeltSubsystem());
    }
    public Command getBeltOutCommand(){
        return new BeltOutCommand(sm.getBeltSubsystem());
    }
    public Command getBeltStopCommand(){
        return new BeltStopCommand(sm.getBeltSubsystem());
    }
    public Command getAutoBeltCommand(){
        return new AutoBeltCommand(sm.getBeltSubsystem(),sm.getColorSensorSubsystem());
    }

    public Command getIntakeInCommand(){
        return new IntakeInCommand(sm.getIntakeSubsystem());
    }
    public Command getIntakeOutCommand(){
        return new IntakeOutCommand(sm.getIntakeSubsystem());
    }
    public Command getIntakeStopCommand(){
        return new IntakeStopCommand(sm.getIntakeSubsystem());
    }

    public Command getHookUpCommand(){
        return new HookUpCommand(sm.getHookSubsystem());
    }
    public Command getHookDownCommand(){
        return new HookDownCommand(sm.getHookSubsystem());
    }
    public Command getHookStopCommand(){
        return new HookStopCommand(sm.getHookSubsystem());
    }

    public Command getVisionDriveCommand(){
        return new VisionDriveCommand(sm.getDriveSubsystem(), sm.getVisionSubsytem());
    }
    public Command getDriveStraightVisionCommand(){
        return new DriveStraightVisionCommand(sm.getDriveSubsystem(), sm.getVisionSubsytem(), 24, 0.5);
    }
    public Command getDriveUntilBallPickUpCommand() {
        return new DriveUntilBallPickUpCommand(sm.getDriveSubsystem(), sm.getVisionSubsytem(), sm.getColorSensorSubsystem(), -0.5);
    }
    public Command getDriveUntilEncoderZeroCommand() {
        return new DriveUntilEncoderZeroCommand(sm.getDriveSubsystem(), 0.5);
    }
    public Command getResetGyroCommand() {
        return new InstantCommand(() -> sm.getDriveSubsystem().resetGyro(), sm.getDriveSubsystem());
    }
    public Command getAutonomousCommand(){
        return getAutonomousCommand1();
    }
    public Command getShootBallCommand(){
        return new SequentialCommandGroup(
            getBeltInCommand(),
            new WaitCommand(1.0),
            getBeltStopCommand()
        );
    }

    public Command getAutonomousCommand1(){
        return new SequentialCommandGroup(
            getResetGyroCommand(),
            getShootBallCommand(),
            getTurnByAngleCommand(165),
            getIntakeInCommand(),
            getBeltInCommand(),
            getDriveUntilBallPickUpCommand(),
            getIntakeStopCommand(),
            getBeltStopCommand(),
            getDriveUntilEncoderZeroCommand(),
            getTurnToGyroAngleCommand(0),
            getShootBallCommand()
        );
    }
}
