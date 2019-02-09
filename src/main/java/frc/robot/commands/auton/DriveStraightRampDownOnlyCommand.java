package frc.robot.commands.auton;

import frc.robot.Robot;

public class DriveStraightRampDownOnlyCommand extends DriveStraightWithRampingCommand {
    public DriveStraightRampDownOnlyCommand(double targetDistance) {
        super(targetDistance);
    }

    @Override
    protected void initialize() {
        super.initialize();
        Robot.drivetrain.setRamp(0);
    }
}
