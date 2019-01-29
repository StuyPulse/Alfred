/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.commands.DrivetrainDriveCommand;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.util.LimeLight;

public class AutomaticTurnCommand extends DrivetrainDriveCommand {

    @Override
    protected void initialize() {
        // Enable CV on the limelight
        LimeLight.setCamMode(LimeLight.CAM_MODE.VISION);
    }

    @Override
    protected void setTurn() {
        // Set the turn value to the joysticks x value
        turn = Math.pow(Robot.oi.driverGamepad.getLeftX(), RobotMap.JOYSTICK_SCALAR);

        // Add corrective values to turn based on how fast the robot is moving
        turn += LimeLight.getTargetXOffset() / (RobotMap.TURN_DIV * Math.max(RobotMap.MOVE_TURN_DIV * speed, 1));
    }
}
