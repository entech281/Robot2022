package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
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
    private final Color kBlue  = new Color(0.143, 0.427, 0.429);
    private final Color kRed = new Color(0.561, 0.232, 0.114);


     @Override
     public void initialize() {
       colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
       colorMatcher = new ColorMatch();
       colorMatcher.addColorMatch(kBlue);
          colorMatcher.addColorMatch(kRed);
     }


    public String getSensorColor() {
      
      String ballColor;

      Color detectedColor = colorSensor.getColor();


      ColorMatchResult match =  colorMatcher.matchClosestColor(detectedColor);

      if (match.color == Color.kBlue) {
          ballColor = blue;
      } else if (match.color == Color.kRed) {
          ballColor = red;
      } else {
        ballColor = none;
      }

      
   return ballColor;
      

      
  }

  @Override
  public void periodic() {

      String ball = getSensorColor();


      logger.log("ball color", ball);

      }


    
}