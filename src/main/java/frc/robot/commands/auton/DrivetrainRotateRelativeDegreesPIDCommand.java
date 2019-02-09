package frc.robot.commands.auton;

import frc.robot.Robot;

public class DrivetrainRotateRelativeDegreesPIDCommand extends DrivetrainRotateDegreesPIDCommand {

    public DrivetrainRotateRelativeDegreesPIDCommand(double targetAngle) {
        super(targetAngle);
    }

    @Override
    protected void initialize() {
        super.initialize();
        Robot.drivetrain.resetGyro();
    }

    @Override
    protected double getAngle() {
        return Robot.drivetrain.getGyroAngle();
    }
}
