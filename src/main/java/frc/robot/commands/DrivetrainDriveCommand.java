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
        // Reset the speed to prevent this from becoming acceleration
        speed = 0;

        // Set speed to the axes of the triggers
        speed += Math.pow(Robot.oi.driverGamepad.getRawRightTriggerAxis(), Drivetrain.Controls.TRIGGER_SCALAR);
        speed -= Math.pow(Robot.oi.driverGamepad.getRawLeftTriggerAxis(), Drivetrain.Controls.TRIGGER_SCALAR);
    }

    protected void setTurn() {
        // Set the turn value to the joystick's x value
        double newTurn = Robot.oi.driverGamepad.getLeftX();
        newTurn = Math.pow(newTurn, Drivetrain.Controls.JOYSTICK_SCALAR);

        // Fix the sign for even powers
        if (Drivetrain.Controls.JOYSTICK_SCALAR % 2 == 0) {
            newTurn *= Math.signum(Robot.oi.driverGamepad.getLeftX());
        }

        // Adjust Left stick to max speed
        newTurn *= Drivetrain.TurnSpeed.MAX;

        // Smooth Quickturn Implementation
        if(quickTurn 
        && SmarterDashboard.getBoolean("SMOOTH_QUICKTURN", Drivetrain.Weights.SMOOTH_QUICKTURN)
        ) {
            double weight = (Math.abs(turn) < Math.abs(newTurn))
                ? SmarterDashboard.getNumber("QUICK_ACCEL", Drivetrain.Weights.ACCEL)
                : SmarterDashboard.getNumber("QUICK_DECEL", Drivetrain.Weights.DECEL);

            turn = (newTurn + turn * (weight - 1.0)) / weight;
        } else {
            turn = newTurn;
        }
    }

    /* Updating Quick Turn */
    protected void setQuickTurn() {
        // Enable Quick Turn if robot is not moving
        quickTurn = Math.abs(speed) < SmarterDashboard.getNumber("QUICKTURN_THRESHOLD", 
                                      Drivetrain.QuickTurn.THRESHOLD);
        
        if (quickTurn) {
            // Slow down quick turn as it is only used
            // when the driver is scoring
            turn *= SmarterDashboard.getNumber("QUICKTURN_SPEED", 
                    Drivetrain.QuickTurn.SPEED);
        }
    }
}
