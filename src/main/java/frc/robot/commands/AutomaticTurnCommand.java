/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap.Drivetrain;
import frc.util.Limelight;
import frc.util.SmarterDashboard;

public class AutomaticTurnCommand extends DrivetrainDriveCommand {

    @Override
    protected void initialize() {
        setInterruptible(false);
    }

    @Override
    protected void setCameraMode() {
        if(currentState != Mode.CV) {
            Limelight.setPipeline(Drivetrain.Pipeline.CV);
            Limelight.setCamMode(Limelight.CamMode.VISION);
            Limelight.setLEDMode(Limelight.LEDMode.FORCE_ON);
            currentState = Mode.CV;
        }
    }

    protected void getPlayerTurn() {
        // Get Player Input from DrivetrainDriveCommand
        super.setTurn();
    }

    @Override
    protected void setTurn() {
        // Set Turn to Player Input
        getPlayerTurn();
        
        // If Using CV
        if(Limelight.hasValidTarget()) {
            // Get Turn Div from Smart Dash Board
            double turnDiv = SmarterDashboard.getNumber("TURN_DIV", Drivetrain.CV.TURN_DIV);
            double moveTurnMult = SmarterDashboard.getNumber("MOVE_TURN_MUL", Drivetrain.CV.MOVE_TURN_MUL);;

            // Take The Square Root of the X Angle
            double turnDelta = Limelight.getTargetXAngle();
            turnDelta = Math.signum(turnDelta) * Math.sqrt(Math.abs(turnDelta));

            // Increase Turning if robot is moving faster
            turnDelta *= Math.max(moveTurnMult * speed, 1);

            // Scale the Turn Delta
            turnDelta /= turnDiv;
            
            if(Drivetrain.SMARTDASHBOARD_DEBUG) {
                SmartDashboard.putNumber("Drivetrain CV Turning", turnDelta);
            }

            // Add Turn Delta to Turn
            turn += turnDelta;
        }
    }
}