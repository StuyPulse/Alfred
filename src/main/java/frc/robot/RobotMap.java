/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public interface RobotMap {

    String ROBOT_NAME = "Alfred";
    
    enum ROBOT_TYPE { ALFRED, EDWIN };
    
    // Change based on current bot being usedgi
    ROBOT_TYPE CURRENT_ROBOT = ROBOT_TYPE.ALFRED;

    /***************************************************************************************
    * Drivetrain Motor Ports
    ****************************************************************************************/
    int LEFT_TOP_MOTOR_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 2
        /* Edwin  */ : 2;
    int LEFT_MIDDLE_MOTOR_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 3
        /* Edwin  */ : 3;
    int LEFT_BOTTOM_MOTOR_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 1
        /* Edwin  */ : 1;

    int RIGHT_TOP_MOTOR_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 6
        /* Edwin  */ : 6;
    int RIGHT_MIDDLE_MOTOR_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 7
        /* Edwin  */ : 7;
    int RIGHT_BOTTOM_MOTOR_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 5
        /* Edwin  */ : 5;

    int GEAR_SHIFT_CHANNEL = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 0
        /* Edwin  */ : 0;

    /***************************************************************************************
    * Drivetrain Encoder Ports
    ****************************************************************************************/
    int DRIVETRAIN_LEFT_ENCODER_CHANNEL_A = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 2
        /* Edwin  */ : 2;
    int DRIVETRAIN_LEFT_ENCODER_CHANNEL_B = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 3
        /* Edwin  */ : 3;

    int DRIVETRAIN_RIGHT_ENCODER_CHANNEL_A = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 0
        /* Edwin  */ : 0;
    int DRIVETRAIN_RIGHT_ENCODER_CHANNEL_B = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 1
        /* Edwin  */ : 1;

    /***************************************************************************************
     * Tail and Abom Ports
     ****************************************************************************************/
    //TODO: find actual ports
    int RATCHET_DOUBLE_SOLENOID_FORWARD_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 2
        /* Edwin  */ : 2;
    int RATCHET_DOUBLE_SOLENOID_REVERSE_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 3
        /* Edwin  */ : 3; 
    int RATCHET_SINGLE_SOLENOID_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 1
        /* Edwin  */ : 1; 
    int TAIL_MOTOR_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 4
        /* Edwin  */ : 4;
    int ABOM_SOLENOID_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 6
        /* Edwin  */ : 6;

    /**************************************************************************
     * Lift Ports
     *************************************************************************/
    int LIFT_MASTER_TALON_MOTOR_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 8
        /* Edwin  */ : 8;
    int LIFT_FOLLOWER_VICTOR_MOTOR_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 9
        /* Edwin  */ : 9;

    int LIFT_TILT_SOLENOID_FORWARD_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 5
        /* Edwin  */ : 5; //PCM 2 (13)
    int LIFT_TILT_SOLENOID_REVERSE_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 7
        /* Edwin  */ : 7; //PCM 2 (15)

    int LIFT_BRAKE_SOLENOID_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 6
        /* Edwin  */ : 6; //PCM 2 (8)
    int LIFT_BOTTOM_LIMIT_SENSOR_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 5
        /* Edwin  */ : 5;

    /***************************************************************************************
     * Floop Solenoid Port
     ****************************************************************************************/
    int FLOOP_CHANNEL = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 4
        /* Edwin  */ : 4;
        
    /***************************************************************************************
     * Pusher Solenoid Port
     ****************************************************************************************/
    int PUSHER_CHANNEL = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 3
        /* Edwin  */ : 3;

    /***************************************************************************************
     * Rollers Motor Port
     ****************************************************************************************/
    int ROLLER_MOTOR_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 10
        /* Edwin  */ : 10;

    /***************************************************************************************
     * IR Sensor Port
     ****************************************************************************************/
    int IR_SENSOR_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 9
        /* Edwin  */ : 9;

    /**************************************************************************************** 
     * IR Sensor Port
     ****************************************************************************************/
    int LED_CHANNEL = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 0
        /* Edwin  */ : 0;
    
    /***************************************************************************************
     * Gamepad Ports
     ****************************************************************************************/
    int DRIVER_GAMEPAD_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 0
        /* Edwin  */ : 0;

    int OPERATOR_GAMEPAD_PORT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 1
        /* Edwin  */ : 1;

    /***************************************************************************************
     * Abom Charge Constants
     ****************************************************************************************/
    double ABOM_CHARGE_DELAY_SEC = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 2.0/3.0
        /* Edwin  */ : 2.0/3.0; // Time it takes for a piston to fire and retract
    double ABOM_TIME_TO_EXTEND = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 0.1
        /* Edwin  */ : 0.1;
    double ABOM_TIME_TO_RETRACT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 0.2
        /* Edwin  */ : 0.2;

    /***************************************************************************************
     * Limelight Pipeline Constants
     ****************************************************************************************/
    int DRIVER_PIPELINE = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 1
        /* Edwin  */ : 1;
    int CV_PIPELINE = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 0
        /* Edwin  */ : 0;

    /***************************************************************************************
     * Drivetrain CV Constants
     ****************************************************************************************/
    boolean DRIVETRAIN_SMARTDASHBOARD_DEBUG = true;
    double AUTOMATIC_DRIVE_SPEED = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 0.3
        /* Edwin  */ : 0.3;

    double MOVE_TURN_MUL = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 5.5
        /* Edwin  */ : 5.5;

    double TURN_DIV = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 20
        /* Edwin  */ : 20;
        
    int JOYSTICK_SCALAR = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 3
        /* Edwin  */ : 3; // Used to make joystick values smaller

    /***************************************************************************************
     * Drivetrain Motor Constants
     ****************************************************************************************/
    double WHEEL_DIAMETER = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 6
        /* Edwin  */ : 6;
    double WHEEL_INCHES_PER_REVOLUTION = WHEEL_DIAMETER * Math.PI;

    double NEO_ENCODER_EMPIRICAL_MULTIPLIER = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 1
        /* Edwin  */ : 1;
    double NEO_ENCODER_RAW_MULTIPLIER = WHEEL_INCHES_PER_REVOLUTION * NEO_ENCODER_EMPIRICAL_MULTIPLIER;

    /***************************************************************************************
     * Drivetrain Greyhill Encoder Constants
     ****************************************************************************************/
    double GREYHILL_PULSES_PER_REVOLUTION = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 256 * 4.0
        /* Edwin  */ : 256 * 4.0; //TODO: check this

    double DRIVETRAIN_OUTER_GEAR_RATIO = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 24.0 / 60.0
        /* Edwin  */ : 24.0 / 60.0;

    double DRIVETRAIN_ENCODER_EMPIRICAL_MULTIPLIER = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 1.3
        /* Edwin  */ : 1.3;

    double DRIVETRAIN_GREYHILL_INCHES_PER_PULSE = ((WHEEL_INCHES_PER_REVOLUTION * DRIVETRAIN_OUTER_GEAR_RATIO) / GREYHILL_PULSES_PER_REVOLUTION) * DRIVETRAIN_ENCODER_EMPIRICAL_MULTIPLIER;

    int DRIVETRAIN_CURRENT_LIMIT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 65
        /* Edwin  */ : 65;

    /**************************************************************************
     * Lift Constant
     *************************************************************************/
    double LIFT_ENCODER_TICKS_PER_REV = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 1024.0
        /* Edwin  */ : 1024.0;
    double LIFT_ENCODER_RAW_MULTIPLIER = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 1.0 / 1207.0
        /* Edwin  */ : 1.0 / 1207.0;
    double LIFT_MIN_SPEED = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 0.05
        /* Edwin  */ : 0.05;
    double LIFT_DEADBAND_OVERRIDE_THRESHOLD = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 0.2
        /* Edwin  */ : 0.2;
    double LIFT_SPEED_MULTIPLIER = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 0.8
        /* Edwin  */ : 0.8;
    double LIFT_RAMP_RATE = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 0.25
        /* Edwin  */ : 0.25;

    // Waiting on eng for the height
    // Eric said the total carriage movement is about 74 in
    double LIFT_MAX_HEIGHT = -1;
    double LIFT_MIN_HEIGHT = 0.0;
    double LIFT_RAMP_HEIGHT_THRESHOLD = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 1.0
        /* Edwin  */ : 1.0;
    double LIFT_RAMP_MOVE_TO_HEIGHT_THRESHOLD = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 5.0
        /* Edwin  */ : 5.0;

    double HP_LEVEL_1_HEIGHT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 8.68 + 2.0
        /* Edwin  */ : 8.68 + 2.0;
    double HP_LEVEL_2_HEIGHT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 37.5 + 2.0
        /* Edwin  */ : 37.5 + 2.0;
    double HP_LEVEL_3_HEIGHT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 64.6 + 2.0
        /* Edwin  */ : 64.6 + 2.0;
    double C_LEVEL_1_HEIGHT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 16.0
        /* Edwin  */ : 16.0;
    double C_LEVEL_2_HEIGHT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 43.75
        /* Edwin  */ : 43.75;
    double C_LEVEL_3_HEIGHT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 71.25
        /* Edwin  */ : 71.25;

    double START_HEIGHT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 26.0
        /* Edwin  */ : 26.0;

    double LIFT_LEVEL_OFFSHOOT = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 2.0
        /* Edwin  */ : 2.0;

    /***************************************************************************************
     * Rollers Constants
     ****************************************************************************************/
    double SLOW_ROLLER_MULTIPLIER = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 0.25
        /* Edwin  */ : 0.25;
    
    //TODO: find the actual max speed
    double SLOW_ROLLER_MAXIMUM = (CURRENT_ROBOT == ROBOT_TYPE.ALFRED)
        /* Alfred */ ? 1
        /* Edwin  */ : 1;
}
