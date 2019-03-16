/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
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

<<<<<<< HEAD
        super.setTurn(); 
        // Add corrective values to turn based on how fast the robot is moving
        if (Robot.limelight.hasValidTarget()) {
            double turn_MUL = SmartDashboard.getNumber("MOVE_TURN_MUL", 6) * speed;
            double sgn = Math.signum(Robot.limelight.getTargetXAngle());
            double output =  Math.max(turn_MUL, 1) * sgn * 
                             Math.sqrt(Math.abs(Robot.limelight.getTargetXAngle())) /
                             (SmartDashboard.getNumber("TURN_DIV", 35));
            SmartDashboard.putNumber("LIMELIGHT_MOTOR_OUTPUT", output);
            turn += output; 
=======
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
>>>>>>> origin/autodrive-fix
        }
    }
}