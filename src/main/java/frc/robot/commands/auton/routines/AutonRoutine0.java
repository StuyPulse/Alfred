/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton.routines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.DrivetrainDriveCommand;
import frc.robot.commands.FloopOpenCommand;
import frc.robot.commands.FloopCloseCommand;

public class AutonRoutine0 extends CommandGroup {

  //Level 2, Cargo Ship, Human Player, Cargo Ship

  public AutonRoutine0() {
    //TODO: add limelight code later
    addSequential(new FloopOpenCommand());
    addSequential(new DrivetrainDriveCommand()); //100 -1
    addSequential(new DrivetrainTurnCommnd(-90));
    addSequential(new DrivetrainDriveCommand()); //107 1
    addSequential(new DrivetrainTurnCommand(-90)); 
    //TODO: add limelight stuff
    addSequential(new FloopCloseCommand());
    addSequential(new DrivetrainDriveCommand()); //160 -1
    addSequential(new DrivetrainTurnCommand(-90)); 
    //TODO: limelight
    addSequential(new FloopOpenCommand());
  }
}
