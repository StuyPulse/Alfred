/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Robot;

public class FloopStopIntakeCommand extends CommandGroup {

  public FloopStopIntakeCommand() {
    if (!Robot.floop.isOpen() || !Robot.floop.pushed()) {
      addSequential(new FloopOpenCommand());
      addSequential(new WaitCommand(1));
      addSequential(new FloopPushCommand());
    }
  }
}
