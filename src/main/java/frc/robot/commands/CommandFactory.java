package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
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
    public Command getShootBallCommand(){
        return new SequentialCommandGroup(
            getBeltOutCommand(),
            new WaitCommand(0.1),
            getBeltInCommand(),
            new WaitCommand(1.25),
            getBeltStopCommand()
        );
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
    public Command getIntakeArmDownCommand(){
        return new IntakeArmDownCommand(sm.getIntakeSubsystem());
    }
    public Command getIntakeArmUpCommand(){
        return new IntakeArmUpCommand(sm.getIntakeSubsystem());
    }
    public Command getIntakeArmNudgeDownCommand(){
        return new IntakeArmNudgeDownCommand(sm.getIntakeSubsystem());
    }
    public Command getIntakeArmNudgeUpCommand(){
        return new IntakeArmNudgeUpCommand(sm.getIntakeSubsystem());
    }
    public Command getIntakeDeployCommand() {
        return new ParallelCommandGroup(
            getAutoBeltCommand(),
            getIntakeInCommand(),
            getIntakeArmDownCommand()
        );
    }
    public Command getIntakeRetractCommand() {
        return new ParallelCommandGroup(
            getBeltStopCommand(),
            getIntakeStopCommand(),
            getIntakeArmUpCommand()
        );
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
    public Command getDriveUntilVisionBallPickupCommand(){
        return new DriveUntilVisionBallPickUpCommand(sm.getDriveSubsystem(), sm.getVisionSubsytem(), sm.getColorSensorSubsystem(), -0.5);
    }
    public Command getResetGyroCommand() {
        return new InstantCommand(() -> sm.getDriveSubsystem().resetGyro(), sm.getDriveSubsystem());
    }
    public Command getTurnUntillBallSeenCommand() {
        return new TurnUntilBallSeenCommand(sm.getDriveSubsystem(),sm.getVisionSubsytem());
    }

    public Command getAutonomousCommand(int choice){
        if (choice == 1){
            return getAutonomousCommand1();
        }
        if (choice == 2){
            return getAutonomousCommand2();
        }
        return getAutonomousCommand0();
    }
    public Command getAutonomousCommand0(){
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
    public Command getAutonomousCommand1(){
        return new SequentialCommandGroup(
            getAutonomousCommand0(),
            getTurnUntillBallSeenCommand(),
            getIntakeInCommand(),
            getBeltInCommand(),
            getDriveUntilBallPickUpCommand()
        );
    }
    public Command getAutonomousCommand2(){
        return getAutonomousCommand1();
    }
}
