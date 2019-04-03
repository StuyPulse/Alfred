/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                                                         */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import frc.util.Limelight;
import frc.util.Limelight.LEDMode;

public class FloopControlCommand extends Command {
    public FloopControlCommand() {
        requires(Robot.floop);
    }

    public class BlinkThread extends Thread {
        public void run() {
            Limelight.setLEDMode(LEDMode.FORCE_BLINK);
            
            try {
                Thread.sleep(500);
            } catch(InterruptedException e) { 
                System.out.println(e);
            } 

            Limelight.setLEDMode(LEDMode.PIPELINE);
        }
    }

    BlinkThread thread = new BlinkThread();

    @Override
    protected void execute() {
        if (Robot.oi.operatorGamepad.getRawTopButton()) {
            Robot.floop.push();
        } else {
            Robot.floop.pull();
        }

        if (Robot.oi.operatorGamepad.getRawLeftButton()) {
            Robot.floop.close();
        } else if(Robot.oi.operatorGamepad.getRawRightButton() && Robot.isGamePieceDetected()) {
            Robot.floop.open();
            
            if(!thread.isAlive()) {
                thread = new BlinkThread();
                thread.start();
            }
        } else if(Robot.oi.operatorGamepad.getRawRightButton()) {
            Robot.floop.close();
        } else {
            Robot.floop.open();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
