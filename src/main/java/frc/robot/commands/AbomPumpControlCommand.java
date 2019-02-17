/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class AbomPumpControlCommand extends Command {
  private double timeSinceStateChange;

  public AbomPumpControlCommand() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.abom);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timeSinceStateChange = -1;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (!Robot.abom.wantPumping) {
      Robot.abom.stop();
    } else if (timeSinceStateChange == -1 || timeSinceStateChange > RobotMap.ABOM_DELAY_BTW_IN_AND_OUT) {
      Robot.abom.pump();
      timeSinceStateChange = Timer.getFPGATimestamp();
    }
    // if you want to pump but it hasn't been long enough, it will not do anything until the above conditional is reached
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
