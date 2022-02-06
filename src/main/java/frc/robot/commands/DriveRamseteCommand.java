/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;


import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import frc.robot.RobotConstants;
import frc.robot.subsystems.DriveSubsystem;


public class DriveRamseteCommand extends EntechCommandBase{

    public static final double TOLERANCE_INCHES = 0.5;
    
    private DriveSubsystem drive;
    private Pose2d targetPosition;
    private Pose2d currentPosition;
    private Trajectory trajectory;
    private Transform2d difference;
    private double differenceX;
    private double differenceY;
    private double differenceTheta;
    boolean closeEnough;


    
    public DriveRamseteCommand(DriveSubsystem drive,  Trajectory trajectory){
        super(drive);
        this.drive = drive;
        this.trajectory = trajectory;
    }
    

    
   @Override
    public void initialize(){
        drive.getTrajectory();
 
    }
    
    @Override
    public void execute() {
        drive.getRamseteCommand(trajectory).execute();;
        logger.log("Current Position", drive.getPose());
        logger.log("Target Position", drive.theEnd);
        drive.feedWatchDog();
    }
    
    @Override
    public boolean isFinished(){
        
        difference = drive.theEnd.minus(drive.getPose());
        differenceX = difference.getX();
        differenceY = difference.getY();
        differenceTheta = difference.getRotation().getDegrees();
        
        
        if (differenceX <= RobotConstants.DRIVETRAIN_CONSTANTS.ramseteToleranceInches && differenceY <= RobotConstants.DRIVETRAIN_CONSTANTS.ramseteToleranceInches && differenceTheta <= RobotConstants.DRIVETRAIN_CONSTANTS.ramseteToleranceDegrees) {
            closeEnough = true;
        } else {
            closeEnough = false;
        }

        return closeEnough;
    }

    
}