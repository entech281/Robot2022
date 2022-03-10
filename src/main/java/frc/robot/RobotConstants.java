// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
        public static final int OPERATOR_JOYSTICK = 1;
    }
    public interface DRIVER_STICK {
        public static final int DRIVESTRAIGHT = 1;
        public static final int VISIONDRIVE = 2;
        public static final int TURNBYANGLE = 3;
        public static final int TURNUNTILLBALLSEEN = 4;
        public static final int INTAKEIN = 7;
        public static final int INTAKEOUT = 8;
        public static final int BELTIN = 9;
        public static final int BELTOUT = 10;
        public static final int HOOKUP = 11;
        public static final int HOOKDOWN= 12;
    }
    public interface OPERATOR_STICK {
        public static final int INTAKE_DEPLOY = 3;
        public static final int INTAKE_NUDGE_UP = 4;
        public static final int INTAKE_NUDGE_DOWN = 5;
        public static final int INTAKE_REVERSE = 8;
        public static final int BELT_IN  = 9;
        public static final int BELT_OUT = 10;
        public static final int FIRE = 11;
        public static final int HOOK_UP = 12;
        public static final int HOOK_DOWN = 13;
        public static final int AUTO1 = 6;
        public static final int AUTO2 = 7;

    }
    public interface CAN {
        public static final int FRONT_RIGHT_MOTOR = 1;
        public static final int REAR_RIGHT_MOTOR = 2;
        public static final int FRONT_LEFT_MOTOR = 3;
        public static final int REAR_LEFT_MOTOR = 4;
        public static final int ARM_MOTOR = 5;
        public static final int ROLLER_MOTOR = 6;
        public static final int BELT_MOTOR = 7;
        public static final int HOOK_MOTOR = 8;
    }
    public interface DIGITAL_IO {
        public static final int HOOK_UP_LIMIT = 1;
        public static final int HOOK_DOWN_LIMIT = 2;
    }
}