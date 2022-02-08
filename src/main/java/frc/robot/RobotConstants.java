// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class RobotConstants {
    public interface PWM {
        public static final int PWM_0 = 0;
        public static final int PWM_1 = 1;
    }
    public interface JOYSTICKS {
        public static final int DRIVER_JOYSTICK = 0;
    }
    public interface DRIVER_STICK {
    }
    public interface CAN {
        public static final int FRONT_LEFT_MOTOR = 3;
        public static final int REAR_LEFT_MOTOR = 4;
        public static final int FRONT_RIGHT_MOTOR = 1;
        public static final int REAR_RIGHT_MOTOR = 2;
    }
    public interface DIGITAL_IO {
    }
    public interface DRIVETRAIN_CONSTANTS {

        //meters
        public static final double trackwidth = 10;

        //lol exdeee
        DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics((trackwidth));

        // *I think* velocity is rpm, convert to meters per second. Position is encoder clicks, convert to meters
        public static final double velocityConversionFactor = 1024;
        public static final double positionConversionFacotr = 1;

        // Characterization values
        public static final double ksVolts = 0;
        public static final double kvVoltSecondsPerMeter = 0;
        public static final double kaVoltSecondsSquaredPerMeter = 0;
        public static final double kPDriveVel = 0;
        public static final double kMaxSpeedMetersPerSecond = 0;
        public static final double kMaxAccelerationMetersPerSecondSquared = 0;
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;

        //SparkMax pid controller values left side drivetrain
        public static final double lkP = 1;
        public static final double lkI = 1;
        public static final double lkD = 1;
        public static final double lkIz = 1;
        public static final double lkFF = 1;
        public static final double lkMaxOutput = 1;
        public static final double lkMinOutput = 1;
        public static final double lmaxRPM = 1;
        public static final double lmaxVel = 1;
        public static final double lminVel = 1;
        public static final double lmaxAcc = 1;
        public static final double lAllowedErr = 1;

        //right side drivetrain
        public static final double rkP = 1;
        public static final double rkI = 1;
        public static final double rkD = 1;
        public static final double rkIz = 1;
        public static final double rkFF = 1;
        public static final double rkMaxOutput = 1;
        public static final double rkMinOutput = 1;
        public static final double rmaxRPM = 1;
        public static final double rmaxVel = 1;
        public static final double rminVel = 1;
        public static final double rmaxAcc = 1;
        public static final double rAllowedErr = 1;

        public static final double ramseteToleranceInches = 4;
        public static final double ramseteToleranceDegrees = 45;
    }
}
