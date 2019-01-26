/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LiftMoveToHeightCommand extends Command {
  private double targetHeight; 

  public LiftMoveToHeightCommand(double targetHeight) {
    requires(Robot.lift);
    this.targetHeight = targetHeight; 
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.lift.setManual();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.lift.getHeight() >= targetHeight) {
      Robot.lift.moveRamp(-1);
    }else {
      Robot.lift.moveRamp(1);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    double error = targetHeight - Robot.lift.getHeight(); 
    return Math.abs(error) < 2 || 
      (Robot.lift.isAtBottom() && error < 0) || 
      (Robot.lift.isAtTop() && error > 0);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.lift.stopLift();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end(); 
  }
}
