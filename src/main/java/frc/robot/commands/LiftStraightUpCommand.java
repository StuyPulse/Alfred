
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class LiftStraightUpCommand extends InstantCommand {
  public LiftStraightUpCommand() {
    requires(Robot.lift);
  }

  @Override
  protected void initialize() {
    Robot.lift.straightUp(); 
  }

}
