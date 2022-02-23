package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

public class ColorSensorSubsystem extends EntechSubsystem{
    private ColorSensorV3  colorSensor;
    private final static String blue = "BLUE";
    private final static String red = "RED";
    private final static String none = "IDK bro";
    private final static int proximityThreshold = 420;

    @Override
    public void initialize() {
        colorSensor = new ColorSensorV3(I2C.Port.kMXP);
    }

    @Override
    public void periodic() {
        Color detectedColor = colorSensor.getColor();

        logger.log("red value",   detectedColor.red);
        logger.log("green value", detectedColor.green);
        logger.log("blue value",  detectedColor.blue);
        logger.log("proximity",   colorSensor.getProximity());
        logger.log("ball isPresent", isBallPresent());
        logger.log("ball isRed",    isBallRed());
        logger.log("ball isBlue",   isBallBlue());
    }

    public String getSensorColor() {
        String ballColor;

        double r = colorSensor.getRed();
        double b = colorSensor.getBlue();

        if (isBallPresent()) {
            if ( b > r) {
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

    public boolean isBallPresent() {
        return (colorSensor.getProximity() > proximityThreshold);
    }

    public boolean isBallRed() {
        return (colorSensor.getRed() > colorSensor.getBlue());
    }

    public boolean isBallBlue() {
        return (colorSensor.getBlue() > colorSensor.getRed());
    }
}
