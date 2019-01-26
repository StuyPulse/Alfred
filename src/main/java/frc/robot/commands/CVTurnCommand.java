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

public class CVTurnCommand extends Command {
    double speed = 0;
    double turn = 0;
    boolean quickTurn = true;

    public CVTurnCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        LimeLight.setCamMode(LimeLight.CAM_MODE.VISION);
    }

    // Called repeatedly when this Command is scheduled to run
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
        turn = Math.pow(Robot.DriverPad.getLeftX(), RobotMap.JOYSTICK_SCALAR); // Left Stick
        turn += LimeLight.getTargetXOffset() / (RobotMap.TURN_DIV * Math.max(RobotMap.MOVE_DIV * speed, 1));
    }

    protected void setSpeed() {
        quickTurn = true;
        speed *= RobotMap.ACCELERATION_DIV - 1;
        if (Robot.DriverPad.getRawRightTrigger()) {
            speed++;
            quickTurn = false;
        }
        if (Robot.DriverPad.getRawLeftTrigger()) {
            speed--;
            quickTurn = false;
        }
        speed /= RobotMap.ACCELERATION_DIV;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return !(Robot.DriverPad.getRawLeftButton());
    }
}
