/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class LiftMoveToHeightCommand extends Command {
    private double targetHeight;

    public LiftMoveToHeightCommand(double targetHeight) {
        requires(Robot.lift);
        this.targetHeight = targetHeight;
    }

    @Override
    protected void execute() {
        Robot.lift.moveMagic(targetHeight);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(Robot.lift.getHeight() - targetHeight) > RobotMap.LIFT_RAMP_MOVE_TO_HEIGHT_THRESHOLD;
    }

    @Override
    protected void end() {
        Robot.lift.stop();
    }
}