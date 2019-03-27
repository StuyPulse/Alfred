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

public class AutomaticTurnCommand extends DrivetrainDriveCommand {

    @Override
    protected void initialize() {
        setInterruptible(false);
    }

    @Override
    protected void setTurn() {
        // Get Gamepad Input
        super.setTurn();

        // If Using CV
        if(Limelight.hasValidTarget()) {
            // Get Turn Div from Smart Dash Board
            double turnDiv = SmartDashboard.getNumber("TURN_DIV", RobotMap.TURN_DIV);
            double moveTurnMult = SmartDashboard.getNumber("MOVE_TURN_MUL", RobotMap.MOVE_TURN_MUL);

            // Take The Square Root of the X Angle
            double turnDelta = Limelight.getTargetXAngle();
            turnDelta = Math.signum(turnDelta) * Math.sqrt(Math.abs(turnDelta));

            // Increase Turning if robot is moving faster
            turnDelta *= Math.max(moveTurnMult * speed, 1);

            // Scale the Turn Delta
            turnDelta /= turnDiv;
            
            if(RobotMap.DRIVETRAIN_SMARTDASHBOARD_DEBUG) {
                SmartDashboard.putNumber("Drivetrain CV Turning", turnDelta);
            }

            // Add Turn Delta to Turn
            turn += turnDelta;
        }
    }
}