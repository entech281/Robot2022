package frc.robot.subsystems;

import frc.robot.RobotConstants;

import com.kauailabs.navx.frc.*;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;



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
    }

    public void arcadeDrive(double forward, double rotation) {
        robotDrive.arcadeDrive(forward, rotation);
        feedWatchDog();
    }


    public double getAngle() {
    return navX.getAngle();
    }
    


}
