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

    double turnAngle;

    private final int MAX_OFFSET = 5;

    public DrivetrainAbsoluteRotateCommand(double targetAngle, double speed) {
        this.targetAngle = convertAngle(targetAngle);
        this.speed = speed;

        requires(Robot.drivetrain);
    }

    @Override
    protected void initialize() {
        Robot.drivetrain.resetGyro();
        turnAngle = targetAngle - convertAngle(Robot.drivetrain.getAbsoluteAngle());
    }

    @Override
    protected void execute() {
        speed = Math.abs(speed) * Math.signum(turnAngle);
        Robot.drivetrain.tankDrive(speed, -speed);
    }

    @Override
    protected boolean isFinished() {
        return getDeltaAngle() <= MAX_OFFSET;
    }

    @Override
    protected void end() {
        Robot.drivetrain.resetGyro();
        Robot.drivetrain.stop();
    }

    protected double getDeltaAngle() {
        return Math.abs(targetAngle - Robot.drivetrain.getAbsoluteAngle()) % 360;
    }

    protected double convertAngle(double angle) {
        return angle < 0 ? 360 + angle : angle;
    }
}
