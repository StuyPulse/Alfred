package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class DrivetrainStopCommand extends InstantCommand {

    public DrivetrainStopCommand() {
        requires(Robot.drivetrain);
    }

    @Override
    protected void initialize() {
        Robot.drivetrain.stop();
    }
}