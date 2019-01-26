/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public interface RobotMap {

    /*CV VARIABLES*/  
    
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
  
    //Used to make joystick values smaller (Must be an odd number)(for CV)
    int JOYSTICK_SCALAR = 3;

    //Driver&Operator ports
    int DRIVER_PORT = -1;

    //Drivetrain ports
    int LEFT_TOP_MOTOR_PORT = -1;
    int LEFT_MIDDLE_MOTOR_PORT = -1;
    int LEFT_BOTTOM_MOTOR_PORT = -1;

    int RIGHT_TOP_MOTOR_PORT = -1;
    int RIGHT_MIDDLE_MOTOR_PORT = -1;
    int RIGHT_BOTTOM_MOTOR_PORT = -1;
  
    double WHEEL_DIAMETER = 6;
    double WHEEL_INCHES_PER_REVOLLUTION = WHEEL_DIAMETER*Math.PI;


    int FLOOP_FORWARD_CHANNEL = -1;
    int FLOOP_REVERSE_CHANNEL = -1;
}
