package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public abstract class DrivetrainRotateCommand extends Command {

    protected double targetAngle;

    public DrivetrainRotateCommand(double targetAngle) {
        this.targetAngle = targetAngle;
        requires(Robot.drivetrain);
    }

    @Override
    protected void initialize() {
        Robot.drivetrain.lowGearShift();
    }

    @Override
    protected void end() {
        Robot.drivetrain.highGearShift();
    }

    protected double getDeltaAngle() {
        return Robot.drivetrain.getGyroAngle() - targetAngle;
    }
}
