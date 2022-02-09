package frc.robot.subsystems;

import frc.robot.Robot;
import frc.robot.RobotConstants;
import frc.robot.subsystems.ColorSensor;

import java.util.ArrayList;
import java.util.List;

import com.kauailabs.navx.frc.*;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.apache.commons.math3.analysis.function.Sigmoid;
import org.apache.commons.math3.analysis.function.Tan;
import org.apache.commons.math3.analysis.function.Tanh;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import org.apache.commons.math3.*;

import java.io.*;
import java.io.IOException;




public class DriveSubsystem extends EntechSubsystem {



    private CANSparkMax frontLeftSpark;
    private CANSparkMax frontRightSpark;
    private CANSparkMax rearLeftSpark;
    private CANSparkMax rearRightSpark;

    private RelativeEncoder frontLeftEncoder;
    private RelativeEncoder frontRightEncoder;
    private RelativeEncoder rearLeftEncoder;
    private RelativeEncoder rearRightEncoder;

    private MotorControllerGroup leftMotorController;
    private MotorControllerGroup rightMotorController;
    public DifferentialDrive robotDrive;

    private  SparkMaxPIDController leftPID;
    private  SparkMaxPIDController rightPID;
    private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(RobotConstants.DRIVETRAIN_CONSTANTS.ksVolts,RobotConstants.DRIVETRAIN_CONSTANTS.kvVoltSecondsPerMeter,RobotConstants.DRIVETRAIN_CONSTANTS.kaVoltSecondsSquaredPerMeter);

    private AHRS navX = new AHRS(SPI.Port.kMXP);
    
    Pose2d pose = new Pose2d();
    DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(getHeading(), pose);
    RamseteController trajectoryController = new RamseteController(RobotConstants.DRIVETRAIN_CONSTANTS.kRamseteB, RobotConstants.DRIVETRAIN_CONSTANTS.kRamseteZeta);

    public  Trajectory trajectory;


