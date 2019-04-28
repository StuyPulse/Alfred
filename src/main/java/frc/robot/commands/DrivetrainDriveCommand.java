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
        if(state != States.DRIVER) {
            Limelight.setPipeline(Drivetrain.Pipeline.DRIVER);
            Limelight.setCamMode(Limelight.CamMode.DRIVER);
            Limelight.setLEDMode(Limelight.LEDMode.FORCE_OFF);
            state = States.DRIVER;
        }
    }

    protected void setSpeed() {
        speed = Controller.getTriggers();
    }

    protected void setTurn() {
        double newTurn = Controller.getJoystick();
        newTurn *= Drivetrain.TurnSpeed.MAX;

        if(SmarterDashboard.getBoolean("SMOOTH_TURN", Drivetrain.SmoothTurn.ENABLED)) {
            useSmoothTurn(newTurn);
        } else {
            turn = newTurn;
        }
    }

    private void useSmoothTurn(double newTurn) {
        if(quickTurn) {
            double weight = (Math.abs(turn) < Math.abs(newTurn))
                ? SmarterDashboard.getNumber("SMOOTH_ACCEL", Drivetrain.SmoothTurn.ACCEL)
                : SmarterDashboard.getNumber("SMOOTH_DECEL", Drivetrain.SmoothTurn.DECEL);

            turn *= weight - 1.0;
            turn += newTurn;
            turn /= weight;
        }
    }

    /* Updating Quick Turn */
    protected void setQuickTurn() {
        quickTurn = Math.abs(speed) < SmarterDashboard.getNumber("QUICKTURN_THRESHOLD", 
                                      Drivetrain.QuickTurn.THRESHOLD);
        
        if (quickTurn) {
            turn *= SmarterDashboard.getNumber("QUICKTURN_SPEED", 
                    Drivetrain.QuickTurn.SPEED);
        }
    }
}
