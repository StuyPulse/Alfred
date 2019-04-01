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
        setQuickTurn();
        updateDrivetrain();
    }

    /* Switching between CV and Driver Mode */
    private boolean getCVButtonsPressed(){
        return (
            Robot.oi.driverGamepad.getRawLeftButton() || 
            Robot.oi.driverGamepad.getRawTopButton()
        );
    }

    private boolean isDriverControlling = true;
    protected void setCameraMode() {
        boolean controlling = !getCVButtonsPressed();

        // Optimization that prevents spamming network table
        if(controlling != isDriverControlling) {
            isDriverControlling = controlling;

            if(isDriverControlling){
                Limelight.setPipeline(RobotMap.DRIVER_PIPELINE);
                Limelight.setCamMode(Limelight.CamMode.DRIVER);
                Limelight.setLEDMode(Limelight.LEDMode.FORCE_OFF);
            } else {
                Limelight.setPipeline(RobotMap.CV_PIPELINE);
                Limelight.setCamMode(Limelight.CamMode.VISION);
                Limelight.setLEDMode(Limelight.LEDMode.FORCE_ON);
            }
        }
    }

    /* Updating Speed */
    protected void setSpeed() {
        // Reset the speed to prevent this from becoming acceleration
        speed = 0;

        // Set speed to the axes of the triggers
        speed += Math.pow(Robot.oi.driverGamepad.getRawRightTriggerAxis(), 2);
        speed -= Math.pow(Robot.oi.driverGamepad.getRawLeftTriggerAxis(), 2);
    }

    /* Updating Turning */
    protected void setTurn() {
        // Set the turn value to the joystick's x value
        double leftStick = Robot.oi.driverGamepad.getLeftX();
        leftStick = Math.pow(leftStick, RobotMap.JOYSTICK_SCALAR);
        //leftStick /= 2.0;

        // Fix the sign for even powers
        if (RobotMap.JOYSTICK_SCALAR % 2 == 0) {
            leftStick *= Math.signum((Robot.oi.driverGamepad.getLeftX()));
        }

        turn = leftStick;
    }

    /* Updating Quick Turn */
    protected void setQuickTurn() {
        // Enable Quick Turn if robot is not moving
        quickTurn = Math.abs(speed) < 0.125;
    }

    // Sub commands for each curvature drive variable
    protected void updateDrivetrain() {
        if(RobotMap.DRIVETRAIN_SMARTDASHBOARD_DEBUG) {
            SmartDashboard.putNumber("Drivetrain Speed", speed);
            SmartDashboard.putNumber("Drivetrain Turn", turn);
            SmartDashboard.putBoolean("Drivetrain QuickTurn", quickTurn);
            SmartDashboard.putBoolean("Drivetrain CV", getCVButtonsPressed());

            boolean isConnected = Limelight.isConnected();
            SmartDashboard.putBoolean("Limelight Connected", isConnected);
        }

        Robot.drivetrain.curvatureDrive(speed, turn, quickTurn);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
