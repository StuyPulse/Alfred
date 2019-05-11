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
        if(mState != States.CV) {
            Limelight.setPipeline(Drivetrain.Pipeline.CV);
            Limelight.setCamMode(Limelight.CamMode.VISION);
            Limelight.setLEDMode(Limelight.LEDMode.FORCE_ON);
            mState = States.CV;
        }
    }

    protected void getPlayerTurn() {
        // Get Player Input from DrivetrainDriveCommand
        super.setTurn();
    }

    // Used in PID loop
    double mIntegral, mPreviousError, mPreviousTime;

    @Override
    protected void setTurn() {
        // Set Turn to Player Input
        getPlayerTurn();
        
        // If Using CV
        if(Limelight.hasValidTarget()) {
            double P = SmarterDashboard.getNumber("Autoturn P", Drivetrain.CV.P);
            double I = SmarterDashboard.getNumber("Autoturn I", Drivetrain.CV.I);
            double D = SmarterDashboard.getNumber("Autoturn D", Drivetrain.CV.D);
            
            // Get Time Between Loops
            double currentTime = System.currentTimeMillis() / 1000.0;
            double time = currentTime - mPreviousTime;
            mPreviousTime = currentTime;

            // P loop
            double error = Limelight.getTargetXAngle() / Limelight.MAX_X_ANGLE;

            // Prevent Scene Shifts
            if(time > Drivetrain.CV.MAX_TIME) {
                mPreviousError = error;
                time = 0;
            }

            // I Loop
            mIntegral += error * time;

            // D Loop
            double derivative = (error - mPreviousError) / time;

            // Update turn values
            mTurn = (P * error) + (I * mIntegral) + (D * derivative);
            mPreviousError = error;
        }
    }
}
