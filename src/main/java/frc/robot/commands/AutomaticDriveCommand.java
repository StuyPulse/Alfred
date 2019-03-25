/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.util.Limelight;

public class AutomaticDriveCommand extends AutomaticTurnCommand {
    @Override
    protected void setSpeed() {
        quickTurn = true; // Automatic Drive Uses Quick Turn
        SmartDashboard.putBoolean("LIMELIGHT_CONNECTED:", Limelight.isConnected());
        if (Limelight.hasValidTarget()) {
            // Set speed depending on how far away the goal is
            double minSpeed = SmartDashboard.getNumber("AUTODRIVE_MIN_SPEED", RobotMap.MIN_AUTO_SPEED);
            double speedMultiplier = SmartDashboard.getNumber("AUTODRIVE_SPEED_MUL", RobotMap.AUTO_SPEED_MUL);

            double area = Limelight.getTargetArea();
            double forwardArea = SmartDashboard.getNumber("AUTODRIVE_FORWARD_AREA", RobotMap.FORWARD_AREA);
            SmartDashboard.putBoolean("IS_NEAR", forwardArea < area);

            double accel;
            accel = Math.max(forwardArea - area, 0);
            accel *= speedMultiplier;

            double angle_P = 5;
            double xAngleMul = Math.abs(Limelight.getTargetXAngle());
            xAngleMul = Math.max(1.0 - xAngleMul / 24.0, 0.1);
            xAngleMul *= angle_P;

            accel *= xAngleMul;

            speed = minSpeed;
            speed += accel;
            
            SmartDashboard.putNumber("AutoDrive-AreaDifference:", Math.max(forwardArea - area, 0));
            SmartDashboard.putNumber("AutoDrive-AddedSpeed:", accel);
            SmartDashboard.putNumber("AutoDrive-FinalSpeed:", speed);

        } else {
            // if no target is found, fall back on gamepad speed
            super.setSpeed();
        }
    }

}