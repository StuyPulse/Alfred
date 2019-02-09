package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class WaitUntilLiftGoesBelowHeightCommand extends Command {

    private double targetHeight;

    public WaitUntilLiftGoesBelowHeightCommand(double targetHeight) {
        this.targetHeight = targetHeight;
    }

    protected boolean isFinished() {
        return Math.abs(Robot.lift.getHeight()) < targetHeight;
    }
}
