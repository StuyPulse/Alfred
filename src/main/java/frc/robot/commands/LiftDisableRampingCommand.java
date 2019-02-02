package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class LiftDisableRampingCommand extends InstantCommand {
    public LiftDisableRampingCommand() {
        requires(Robot.lift);
    }

    @Override
    protected void initialize() {
        Robot.lift.disableRamping();
    }
}
