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

public class Lvl2_CS1_HP_CS2AutonCommand extends CommandGroup {
  /** 
   * Start on level 2
   * Score on cargo ship (closest to level 2)
   * Go to hp
   * Score on cargo ship (closest to level 1)
  */
  public final double BACKUP_DISTANCE = 10;
  public final double ANGLE_TO_HP = -180 + Math.toDegrees(Math.atan(107 / 100));
  public final double CS1_GET_CLOSER_TO_HP = Math.sqrt(107 * 107 +  100 * 100);
  public final double ANGLE_TO_CS2 = Math.toDegrees(Math.atan(110 / 185));
  public final double HP_TO_CS2 = Math.sqrt(110 * 110 + 185 * 185);

  public Lvl2_CS1_HP_CS2AutonCommand(boolean isRobotOnRight) {
    // score first hatch panel
    addSequential(new FloopOpenCommand());
    addSequential(new AutomaticDriveCommand());
    addSequential(new FloopCloseCommand());

    // go to hp, acquire hatch panel
    addSequential(new DrivetrainMoveInchesCommand(BACKUP_DISTANCE, -1));
    addSequential(new DrivetrainRelativeRotateCommand(isRobotOnRight ? ANGLE_TO_HP : -ANGLE_TO_HP, 1));
    addSequential(new DrivetrainMoveInchesCommand(CS1_GET_CLOSER_TO_HP, 1));
    addSequential(new AutomaticDriveCommand());
    addSequential(new FloopOpenCommand());
    
    // score second hatch panel
    addSequential(new DrivetrainMoveInchesCommand(BACKUP_DISTANCE, -1));
    addSequential(new DrivetrainRelativeRotateCommand(isRobotOnRight ? ANGLE_TO_CS2 : -ANGLE_TO_CS2, -1));
    addSequential(new DrivetrainMoveInchesCommand(HP_TO_CS2, -1));
    addSequential(new DrivetrainAbsoluteRotateCommand(0, -1));
    addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new FloopCloseCommand());
    addSequential(new DrivetrainMoveInchesCommand(BACKUP_DISTANCE, -1));
  }
}
