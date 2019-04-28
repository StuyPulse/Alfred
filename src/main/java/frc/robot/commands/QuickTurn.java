/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.util.SmarterDashboard;
import frc.robot.RobotMap.Drivetrain;
/**
 * Add your docs here.
 */
public interface QuickTurn {
    public interface QuickTurnFunction{void setQuickTurn(DrivetrainCommand drive);}
    QuickTurnFunction CV = (DrivetrainCommand drive) -> {drive.quickTurn = false;};
    QuickTurnFunction RAMP = (DrivetrainCommand drive) -> {
            double THRESHOLD = Drivetrain.QuickTurn.THRESHOLD;
            double SPEED = Drivetrain.QuickTurn.SPEED;
            // Enable Quick Turn if robot is not moving
            drive.quickTurn = Math.abs(drive.speed) < SmarterDashboard.getNumber("QUICKTURN_THRESHOLD", 
                                            THRESHOLD);

            if (drive.quickTurn) {
                // Slow down quick turn as it is only used
                // when the driver is scoring
                drive.turn *= SmarterDashboard.getNumber("QUICKTURN_SPEED", SPEED);
            }
    };
    
}
