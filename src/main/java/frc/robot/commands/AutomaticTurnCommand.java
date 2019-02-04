/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.RobotMap;
import frc.util.Limelight;

public class AutomaticTurnCommand extends DrivetrainDriveCommand {

    @Override
    protected void initialize() {
        setInterruptible(false);
        // Enable CV on the limelight
        Limelight.setCamMode(Limelight.CamMode.VISION);
    }

    @Override
    protected void setTurn() {
        // Set the turn value to the joysticks x value
        super.setTurn();

        // Add corrective values to turn based on how fast the robot is moving
        turn += Limelight.getTargetXAngle() / (RobotMap.TURN_DIV * Math.max(RobotMap.MOVE_TURN_DIV * speed, 1));
    }
}
