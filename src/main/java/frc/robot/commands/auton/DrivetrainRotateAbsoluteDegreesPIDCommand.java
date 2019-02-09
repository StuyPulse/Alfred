package frc.robot.commands.auton;

import frc.robot.Robot;

public class DrivetrainRotateAbsoluteDegreesPIDCommand extends DrivetrainRotateDegreesPIDCommand {

    public DrivetrainRotateAbsoluteDegreesPIDCommand(double targetAngle) {
        super(targetAngle);
    }

    @Override
    protected double getAngle() {
        return Robot.drivetrain.getGyroAngle();
    }
}
