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
    /**
     * Runs the acquirer for cargo
     */
    public final int WAIT_TIMEOUT = -1;

    public BITHPOIN() {
        addParallel(new FloopCloseCommand());
        addSequential(new WaitCommand(WAIT_TIMEOUT));
        addSequential(new FangsRaiseCommand());
        addParallel(new FloopOpenCommand());
        addSequential(new WaitCommand(WAIT_TIMEOUT));
        addParallel(new FangsLowerCommand());
        addSequential(new RollersConstantAcquireCommand());
    }
}
