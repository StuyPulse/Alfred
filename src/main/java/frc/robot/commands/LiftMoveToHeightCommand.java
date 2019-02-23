/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LiftMoveToHeightCommand extends Command {
    private double targetHeight;
    private final double ACCEPTED_ERROR_RANGE = .5;

    public LiftMoveToHeightCommand(double targetHeight) {
        requires(Robot.lift);
        this.targetHeight = targetHeight;
    }

    @Override
    protected void execute() {
        if (Robot.lift.getHeight() > targetHeight) {
            Robot.lift.move(-0.5);
        } else{
            Robot.lift.move(0.5);
        }
    }

    @Override
    protected boolean isFinished() {
        // Ends if the lift is close enough to the target or at the bottom and top.
        double error = targetHeight - Robot.lift.getHeight();
        return Math.abs(error) < ACCEPTED_ERROR_RANGE 
            || (Robot.lift.isAtBottom() && error < 0)
            || Math.abs(Robot.oi.operatorGamepad.getLeftY()) > 0.05;
    }

    @Override
    protected void end() {
        Robot.lift.stop();
    }
}