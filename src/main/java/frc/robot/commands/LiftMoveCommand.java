package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LiftMoveCommand extends Command {
  public LiftMoveCommand() {
    requires(Robot.lift);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    // TODO: Implement OI
    // double speed = Robot.m_oi.operatorGamepad.getY();
    Robot.isLiftRunning = true;
    double speed = 0;
    Robot.lift.moveLift(speed);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.isLiftRunning = false;
  }

  @Override
  protected void interrupted() {
  }
}
