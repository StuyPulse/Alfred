/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.util.Limelight;
import frc.util.Limelight.LEDMode;

public class DrivetrainDriveCommand extends Command {
    // Variables to feed to curvature drive
    double speed = 0;
    double turn = 0;
    boolean quickTurn = true;
    boolean isDriverControlling = true;

    public DrivetrainDriveCommand() {
        requires(Robot.drivetrain);
    }

    @Override
    protected void execute() {
        setCameraMode();
        setSpeed();
        setTurn();
        updateDrivetrain();
    }
    
    private boolean getCVButtons(){
        return (
            Robot.oi.driverGamepad.getRawLeftButton() || 
            Robot.oi.driverGamepad.getRawTopButton()
        );
    }

    protected void setCameraMode() {
        boolean controlling = !getCVButtons();

        // Optimization that prevents spamming network table
        if(controlling != isDriverControlling) {
            isDriverControlling = controlling;

            if(isDriverControlling){
                Limelight.setPipeline(RobotMap.DRIVER_PIPELINE);
                Limelight.setCamMode(Limelight.CamMode.DRIVER);
                Limelight.setLEDMode(LEDMode.FORCE_OFF);
            } else {
                Limelight.setPipeline(RobotMap.CV_PIPELINE);
                Limelight.setCamMode(Limelight.CamMode.VISION);
                Limelight.setLEDMode(LEDMode.FORCE_ON);
            }
        }
    }

    protected void setSpeed() {
        // Reset the speed to prevent this from becoming acceleration
        speed = 0;

        // Set speed to the axes of the triggers
        speed += Math.pow(Robot.oi.driverGamepad.getRawRightTriggerAxis(), 2);
        speed -= Math.pow(Robot.oi.driverGamepad.getRawLeftTriggerAxis(), 2);

        // Enable Quick Turn if robot is not moving
        quickTurn = Math.abs(speed) < 0.125;
    }

    protected void setTurn() {
        // Set the turn value to the joystick's x value
        double leftStick = Robot.oi.driverGamepad.getLeftX();
        leftStick = Math.pow(leftStick, RobotMap.JOYSTICK_SCALAR);
        leftStick /= 2.0;

        // Fix the sign for even powers
        if (RobotMap.JOYSTICK_SCALAR % 2 == 0) {
            leftStick *= Math.signum((Robot.oi.driverGamepad.getLeftX()));
        }

        turn = leftStick;
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
