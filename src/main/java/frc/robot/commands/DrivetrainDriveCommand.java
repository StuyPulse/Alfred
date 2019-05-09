/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotMap.Drivetrain;
import frc.robot.commands.DrivetrainCommand;
import frc.util.SmarterDashboard;
import frc.util.Limelight;

@SuppressWarnings("unused")
public class DrivetrainDriveCommand extends DrivetrainCommand {
    
    protected void setCameraMode() {
        if(mState != States.DRIVER) {
            Limelight.setPipeline(Drivetrain.Pipeline.DRIVER);
            Limelight.setCamMode(Limelight.CamMode.DRIVER);
            Limelight.setLEDMode(Limelight.LEDMode.FORCE_OFF);
            mState = States.DRIVER;
        }
    }

    protected void setSpeed() {
        mSpeed = Controller.getTriggers();
    }

    protected void setTurn() {
        double newTurn = Controller.getJoystick();
        newTurn *= Drivetrain.TurnSpeed.MAX;

        if(SmarterDashboard.getBoolean("SMOOTH_TURN", Drivetrain.SmoothTurn.ENABLED)) {
            useSmoothTurn(newTurn);
        } else {
            mTurn = newTurn;
        }
    }

    private void useSmoothTurn(double newTurn) {
        if(mQuickTurn) {
            double weight = (Math.abs(mTurn) < Math.abs(newTurn))
                ? SmarterDashboard.getNumber("SMOOTH_ACCEL", Drivetrain.SmoothTurn.ACCEL)
                : SmarterDashboard.getNumber("SMOOTH_DECEL", Drivetrain.SmoothTurn.DECEL);

            mTurn *= weight - 1.0;
            mTurn += newTurn;
            mTurn /= weight;
        } else {
            mTurn = newTurn;
        }
    }

    /* Updating Quick Turn */
    protected void setQuickTurn() {
        mQuickTurn = Math.abs(mSpeed) < SmarterDashboard.getNumber("QUICKTURN_THRESHOLD", 
                                      Drivetrain.QuickTurn.THRESHOLD);
        
        if (mQuickTurn) {
            mTurn *= SmarterDashboard.getNumber("QUICKTURN_SPEED", 
                    Drivetrain.QuickTurn.SPEED);
        }
    }
}
