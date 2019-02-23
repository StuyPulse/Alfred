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

public class Lvl1Cs1HpCs2AutonCommand extends CommandGroup {
  /**
   * Start on level 1
   * Score on cargo ship (closest to level 1)
   * Go to hp
   * Score on cargo ship (first one on side)
   */

  public final double LVL1_TO_CS1 = 20;
  public final double BACKUP_DISTANCE = 15;
  public final double GET_CLOSER_TO_HP = 10;
  public final double FACE_HP_ANGLE = 20;
  public final double ROCKET_TO_HP = 30;
  public final double TURN_TO_CS3 = 10;
  public final double HP_TO_CS3 = 150;

  public Lvl1Cs1HpCs2AutonCommand(boolean isRobotOnRight) {

    //score first hatch panel 
    //addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new DrivetrainMoveInchesCommand(LVL1_TO_CS1, 1));
    addSequential(new FloopCloseCommand());

    //get second hatch panel from hp
    addSequential(new DrivetrainMoveInchesCommand(BACKUP_DISTANCE, -1));
    addSequential(new DrivetrainAbsoluteRotateCommand(isRobotOnRight ? 90 : -90, 1));
    addSequential(new DrivetrainMoveInchesCommand(GET_CLOSER_TO_HP, 1));
    //addSequential(new DrivetrainRelativeRotateCommand(isRobotOnRight ? FACE_HP_ANGLE : -FACE_HP_ANGLE, 1));
    addSequential(new DrivetrainRelativeRotateCommand(180, 1));
    //addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new DrivetrainMoveInchesCommand(ROCKET_TO_HP, 1));
    addSequential(new FloopOpenCommand());

    //score second hatch panel
    addSequential(new DrivetrainMoveInchesCommand(BACKUP_DISTANCE, -1));
    addSequential(new DrivetrainRelativeRotateCommand(isRobotOnRight ? -TURN_TO_CS3 : TURN_TO_CS3, 1));
    addSequential(new DrivetrainMoveInchesCommand(HP_TO_CS3, -1));
    addSequential(new DrivetrainAbsoluteRotateCommand(isRobotOnRight ? -90 : 90, 1));
    //addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new DrivetrainMoveInchesCommand(10, 1));
    addSequential(new FloopCloseCommand());
    addSequential(new DrivetrainMoveInchesCommand(BACKUP_DISTANCE,-1));
  }
}
