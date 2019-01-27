/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
public class Rollers extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private WPI_TalonSRX motor;

  @Override
  public void initDefaultCommand() {
    motor = new WPI_TalonSRX(RobotMap.ROLLER_MOTOR_PORT);
  }

  public void stop() {
    motor.set(0);
  }

  public void acquireSpeed(double speed) {
    motor.set(speed);
  }

  
}
