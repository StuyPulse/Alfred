/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.util.LimeLight;

public class AutomaticDrive extends AutoTurnDrive {

    @Override
    protected void setSpeed() {
        quickTurn = true; // Automatic Drive Uses Quick Turn
        final double AREA = LimeLight.getTargetArea();
        if (AREA != 0) {
            // Set speed depending on how far away the goal is
            speed = RobotMap.MIN_AUTO_SPEED + Math.max(RobotMap.FORWARD_AREA - AREA, 0) * RobotMap.AUTO_SPEED_MUL;
        } else {
            // Stop Robot
            speed = 0;
        }
    }
}
