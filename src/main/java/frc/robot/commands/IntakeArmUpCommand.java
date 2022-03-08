package frc.robot.commands;

import frc.robot.subsystems.IntakeSubsystem;

public class IntakeArmUpCommand extends EntechCommandBase {
    private final IntakeSubsystem m_intake;

    public IntakeArmUpCommand(IntakeSubsystem intake){
        super(intake);
        m_intake = intake;
    }

    @Override
    public void initialize(){

    }

    @Override 
    public void execute(){
        m_intake.armsUp();
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





