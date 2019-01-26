
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class LiftDisableOpenLoopRamping extends InstantCommand {
  public LiftDisableOpenLoopRamping() {
    requires(Robot.lift);
  }

  @Override
  protected void initialize() {
    Robot.lift.disableOpenLoopRamping();
  }
}
