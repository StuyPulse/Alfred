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

public class TailClimbCommand extends Command {

    private double speed;
    private double currTime;
    private double startTime;

    public TailClimbCommand() {;
        requires(Robot.tail);
    }

    @Override
    protected void execute() {
        currTime = Timer.getFPGATimestamp();
        this.speed = Robot.oi.operatorGamepad.getRightY();
        if(speed > .5) {
            if(!Robot.tail.ratchetMoved()) {
                Robot.tail.disengageRatchet();
                startTime = Timer.getFPGATimestamp();
            }
            // if(currTime - startTime > .2) {
                Robot.tail.setSpeed(1.0);
            // }
        } else if(speed < -0.5) {
            if(Robot.tail.ratchetMoved()) {
                Robot.tail.engageRatchet();
            }
            Robot.tail.setSpeed(speed);
            Robot.abom.setWantPumpingStatus(true);
        } else {
            Robot.tail.stop();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.tail.stop();
    }
}