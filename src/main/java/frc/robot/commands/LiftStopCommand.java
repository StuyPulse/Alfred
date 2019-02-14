package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class LiftStopCommand extends InstantCommand {
   
    public LiftStopCommand() {
        requires(Robot.lift);
    }

    @Override
    protected void initialize() {
        Robot.lift.stop();
    }
}
