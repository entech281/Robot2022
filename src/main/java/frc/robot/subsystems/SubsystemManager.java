package frc.robot.subsystems;

// Exercise 2: Add the OnboardIO subsystem to the subsystem manager.
// This involves storing the subsystem privately in this class, initializing it, and providing a get method.
// You can use the drive subsystem as an example.

public class SubsystemManager {
    private DriveSubsystem driveSubsystem;
    private ColorSensorSubsystem colorSensorSubsystem;
    private HookSubsystem hookSubsystem;

    public SubsystemManager() {
    }

    public DriveSubsystem getDriveSubsystem() {
        return driveSubsystem;
    }

    public ColorSensorSubsystem getColorSensorSubsystem() {
        return colorSensorSubsystem;
    }

    public HookSubsystem getHookSubsystem() {
        return hookSubsystem;
    }

    public void initAll() {
        driveSubsystem = new DriveSubsystem();
        colorSensorSubsystem = new ColorSensorSubsystem();
        hookSubsystem = new HookSubsystem();

        driveSubsystem.initialize();
        colorSensorSubsystem.initialize();
        hookSubsystem.initialize();
    }
}
