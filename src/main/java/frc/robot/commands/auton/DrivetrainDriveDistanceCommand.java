package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public abstract class DrivetrainDriveDistanceCommand extends Command {

    protected double targetDistance;

    protected double startDistance;

    public DrivetrainDriveDistanceCommand(double distance) {
        this.targetDistance = distance;
        requires(Robot.drivetrain);
    }

    protected void initialize() {
        startDistance = getRawDistance();
        System.out.println("[DrivetrainDriveDistance] SET: " + startDistance);
    }

    private double getRawDistance() {
        return Robot.drivetrain.getDistance();
    }

    protected double getDistance() {
        return getRawDistance() - startDistance;
    }

    protected double getDistanceFromTarget() {
        return targetDistance - getDistance();
    }

}
