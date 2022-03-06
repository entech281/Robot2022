package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.BetterArcadeDriveCommand;
import frc.robot.commands.CommandFactory;
import frc.robot.subsystems.SubsystemManager;

public class OperatorInterface {

    private Joystick driveJoystick;
    private JoystickButtonManager driverJoystickManager;
    private Joystick operatorJoystick;
    private JoystickButtonManager operatorJoystickManager;
    private SubsystemManager sm;
    private CommandFactory commandFactory;

    public OperatorInterface(final SubsystemManager subMan, final CommandFactory cf) {
        this.sm = subMan;
        this.commandFactory = cf;
        this.driveJoystick = new Joystick(RobotConstants.JOYSTICKS.DRIVER_JOYSTICK);
        this.driverJoystickManager = new JoystickButtonManager(driveJoystick);
        this.operatorJoystick = new Joystick(RobotConstants.JOYSTICKS.OPERATOR_JOYSTICK);
        this.operatorJoystickManager = new JoystickButtonManager(operatorJoystick);

        driverJoystickManager.addButton(RobotConstants.DRIVER_STICK.DRIVESTRAIGHT)
                .whenPressed(commandFactory.getDriveStraightCommand(48, 0.5))
                .add();

        driverJoystickManager.addButton(RobotConstants.DRIVER_STICK.TURNBYANGLE)
                .whenPressed(commandFactory.getTurnByAngleCommand(90))
                .add();

        driverJoystickManager.addButton(RobotConstants.DRIVER_STICK.BELTIN)
                .whenPressed(commandFactory.getAutoBeltCommand())
                .whenReleased(commandFactory.getBeltStopCommand())
                .add();
        driverJoystickManager.addButton(RobotConstants.DRIVER_STICK.BELTOUT)
                .whenPressed(commandFactory.getBeltOutCommand())
                .whenReleased(commandFactory.getBeltStopCommand())
                .add();        
        driverJoystickManager.addButton(RobotConstants.DRIVER_STICK.INTAKEIN)
                .whenPressed(commandFactory.getIntakeInCommand())
                .whenReleased(commandFactory.getIntakeStopCommand())
                .add();
        driverJoystickManager.addButton(RobotConstants.DRIVER_STICK.INTAKEOUT)
                .whenPressed(commandFactory.getIntakeOutCommand())
                .whenReleased(commandFactory.getIntakeStopCommand())
                .add();
        driverJoystickManager.addButton(RobotConstants.DRIVER_STICK.HOOKUP)
                .whenPressed(commandFactory.getHookUpCommand())
                .whenReleased(commandFactory.getHookStopCommand())
                .add();
        driverJoystickManager.addButton(RobotConstants.DRIVER_STICK.HOOKDOWN)
                .whenPressed(commandFactory.getHookDownCommand())
                .whenReleased(commandFactory.getHookStopCommand())
                .add();                
        operatorJoystickManager.addButton(RobotConstants.OPERATOR_STICK.BELT_IN)
                .whenPressed(commandFactory.getAutoBeltCommand())
                .whenReleased(commandFactory.getBeltStopCommand())
                .add();
        operatorJoystickManager.addButton(RobotConstants.OPERATOR_STICK.BELT_OUT)
                .whenPressed(commandFactory.getBeltOutCommand())
                .whenReleased(commandFactory.getBeltStopCommand())
                .add();        
        operatorJoystickManager.addButton(RobotConstants.OPERATOR_STICK.HOOK_UP)
                .whenPressed(commandFactory.getHookUpCommand())
                .whenReleased(commandFactory.getHookStopCommand())
                .add();
        operatorJoystickManager.addButton(RobotConstants.OPERATOR_STICK.HOOK_DOWN)
                .whenPressed(commandFactory.getHookDownCommand())
                .whenReleased(commandFactory.getHookStopCommand())
                .add();
        driverJoystickManager.addButton(RobotConstants.DRIVER_STICK.TURNUNTILLBALLSEEN)
                .whenPressed(commandFactory.getTurnUntillBallSeenCommand())
                .add();
        sm.getDriveSubsystem().setDefaultCommand ( new BetterArcadeDriveCommand(sm.getDriveSubsystem(), sm.getVisionSubsytem(), driveJoystick) );
    }

}
