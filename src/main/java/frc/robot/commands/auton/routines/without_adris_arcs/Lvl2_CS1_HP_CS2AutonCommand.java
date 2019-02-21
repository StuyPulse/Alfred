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
import frc.robot.commands.auton.DrivetrainMoveInchesCommand;
import frc.robot.commands.auton.DrivetrainRelativeRotateCommand;

public class Lvl2_CS1_HP_CS2AutonCommand extends CommandGroup {
  /** 
   * Start on level 2
   * Score on cargo ship (closest to level 2)
   * Go to hp
   * Score on cargo ship (closest to level 1)
  */
  public final double ANGLE_TO_HP = Math.toDegrees(Math.atan(107 / 100));
  public final double CS1_TO_HP = Math.sqrt(107 * 107 / 100 / 100);

  public Lvl2_CS1_HP_CS2AutonCommand() {
    // score first hatch panel
    addSequential(new FloopOpenCommand());
    addSequential(new AutomaticDriveCommand());
    addSequential(new FloopCloseCommand());

    // go to hp, acquire hatch panel
    addSequential(new DrivetrainMoveInches(10, -1));
    addSequential(new DrivetrainRelativeRotate(-180 + ANGLE_TO_HP);
    addSequential(new DrivetrainDriveInchesCommand(CS1_TO_HP);
    addSequential(new AutomaticDriveCommand());
    addSequential(new FloopOpenCommand());
    
    // score second hatch panel
    addSequential(new DrivetrainRelativeRotateCommand(20, -1));
    addSequential(new DrivetrainMoveInchesCommand(50, -1));
    addSequential(new DrivetrainAbsoluteRotateCommand(90, -1));
    addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new FloopCloseCommand());
    addSequential(new DrivetrainMoveInchesCommand(10, -1))
  }
}
