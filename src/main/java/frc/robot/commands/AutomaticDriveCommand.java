/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.RobotMap.Drivetrain;
import frc.util.Limelight;
import frc.util.SmarterDashboard;

public class AutomaticDriveCommand extends AutomaticTurnCommand {

    @Override
    protected void setSpeed() {
        // if no target is found, fall back on gamepad speed
        super.setSpeed();
        if (Limelight.hasValidTarget()) {
            double speedWhileTurning   = SmarterDashboard.getNumber("SPEED_WHILE_TURNING",
                                         Drivetrain.CV.SPEED_WHILE_TURNING);
            double automaticDriveSpeed = SmarterDashboard.getNumber("AUTOMATIC_DRIVE_SPEED",
                                         Drivetrain.CV.AUTOMATIC_DRIVE_SPEED);

            double targetXValue = Math.abs(Limelight.getTargetXAngle());
            
            targetXValue /= speedWhileTurning;

            double accel = Limelight.MAX_X_ANGLE - targetXValue;
            accel /= Limelight.MAX_X_ANGLE;

            accel *= automaticDriveSpeed;

            speed += accel;
        } 
    }

    @Override
    protected void getPlayerTurn() {
        if(Limelight.hasValidTarget()) {
            turn = 0;
        } else {
            super.getPlayerTurn();
        }
    }

    @Override
    protected void setQuickTurn() {
        quickTurn = true;
    }
}
