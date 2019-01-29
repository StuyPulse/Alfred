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

public class ManualDrive extends Command {
    // Variables to feed to curvature drive
    double speed = 0, turn = 0;
    boolean quickTurn = true;

    public ManualDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        LimeLight.setCamMode(LimeLight.CAM_MODE.DRIVER);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        setSpeed();
        setTurn();
        updateDrivetrain();
    }

    // Sub commands for each curvature drive variable
    protected void updateDrivetrain() {
        Robot.drivetrain.curvatureDrive(speed, turn, quickTurn);
    }

    protected void setTurn() {
        // Set the turn value to the joysticks x value
        turn = Math.pow(Robot.oi.driverGamepad.getLeftX(), RobotMap.JOYSTICK_SCALAR);
    }

    protected void setSpeed() {
        quickTurn = true;
        speed *= RobotMap.ACCELERATION_DIV - 1;
        if (Robot.oi.driverGamepad.getRawRightTrigger()) {
            speed++;
            quickTurn = false;
        }
        if (Robot.oi.driverGamepad.getRawLeftTrigger()) {
            speed--;
            quickTurn = false;
        }
        speed /= RobotMap.ACCELERATION_DIV;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }
}
