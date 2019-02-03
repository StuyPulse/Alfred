/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public interface RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;

    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;

    /***************************************************************************************
     * Tail and Abom Ports
     ****************************************************************************************/
    int ABOM_SOLENOID_PORT = -1;
    int TAIL_MOTOR_PORT = -1;
    int RAISE_TAIL_SOLENOID_PORT = -1;
    int RELEASE_ABOM_SOLENOID_PORT = -1;
    /***************************************************************************************
     * Abom Charge Constants
     ****************************************************************************************/
    int ABOM_CHARGE_DELAY_MS = 300; // Time it takes for a piston to fire and retract
    /***************************************************************************************
     * CV Constants
     ****************************************************************************************/
    double ACCELERATION_DIV = 4; // Using averages, you can allow for smoother movement
    double CV_SPEED = 0; // Speed that stays the same through movements
    /***************************************************************************************
     * Aim Assist Constants
     ****************************************************************************************/
    double TURN_DIV = 24; // Changes the speed that the robot will turn
    double MOVE_TURN_DIV = 2; // Changes the speed that the robot will turn while moving
    /***************************************************************************************
     * Auton Assist Variables
     ****************************************************************************************/
    double FORWARD_AREA = 0.0145; // Area at which robot will move forward
    double MIN_AUTO_SPEED = 0.25; // Slowest speed for auto accelerate
    double AUTO_SPEED_MUL = 1.5 / FORWARD_AREA; // Auto Drive Speed
    int JOYSTICK_SCALAR = 3; // Used to make joystick values smaller (Must be an odd number)(for CV)

    /***************************************************************************************
     * Gamepad Ports
     ****************************************************************************************/
    int DRIVER_GAMEPAD_PORT = -1;
    int OPERATOR_GAMEPAD_PORT = -1;

    /***************************************************************************************
     * Drivetrain Motor Ports
     ****************************************************************************************/
    int LEFT_TOP_MOTOR_PORT = -1;
    int LEFT_MIDDLE_MOTOR_PORT = -1;
    int LEFT_BOTTOM_MOTOR_PORT = -1;

    int RIGHT_TOP_MOTOR_PORT = -1;
    int RIGHT_MIDDLE_MOTOR_PORT = -1;
    int RIGHT_BOTTOM_MOTOR_PORT = -1;

    double WHEEL_DIAMETER = 6;
    double WHEEL_INCHES_PER_REVOLUTION = WHEEL_DIAMETER * Math.PI;

    int GEAR_SHIFT_CHANNEL = -1;

    /***************************************************************************************
     * Floop Solenoid Constants
     ****************************************************************************************/
    int FLOOP_CHANNEL = -1;

    /**************************************************************************
     * Lift Ports
     *************************************************************************/
    int LIFT_MASTER_TALON_MOTOR_PORT = -1;
    int LIFT_FOLLOWER_TALON_MOTOR_PORT = -1;

    int LIFT_TILT_SOLENOID_FORWARD_PORT = -1;
    int LIFT_TILT_SOLENOID_REVERSE_PORT = -1;

    int LIFT_BRAKE_SOLENOID_PORT = -1;

    int LIFT_TOP_LIMIT_SWITCH_PORT = -1;
    int LIFT_BOTTOM_LIMIT_SWITCH_PORT = -1;

    /**************************************************************************
     * Lift Constants
     *************************************************************************/
    double LIFT_WINCH_DIAMETER_INCHES = 1.75;
    double LIFT_EMPIRICAL_RAW_MULTIPLIER = 1;
    double LIFT_ENCODER_RAW_MULTIPLIER = LIFT_EMPIRICAL_RAW_MULTIPLIER * (LIFT_WINCH_DIAMETER_INCHES * Math.PI);
    double LIFT_MIN_SPEED = 0.1;
    // Waiting on eng for the height
    double LIFT_MAX_HEIGHT = -1;
    double LIFT_MIN_HEIGHT = -1;
    double LIFT_RAMP_HEIGHT_THRESHOLD = 1;

    //TODO: Figure out actual heights and input
    double LEVEL_1_HEIGHT = 5;
    double LEVEL_2_HEIGHT = 10;
    double LEVEL_3_HEIGHT = 15;
    /***************************************************************************************
     * Fangs Solenoid Constants
     ****************************************************************************************/
    int FANGS_CHANNEL = -1;

    /***************************************************************************************
     * Rollers Constants
     ****************************************************************************************/
    int ROLLER_MOTOR_PORT = -1;
    double SLOW_ROLLER_MULTIPLIER = 0.75;
}