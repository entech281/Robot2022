package frc.robot.subsystems;

import frc.robot.RobotConstants;
import frc.robot.subsystems.ColorSensor;

import java.util.ArrayList;

import com.kauailabs.navx.frc.*;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;




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



    @Override
    public void initialize() {
        frontLeftSpark = new CANSparkMax(RobotConstants.CAN.FRONT_LEFT_MOTOR, MotorType.kBrushless);
        rearLeftSpark = new CANSparkMax(RobotConstants.CAN.REAR_LEFT_MOTOR, MotorType.kBrushless);
        leftMotorController = new MotorControllerGroup(frontLeftSpark, rearLeftSpark);

        frontRightSpark = new CANSparkMax(RobotConstants.CAN.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
        rearRightSpark = new CANSparkMax(RobotConstants.CAN.REAR_RIGHT_MOTOR, MotorType.kBrushless);
        rightMotorController = new MotorControllerGroup(frontRightSpark, rearRightSpark);
        frontLeftEncoder = frontLeftSpark.getEncoder();
        frontRightEncoder = frontRightSpark.getEncoder();
        rearLeftEncoder = rearLeftSpark.getEncoder();
        rearRightEncoder = rearRightSpark.getEncoder();
        robotDrive = new DifferentialDrive(leftMotorController, rightMotorController);
        navX = new AHRS(SPI.Port.kMXP);
    }

    public void feedWatchDog(){
        robotDrive.feed();
    }

    @Override
    public void periodic() {
        feedWatchDog();
        logger.log("Front Left Encoder Ticks", frontLeftEncoder.getPosition());
        logger.log("Front Right Encoder Ticks", frontRightEncoder.getPosition());
        logger.log("Rear Left Encoder Ticks", rearLeftEncoder.getPosition());
        logger.log("Rear Right Encoder Ticks", rearRightEncoder.getPosition());
        logger.log("navX angle", getAngle());
        logger.log("acceleration x", navX.getRawAccelX());
        logger.log("acceleration y", navX.getRawAccelY());
        logger.log("displacement x", navX.getDisplacementX());
        logger.log("displacement y", navX.getDisplacementY());
    }

    public void arcadeDrive(double forward, double rotation) {
        robotDrive.arcadeDrive(forward, rotation);
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
    


}
