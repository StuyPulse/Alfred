/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton.routines.with_adris_arcs;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Lvl2_CS1_HP_CS2AutonCommand extends CommandGroup {
  /**
   * Starts on level 2
   * scores on cargo ship (front, with Adris Arcs)
   * goes to hp (with Adris Arcs)
   * scores on cargo ship (side 1, with Adris Arcs)
   */
  public final double BACK_UP_DISTANCE = 10;
  public final double LV2_1_GO_TO_MID_TO_CS1 = //2400 mm
  public final double LV2_2_MID_ALIGN_TO_CS1 = //1750 mm
  public final double LV2_3_MID_GO_TO_CS =  //2000 mm
  public final double CS1_1_GO_TO_MID1_TO_HP = //2500 mm
  public final double CS1_2_MID1_GO_TO_MID2_TO_HP = //2700 mm
  public final double CS1_3_MID2_ALIGN_TO_HP = //100 mm
  public final double CS1_4_MID2_GO_TO_HP = //2700 mm
  public final double HP_1_GO_TO_MID1_TO_CS2 = //3500 mm
  public final double HP_2_MID1_ADJUST_TO_CS2 = //100 mm
  public final double HP_3_MID1_GO_TO_MID2_TO_CS2 = //3500 mm
  public final double HP_4_MID2_GO_TO_CS2 = //2000 mm

  public Lvl2_CS1_HP_CS2AutonCommand() {
  }
}
