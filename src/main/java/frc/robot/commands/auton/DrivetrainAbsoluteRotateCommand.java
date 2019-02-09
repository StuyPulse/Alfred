/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DrivetrainAbsoluteRotateCommand extends Command {

    double targetAngle;
    double speed;

    private final int MAX_OFFSET = 5;

    public DrivetrainAbsoluteRotateCommand(double targetAngle, double speed) {
        this.targetAngle = targetAngle;
        this.speed = speed;

        requires(Robot.drivetrain);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        if (Robot.drivetrain.getAbsoluteGyroAngle() == targetAngle) {
            Robot.drivetrain.stop();
        } else if (Robot.drivetrain.getAbsoluteGyroAngle() > targetAngle) {
            Robot.drivetrain.tankDrive(-speed, speed);
        } else {
            Robot.drivetrain.tankDrive(speed, -speed);
        }
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(Robot.drivetrain.getAbsoluteGyroAngle() - targetAngle) <= MAX_OFFSET;
    }

    @Override
    protected void end() {
        Robot.drivetrain.stop();
    }
}
