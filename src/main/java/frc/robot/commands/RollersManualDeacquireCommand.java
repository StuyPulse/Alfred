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

public class RollersManualDeacquireCommand extends Command {

    public RollersManualDeacquireCommand() {
        requires(Robot.rollers);
    }

    @Override
    protected void execute() {
        double speed = Robot.oi.operatorGamepad.getRawLeftTriggerAxis();
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