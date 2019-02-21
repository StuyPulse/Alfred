/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class FangsLowerRollersInCommand extends CommandGroup {
    public FangsLowerRollersInCommand() {
        addParallel(new FangsLowerCommand());
        addSequential(new RollersConstantAcquireCommand(), 0.5);
    }
}