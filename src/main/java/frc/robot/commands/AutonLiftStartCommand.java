/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.RobotMap;

public class AutonLiftStartCommand extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutonLiftStartCommand() {
    addSequential(new LiftTiltFowardCommand());
    addSequential(new LiftMoveToBottomCommand(), 1.5);
    addSequential(new WaitCommand(0.5));
    addSequential(new LiftMoveToHeightCommand(RobotMap.LEVEL_1_HEIGHT));
  }
}
