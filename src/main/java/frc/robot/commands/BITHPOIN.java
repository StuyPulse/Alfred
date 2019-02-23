/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class BITHPOIN extends CommandGroup {

    //Bring In The Hatch Panel Orient It Nicely

    public final double WAIT_TIMEOUT = 0.7;

    public BITHPOIN() {
        addParallel(new FloopCloseCommand());
        addSequential(new FangsRaiseCommand());
        addSequential(new LiftMoveTimedCommand(0.3, 0.5));
        addSequential(new WaitCommand(WAIT_TIMEOUT));
        addParallel(new FloopOpenCommand());
        addParallel(new FangsLowerCommand());
        addSequential(new RollersConstantAcquireCommand(), 1.5);
    }
}
