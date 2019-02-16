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
    private boolean isRaised;
    private boolean isRetracted;

    public TailClimbCommand() {;
        requires(Robot.tail);
        this.isRaised = false;
        this.isRetracted = false;
    }

    @Override
    protected void execute() {
        this.speed = Robot.oi.operatorGamepad.getRightY();
        if(speed > .5)  {
            if(!Robot.tail.ratchetMoved()) {
                Robot.tail.disengageRatchet();
            }
            Robot.tail.setSpeed(0.2);
        }
        if(speed < .5) {
            if(Robot.tail.ratchetMoved()) {
                Robot.tail.engageRatchet();
            }
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
