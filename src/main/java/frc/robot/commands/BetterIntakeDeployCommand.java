package frc.robot.commands;
import frc.robot.subsystems.ColorSensorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.BeltSubsystem;

public class BetterIntakeDeployCommand extends EntechCommandBase{
    private final ColorSensorSubsystem m_color;
    private final BeltSubsystem m_belt;
    private IntakeSubsystem m_intake;

    public BetterIntakeDeployCommand(IntakeSubsystem intake, BeltSubsystem belt, ColorSensorSubsystem color){
        super(color, belt, intake);
        m_color = color;
        m_belt = belt;
        m_intake = intake;
    }

    @Override
    public void initialize(){
        m_intake.armsDown();
        m_intake.rollersIn();
        m_belt.intake();
    }
   
    @Override
    public void execute(){        
    }
    

    public boolean isFinished(){        
        if (m_color.isBallPresent()){
            return true;
        }
        return false;
        
    }
    @Override
    public void end(boolean interrupted) {
        m_belt.stop();
        m_intake.rollersStop();
        m_intake.armsUp();
    }
    
}
