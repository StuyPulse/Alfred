/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.RobotMap;
import frc.util.LimeLight;

public class AutomaticDriveCommand extends AutomaticTurnCommand {

    @Override
    protected void setSpeed() {
        quickTurn = true; // Automatic Drive Uses Quick Turn
        double Area = LimeLight.getTargetArea();
        if (Area != 0) {
            // Set speed depending on how far away the goal is
            speed = RobotMap.MIN_AUTO_SPEED + Math.max(RobotMap.FORWARD_AREA - Area, 0) * RobotMap.AUTO_SPEED_MUL;
        } else {
            // if no target is found, fall back on gamepad speed
            super.setSpeed();
        }
    }
}
