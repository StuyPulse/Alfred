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
public class RobotMap {
  /**************************************************************************
   * Lift Ports
   *************************************************************************/

  public static final int LIFT_MASTER_TALON_MOTOR_PORT = -1;
  public static final int LIFT_FOLLOWER_TALON_MOTOR_PORT = -1;

  public static final int LIFT_TILT_SOLENOID_FORWARD_PORT = -1;
  public static final int LIFT_TILT_SOLENOID_REVERSE_PORT = -1;

  public static final int LIFT_BRAKE_SOLENOID_FORWARD_PORT = -1;
  public static final int LIFT_BRAKE_SOLENOID_REVERSE_PORT = -1;

  public static final int LIFT_TOP_LIMIT_SWITCH_PORT = -1; 
  public static final int LIFT_BOTTOM_LIMIT_SWITCH_PORT = -1; 

  /**************************************************************************
   * Lift Constants
   *************************************************************************/

  public static final double LIFT_ENCODER_RAW_MULTIPLIER = 1;
  public static final double LIFT_MIN_SPEED = 0.1;
  public static final double LIFT_MAX_HEIGHT = 1;  
  public static final double LIFT_RAMP_HEIGHT_THRESHOLD = 1;
  public static final double LIFT_RAMP_SLOPE = 1;   
}
