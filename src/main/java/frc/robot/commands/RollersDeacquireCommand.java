/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class RollersDeacquireCommand extends CommandGroup {

  public RollersDeacquireCommand() {
    addParallel(new FloopPrepareForRollersCommand());
    addSequential(new RollersDeacquire());
  }

  public class RollersDeacquire extends Command {

    public RollersDeacquire() {
      requires(Robot.rollers);
    }

    @Override
    protected void execute() {
      Robot.rollers.setSpeed(-1.0);
    }

    @Override
    protected boolean isFinished() {
      return false;
    }

    @Override
    protected void end() {
      Robot.rollers.stop();
    }
  }
}
