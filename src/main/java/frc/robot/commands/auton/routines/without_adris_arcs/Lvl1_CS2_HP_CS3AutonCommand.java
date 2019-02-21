/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton.routines.without_adris_arcs;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.AutomaticDriveCommand;
import frc.robot.commands.FloopCloseCommand;
import frc.robot.commands.FloopOpenCommand;
import frc.robot.commands.auton.DrivetrainAbsoluteRotateCommand;
import frc.robot.commands.auton.DrivetrainMoveInchesCommand;
import frc.robot.commands.auton.DrivetrainRelativeRotateCommand;

public class Lvl1_CS2_HP_CS3AutonCommand extends CommandGroup {
  /**
   * Start on level 1
   * Score on cargo ship (closest to level 1)
   * Go to hp
   * Score on cargo ship (first one on side)
   */
  public Lvl1_CS2_HP_CS3AutonCommand() {

    //score first hatch panel 
    addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new FloopCloseCommand());

    //get second hatch panel from hp
    addSequential(new DrivetrainMoveInchesCommand(10, -1));
    addSequential(new DrivetrainAbsoluteRotateCommand(-90, 1));
    addSequential(new DrivetrainMoveInchesCommand(10, 1));
    addSequential(new DrivetrainRelativeRotateCommand(-20, 1));
    addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new FloopOpenCommand());

    //score second hatch panel
    addSequential(new DrivetrainMoveInchesCommand(-10, 1));
    addSequential(new DrivetrainRelativeRotateCommand(20, 1));
    addSequential(new DrivetrainMoveInchesCommand(50, -1));
    addSequential(new DrivetrainAbsoluteRotateCommand(0, -1));
    addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new FloopCloseCommand());
    addSequential(new DrivetrainMoveInchesCommand(10,-1));
  }
}
