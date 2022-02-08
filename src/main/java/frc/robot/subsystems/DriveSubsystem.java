package frc.robot.subsystems;

import frc.robot.RobotConstants;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
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
 
    @Override
    public void initialize() {

        frontLeftSpark = new CANSparkMax(RobotConstants.CAN.FRONT_LEFT_MOTOR, MotorType.kBrushless);
        rearLeftSpark = new CANSparkMax(RobotConstants.CAN.REAR_LEFT_MOTOR, MotorType.kBrushless);
        frontRightSpark = new CANSparkMax(RobotConstants.CAN.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
        rearRightSpark = new CANSparkMax(RobotConstants.CAN.REAR_RIGHT_MOTOR, MotorType.kBrushless);
        frontLeftSpark.setInverted(true);
        rearLeftSpark.setInverted(true);
        frontRightSpark.setInverted(false);
        rearRightSpark.setInverted(false);

        leftMotorController = new MotorControllerGroup(frontLeftSpark, rearLeftSpark);
        rightMotorController = new MotorControllerGroup(frontRightSpark, rearRightSpark);
        robotDrive = new DifferentialDrive(leftMotorController, rightMotorController);

        frontLeftEncoder = frontLeftSpark.getEncoder();
        frontRightEncoder = frontRightSpark.getEncoder();
        rearLeftEncoder = rearLeftSpark.getEncoder();
        rearRightEncoder = rearRightSpark.getEncoder();
    }

    public void feedWatchDog(){
        robotDrive.feed();
    }

    @Override
    public void periodic() {
        feedWatchDog();
        logger.log("Front Left Encoder Ticks", frontLeftEncoder.getPosition() * -1);
        logger.log("Front Right Encoder Ticks", frontRightEncoder.getPosition() * -1);
        logger.log("Rear Left Encoder Ticks", rearLeftEncoder.getPosition() * -1);
        logger.log("Rear Right Encoder Ticks", rearRightEncoder.getPosition() * -1);
    }

    public void arcadeDrive(double forward, double rotation) {
        robotDrive.arcadeDrive(forward, rotation);
        feedWatchDog();
    }

    public void resetEncoders(){
        frontLeftEncoder.setPosition(0);
        frontRightEncoder.setPosition(0);
        rearRightEncoder.setPosition(0);
        rearLeftEncoder.setPosition(0);
    }

public double getLeftDistance() {
    return (0.5 * (frontLeftEncoder.getPosition() + rearLeftEncoder.getPosition()));
}

public double getRightDistance() {
        return (0.5 * (frontRightEncoder.getPosition() + rearRightEncoder.getPosition()));
}

}
