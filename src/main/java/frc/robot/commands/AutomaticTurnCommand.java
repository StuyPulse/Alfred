/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
            double turnDiv = SmartDashboard.getNumber("TURN_DIV", 35);

            // Establish Turn Multiplier
            double turnMult = SmartDashboard.getNumber("MOVE_TURN_MUL", 6);
            turnMult = Math.max(turnMult * speed, 1);

            // Calculating the amount to turn based on TargetXAngle
            double turnSign = Math.signum(Limelight.getTargetXAngle());
            double turnDelta = Math.sqrt(Math.abs(Limelight.getTargetXAngle()));
            turnDelta *= turnSign; // SQRT of delta removes sign
            turnDelta *= turnMult;
            turnDelta /= turnDiv;
            
            // Add Turn Delta to Turn
            turn += turnDelta;
        }
    }
}