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

public class ConditionalDistanceEncodersCommand extends CommandGroup {
    public ConditionalDistanceEncodersCommand(Command onTrue, double distance) {
        addSequential(new InitialDistanceEncodersCommand(distance));
        addSequential(onTrue);
    }

    private static class InitialDistanceEncodersCommand extends Command {

        private double distance;

        private double startDistance;

        public InitialDistanceEncodersCommand(double distance) {
            this.distance = distance;
        }

        @Override
        protected void initialize() {
            startDistance = Robot.drivetrain.getGreyhillDistance();
        }

        @Override
        protected boolean isFinished() {
            return Math.abs(Robot.drivetrain.getGreyhillDistance() - startDistance) > Math.abs(distance);

        }
    }
}
