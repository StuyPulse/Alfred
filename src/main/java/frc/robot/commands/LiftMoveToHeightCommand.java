
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LiftMoveToHeightCommand extends Command {
  private double targetHeight; 

  public LiftMoveToHeightCommand(double targetHeight) {
    requires(Robot.lift);
    this.targetHeight = targetHeight;
  }

  @Override
  protected void initialize() {
    Robot.lift.setAutomatic();
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
    double error = targetHeight - Robot.lift.getHeight(); 
    return Math.abs(error) < 2 || 
      (Robot.lift.isAtBottom() && error < 0) || 
      (Robot.lift.isAtTop() && error > 0);
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
