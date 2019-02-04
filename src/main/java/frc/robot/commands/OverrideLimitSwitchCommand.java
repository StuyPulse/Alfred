/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class OverrideLimitSwitchCommand extends InstantCommand {
  public OverrideLimitSwitchCommand() {
      requires (Robot.lift);
  }
//TODO: Code limit switch override, the kiddos who made lift left us no way of overriding limit switches .-.
  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
  }

}
