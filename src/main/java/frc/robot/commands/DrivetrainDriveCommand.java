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
import frc.util.LimeLight;

public class DrivetrainDriveCommand extends Command {
    // Variables to feed to curvature drive
    double speed = 0, turn = 0;
    boolean quickTurn = true;

    public DrivetrainDriveCommand() {
        requires(Robot.drivetrain);
    }

    @Override
    protected void initialize() {
        LimeLight.setCamMode(LimeLight.CAM_MODE.DRIVER);
    }

    @Override
    protected void execute() {
        setSpeed();
        setTurn();
        updateDrivetrain();
    }

    protected void updateDrivetrain() {
        Robot.drivetrain.curvatureDrive(speed, turn, quickTurn);
    }

    protected void setTurn() {
        // Set the turn value to the joysticks x value
        turn = Math.pow(Robot.oi.driverGamepad.getLeftX(), RobotMap.JOYSTICK_SCALAR);
    }

    protected void setSpeed() {
        Math.abs(Robot.oi.driverGamepad.getRawLeftTrigger() < 0.001
        // Enable quick turn if no triggers are pressed
        quickTurn = !(Robot.oi.driverGamepad.getRawRightTrigger() || Robot.oi.driverGamepad.getRawLeftTrigger());
        
        // Set speed to the axis' of the triggers
        speed += Math.pow(Robot.oi.driverGamepad.getRawRightTriggerAxis(), 2);
        speed -= Math.pow(Robot.oi.driverGamepad.getRawLeftTriggerAxis(), 2);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
