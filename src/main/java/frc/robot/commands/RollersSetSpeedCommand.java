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

public class RollersSetSpeedCommand extends CommandGroup {
  
  public RollersSetSpeedCommand(double speed) {
    addParallel(new FloopPrepareForRollersCommand());
    addSequential(new RollersSetSpeed(speed));
  }

  public class RollersSetSpeed extends Command {
    
    double speed;

    public RollersSetSpeed(double speed) {
      requires(Robot.rollers);
      this.speed = speed;
    }

    @Override
        protected void execute() {
            Robot.rollers.setSpeed(speed);
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
