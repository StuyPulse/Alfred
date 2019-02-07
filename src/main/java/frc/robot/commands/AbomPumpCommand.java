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

public class AbomPumpCommand extends Command {
    private long lastToggled;

    public AbomPumpCommand() {
        requires(Robot.abom);
    }

    @Override
    protected void initialize() {
        lastToggled = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        // Fire the piston repeatedly so that it fully extends before retracting 
        if (System.currentTimeMillis() - lastToggled <= RobotMap.ABOM_CHARGE_DELAY_MS) {
            Robot.abom.toggle();
            lastToggled = System.currentTimeMillis();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.abom.stop();
    }
    @Override
    protected void interrupted() {

    }
}
