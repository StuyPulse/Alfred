package frc.robot.commands.auton;

import frc.robot.Robot;

public class DrivetrainMoveInchesEncoderCommand extends DrivetrainDriveDistanceCommand {
    protected double moveSpeed;

    public DrivetrainMoveInchesEncoderCommand(double targetDistance, double speed) {
        super(targetDistance);
        this.moveSpeed = speed;
    }

    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    protected void execute() {
        Robot.drivetrain.tankDrive(moveSpeed, moveSpeed);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(getDistance()) >= Math.abs(targetDistance);
    }

    @Override
    protected void end() {
        Robot.drivetrain.stop();
    }
}