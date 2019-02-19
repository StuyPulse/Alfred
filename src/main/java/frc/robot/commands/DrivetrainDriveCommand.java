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

public class DrivetrainDriveCommand extends Command {
    // Variables to feed to curvature drive
    double speed = 0;
    double turn = 0;
    boolean quickTurn = true;

    public DrivetrainDriveCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Limelight.setCamMode(Limelight.CamMode.VISION);
        printDebugStatements();
        setSpeed();
        setTurn();
        updateDrivetrain();
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

    private void printDebugStatements(){
        // Debug statement prints out if there is a valid target
        // System.out.println("validTarget? :" + Limelight.hasValidTarget());
        // System.out.println("XOffset :" + Limelight.getTargetXAngle());
        // System.out.println("Skew :" + Limelight.getTargetSkew());
        // System.out.println("W/H :" + Limelight.hasValidBlueAspectRatio(minRatio, maxRatio));
        // Sets to driver mode for debugging
        if(Robot.oi.driverGamepad.getRawDPadDown()){
            Limelight.setCamMode(Limelight.CamMode.DRIVER);
        }
    }

    protected void setTurn() {
        // Turn on Driver mode
        // Limelight.setCamMode(Limelight.CamMode.DRIVER);

        // Set the turn value to the joystick's x value
        turn = Math.pow(Robot.oi.driverGamepad.getLeftX(), RobotMap.JOYSTICK_SCALAR) / 2.0;

        if (RobotMap.JOYSTICK_SCALAR % 2 == 0) {
            turn *= Math.signum((Robot.oi.driverGamepad.getLeftX()));
        }
    }

    // Sub commands for each curvature drive variable
    protected void updateDrivetrain() {
        Robot.drivetrain.curvatureDrive(speed, turn, quickTurn);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }
}
