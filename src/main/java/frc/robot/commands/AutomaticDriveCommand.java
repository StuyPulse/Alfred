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

public class AutomaticDriveCommand extends AutomaticTurnCommand {

    @Override
    protected void setSpeed() {
        quickTurn = true; // Automatic Drive Uses Quick Turn

        SmartDashboard.putBoolean("LIMELIGHT_CONNECTED:", !(Robot.limelight.getTargetXAngle() == 0));
        if (Robot.limelight.hasValidTarget()) {
            // Set speed depending on how far away the goal is
            double area = Robot.limelight.getTargetArea();
            double minSpeed = SmartDashboard.getNumber("AUTODRIVE_MIN_SPEED", RobotMap.MIN_AUTO_SPEED);
            double forwardArea = SmartDashboard.getNumber("AUTODRIVE_FORWARD_AREA", RobotMap.FORWARD_AREA);
            double speedMultiplier = SmartDashboard.getNumber("AUTODRIVE_SPEED_MUL", RobotMap.AUTO_SPEED_MUL);
            SmartDashboard.putNumber("AutoDrive-MinSpeed:", minSpeed);
            SmartDashboard.putNumber("AutoDrive-AreaDifference:", forwardArea - area);
            SmartDashboard.putNumber("AutoDrive-AddedSpeed:", (forwardArea - area)*speedMultiplier);
            speed = minSpeed;
            speed += Math.max(forwardArea - area, 0) * speedMultiplier;
            SmartDashboard.putNumber("AutoDrive-FinalSpeed:", speed);
        } else {
            // if no target is found, fall back on gamepad speed
            super.setSpeed();
        }
    }
}