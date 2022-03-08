package frc.robot.commands;

import frc.robot.subsystems.IntakeSubsystem;

public class IntakeArmNudgeUpCommand extends EntechCommandBase {

    private final IntakeSubsystem m_intake;

    public IntakeArmNudgeUpCommand(IntakeSubsystem intake){
        super(intake);
        m_intake = intake;

    }

    @Override
    public void initialize(){

    }

    @Override 
    public void execute(){
        m_intake.nudgeArmUp();
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
