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
import frc.robot.RobotMap;

public class RollersManualAcquireCommand extends CommandGroup {

    public RollersManualAcquireCommand() {
        addParallel(new FloopPrepareForRollersCommand());
        addSequential(new RollersConstantAcquire());
    }

    public class RollersConstantAcquire extends Command {

        public RollersConstantAcquire() {
            requires(Robot.rollers);
        }

        @Override
        protected void execute() {
            double speed = Robot.oi.operatorGamepad.getRawRightTriggerAxis();
            double tunedSpeed = Math.pow(speed, 2) * RobotMap.SLOW_ROLLER_MAXIMUM;
            Robot.rollers.setSpeed(-tunedSpeed);
        }

        @Override
        protected boolean isFinished() {
            return false;
        }

        @Override
        protected void end() {
            Robot.rollers.stop();
        }
    }
}