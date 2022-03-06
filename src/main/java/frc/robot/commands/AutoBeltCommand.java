package frc.robot.commands;
import frc.robot.subsystems.ColorSensorSubsystem;
import frc.robot.subsystems.BeltSubsystem;

public class AutoBeltCommand extends EntechCommandBase{
    private final ColorSensorSubsystem m_color;
    private final BeltSubsystem m_belt;
    private Boolean wasBallPresent;

    public AutoBeltCommand(BeltSubsystem belt, ColorSensorSubsystem color){
        super(color, belt);
        m_color = color;
        m_belt = belt;
        wasBallPresent = false;

    }

    @Override
    public void initialize(){
        if (m_color.isBallPresent()){
            wasBallPresent = true;
        }
        m_belt.intake();
    }

    public boolean isFinished(){
        if (wasBallPresent){
            return true;
        }
        
        if (m_color.isBallPresent()){
            m_belt.stop();
            return true;
        }
        return false;
        
    }
   
    @Override
    public void execute(){        
    }
    
}
