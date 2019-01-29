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

public class CVAutoCommand extends CVTurnCommand {

    @Override
    protected void setSpeed() {
        quickTurn = true;
        double Area = LimeLight.getTargetArea();
        if (Area != 0) {
            speed = RobotMap.MIN_SPEED + Math.max(RobotMap.FORWARD_AREA - Area, 0) * RobotMap.AUTO_SPEED;
            return;
        }
        speed *= (RobotMap.ACCELERATION_DIV - 1) / RobotMap.ACCELERATION_DIV;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return !(Robot.oi.driverGamepad.getRawTopButton());
    }
}
