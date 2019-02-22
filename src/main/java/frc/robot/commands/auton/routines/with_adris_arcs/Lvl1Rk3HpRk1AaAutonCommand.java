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

public class Lvl1Rk3HpRk1AaAutonCommand extends CommandGroup {
  /**
   * Starts backwards on level 1,
   * scores on far side of rocket,
   * goes to human player,
   * scores on close side of rocket.
   * 
   * With Adris Arcs
   */

  public final double LVL1_TO_CS2 = 216;
  public final double CS2_TO_RK3 = 87;
  public final double BACKUP = 10;
  public final double PASS_RKT = 138;
  public final double CENTER_WITH_HP = 39;
  public final double RK1_TO_HP = 79;
  
  public Lvl1Rk3HpRk1AaAutonCommand(boolean isRobotOnRight) {

    DrivetrainDriveCurveCommand driveCommandToRk3 = 
      new DrivetrainDriveCurveCommand(LVL1_TO_CS2 + CS2_TO_RK3);
    driveCommandToRk3.addTurn(LVL1_TO_CS2, isRobotOnRight? 90 : -90);

    DrivetrainDriveCurveCommand driveCommandToHp = 
      new DrivetrainDriveCurveCommand(PASS_RKT + CENTER_WITH_HP + RK1_TO_HP);
    driveCommandToHp.addTurn(0, 180);
    driveCommandToHp.addTurn(PASS_RKT, isRobotOnRight? -90 : 90);
    driveCommandToHp.addTurn(PASS_RKT + CENTER_WITH_HP, 180);

    addSequential(driveCommandToRk3);
    addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new FloopCloseCommand());
    addSequential(new DrivetrainMoveInchesCommand(BACKUP, -1));
    addSequential(driveCommandToHp);
    addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new FloopOpenCommand());
    addSequential(new DrivetrainAbsoluteRotateCommand(0, 1));
    addSequential(new AutomaticDriveCommand());
    addSequential(new FloopCloseCommand());
  }
}
