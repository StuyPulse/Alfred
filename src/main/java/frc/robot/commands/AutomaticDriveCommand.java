/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.RobotMap;
import frc.util.Limelight;

public class AutomaticDriveCommand extends AutomaticTurnCommand {

    @Override
    protected void setSpeed() {
        // Automatic Drive Uses Quick Turn
        quickTurn = true; 

        // Run auto drive when target is in view
        if (Limelight.hasValidTarget()) {
            double area = Limelight.getTargetArea();

            // Set speed depending distance of target
            double accel; 
            accel = Math.max(RobotMap.FORWARD_AREA - area, 0);
            accel *= RobotMap.AUTO_SPEED_MUL;

            speed = RobotMap.MIN_AUTO_SPEED;
            speed += accel;
        } else {
            // if no target is found, fall back on gamepad speed
            super.setSpeed();
        }
    }
}