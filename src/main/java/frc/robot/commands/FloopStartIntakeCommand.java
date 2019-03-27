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

public class FloopStartIntakeCommand extends Command {

  double startTime;

  public FloopStartIntakeCommand() {
    super();
    requires(Robot.floop);
  }

  @Override 
  protected void initialize() {
    Robot.floop.close();
    Robot.floop.pull();
  }

  @Override
  protected void execute() {
    if (Robot.isGamePieceDetected()) {
      Robot.floop.open();
      startTime = Timer.getFPGATimestamp();
      if (Timer.getFPGATimestamp() - startTime >= 1) {
        Robot.floop.push();
      }
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}
