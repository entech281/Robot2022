package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.ArcadeDriveCommand;
import frc.robot.commands.CommandFactory;
import frc.robot.subsystems.SubsystemManager;

public class OperatorInterface {

    private Joystick driveStick;
    private JoystickButtonManager joystickManager;
    private SubsystemManager subsystemManager;
    private CommandFactory commandFactory;

    public OperatorInterface(final SubsystemManager subMan, final CommandFactory cf) {
        this.subsystemManager = subMan;
        this.commandFactory = cf;
        this.driveStick = new Joystick(RobotConstants.JOYSTICKS.DRIVER_JOYSTICK);
        this.joystickManager = new JoystickButtonManager(driveStick);

        joystickManager.addButton(RobotConstants.DRIVER_STICK.DRIVESTRAIGHT)
                .whenPressed(commandFactory.getDriveStraightCommand(48, 0.5))
                .add();

        joystickManager.addButton(RobotConstants.DRIVER_STICK.DRIVESTRAIGHTGYRO)
                .whenPressed(commandFactory.getDriveStraightGyroCommand(60, 0.5))
                .add();
        joystickManager.addButton(RobotConstants.DRIVER_STICK.TURNBYANGLE)
                .whenPressed(commandFactory.getTurnByAngleCommand(90))
                .add();
        joystickManager.addButton(RobotConstants.DRIVER_STICK.BELTIN)
                .whenPressed(commandFactory.getbeltInCommand())
                .whenReleased(commandFactory.getbeltStopCommand())
                .add();
        joystickManager.addButton(RobotConstants.DRIVER_STICK.BELTOUT)
                .whenPressed(commandFactory.getbeltOutCommand())
                .whenReleased(commandFactory.getbeltStopCommand())
                .add();        
        subsystemManager.getDriveSubsystem().setDefaultCommand ( new ArcadeDriveCommand(subsystemManager.getDriveSubsystem(), driveStick) );
    }

}
