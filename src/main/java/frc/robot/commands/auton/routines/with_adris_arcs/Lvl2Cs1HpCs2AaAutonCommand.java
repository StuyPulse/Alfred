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
import frc.robot.commands.auton.DrivetrainDriveCurveCommand;
import frc.robot.commands.auton.DrivetrainMoveInchesCommand;
import frc.robot.commands.auton.DrivetrainRelativeRotateCommand;

public class Lvl2Cs1HpCs2AaAutonCommand extends CommandGroup {
  /**
   * Starts on level 2
   * scores on cargo ship (front, with Adris Arcs)
   * goes to hp (with Adris Arcs)
   * scores on cargo ship (side 1, with Adris Arcs)
   */
  public final double BACK_UP_DISTANCE = 10;
  public final double LV2_1_GO_TO_MID_TO_CS1 = 94.5; //2400 mm
  public final double LV2_2_MID_ALIGN_TO_CS1 = 69; //1750 mm
  public final double LV2_3_MID_GO_TO_CS1 = 78.75; //2000 mm
  public final double CS1_1_GO_TO_MID1_TO_HP = 98.5; //2500 mm
  public final double CS1_2_MID1_GO_TO_MID2_TO_HP = 106; //2700 mm
  public final double CS1_3_MID2_ALIGN_TO_HP = 10;
  public final double CS1_4_MID2_GO_TO_HP = 106; //2700 mm
  public final double HP_1_GO_TO_MID1_TO_CS2 = 138; //3500 mm
  public final double HP_2_MID1_ADJUST_TO_CS2 = 10;
  public final double HP_3_MID1_GO_TO_MID2_TO_CS2 = 138; //3500 mm
  public final double HP_4_MID2_GO_TO_CS2 = 80; //2000 mm

  public Lvl2Cs1HpCs2AaAutonCommand(boolean isRobotOnRight) {
    DrivetrainDriveCurveCommand driveToCs1 = new DrivetrainDriveCurveCommand(
      LV2_1_GO_TO_MID_TO_CS1 + 
      LV2_2_MID_ALIGN_TO_CS1 + 
      LV2_3_MID_GO_TO_CS1);
    driveToCs1.addTurn(LV2_1_GO_TO_MID_TO_CS1, isRobotOnRight ? 90 : -90);
    driveToCs1.addTurn(LV2_1_GO_TO_MID_TO_CS1 + LV2_2_MID_ALIGN_TO_CS1, isRobotOnRight ? -90 : 90);

    DrivetrainDriveCurveCommand driveToHp = new DrivetrainDriveCurveCommand(
      CS1_1_GO_TO_MID1_TO_HP + 
      CS1_2_MID1_GO_TO_MID2_TO_HP + 
      CS1_3_MID2_ALIGN_TO_HP + 
      CS1_4_MID2_GO_TO_HP);
    driveToHp.addTurn(CS1_1_GO_TO_MID1_TO_HP, isRobotOnRight ? -90 : 90);
    driveToHp.addTurn(CS1_1_GO_TO_MID1_TO_HP + CS1_2_MID1_GO_TO_MID2_TO_HP, isRobotOnRight ? 90 : -90);
    driveToHp.addTurn(CS1_1_GO_TO_MID1_TO_HP + CS1_2_MID1_GO_TO_MID2_TO_HP + CS1_3_MID2_ALIGN_TO_HP, isRobotOnRight ? -90 : 90);

    DrivetrainDriveCurveCommand driveToCs2 = new DrivetrainDriveCurveCommand(
      HP_1_GO_TO_MID1_TO_CS2 + 
      HP_2_MID1_ADJUST_TO_CS2 + 
      HP_3_MID1_GO_TO_MID2_TO_CS2 + 
      HP_4_MID2_GO_TO_CS2);
    driveToCs2.addSpeedChange(HP_1_GO_TO_MID1_TO_CS2, -1);
    driveToCs2.addTurn(HP_1_GO_TO_MID1_TO_CS2, isRobotOnRight ? -90 : 90);
    driveToCs2.addSpeedChange(HP_1_GO_TO_MID1_TO_CS2 + HP_2_MID1_ADJUST_TO_CS2, 1);
    driveToCs2.addTurn(HP_1_GO_TO_MID1_TO_CS2 + HP_2_MID1_ADJUST_TO_CS2, isRobotOnRight ? -90 : 90);
    driveToCs2.addTurn(HP_1_GO_TO_MID1_TO_CS2 + HP_2_MID1_ADJUST_TO_CS2 + HP_3_MID1_GO_TO_MID2_TO_CS2, isRobotOnRight ? 90 : -90);

    // score on cargo ship
    addSequential(driveToCs1);
    addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new FloopCloseCommand());

    // got to HP
    addSequential(new DrivetrainMoveInchesCommand(BACK_UP_DISTANCE, -1));
    addSequential(new DrivetrainRelativeRotateCommand(-90, 1));
    addSequential(driveToHp);
    addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new FloopOpenCommand());

    // score on cargo ship
    addSequential(driveToCs2);
    addSequential(new AutomaticDriveCommand(), 5);
    addSequential(new FloopCloseCommand());
    addSequential(new DrivetrainMoveInchesCommand(BACK_UP_DISTANCE, -1));


  }
}
