/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.util.Limelight;

public class AutomaticDriveCommand extends AutomaticTurnCommand {

    @Override
    protected void setSpeed() {
        quickTurn = true; // Automatic Drive Uses Quick Turn
        SmartDashboard.putBoolean("LIMELIGHT_CONNECTED:", !(Limelight.getTargetXAngle() == 0));
        if (Limelight.hasValidTarget()) {
            // Set speed depending on how far away the goal is
            double area = Limelight.getTargetArea();
            double minSpeed = SmartDashboard.getNumber("AUTODRIVE_MIN_SPEED", RobotMap.MIN_AUTO_SPEED);
            double forwardArea = SmartDashboard.getNumber("AUTODRIVE_FORWARD_AREA", RobotMap.FORWARD_AREA);
            double speedMultiplier = SmartDashboard.getNumber("AUTODRIVE_SPEED_MUL", RobotMap.AUTO_SPEED_MUL);

            double accel;
            SmartDashboard.putNumber("AutoDrive-MinSpeed:", minSpeed);
            accel = Math.max(forwardArea - area, 0);
            SmartDashboard.putNumber("AutoDrive-AreaDifference:", accel);
            boolean isNear = accel == 0;
            SmartDashboard.putBoolean("IS_NEAR", isNear);
            accel *= speedMultiplier;
            SmartDashboard.putNumber("AutoDrive-AddedSpeed:", accel);
            speed = minSpeed;
            speed += accel;
            
            SmartDashboard.putNumber("AutoDrive-FinalSpeed:", speed);

        } else {
            // if no target is found, fall back on gamepad speed
            super.setSpeed();
        }
    }
}