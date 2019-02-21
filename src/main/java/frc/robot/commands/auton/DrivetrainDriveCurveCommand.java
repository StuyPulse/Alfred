/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.commands.ConditionalDistanceEncodersCommand;;

/**
 * A command that lets us trick DriveStraightPID to turn and change speed mid
 * trajectory (wildcard motion profiling)
 */

public class DrivetrainDriveCurveCommand extends CommandGroup {

  public enum RampMode {
      RAMP_FULL, NO_RAMPING
  }

  private DrivetrainDriveStraightCommand driveCommand = null;

  public DrivetrainDriveCurveCommand(double targetDistance, RampMode ramping) {
      switch (ramping) {
      case RAMP_FULL:
          driveCommand = new DrivetrainStraightRampingCommand(targetDistance);
          break;
      case NO_RAMPING:
          driveCommand = new DrivetrainDriveStraightCommand(targetDistance, 1);
          break;
      }
      addParallel(driveCommand); // Doing this so that we can make everything run at once
  }

  // By default, do regular ramping
  public DrivetrainDriveCurveCommand(double targetDistance) {
      this(targetDistance, RampMode.RAMP_FULL);
  }

  @Override
  protected void interrupted() {
      System.out.println("[DrivetrainDriveCurve] INTERRUPT");
  }

  // Changes the target angle at a certain distance
  public void addTurn(double distance, double targetAngle) {
      addParallel(new DrivetrainRampingSetTargetAngleAtDistanceCommand(driveCommand, distance, targetAngle));
  }

  // Changes the speed scale factor at a certain distance (slows or speeds us up basically)
  public void addSpeedChange(double distance, double speedScale) {
      addParallel(new DrivetrainRampingSetSpeedScaleAtDistanceCommand(driveCommand, distance, speedScale));
  }

  private static class DrivetrainRampingSetSpeedScaleAtDistanceCommand extends ConditionalDistanceEncodersCommand {

      public DrivetrainRampingSetSpeedScaleAtDistanceCommand(DrivetrainDriveStraightCommand rampCommand, double distance,
              double factor) {
          super(new DrivetrainRampingSetSpeedScaleCommand(rampCommand, factor), distance);
      }

      private static class DrivetrainRampingSetSpeedScaleCommand extends InstantCommand {
          private DrivetrainDriveStraightCommand rampCommand;
          private double speedScaleFactor;

          public DrivetrainRampingSetSpeedScaleCommand(DrivetrainDriveStraightCommand rampCommand, double speedScaleFactor) {
              this.rampCommand = rampCommand;
              this.speedScaleFactor = speedScaleFactor;
          }

          @Override
          protected void initialize() {
              rampCommand.setSpeedScale(speedScaleFactor);
              System.out.println("[DrivetrainRampSetSpeed] set to " + speedScaleFactor + " at "
                      + Robot.drivetrain.getGreyhillDistance() + "!");
          }
      }
  }

  private static class DrivetrainRampingSetTargetAngleAtDistanceCommand extends ConditionalDistanceEncodersCommand {

      public DrivetrainRampingSetTargetAngleAtDistanceCommand(DrivetrainDriveStraightCommand rampCommand, double distance,
              double angle) {
          super(new DrivetrainRampingSetTargetAngleCommand(rampCommand, angle), distance);
      }

      @Override
      protected void initialize() {
          super.initialize();
          System.out.println("[SetTargetAngle] Set at " + Robot.drivetrain.getGreyhillDistance());
      }

      private static class DrivetrainRampingSetTargetAngleCommand extends InstantCommand {
          private double targetAngle;
          private DrivetrainDriveStraightCommand rampCommand;

          public DrivetrainRampingSetTargetAngleCommand(DrivetrainDriveStraightCommand rampCommand, double targetAngle) {
              this.rampCommand = rampCommand;
              this.targetAngle = targetAngle;
          }

          @Override
          protected void initialize() {
              rampCommand.setTargetAngle(targetAngle);
          }
      }
  }

@Override
  protected void end() {
  }

}
