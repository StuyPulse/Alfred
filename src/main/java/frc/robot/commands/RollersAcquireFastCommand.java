/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

private double speed;

public class RollersAcquireFastCommand extends Command {
  public RollersAcquireFastCommand() {
    requires(Robot.rollers);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    this.speed = Robot.oi.driverGamepad.getRawRightBumper();
    Robot.rollers.acquireSpeed(speed);
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
