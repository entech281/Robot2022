package frc.robot.subsystems;

import frc.robot.RobotConstants;
import frc.robot.subsystems.ColorSensor;

import java.util.ArrayList;

import com.kauailabs.navx.frc.*;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.apache.commons.math3.analysis.function.Sigmoid;

import edu.wpi.first.math.controller.RamseteController;
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
    private DifferentialDrive robotDrive;

    private AHRS navX;
    
    Pose2d pose = new Pose2d();
    DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics((RobotConstants.DRIVETRAIN_CONSTANTS.trackwidth));
    DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(getHeading(), pose);
    RamseteController trajectoryController = new RamseteController(RobotConstants.DRIVETRAIN_CONSTANTS.kRamseteB, RobotConstants.DRIVETRAIN_CONSTANTS.kRamseteZeta);

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

        frontLeftEncoder.setVelocityConversionFactor(RobotConstants.DRIVETRAIN_CONSTANTS.velocityConversionFactor);
        frontRightEncoder.setVelocityConversionFactor(RobotConstants.DRIVETRAIN_CONSTANTS.velocityConversionFactor);
        rearLeftEncoder.setVelocityConversionFactor(RobotConstants.DRIVETRAIN_CONSTANTS.velocityConversionFactor);
        rearRightEncoder.setVelocityConversionFactor(RobotConstants.DRIVETRAIN_CONSTANTS.velocityConversionFactor);

        frontLeftEncoder.setPositionConversionFactor(RobotConstants.DRIVETRAIN_CONSTANTS.positionConversionFacotr);
        frontRightEncoder.setVelocityConversionFactor(RobotConstants.DRIVETRAIN_CONSTANTS.positionConversionFacotr);
        rearLeftEncoder.setVelocityConversionFactor(RobotConstants.DRIVETRAIN_CONSTANTS.positionConversionFacotr);
        rearRightEncoder.setVelocityConversionFactor(RobotConstants.DRIVETRAIN_CONSTANTS.positionConversionFacotr);

        robotDrive = new DifferentialDrive(frontLeftSpark, frontRightSpark);
        navX = new AHRS(SPI.Port.kMXP);



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

        double leftPosition = frontLeftEncoder.getPosition();
        double rightPosition = frontRightEncoder.getPosition();
        pose = odometry.update(getHeading(), leftPosition, rightPosition);
    }

    public void arcadeDrive(double forward, double rotation) {
        robotDrive.arcadeDrive(forward, rotation);
        feedWatchDog();
    }

    public void rampingDrive(double x, double y) {
        
        double scaledX;
        double scaledY;

        Sigmoid positiveSigmoid = new Sigmoid(0, 1);
        Sigmoid negativeSigmoid = new Sigmoid(-1,0);

        if (x > 1){
        scaledX = positiveSigmoid.value(x);
        } else if (x < 1) {
        scaledX = negativeSigmoid.value(x);
        } else {
            scaledX = x;
        }

        if (y > 1){
        scaledY = positiveSigmoid.value(y);
        } else if (y < 1) {
        scaledY = negativeSigmoid.value(y);
        } else {
            scaledY = y;
        }
        robotDrive.arcadeDrive(scaledX, scaledY);
        feedWatchDog();
    }

    public Trajectory generateTrajectory() {

        var currentPos = new Pose2d(Units.feetToMeters(0), Units.feetToMeters(0),
            Rotation2d.fromDegrees(0));
        var endpoint = new Pose2d(Units.feetToMeters(3), Units.feetToMeters(4),
            Rotation2d.fromDegrees(45));
    
        var interiorWaypoints = new ArrayList<Translation2d>();
        interiorWaypoints.add(new Translation2d(Units.feetToMeters(1), Units.feetToMeters(2)));
        interiorWaypoints.add(new Translation2d(Units.feetToMeters(2), Units.feetToMeters(3)));
    
        TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(2), Units.feetToMeters(.5));
        config.setReversed(false);
    
        var trajectory = TrajectoryGenerator.generateTrajectory(
            currentPos,
            interiorWaypoints,
            endpoint,
            config);
            
            return trajectory;
      }




    public double getAngle() {
        return navX.getAngle();
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
        return Rotation2d.fromDegrees(-navX.getAngle());
    }

    public Pose2d getPose() {
        return pose;
      }

    public DifferentialDriveWheelSpeeds getSpeeds(){

        double leftSpeeds = frontLeftEncoder.getVelocity();
        double rightSpeeds = frontRightEncoder.getVelocity();

    return new DifferentialDriveWheelSpeeds(leftSpeeds,rightSpeeds);
    }

    public DifferentialDriveKinematics getKinematics() {
        return kinematics;
      }
    
    public void resetEncoders() {
        frontRightEncoder.setPosition(0);
        frontLeftEncoder.setPosition(0);
        rearRightEncoder.setPosition(0);
        rearLeftEncoder.setPosition(0);

    }

    public void driveVolts(double leftVolts, double rightVolts){
        frontLeftSpark.setVoltage(leftVolts);
        frontRightSpark.setVoltage(rightVolts);
        robotDrive.feedWatchdog();
    }

    public void setMaxOutput(double maxOutput) {
    robotDrive.setMaxOutput(maxOutput);        

    }





}
