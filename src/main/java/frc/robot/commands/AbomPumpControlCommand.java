/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class AbomPumpControlCommand extends Command {
    
    private double lastPumped;

    public AbomPumpControlCommand() {
        requires(Robot.abom);
    }

    @Override
    protected void initialize() {
        lastPumped = -1;
    }

    @Override
    protected void execute() {
        if (!Robot.abom.getWantPumpingStatus()) {
            Robot.abom.pumpOut();
        } else if (!Robot.abom.get() && Robot.abom.shouldTakeAction(lastPumped, RobotMap.ABOM_TIME_TO_EXTEND)) {
            Robot.abom.pumpIn();
            lastPumped = Timer.getFPGATimestamp();
        } else if (Robot.abom.get() && Robot.abom.shouldTakeAction(lastPumped, RobotMap.ABOM_TIME_TO_RETRACT)) {
            Robot.abom.pumpOut();
            lastPumped = Timer.getFPGATimestamp();
        }
        // if you want to pump but it hasn't been long enough, it will not do anything
        // until the above conditional is reached
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}