/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TailClimbCommand extends Command {

    private double speed;
    private boolean isRaised,isRetracted;

    public TailClimbCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.tail);
        isRaised = isRetracted = false;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        this.speed = Robot.oi.operatorGamepad.getLeftY();
        // Raises the lift once
        if (speed > .9 && !isRaised) {
            Robot.tail.raiseTail();
            isRaised = true;
        }
        // Retracts the lift once
        if (speed < .9 && isRaised && !isRetracted) {
            Robot.tail.resetElevator();
            isRetracted = true;
        }
        // Makes the lift go up and down
        if (speed < 0) {
            Robot.tail.setSpeed(speed);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.tail.setSpeed(0);
    }

}
