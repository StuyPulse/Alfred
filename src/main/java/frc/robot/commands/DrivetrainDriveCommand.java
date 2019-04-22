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
import frc.robot.RobotMap;
import frc.util.Limelight;

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
        setNudging();
        setQuickTurn();
        updateSmartdashboard();
        updateDrivetrain();
    }

    // Used for changing state of limelight without spamming networktable
    public enum Mode { Start, Driver, CV; }
    protected static Mode currentState = Mode.Start;

    protected void setCameraMode() {
        if(currentState != Mode.Driver) {
            Limelight.setPipeline(RobotMap.Pipeline.DRIVER);
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
        speed += Math.pow(Robot.oi.driverGamepad.getRawRightTriggerAxis(), RobotMap.Drivetrain.TRIGGER_SCALAR);
        speed -= Math.pow(Robot.oi.driverGamepad.getRawLeftTriggerAxis(), RobotMap.Drivetrain.TRIGGER_SCALAR);
    }

    /* Updating Turning */
    protected void setTurn() {
        // Set the turn value to the joystick's x value
        double leftStick = Robot.oi.driverGamepad.getLeftX();
        leftStick = Math.pow(leftStick, RobotMap.Drivetrain.JOYSTICK_SCALAR);

        // Fix the sign for even powers
        if (RobotMap.Drivetrain.JOYSTICK_SCALAR % 2 == 0) {
            leftStick *= Math.signum(Robot.oi.driverGamepad.getLeftX());
        }

        leftStick *= RobotMap.Drivetrain.TURN_UPPER_LIMIT;
        turn = leftStick;
    }

    protected void setNudging() {
        if(false) { // TODO: Get Button For Nugging
            speed *= RobotMap.Drivetrain.NUDGE_SPEED;
            turn *= RobotMap.Drivetrain.NUDGE_SPEED;
        }
    }

    /* Updating Quick Turn */
    protected void setQuickTurn() {
        // Enable Quick Turn if robot is not moving
        quickTurn = Math.abs(speed) < RobotMap.Drivetrain.QUICKTURN_THRESHOLD;
        
        if (quickTurn) {
            // Slow down quick turn as it is only used
            // when the driver is scoring
            turn *= RobotMap.Drivetrain.QUICKTURN_SPEED;
        }
    }

    protected void updateSmartdashboard() {
        if(RobotMap.Drivetrain.SMARTDASHBOARD_DEBUG) {
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
