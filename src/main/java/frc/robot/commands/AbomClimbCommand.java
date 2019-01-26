/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AbomClimbCommand extends Command {
    private boolean climb;
    private int count;

    public AbomClimbCommand(boolean climb) {
        requires(Robot.abom);
        this.climb = climb;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // Count the times execute has run, to know when to fire the pistons
        count++;
        // Fire the piston repeatedly so that it fully extends before retracting 
        if (climb && count % 15 == 0) {
            Robot.abom.toggle();
        }
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
