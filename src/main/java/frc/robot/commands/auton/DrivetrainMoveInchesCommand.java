/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DrivetrainMoveInchesCommand extends Command {

    protected double distance;
    protected double speed;

    public DrivetrainMoveInchesCommand(double distance, double speed) {
        requires(Robot.drivetrain);
        this.distance = distance;
        this.speed = speed;
    }

    @Override
    protected void initialize() {
        Robot.drivetrain.resetGreyhills();
    }

    @Override
    protected void execute() {
        Robot.drivetrain.tankDrive(speed, speed);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(Robot.drivetrain.getGreyhillDistance()) > Math.abs(distance);
    }

    @Override
    protected void end() {
        Robot.drivetrain.stop();
    }
}