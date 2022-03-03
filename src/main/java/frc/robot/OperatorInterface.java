package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.BetterArcadeDriveCommand;
import frc.robot.commands.CommandFactory;
import frc.robot.subsystems.SubsystemManager;

public class OperatorInterface {

    private Joystick driveStick;
    private JoystickButtonManager driverJoystickManager;
    private Joystick operatorJoystick;
    private SubsystemManager sm;
    private CommandFactory commandFactory;

    public OperatorInterface(final SubsystemManager subMan, final CommandFactory cf) {
        this.sm = subMan;
        this.commandFactory = cf;
        this.driveStick = new Joystick(RobotConstants.JOYSTICKS.DRIVER_JOYSTICK);
        this.driverJoystickManager = new JoystickButtonManager(driveStick);
        this.operatorJoystick = new Joystick(RobotConstants.JOYSTICKS.OPERATOR_JOYSTICK);

        driverJoystickManager.addButton(RobotConstants.DRIVER_STICK.DRIVESTRAIGHT)
                .whenPressed(commandFactory.getDriveStraightCommand(48, 0.5))
                .add();

        driverJoystickManager.addButton(RobotConstants.DRIVER_STICK.DRIVESTRAIGHTGYRO)
                .whenPressed(commandFactory.getDriveStraightGyroCommand(60, 0.5))
                .add();

        driverJoystickManager.addButton(RobotConstants.DRIVER_STICK.TURNBYANGLE)
                .whenPressed(commandFactory.getTurnByAngleCommand(90))
                .add();


        driverJoystickManager.addButton(RobotConstants.DRIVER_STICK.BELTIN)
                .whenPressed(commandFactory.getbeltInCommand())
                .whenReleased(commandFactory.getbeltStopCommand())
                .add();
        driverJoystickManager.addButton(RobotConstants.DRIVER_STICK.BELTOUT)
                .whenPressed(commandFactory.getbeltOutCommand())
                .whenReleased(commandFactory.getbeltStopCommand())
                .add();        
        driverJoystickManager.addButton(RobotConstants.DRIVER_STICK.HOOKUP)
                .whenPressed(commandFactory.getHookUpCommand())
                .add();
        driverJoystickManager.addButton(RobotConstants.DRIVER_STICK.HOOKDOWN)
                .whenPressed(commandFactory.getHookDownCommand())
                .add();                
        driverJoystickManager.addButton(RobotConstants.DRIVER_STICK.DRIVESTRAIGHTVISION)
                .whenPressed(commandFactory.getDriveStraightVisionCommand())
                .add();

        sm.getDriveSubsystem().setDefaultCommand ( new BetterArcadeDriveCommand(sm.getDriveSubsystem(), sm.getVisionSubsytem(), driveStick) );
    }

}
