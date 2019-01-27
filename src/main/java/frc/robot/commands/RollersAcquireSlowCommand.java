/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

private double speed;

public class RollersAcquireSlowCommand extends Command {
  public RollersAcquireSlowCommand() {
    requires(Robot.rollers);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    this.speed = Robot.oi.driverGamepad.getRawRightTriggerAxis();
    Robot.rollers.acquireSpeed(speed * RobotMap.SLOW_ROLLER_MULTIPLIER);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.rollers.stop();
  }

  @Override
  protected void interrupted() {
  }
}
