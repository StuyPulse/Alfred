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

    public static final int FLOOP_FORWARD_CHANNEL = -1;
    public static final int FLOOP_REVERSE_CHANNEL = -1;
    public static final int ABOM_SOLENOID = -1;
    public static final int TAIL_MOTOR = -1;
    public static final int RAISE_TAIL_SOLENOID = -1;
    public static final int RELEASE_ABOM_SOLENOID = -1;

    /* CV VARIABLES */

    // Using averages, you can allow for smoother movement
    double ACCELERATION_DIV = 4;
    double CV_SPEED = 0; // Speed that stays the same through movements

    /* AIM ASSIST */
    // Changes the speed that the robot will turn
    double TURN_DIV = 24;

    // Changes the speed that the robot will turn
    double MOVE_DIV = 2;

    /* AUTO ACCELERATE VARIABLES */
    // Area at which robot will move forward
    double FORWARD_AREA = 0.0145;

    // Slowest speed for auto accelerate
    double MIN_SPEED = 0.25;

    // Auto Drive Speed
    double AUTO_SPEED = 1.5 / FORWARD_AREA;

    // Prevent overwriting to the network table
    boolean DRIVER_MODE = false;

    // Used to make joystick values smaller (Must be an odd number)(for CV)
    int JOYSTICK_SCALAR = 3;

    // Driver&Operator ports
    int DRIVER_PORT = -1;

    // Drivetrain ports
    int LEFT_TOP_MOTOR_PORT = -1;
    int LEFT_MIDDLE_MOTOR_PORT = -1;
    int LEFT_BOTTOM_MOTOR_PORT = -1;

    int RIGHT_TOP_MOTOR_PORT = -1;
    int RIGHT_MIDDLE_MOTOR_PORT = -1;
    int RIGHT_BOTTOM_MOTOR_PORT = -1;

    double WHEEL_DIAMETER = 6;
    double WHEEL_INCHES_PER_REVOLLUTION = WHEEL_DIAMETER * Math.PI;

}
