/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class RollersConstantAcquireCommand extends CommandGroup {
    double start_time;
    double change_distance = 0.0;
    double start_encoder_value;
    double abs_raw_distance;
    double raw_distance;
    
    // Move the joystick right at the edge of where a motor doesn't move and does
    // This POC is using ticks
    double encoder_approach_stall_threshold = 5.0;
    double startTime;
    double passedTime;

    public RollersConstantAcquireCommand() {
        addParallel(new FloopPrepareForRollersCommand());
        addSequential(new RollersConstantAcquire());
    }

    public class RollersConstantAcquire extends Command {

        public RollersConstantAcquire() {
            requires(Robot.rollers);
        }

        @Override
        protected void initialize() {
            // Robot.rollers.enableRamping();
            raw_distance = Robot.rollers.getEncoderVal();
            abs_raw_distance = Math.abs(raw_distance);
            start_encoder_value = abs_raw_distance;
            startTime = Timer.getFPGATimestamp();
        }

        @Override
        protected void execute() {
            System.out.println("ROLLERS CONSTANT ACQUIRE COMMAND EXECUTE");
            passedTime = Timer.getFPGATimestamp() - startTime;
            SmartDashboard.putNumber("TIME PASSED FOR ROLLERS", passedTime);
            if (Robot.isRollersStalling() && passedTime > 0.5) {
                Robot.rollers.setSpeed(-0.2);
            } else {
                Robot.rollers.acquire();
            }
        }

        @Override
        protected boolean isFinished() {
            return false;
        }

        @Override
        protected void end() {
            Robot.rollers.stop();
            Robot.rollers.disableRamping();
        }
    }
}