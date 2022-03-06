package frc.robot.controllers;

public abstract class BaseController {

    private  boolean reversed = false;
    public BaseController(boolean reversed){
        this.reversed = reversed;
    }
    public boolean isReversed(){
        return reversed;
    }

    protected double correctDirection(double input){
        if ( reversed ){
            return -input;
        }
        else{
            return input;
        }
    }
}