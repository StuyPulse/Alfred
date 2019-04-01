/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class RollersConstantAcquireCommand extends CommandGroup {

    public RollersConstantAcquireCommand() {
        addParallel(new FloopPrepareForRollersCommand());
        addSequential(new RollersConstantAcquire());
    }

    public class RollersConstantAcquire extends Command {

        public RollersConstantAcquire() {
            requires(Robot.rollers);
        }

        @Override
        protected void initialize() {
          Robot.rollers.enableRamping();
        }    

        @Override
        protected void execute() {
            if(Robot.isGamePieceDetected()) {
                Robot.rollers.setSpeed(-0.2);
            } else {
                Robot.rollers.acquire();
            }
        }

        @Override
        protected boolean isFinished() {
            return false;
        }

        @Override
        protected void end() {
            Robot.rollers.stop();
            Robot.rollers.disableRamping();
        }
    }
}