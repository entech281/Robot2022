package frc.robot.subsystems;

// Exercise 2: Add the OnboardIO subsystem to the subsystem manager.
// This involves storing the subsystem privately in this class, initializing it, and providing a get method.
// You can use the drive subsystem as an example.

public class SubsystemManager {
    private DriveSubsystem driveSubsystem;
    private ColorSensorSubsystem colorSensorSubsystem;
    private HookSubsystem hookSubsystem;
    private IntakeSubsystem intakeSubsystem;

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

    public IntakeSubsystem getIntakeSubsystem() {
        return intakeSubsystem;
    }

    public void initAll() {
        driveSubsystem = new DriveSubsystem();
        colorSensorSubsystem = new ColorSensorSubsystem();
        hookSubsystem = new HookSubsystem();
        intakeSubsystem = new IntakeSubsystem();

        driveSubsystem.initialize();
        colorSensorSubsystem.initialize();
        hookSubsystem.initialize();
        intakeSubsystem.initialize();
    }
}
