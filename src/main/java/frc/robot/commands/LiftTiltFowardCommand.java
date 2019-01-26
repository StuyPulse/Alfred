package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class LiftTiltFowardCommand extends InstantCommand {
  public LiftTiltFowardCommand() {
    requires(Robot.lift);
  }

  @Override
  protected void initialize() {
    Robot.lift.tiltFoward();
  }

}
