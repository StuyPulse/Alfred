/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.Robot;
public class LiftMoveTimedCommand extends TimedCommand {

  private double speed;

  public LiftMoveTimedCommand(double timeout, double speed) {
    super(timeout);
    requires(Robot.lift);
    this.speed = speed;
  }

  @Override
  protected void execute() {
    Robot.lift.move(speed);
  }

  @Override
  protected void end() {
    Robot.lift.stop();
  }
  
}
