package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class VisionSubsystem extends EntechSubsystem {
    
    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("Vision");
    public static NetworkTableEntry tableEntryLowerBound = table.getEntry("HSVValuesLowerBound");
    public static NetworkTableEntry tableEntryUpperBound = table.getEntry("HSVValuesUpperBound");

    public VisionSubsystem() {

    }

    @Override
    public void initialize(){

    }

    public static void publishHSV() {
    
        if (DriverStation.getAlliance() == DriverStation.Alliance.Red){
            Number[] arrLowerBound = {160, 100, 0};
            Number[] arrUpperBound = {180, 255, 255};
            tableEntryLowerBound.forceSetNumberArray(arrLowerBound);  
            tableEntryUpperBound.forceSetNumberArray(arrUpperBound);

        }
        if (DriverStation.getAlliance() == DriverStation.Alliance.Blue){
            Number[] arrLowerBound = {100, 100, 0};
            Number[] arrUpperBound = {130, 255, 255};
            tableEntryLowerBound.forceSetNumberArray(arrLowerBound);  
            tableEntryUpperBound.forceSetNumberArray(arrUpperBound);
        }
        if (DriverStation.getAlliance() == DriverStation.Alliance.Invalid){
            Number[] arrLowerBound = {0, 0, 0};
            Number[] arrUpperBound = {0, 0, 0};
            tableEntryLowerBound.forceSetNumberArray(arrLowerBound);  
            tableEntryUpperBound.forceSetNumberArray(arrUpperBound);            
        }

        
    }

    @Override
    public void periodic(){

        
    }

}
