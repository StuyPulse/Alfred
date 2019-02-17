package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LiftMoveToHeightCommand extends Command {
    private double targetHeight;
    private final double ACCEPTED_ERROR_RANGE = .5;

    public LiftMoveToHeightCommand(double targetHeight) {
        requires(Robot.lift);
        this.targetHeight = targetHeight;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        if (Robot.lift.getHeight() > targetHeight) {
            Robot.lift.moveToHeightWithRamp(-1, targetHeight);
        } else {
            Robot.lift.moveToHeightWithRamp(1, targetHeight);
        }
    }

    @Override
    protected boolean isFinished() {
        // Ends if the lift is close enough to the target or at the bottom and top.
        double error = targetHeight - Robot.lift.getHeight();
        return Math.abs(error) < ACCEPTED_ERROR_RANGE 
            || (Robot.lift.isAtBottom() && error < 0)
            || (Robot.lift.isAtTop() && error > 0);
    }

    @Override
    protected void end() {
        Robot.lift.stop();
    }
}