    @Override
    public void initialize() {
        frontLeftSpark = new CANSparkMax(RobotConstants.CAN.FRONT_LEFT_MOTOR, MotorType.kBrushless);
        rearLeftSpark = new CANSparkMax(RobotConstants.CAN.REAR_LEFT_MOTOR, MotorType.kBrushless);
        rearLeftSpark.follow(frontLeftSpark);
        leftMotorController = new MotorControllerGroup(frontLeftSpark, rearLeftSpark);

        frontRightSpark = new CANSparkMax(RobotConstants.CAN.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
        rearRightSpark = new CANSparkMax(RobotConstants.CAN.REAR_RIGHT_MOTOR, MotorType.kBrushless);
        rearRightSpark.follow(frontRightSpark);
        rightMotorController = new MotorControllerGroup(frontRightSpark, rearRightSpark);

        frontLeftEncoder = frontLeftSpark.getEncoder();
        frontRightEncoder = frontRightSpark.getEncoder();
        rearLeftEncoder = rearLeftSpark.getEncoder();
        rearRightEncoder = rearRightSpark.getEncoder();

        leftPID = frontLeftSpark.getPIDController();
        rightPID = frontRightSpark.getPIDController();

        frontLeftEncoder.setVelocityConversionFactor(RobotConstants.DRIVETRAIN_CONSTANTS.velocityConversionFactor);
        frontRightEncoder.setVelocityConversionFactor(RobotConstants.DRIVETRAIN_CONSTANTS.velocityConversionFactor);
        rearLeftEncoder.setVelocityConversionFactor(RobotConstants.DRIVETRAIN_CONSTANTS.velocityConversionFactor);
        rearRightEncoder.setVelocityConversionFactor(RobotConstants.DRIVETRAIN_CONSTANTS.velocityConversionFactor);

        frontLeftEncoder.setPositionConversionFactor(RobotConstants.DRIVETRAIN_CONSTANTS.positionConversionFacotr);
        frontRightEncoder.setVelocityConversionFactor(RobotConstants.DRIVETRAIN_CONSTANTS.positionConversionFacotr);
        rearLeftEncoder.setVelocityConversionFactor(RobotConstants.DRIVETRAIN_CONSTANTS.positionConversionFacotr);
        rearRightEncoder.setVelocityConversionFactor(RobotConstants.DRIVETRAIN_CONSTANTS.positionConversionFacotr);


        leftPID.setP(RobotConstants.DRIVETRAIN_CONSTANTS.lkP);
        leftPID.setI(RobotConstants.DRIVETRAIN_CONSTANTS.lkI);
        leftPID.setD(RobotConstants.DRIVETRAIN_CONSTANTS.lkD);
        leftPID.setIZone(RobotConstants.DRIVETRAIN_CONSTANTS.lkIz);
        leftPID.setFF(RobotConstants.DRIVETRAIN_CONSTANTS.lkFF);
        leftPID.setOutputRange(RobotConstants.DRIVETRAIN_CONSTANTS.lkMinOutput, RobotConstants.DRIVETRAIN_CONSTANTS.lkMaxOutput);

        int smartMotionSlotLeft = 0;
        leftPID.setSmartMotionMaxVelocity(RobotConstants.DRIVETRAIN_CONSTANTS.lmaxVel, smartMotionSlotLeft);
        leftPID.setSmartMotionMinOutputVelocity(RobotConstants.DRIVETRAIN_CONSTANTS.lminVel, smartMotionSlotLeft);
        leftPID.setSmartMotionMaxAccel(RobotConstants.DRIVETRAIN_CONSTANTS.lmaxAcc, smartMotionSlotLeft);
        leftPID.setSmartMotionAllowedClosedLoopError(RobotConstants.DRIVETRAIN_CONSTANTS.lAllowedErr, smartMotionSlotLeft);

        leftPID.setP(RobotConstants.DRIVETRAIN_CONSTANTS.rkP);
        leftPID.setI(RobotConstants.DRIVETRAIN_CONSTANTS.rkI);
        leftPID.setD(RobotConstants.DRIVETRAIN_CONSTANTS.rkD);
        leftPID.setIZone(RobotConstants.DRIVETRAIN_CONSTANTS.rkIz);
        leftPID.setFF(RobotConstants.DRIVETRAIN_CONSTANTS.rkFF);
        leftPID.setOutputRange(RobotConstants.DRIVETRAIN_CONSTANTS.rkMinOutput, RobotConstants.DRIVETRAIN_CONSTANTS.rkMaxOutput);
        

        int smartMotionSlotRight = 0;
        leftPID.setSmartMotionMaxVelocity(RobotConstants.DRIVETRAIN_CONSTANTS.rmaxVel, smartMotionSlotRight);
        leftPID.setSmartMotionMinOutputVelocity(RobotConstants.DRIVETRAIN_CONSTANTS.rminVel, smartMotionSlotRight);
        leftPID.setSmartMotionMaxAccel(RobotConstants.DRIVETRAIN_CONSTANTS.rmaxAcc, smartMotionSlotRight);
        leftPID.setSmartMotionAllowedClosedLoopError(RobotConstants.DRIVETRAIN_CONSTANTS.rAllowedErr, smartMotionSlotRight);

        resetEncoders();
        //resetGyro();


        trajectory = 
        TrajectoryGenerator.generateTrajectory(
        theBeginning,
        List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
        theEnd,
        new TrajectoryConfig(Units.feetToMeters(1.0), Units.feetToMeters(0.5)));
        
         robotDrive = new DifferentialDrive(frontLeftSpark, frontRightSpark);

    }

    public void feedWatchDog(){
        robotDrive.feed();
    }

    @Override
    public void periodic() {
        feedWatchDog();
        logger.log("Front Left Encoder Position", frontLeftEncoder.getPosition());
        logger.log("Front Right Encoder Position", frontRightEncoder.getPosition());
        logger.log("Rear Left Encoder Position", rearLeftEncoder.getPosition());
        logger.log("Rear Right Encoder Position", rearRightEncoder.getPosition());
        logger.log("navX angle", getAngle());
        logger.log("acceleration x", navX.getRawAccelX());
        logger.log("acceleration y", navX.getRawAccelY());
        logger.log("displacement x", navX.getDisplacementX());
        logger.log("displacement y", navX.getDisplacementY());
        logger.log("posex", getPose().getX());
        logger.log("posey", getPose().getY());


        double leftPosition = frontLeftEncoder.getPosition();
        double rightPosition = frontRightEncoder.getPosition();
        pose = odometry.update(getHeading(), leftPosition, rightPosition);
    }

    public void arcadeDrive(double forward, double rotation) {
        robotDrive.arcadeDrive(forward, rotation);
        feedWatchDog();
    }

    public void scaledDrive(double x, double y) {
        
        double scaledX = 0;
        double scaledY = 0;

        if (x == 1) { 

            scaledX = 1;

        } else if (x == -1) {
            
            scaledX = -1;
        
        } else if (x > 0) {

            scaledX = 1/(1 + Math.pow(2.71827, 7+(-14*x)));

        } else if (x < 0) {

            scaledX = 1/(1 + Math.pow(2.71827, -7+(-14*x)))-1;

        } else if (x ==0) {
            
            scaledX = 0;

        } else {

            scaledX = 0;

        }

        
        if (y == 1) { 

            scaledY = 1;

        } else if (y == -1) {
            
            scaledY = -1;
        
        } else if (y > 0) {

            scaledY = 1/(1 + Math.pow(2.71827, 7+(-14*y)));

        } else if (y < 0) {

            scaledY = 1/(1 + Math.pow(2.71827, -7+(-14*y)))-1;

        } else if (y ==0) {
            
            scaledY = 0;

        } else {

            scaledY = 0;
            
        }


        arcadeDrive(scaledX, scaledY);
        feedWatchDog();
        
    }

    public Pose2d theBeginning = new Pose2d();
    public Pose2d theEnd = new Pose2d(3,0, Rotation2d.fromDegrees(0));


    public double getAngle() {
        return navX.getAngle();
    }

    public double getTurnRate() {
        return navX.getRate();
    }

    public void resetGyro() {
        navX.reset();
    }

    public void reset(){
        odometry.resetPosition(new Pose2d(), getHeading());
    }

    public void reset(Pose2d resetPose) {
        odometry.resetPosition(resetPose, getHeading());
      }
    

      
    public Rotation2d getHeading() {
        
        return new Rotation2d(-getAngle());
    }

    public Pose2d getPose() {
        return pose;
      }

    public Trajectory getTrajectory(){
        return trajectory;
    }

    public DifferentialDriveWheelSpeeds getSpeeds(){

        double leftSpeeds = frontLeftEncoder.getVelocity();
        double rightSpeeds = frontRightEncoder.getVelocity();

    return new DifferentialDriveWheelSpeeds(leftSpeeds,rightSpeeds);
    
}

    public DifferentialDriveKinematics getKinematics() {

        return RobotConstants.DRIVETRAIN_CONSTANTS.kinematics;
      }
    
    public void resetEncoders() {

        frontRightEncoder.setPosition(0);
        frontLeftEncoder.setPosition(0);
        rearRightEncoder.setPosition(0);
        rearLeftEncoder.setPosition(0);

    }

    public void setSpeedMax(double xSpeed) {
        robotDrive.setMaxOutput(xSpeed);
      }

    public void driveVolts(double leftVolts, double rightVolts){
        frontLeftSpark.setVoltage(leftVolts);
        frontRightSpark.setVoltage(rightVolts);
        robotDrive.feedWatchdog();
    }

    public void driveVelocity(double leftVelocity, double rightVelocity) {
        leftPID.setReference(leftVelocity, ControlType.kSmartVelocity);
        rightPID.setReference(rightVelocity, ControlType.kSmartVelocity);
    }

    public void setMaxOutput(double maxOutput) {
    robotDrive.setMaxOutput(maxOutput);        

    }

    //public Command createCommandForTrajectory(Trajectory trajectory, Boolean initPose) {

    
        public RamseteCommand getRamseteCommand(Trajectory trajectory) {
        
        RamseteCommand ramseteCommand = new RamseteCommand(
        trajectory, 
        this::getPose, 
        trajectoryController, 
        RobotConstants.DRIVETRAIN_CONSTANTS.kinematics, 
        this::driveVelocity, 
        this);

        return ramseteCommand;



        }




}
