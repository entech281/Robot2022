package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import com.ctre.phoenix.Logger;
import com.revrobotics.CIEColor;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

public class ColorSensorSubsystem extends EntechSubsystem{
    private ColorSensorV3  colorSensor; // = new ColorSensorV3(i2cPort);
    private final static String blue = "BLUE";
    private final static String red = "RED";
    private final static String none = "IDK bro";
    private final static double colorConfidence = .4;

    private Color detectedColor;
    public ColorSensorSubsystem() {

    }

    @Override
    public void initialize() {
        colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    }

    @Override
    public void periodic(){
        String c = getSensorColor();
    
        // logger.log("red value", detectedColor.red);
        // logger.log("green value", detectedColor.green);
        // logger.log("blue value", detectedColor.blue);
        // logger.log("proximity", colorSensor.getProximity());
        // logger.log("getSensorColor", c);
    }

    public String getSensorColor() {
        String ballColor;

        double r = colorSensor.getRed();
        double b = colorSensor.getBlue();
        double p = colorSensor.getBlue();

        if (p > 420) {
            if ( b > r){ 
                ballColor = blue;
            } else if (r > b){
                ballColor = red;
            } else{
                ballColor = none;
            }
        } else {
            ballColor = none;
        }

        return ballColor;

        }
}