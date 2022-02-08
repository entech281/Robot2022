package frc.robot;

import java.util.List;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.commands.ArcadeDriveCommand;
import frc.robot.commands.CommandFactory;
import frc.robot.commands.DriveRamseteCommand;
import frc.robot.commands.ScaledDriveCommand;
import frc.robot.subsystems.DriveSubsystem;
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

        // joystickManager.addButton(RobotConstants.DRIVER_STICK.TURN_LEFT90)
        //        .whenPressed(commandFactory.snapToYawCommand(-90.0))
        //        .add();

        // joystickManager.addButton(RobotConstants.DRIVER_STICK.TURN_RIGHT90)
        //        .whenPressed(commandFactory.snapToYawCommand( 90.0))
        //        .add();

        subsystemManager.getDriveSubsystem().setDefaultCommand (new ScaledDriveCommand(subsystemManager.getDriveSubsystem(), driveStick));
        


        joystickManager.addButton(1).whenPressed( new DriveRamseteCommand(subsystemManager.getDriveSubsystem(), 
        TrajectoryGenerator.generateTrajectory(
        subsystemManager.getDriveSubsystem().theBeginning,
        List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
        subsystemManager.getDriveSubsystem().theEnd,
        new TrajectoryConfig(Units.feetToMeters(3.0), Units.feetToMeters(3.0))))).add();
        
    }

}
