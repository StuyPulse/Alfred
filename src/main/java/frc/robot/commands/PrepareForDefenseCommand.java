/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PrepareForDefenseCommand extends CommandGroup {
    /*
     * A series of commands to bring the robot to a legal position to play defense
     */

    //TODO: find the actual value to bring the carriage to the top of the first stage
    private final double TOP_OF_FIRST_STAGE = 10;
    public PrepareForDefenseCommand() {
        addSequential(new LiftMoveToHeightCommand(TOP_OF_FIRST_STAGE));
        // addSequential(new LiftMoveTimedCommand(0.5, .5));
        addSequential(new LiftTiltBackCommand());
    }
}
