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
     * Limelight Pipeline Constants
     ****************************************************************************************/
    int DRIVER_PIPELINE = 0;
    int CV_PIPELINE = 1;

    /***************************************************************************************
     * Drivetrain CV Constants
     ****************************************************************************************/
    boolean DRIVETRAIN_SMARTDASHBOARD_DEBUG = true;
<<<<<<< HEAD

=======
    
>>>>>>> 767d8fdfc3e4874e88ec583db27ffbc261a04fcd
    interface CV { // Values for autodrive/turn
        double AUTOMATIC_DRIVE_SPEED = 0.125; // Speed robot moves in autodrive
        double SPEED_WHILE_TURNING = 2; // If lower, slows robot at steep angles
        double MOVE_TURN_MUL = 6.3; // Increase turning when moving faster
        double TURN_DIV = 20; // Speed robot turns towards target
    }
    
    double QUICKTURN_THRESHOLD = 0.04;
    double QUICKTURN_SPEED = 1.0/3.0;
<<<<<<< HEAD

=======
  
>>>>>>> 767d8fdfc3e4874e88ec583db27ffbc261a04fcd
    int JOYSTICK_SCALAR = 3; // Used to make joystick values smaller
    double DRIVETRAIN_TURN_UPPER_LIMIT = 2.0 / 3;

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
