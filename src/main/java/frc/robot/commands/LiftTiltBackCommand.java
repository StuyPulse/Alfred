package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class LiftTiltBackCommand extends InstantCommand {
  public LiftTiltBackCommand() {
    requires(Robot.lift);
  }

  @Override
  protected void initialize() {
    Robot.lift.tiltBack();
  }

}
