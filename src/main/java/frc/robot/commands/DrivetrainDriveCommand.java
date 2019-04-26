/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap.Drivetrain;
import frc.util.Limelight;
import frc.util.SmarterDashboard;

@SuppressWarnings("unused")
public class DrivetrainDriveCommand extends Command {
    
    // Variables to feed to curvature drive
    protected double speed = 0;
    protected double turn = 0; 
    protected boolean quickTurn = true;

    public DrivetrainDriveCommand() {
        requires(Robot.drivetrain);
    }

    @Override
    protected void execute() {
        setCameraMode();
        setSpeed();
        setTurn();
        setQuickTurn();
        updateSmartdashboard();
        updateDrivetrain();
    }

    // Used for changing state of limelight without spamming networktable
    public enum Mode { Start, Driver, CV; }
    protected static Mode currentState = Mode.Start;

    protected void setCameraMode() {
        if(currentState != Mode.Driver) {
            Limelight.setPipeline(Drivetrain.Pipeline.DRIVER);
            Limelight.setCamMode(Limelight.CamMode.DRIVER);
            Limelight.setLEDMode(Limelight.LEDMode.FORCE_OFF);
            currentState = Mode.Driver;
        }
    }

    /* Updating Speed */
    protected void setSpeed() {
        // Reset the speed to prevent this from becoming acceleration
        speed = 0;

        // Set speed to the axes of the triggers
        speed += Math.pow(Robot.oi.driverGamepad.getRawRightTriggerAxis(), Drivetrain.Controls.TRIGGER_SCALAR);
        speed -= Math.pow(Robot.oi.driverGamepad.getRawLeftTriggerAxis(), Drivetrain.Controls.TRIGGER_SCALAR);
    }

    /* Updating Turning */
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

        if(quickTurn && Drivetrain.Weights.SMOOTH_QUICKTURN) {
            double weight = (Math.abs(turn) < Math.abs(newTurn))
                ? Drivetrain.Weights.Quick.ACCEL
                : Drivetrain.Weights.Quick.DECEL;

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

    protected void updateSmartdashboard() {
        if(Drivetrain.SMARTDASHBOARD_DEBUG) {
            SmartDashboard.putNumber("Drivetrain Speed", speed);
            SmartDashboard.putNumber("Drivetrain Turn", turn);
            SmartDashboard.putBoolean("Drivetrain QuickTurn", quickTurn);
            SmartDashboard.putBoolean("Drivetrain CV", currentState == Mode.CV);
        }
    }

    // Sub commands for each curvature drive variable
    protected void updateDrivetrain() {
        Robot.drivetrain.curvatureDrive(speed, turn, quickTurn);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
