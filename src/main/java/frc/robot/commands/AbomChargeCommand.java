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

public class AbomChargeCommand extends Command {
    private boolean climb;
    private long start;

    public AbomChargeCommand(boolean climb) {
        requires(Robot.abom);
        this.climb = climb;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        start = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // Fire the piston repeatedly so that it fully extends before retracting 
        if (climb && System.currentTimeMillis() - start <= RobotMap.ABOM_CHARGE_DELAY) {
            Robot.abom.toggle();
            start = System.currentTimeMillis();
        }
        // Count the times execute has run, to know when to fire the pistons
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return !climb;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.abom.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {

    }
}
