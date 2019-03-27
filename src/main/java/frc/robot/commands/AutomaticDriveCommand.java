/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.util.Limelight;

public class AutomaticDriveCommand extends AutomaticTurnCommand {

    @Override
    protected void setSpeed() {
        // if no target is found, fall back on gamepad speed
        super.setSpeed();
        if (Limelight.hasValidTarget()) {
            speed += RobotMap.AUTOMATIC_DRIVE_SPEED;

            /** Scaling speed based on XAngle
             *  double turnSpeedDiv = 2;
             *  double targetXValue = Math.abs(Limelight.getTargetXAngle());
             *  double accel = Limelight.MAX_X_ANGLE - targetXValue / turnSpeedDiv;
             *  accel /= Limelight.MAX_X_ANGLE;
             *  accel *= RobotMap.AUTOMATIC_DRIVE_SPEED;
             *  speed += accel;
             */
        }
    }
}