package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import com.revrobotics.CIEColor;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.DriverStation;


public class ColorSensor extends EntechSubsystem{
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private ColorSensorV3  colorSensor; // = new ColorSensorV3(i2cPort);
    private ColorMatch  colorMatcher; // = new ColorMatch();
    private final static String blue = "BLUE";
    private final static String red = "RED";
    private final static String none = "IDK bro";
    private final static double colorConfidence = .4;

    private Color detectedColor;


     @Override
     public void initialize() {
       colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
     }

     @Override
     public void periodic() {

    String c = getSensorColor();

      
         logger.log("red value", detectedColor.red);
         logger.log("green value", detectedColor.green);
         logger.log("blue value", detectedColor.blue);
         logger.log("proximity", colorSensor.getProximity());
         logger.log("getSensorColor", c);

        
         }


        public String getSensorColor() {
          
        String ballColor;

        detectedColor = colorSensor.getColor();
        double r = colorSensor.getRed();
        double b = colorSensor.getBlue();
        double p = colorSensor.getBlue();

      if (p > 420) {
         
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




    
}