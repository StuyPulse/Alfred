package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LiftMoveToHeightCommand extends Command {
  private double targetHeight;
  private final double ACCEPTED_ERROR_RANGE = 2;

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
      Robot.lift.moveRamp(-1);
    } else {
      Robot.lift.moveRamp(1);
    }
  }

  @Override
  protected boolean isFinished() {
    /* Finish if:
    -are within the height
    -at the top and still trying to go up
    -at the bottom and still trying to go down
     */
    double error = targetHeight - Robot.lift.getHeight();
    return Math.abs(error) < ACCEPTED_ERROR_RANGE || (Robot.lift.isAtBottom() && error < 0)
        || (Robot.lift.isAtTop() && error > 0);
  }

  @Override
  protected void end() {
    Robot.lift.stopLift();
  }

  @Override
  protected void interrupted() {
    end();
  }
}
