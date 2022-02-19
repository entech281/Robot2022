package frc.robot.subsystems;

import edu.wpi.first.wpilibj.util.Color;

// Exercise 2: Add the OnboardIO subsystem to the subsystem manager.
// This involves storing the subsystem privately in this class, initializing it, and providing a get method.
// You can use the drive subsystem as an example.

public class SubsystemManager {
    private DriveSubsystem driveSubsystem;
    private ColorSensorSubsystem colorSensorSubsystem;
    private BeltSubsystem beltSubsystem;

    public SubsystemManager() {
    }

    public DriveSubsystem getDriveSubsystem() {
        return driveSubsystem;
    }

    public ColorSensorSubsystem getColorSensorSubsystem() {
        return colorSensorSubsystem;
    }

    public BeltSubsystem getBeltSubsystem(){
        return beltSubsystem;
    }


    public void initAll() {
        driveSubsystem = new DriveSubsystem();
        colorSensorSubsystem = new ColorSensorSubsystem();
        beltSubsystem = new BeltSubsystem();

        driveSubsystem.initialize();
        colorSensorSubsystem.initialize();
        beltSubsystem.initialize();
    }
}
