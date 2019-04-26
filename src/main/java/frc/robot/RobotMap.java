/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public interface RobotMap {

    String ROBOT_NAME = "Alfred";
    
    /***************************************************************************************
    * Drivetrain Motor Ports
    ****************************************************************************************/
    int LEFT_TOP_MOTOR_PORT = 2;
    int LEFT_MIDDLE_MOTOR_PORT = 3;
    int LEFT_BOTTOM_MOTOR_PORT = 1;

    int RIGHT_TOP_MOTOR_PORT = 6;
    int RIGHT_MIDDLE_MOTOR_PORT = 7;
    int RIGHT_BOTTOM_MOTOR_PORT = 5;

    int GEAR_SHIFT_CHANNEL = 0;

    /***************************************************************************************
    * Drivetrain Encoder Ports
    ****************************************************************************************/
    int DRIVETRAIN_LEFT_ENCODER_CHANNEL_A = 2;
    int DRIVETRAIN_LEFT_ENCODER_CHANNEL_B = 3;

    int DRIVETRAIN_RIGHT_ENCODER_CHANNEL_A = 0;
    int DRIVETRAIN_RIGHT_ENCODER_CHANNEL_B = 1;

    /***************************************************************************************
     * Tail and Abom Ports
     ****************************************************************************************/    //
    int RATCHET_DOUBLE_SOLENOID_FORWARD_PORT = 2;
    int RATCHET_DOUBLE_SOLENOID_REVERSE_PORT = 3; 
    int RATCHET_SINGLE_SOLENOID_PORT = 1; 
    int TAIL_MOTOR_PORT = 4;
    int ABOM_SOLENOID_PORT = 6;

    /**************************************************************************
     * Lift Ports
     *************************************************************************/
    int LIFT_MASTER_TALON_MOTOR_PORT = 8;
    int LIFT_FOLLOWER_VICTOR_MOTOR_PORT = 9;

    int LIFT_TILT_SOLENOID_FORWARD_PORT = 5; //PCM 2 (13)
    int LIFT_TILT_SOLENOID_REVERSE_PORT = 7; //PCM 2 (15)

    int LIFT_BRAKE_SOLENOID_PORT = 6; //PCM 2 (8)
    int LIFT_BOTTOM_LIMIT_SENSOR_PORT = 5;

    /***************************************************************************************
     * Floop Solenoid Port
     ****************************************************************************************/
    int FLOOP_CHANNEL = 4;
    /***************************************************************************************
     * Pusher Solenoid Port
     ****************************************************************************************/
    int PUSHER_CHANNEL = 0;
    /***************************************************************************************
     * Rollers Motor Port
     ****************************************************************************************/
     int ROLLER_MOTOR_PORT = 10;

    /***************************************************************************************
     * IR Sensor Port
     ****************************************************************************************/
    int IR_SENSOR_PORT = 9;

    /**************************************************************************************** 
     * IR Sensor Port
     ****************************************************************************************/
    int LED_CHANNEL = 0;
    
    /***************************************************************************************
     * Gamepad Ports
     ****************************************************************************************/
    int DRIVER_GAMEPAD_PORT = 0;
    int OPERATOR_GAMEPAD_PORT = 1;

    /***************************************************************************************
     * Abom Charge Constants
     ****************************************************************************************/
    double ABOM_CHARGE_DELAY_SEC = 2.0/3.0; // Time it takes for a piston to fire and retract
    double ABOM_TIME_TO_EXTEND = 0.1;
    double ABOM_TIME_TO_RETRACT = 0.2;

    /***************************************************************************************
     * Floop Limelight Constants
     ****************************************************************************************/
    long BLINK_TIME_MS = 500;
    
    /***************************************************************************************
     * Drivetrain/Limelight Constants
     ****************************************************************************************/

    interface Drivetrain {
        // Prints Information to Smartdashboard
        boolean SMARTDASHBOARD_DEBUG = true;

        interface Pipeline {
            int DRIVER = 0;
            int CV = 1;
        }
    
        interface CV { 
            // Speed robot moves in autodrive
            double AUTOMATIC_DRIVE_SPEED = 0.125; // 0 - 1
    
            // If lower, slows robot at steep angles
            // If higher, robot will usually move at automatic drive speed
            double SPEED_WHILE_TURNING = 2; // 1 - 2
    
            // Increase limelight turn/adjustments at higher speeds
            double MOVE_TURN_MUL = 0; // DISABLED
    
            // If higher, robot turns slower towards target
            // If lower, robot turns faster towards target, but may oscilate
            double TURN_DIV = 22; 
        }

        interface TurnSpeed {
            // Max speed robot should turn
            // Every value after this is effectivly 
            // multiplied by this value
            double MAX = 2.0 / 3.0;

            // Speed at which robot moves during nudging
            double NUDGE = 1.0/2.0;

            // Additional Nudging for Quick Turn
            double QUICKTURN_NUDGE = 10;
        }

        interface QuickTurn {
            // Speed at which curvature drive enables quick turn
            double THRESHOLD = 0.04;

            // Threshold used for autodrive
            double AUTO_THRESHOLD = 694.0; // Quickturn is always on for auto

            // Speed at which drivetrain turns when using quick turn
            double SPEED = 2.0/3.0;
        }

        // What percent that quickturn speeds up (Higher = Slower)
        // Used for Smooth Quickturn
        interface Weights {
            // Enables or Disables "Smooth Quickturn"
            boolean SMOOTH_QUICKTURN = false;

            double STANDARD = 1;

            interface Quick {
                double ACCEL = 48;
                double DECEL = 2;
            }
        }

        interface Controls {
            // Scaling joysticks and triggers using exponents
            int JOYSTICK_SCALAR = 3;
            int TRIGGER_SCALAR = 2;
        }
    }

    /***************************************************************************************
     * Drivetrain Motor Constants
     ****************************************************************************************/
    double WHEEL_DIAMETER = 6;
    double WHEEL_INCHES_PER_REVOLUTION = WHEEL_DIAMETER * Math.PI;

    double NEO_ENCODER_EMPIRICAL_MULTIPLIER = 1;
    double NEO_ENCODER_RAW_MULTIPLIER = WHEEL_INCHES_PER_REVOLUTION * NEO_ENCODER_EMPIRICAL_MULTIPLIER;

    double DRIVETRAIN_RAMP_RATE = 0.3;

    /***************************************************************************************
     * Drivetrain Greyhill Encoder Constants
     ****************************************************************************************/
    double GREYHILL_PULSES_PER_REVOLUTION = 256 * 4.0;
    double DRIVETRAIN_OUTER_GEAR_RATIO = 24.0 / 60.0;
    double DRIVETRAIN_ENCODER_EMPIRICAL_MULTIPLIER = 1.3;
    double DRIVETRAIN_GREYHILL_INCHES_PER_PULSE = ((WHEEL_INCHES_PER_REVOLUTION * DRIVETRAIN_OUTER_GEAR_RATIO) / GREYHILL_PULSES_PER_REVOLUTION) * DRIVETRAIN_ENCODER_EMPIRICAL_MULTIPLIER;

    int DRIVETRAIN_CURRENT_LIMIT = 65;

    /**************************************************************************
     * Lift Constant
     *************************************************************************/
    double LIFT_ENCODER_TICKS_PER_REV = 1024.0;
    double LIFT_ENCODER_RAW_MULTIPLIER = 1 / 1207.0;
    double LIFT_MIN_SPEED = 0.05;
    double LIFT_DEADBAND_OVERRIDE_THRESHOLD = 0.2;
    double LIFT_SPEED_MULTIPLIER = .8;
    double LIFT_RAMP_RATE = 0.25;

    // Waiting on eng for the height
    // Eric said the total carriage movement is about 74 in
    double LIFT_MAX_HEIGHT = -1;
    double LIFT_MIN_HEIGHT = 0.0;
    double LIFT_RAMP_HEIGHT_THRESHOLD = 1.0;
    double LIFT_RAMP_MOVE_TO_HEIGHT_THRESHOLD = 5.0;

    double HP_LEVEL_1_HEIGHT = 0; //8.68 + 2.0
    double HP_LEVEL_2_HEIGHT = 29; //37.5 + 2.0; 
    double HP_LEVEL_3_HEIGHT = 58.5; //64.6 + 2.0;
    double C_LEVEL_1_HEIGHT = 16.0;
    double C_LEVEL_2_HEIGHT = 43.75;
    double C_LEVEL_3_HEIGHT = 71.25;

    double START_HEIGHT = 26.0;

    double LIFT_LEVEL_OFFSHOOT = 2.0;

    /***************************************************************************************
     * Rollers Constants
     ****************************************************************************************/
    double SLOW_ROLLER_MULTIPLIER = 0.75;
    double SLOW_ROLLER_MAXIMUM = 1;
}
