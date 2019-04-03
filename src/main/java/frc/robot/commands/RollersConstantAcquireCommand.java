/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class RollersConstantAcquireCommand extends CommandGroup {
    Long start_time;
    double change_distance = 0.0;
    double start_encoder_value;
    double abs_raw_distance;
    double raw_distance;
    
    // Move the joystick right at the edge of where a motor doesn't move and does
    // This POC is using ticks
    double encoder_approach_stall_threshold = 5.0;

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
            start_time = System.currentTimeMillis();
            Robot.rollers.enableRamping();
            raw_distance = Robot.rollers.getEncoderVal();
            abs_raw_distance = Math.abs(raw_distance);
            start_encoder_value = abs_raw_distance;
        }

        @Override
        protected void execute() {
            System.out.println("ROLLERS CONSTANT ACQUIRE COMMAND EXECUTE");
            Long current_time = System.currentTimeMillis();
            Long change_from_start = current_time - start_time;
            if (change_from_start > 100) {
                start_time = System.currentTimeMillis();
                // current_encoder_value needs to be replaced with distance instead
                double current_encoder_value = Math.abs(Robot.rollers.getEncoderVal());
                double change_distance = Math.abs(current_encoder_value - start_encoder_value);

                SmartDashboard.putNumber("Change In Distance Encoder", change_distance);
                if (change_distance <= encoder_approach_stall_threshold) {
                    SmartDashboard.putBoolean("Motor Stall Status:", true);
                    Robot.rollers.setSpeed(-0.2);
                } else {
                    SmartDashboard.putBoolean("Motor Stall Status:", false);
                }
                start_encoder_value = current_encoder_value;
            } else {
                Robot.rollers.acquire();
            }
            SmartDashboard.putNumber("Motor Stall Speed:", Robot.rollers.getSpeed());
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