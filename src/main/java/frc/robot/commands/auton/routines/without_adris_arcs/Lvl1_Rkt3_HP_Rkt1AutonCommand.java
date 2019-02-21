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

public class Lvl1_Rkt3_HP_Rkt1AutonCommand extends CommandGroup {
  /**
   * Starts backwards on level 1,
   * scores on far side of rocket,
   * goes to human player,
   * scores on close side of rocket.
   * No Adris arcs
   */

  public final double LVL2_TO_CS2 = 266.3;
  public final double RKT3_TO_CS2 = 105;
  public final double CS1_TO_CS2 = 43.5;
  public final double ANGLE_TO_RKT3 = Math.toDegrees(Math.atan(RKT3_TO_CS2 / CS1_TO_CS2));
  public final double WIDTH_OF_HP = 48;

  public Lvl1_Rkt3_HP_Rkt1AutonCommand() {

    //score first hatch panel 
    addSequential(new DrivetrainMoveInchesCommand(LVL2_TO_CS2, -1));
    addSequential(new DrivetrainRelativeRotateCommand(ANGLE_TO_RKT3, 1));
    addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new FloopCloseCommand());

    //get second hatch panel from hp
    addSequential(new DrivetrainMoveInchesCommand(10, -1));
    addSequential(new DrivetrainAbsoluteRotateCommand(180, 1));
    addSequential(new DrivetrainMoveInchesCommand(100, 1));
    addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new FloopOpenCommand());

    //score second hatch panel
    addSequential(new DrivetrainMoveInchesCommand(10, -1));
    addSequential(new DrivetrainRelativeRotateCommand(180, 1));
    addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new FloopCloseCommand());
    addSequential(new DrivetrainMoveInchesCommand(10,-1));
  }
}
