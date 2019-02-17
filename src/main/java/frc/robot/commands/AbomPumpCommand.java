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

public class AbomPumpCommand extends Command {
    private double lastToggled;

    public AbomPumpCommand() {
        requires(Robot.abom);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        lastToggled = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // Fire the piston repeatedly so that it fully extends before retracting 
        if (Timer.getFPGATimestamp() - lastToggled <= RobotMap.ABOM_CHARGE_DELAY_SEC) {
            Robot.abom.pump();
            lastToggled = Timer.getFPGATimestamp();
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
        Robot.abom.stop();
    }
    @Override
    protected void interrupted() {

    }
}
