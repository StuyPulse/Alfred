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
        test();
        // TODO: once ratchet is working, uncomment this
        // Raises the lift once
        // if (speed > .9 && !isRaised) {
        //     Robot.tail.disengageRatchet();
        //     isRaised = true;
        // }
        // // Retracts the lift once
        // if (speed < .9 && isRaised && !isRetracted) {
        //     Robot.tail.engageRatchet();
        //     isRetracted = true;
        // }
        // // Makes the lift go up and down
        // if (speed < -0.2) {
        //     Robot.tail.setSpeed(speed);
        // }
    }

    private void test() {
        //moves the tail up and down, however on the real bot, the tail should only be able to go down
        if(Math.abs(speed) > .2) {
            Robot.tail.setSpeed(speed);
            System.out.println("Speedy" + speed);
        } else {
            Robot.tail.setSpeed(0);
            System.out.println("Stopped" + speed);
        }
            System.out.println(speed);
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
