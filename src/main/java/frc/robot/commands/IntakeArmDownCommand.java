package frc.robot.commands;

import frc.robot.subsystems.IntakeSubsystem;

public class IntakeArmDownCommand extends EntechCommandBase{

    private final IntakeSubsystem m_intake;
    
    public IntakeArmDownCommand(IntakeSubsystem intakeSubsystem){
        super(intakeSubsystem);
        m_intake = intakeSubsystem;
    }

    @Override
    public void initialize(){

    }

    @Override 
    public void execute(){
        m_intake.armsDown();
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return true;
    }

    @Override
    public boolean runsWhenDisabled(){
        return false;
    }
}

