/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton.routines.with_adris_arcs;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.AutomaticDriveCommand;
import frc.robot.commands.FloopCloseCommand;
import frc.robot.commands.FloopOpenCommand;
import frc.robot.commands.auton.DrivetrainAbsoluteRotateCommand;
import frc.robot.commands.auton.DrivetrainDriveCurveCommand;
import frc.robot.commands.auton.DrivetrainMoveInchesCommand;

public class Lvl1Cs2HpCs3AutonCommand extends CommandGroup {
  /**
   Start on level 1
   * Score on cargo ship (closest to level 1)
   * Go to hp
   * Score on cargo ship (first one on side)
   * 
   * With Adris Arcs
   */

  public final double LVL1_TO_CS2 = 177;
  public final double BACKUP = 10;
  public final double CS2_TO_HP = 157;
  public final double LINE_UP_WITH_HP = 55;
  public final double HP_TO_CS3 = 181;

  public Lvl1Cs2HpCs3AutonCommand(boolean isRobotOnRight) {

    DrivetrainDriveCurveCommand driveCommandToHp = 
    new DrivetrainDriveCurveCommand(CS2_TO_HP + LINE_UP_WITH_HP);
    driveCommandToHp.addTurn(0, 180);
    driveCommandToHp.addTurn(CS2_TO_HP, isRobotOnRight ? -90 : 90);
    driveCommandToHp.addTurn(CS2_TO_HP + LINE_UP_WITH_HP, 180);

    DrivetrainDriveCurveCommand driveCommandToCs3 = 
    new DrivetrainDriveCurveCommand(BACKUP + LINE_UP_WITH_HP + HP_TO_CS3);
    driveCommandToCs3.addSpeedChange(0, -1);
    driveCommandToCs3.addTurn(BACKUP, isRobotOnRight ? -90 : 90);
    driveCommandToCs3.addSpeedChange(BACKUP + LINE_UP_WITH_HP, 1);
    driveCommandToCs3.addTurn(BACKUP + LINE_UP_WITH_HP, 180);
    driveCommandToCs3.addTurn(BACKUP + LINE_UP_WITH_HP + HP_TO_CS3, isRobotOnRight ? 90 : -90);

    //score first hatch panel
    addSequential(new DrivetrainMoveInchesCommand(LVL1_TO_CS2, 1));
    addSequential(new DrivetrainAbsoluteRotateCommand(isRobotOnRight ? -90 : 90, 1));
    addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new FloopCloseCommand());

    //get second hatch panel
    addSequential(new DrivetrainMoveInchesCommand(BACKUP, -1));
    addSequential(driveCommandToHp);
    addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new FloopOpenCommand());

    //score second hatch panel
    addSequential(driveCommandToCs3);
    addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new FloopCloseCommand());
  }
}
