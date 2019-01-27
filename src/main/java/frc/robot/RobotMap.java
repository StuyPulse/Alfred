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
    *      Tail and Abom Ports                                                                
    ****************************************************************************************/
    int ABOM_SOLENOID = -1;
    int TAIL_MOTOR = -1;
    int RAISE_TAIL_SOLENOID = -1;
    int RELEASE_ABOM_SOLENOID = -1;
    /***************************************************************************************
    *      Abom Charge Constants                                                                
    ****************************************************************************************/
    int ABOM_CHARGE_DELAY = 300; // Time it takes for a piston to fire and retract
    /***************************************************************************************
     * CV Constants
     ****************************************************************************************/
	double ACCELERATION_DIV = 4; // Using averages, you can allow for smoother movement
	double CV_SPEED = 0; // Speed that stays the same through movements
    /***************************************************************************************
    *      Aim Assist Constants                                                                    
    ****************************************************************************************/
	double TURN_DIV = 24; // Changes the speed that the robot will turn
	double MOVE_DIV = 2; // Changes the speed that the robot will turn
	/***************************************************************************************
    *      Auton Assist Variables                                                                    
    ****************************************************************************************/
	double FORWARD_AREA = 0.0145; // Area at which robot will move forward
    double MIN_SPEED = 0.25; // Slowest speed for auto accelerate
	double AUTO_SPEED = 1.5 / FORWARD_AREA; // Auto Drive Speed
    boolean DRIVER_MODE = false; // Prevent overwriting to the network table
    int JOYSTICK_SCALAR = 3; //Used to make joystick values smaller (Must be an odd number)(for CV)

    /***************************************************************************************
    *      Gamepad Ports                                                                    
    ****************************************************************************************/
    int DRIVER_GAMEPAD_PORT = -1;
    int OPERATOR_GAMEPAD_PORT = -1;

    /***************************************************************************************
    *      Drivetrain Motor Ports                                                                    
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
    *      Floop Solenoid Constants                                                                    
    ****************************************************************************************/
    int FLOOP_FORWARD_CHANNEL = -1;
    int FLOOP_REVERSE_CHANNEL = -1;
    /***************************************************************************************
    *      Fangs Solenoid Constants                                                                    
    ****************************************************************************************/
    int FANGS_OPEN_CHANNEL = -1;
    int FANGS_CLOSE_CHANNEL = -1;

    /***************************************************************************************
    *      Rollers Constants                                                                   
    ****************************************************************************************/
    public static final int ROLLER_MOTOR_PORT = -1;
}
