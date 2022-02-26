package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation; 

public class VisionSubsystem extends EntechSubsystem {
    
    private NetworkTable table;
    private static NetworkTableEntry tableEntryLowerBound;
    private static NetworkTableEntry tableEntryUpperBound;
    private int counter;
    private int x_pos;
    private int y_pos;
    private Boolean isBallFound;

    public VisionSubsystem() {

    }

    @Override
    public void initialize(){
        table = NetworkTableInstance.getDefault().getTable("Vision");
        tableEntryLowerBound = table.getEntry("HSVValuesLowerBound");
        tableEntryUpperBound = table.getEntry("HSVValuesUpperBound");
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
            Number[] arrUpperBound = {120, 255, 255};
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

        publishHSV();
        counter = table.getEntry("Counter").getNumber(0.0).intValue();
        x_pos = table.getEntry("x").getNumber(0).intValue();
        y_pos = table.getEntry("y").getNumber(0).intValue();
        isBallFound = table.getEntry("isBallFound").getBoolean(false);

        logger.log("Counter", counter);
        logger.log("Ball Lateral Offset", x_pos);
        logger.log("Ball Vertical Offset", y_pos);
        logger.log("Vision Ball Found?", isBallFound);        
        
    }

    public Boolean isBallFound() {
        return isBallFound;
    }

    public int getBallX() {
        return x_pos;
    }

    public int getBallY() {
        return y_pos;
    }
}
