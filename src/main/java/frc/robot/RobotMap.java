/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public interface RobotMap {
    /***************************************************************************************
    *      CV Constants                                                                    
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
    double WHEEL_INCHES_PER_REVOLLUTION = WHEEL_DIAMETER*Math.PI;

    /***************************************************************************************
    *      Floop Solenoid Constants                                                                    
    ****************************************************************************************/
    int FLOOP_FORWARD_CHANNEL = -1;
    int FLOOP_REVERSE_CHANNEL = -1;
}
