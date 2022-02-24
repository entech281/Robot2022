package frc.robot.subsystems;

// Exercise 2: Add the OnboardIO subsystem to the subsystem manager.
// This involves storing the subsystem privately in this class, initializing it, and providing a get method.
// You can use the drive subsystem as an example.

public class SubsystemManager {
    private DriveSubsystem driveSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private BeltSubsystem beltSubsystem;
    private ColorSensorSubsystem colorSensorSubsystem;
    private HookSubsystem hookSubsystem;
    private VisionSubsystem visionSubsystem;

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
    
    public BeltSubsystem getBeltSubsystem(){
        return beltSubsystem;
    }

    public VisionSubsystem getVisionSubsytem(){
        return visionSubsystem;
    }


    public void initAll() {
        driveSubsystem = new DriveSubsystem();
        intakeSubsystem = new IntakeSubsystem();
        beltSubsystem = new BeltSubsystem();
        colorSensorSubsystem = new ColorSensorSubsystem();
        hookSubsystem = new HookSubsystem();
        visionSubsystem = new VisionSubsystem();

        driveSubsystem.initialize();
        intakeSubsystem.initialize();
        beltSubsystem.initialize();
        colorSensorSubsystem.initialize();
        hookSubsystem.initialize();
        visionSubsystem.initialize();
    }
}
