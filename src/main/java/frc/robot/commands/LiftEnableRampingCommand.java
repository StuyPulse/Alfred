
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class LiftEnableRampingCommand extends InstantCommand {
  public LiftEnableRampingCommand() {
    requires(Robot.lift);
  }

  @Override
  protected void initialize() {
    Robot.lift.enableRamping();
  } 
}
