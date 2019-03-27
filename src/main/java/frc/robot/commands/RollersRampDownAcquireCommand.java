/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.Robot;

public class RollersRampDownAcquireCommand extends TimedCommand {
    
    private double startTime;
    private double currTime;
    private double interval;

    public RollersRampDownAcquireCommand(double timeout) {
        super(timeout);
        requires(Robot.rollers);
        interval = timeout/4;
    }

    @Override
    protected void initialize() {
        startTime = Timer.getFPGATimestamp();
        Robot.floop.prepareForRollers();
    }

    @Override
    protected void execute() {
        currTime = Timer.getFPGATimestamp();
        if (currTime - startTime < interval) { //if you're in the 1st interval
            Robot.rollers.setSpeed(0.8);
        } else if (currTime - startTime < interval * 2) { //if you're in the 2nd interval
            Robot.rollers.setSpeed(0.6);
        } else if (currTime - startTime < interval * 3) { //if you're in the 3rd interval
            Robot.rollers.setSpeed(0.4);
        } else { // if you're in the 4th interval
            Robot.rollers.setSpeed(0.2);
        }
    }

    @Override
    protected void end() {
        Robot.rollers.stop();
    }
}