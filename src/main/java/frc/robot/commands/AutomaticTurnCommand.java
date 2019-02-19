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
        // Set the turn value to the joysticks x value
        super.setTurn();
         
        // Add corrective values to turn based on how fast the robot is moving
        if(Limelight.hasValidTarget()) {
            double turnDiv = SmartDashboard.getNumber("TURN_DIV", 35);
            double turnMul = SmartDashboard.getNumber("MOVE_TURN_MUL", 6) * speed;
            double targetXAngle = Math.sqrt(Math.abs(Limelight.getTargetXAngle()));
            double angleSign = Math.signum(Limelight.getTargetXAngle());
            turn += Math.max(turnMul, 1) * angleSign * targetXAngle / turnDiv;   
        }
    }
}
