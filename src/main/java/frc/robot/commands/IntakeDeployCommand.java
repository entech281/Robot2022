package frc.robot.commands;

import frc.robot.subsystems.IntakeSubsystem;



public class IntakeDeployCommand extends EntechCommandBase{

    private final IntakeSubsystem m_intake;
    private final AutoBeltCommand autoBeltCommand;

    
    public IntakeDeployCommand(IntakeSubsystem intake, AutoBeltCommand ac){
        super(intake);

        m_intake = intake;
        autoBeltCommand = ac;
        
    }

    @Override
    public void initialize(){



    }

    @Override
    public boolean isFinished(){
        if (autoBeltCommand.isFinished()){
            m_intake.armsUp();
            m_intake.rollersStop();
            return true;
        }
        return false;
    }

    @Override
    public void execute(){
        
        m_intake.armsDown();
        m_intake.rollersIn();

        }

    }
    

