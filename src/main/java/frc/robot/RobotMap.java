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
     ****************************************************************************************/
    //TODO: find actual ports
    int RATCHET_DOUBLE_SOLENOID_FORWARD_PORT = -1; //PCM 2 (10)
    int RATCHET_DOUBLE_SOLENOID_BACKWARD_PORT = -1; 
    int RATCHET_SINGLE_SOLENOID_PORT = -1; 
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
    int LIFT_TOP_OPTICAL_SENSOR_PORT = -1;
    int LIFT_BOTTOM_OPTICAL_SENSOR_PORT = 5;

    /***************************************************************************************
     * Floop Solenoid Port
     ****************************************************************************************/
    int FLOOP_CHANNEL = 4;

    /***************************************************************************************
     * Fangs Solenoid Port
     ****************************************************************************************/
    int FANGS_CHANNEL = 3;

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
     * CV Constants
     ****************************************************************************************/
    double TARGET_HEIGHT_THRESHOLD = 6;
    double MIN_ASPECT_RATIO = 1.2;
    double MAX_ASPECT_RATIO = 3.3;
    double ANGLE_THRESHOLD = 25;

    /***************************************************************************************
     * Aim Assist Constants
     ****************************************************************************************/
    
    double TURN_DIV = 100; // Changes the speed that the robot will turn
    double MOVE_TURN_DIV = 3; // Changes the speed that the robot will turn while moving (The more it moves, the less it turns)

    double TURN_MIN_SPEED = 0.3;
    double TURN_MIN_ANGLE = 0.1;
    /***************************************************************************************
     * Auton Assist Constants
     ****************************************************************************************/
    double FORWARD_AREA = 0.0145; // Area at which robot will move forward
    double MIN_AUTO_SPEED = 0.25; // Slowest speed for auto accelerate
    double AUTO_SPEED_MUL = 1.5 / FORWARD_AREA; // Auto Drive Speed
    int JOYSTICK_SCALAR = 3; // Used to make joystick values smaller

    /***************************************************************************************
     * Drivetrain Motor Constants
     ****************************************************************************************/
    double WHEEL_DIAMETER = 6;
    double WHEEL_INCHES_PER_REVOLUTION = WHEEL_DIAMETER * Math.PI;

    double NEO_ENCODER_EMPIRICAL_MULTIPLIER = 1;
    double NEO_ENCODER_RAW_MULTIPLIER = WHEEL_INCHES_PER_REVOLUTION * NEO_ENCODER_EMPIRICAL_MULTIPLIER;

    /***************************************************************************************
     * Drivetrain Greyhill Encoder Constants
     ****************************************************************************************/
    double GREYHILL_PULSES_PER_REVOLUTION = 256 * 4.0; //TODO: check this
    double DRIVETRAIN_OUTER_GEAR_RATIO = 24.0 / 60.0;
    double DRIVETRAIN_ENCODER_EMPIRICAL_MULTIPLIER = 1.3;
    double DRIVETRAIN_GREYHILL_INCHES_PER_PULSE = ((WHEEL_INCHES_PER_REVOLUTION * DRIVETRAIN_OUTER_GEAR_RATIO) / GREYHILL_PULSES_PER_REVOLUTION) * DRIVETRAIN_ENCODER_EMPIRICAL_MULTIPLIER;

    /**************************************************************************
     * Lift Constants
     *************************************************************************/
    double LIFT_ENCODER_TICKS_PER_REV = 1024.0;
    double LIFT_ENCODER_RAW_MULTIPLIER = 1 / 1207.0;
    double LIFT_MIN_SPEED = 0.05;
    double LIFT_SPEED_MULTIPLIER = .8;

    // Waiting on eng for the height
    // Eric said the total carriage movement is about 74 in
    double LIFT_MAX_HEIGHT = -1;
    double LIFT_MIN_HEIGHT = 0;
    double LIFT_RAMP_HEIGHT_THRESHOLD = 1.0;

    //TODO: Test these values
    double LEVEL_1_HEIGHT = 8.68;
    double LEVEL_2_HEIGHT = 37.5;
    double LEVEL_3_HEIGHT = 64.6;

    double LIFT_LEVEL_OFFSHOOT = 2.0;

    /***************************************************************************************
     * Rollers Constants
     ****************************************************************************************/
    double SLOW_ROLLER_MULTIPLIER = 0.75;
    //TODO: find the actual max speed
    double SLOW_ROLLER_MAXIMUM = 1;
}