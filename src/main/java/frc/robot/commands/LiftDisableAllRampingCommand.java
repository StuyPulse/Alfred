
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class LiftDisableAllRampingCommand extends InstantCommand {
  public LiftDisableAllRampingCommand() {
    requires(Robot.lift);
  }

  @Override
  protected void initialize() {
    Robot.lift.disableAllRamping();
  }
}
