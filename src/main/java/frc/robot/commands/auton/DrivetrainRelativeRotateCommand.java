/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DrivetrainRelativeRotateCommand extends Command {

    final double MAX_OFFSET = 5;

    protected double targetAngle;
    protected double speed;

    public DrivetrainRelativeRotateCommand(double targetAngle, double speed) {
        this.targetAngle = targetAngle;
        this.speed = speed;
        requires(Robot.drivetrain);
    }

    @Override
    protected void initialize() {
        Robot.drivetrain.resetGyro();
    }

    @Override
    protected void execute() {
        speed = Math.abs(speed) * Math.signum(targetAngle);
        Robot.drivetrain.tankDrive(speed, -speed);
    }

    @Override
    protected boolean isFinished() {
        return getDeltaAngle() <= MAX_OFFSET;
    }

    @Override
    protected void end() {
        Robot.drivetrain.stop();
        Robot.drivetrain.resetGyro();
    }

    protected double getDeltaAngle() {
        return Math.abs(targetAngle - Robot.drivetrain.getRelativeAngle()) % 360;
    }
}