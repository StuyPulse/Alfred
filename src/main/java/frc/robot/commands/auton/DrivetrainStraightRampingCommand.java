/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class DrivetrainStraightRampingCommand extends DrivetrainDriveStraightCommand {

    private PIDController speedPIDController;
    private double speedPIDOutput;
    private boolean inRange;
    private double timeInRange;
    private double speedP;
    private double speedI; 
    private double speedD;

    public DrivetrainStraightRampingCommand(double distance) {
        super(distance, 1);
    }

    @Override
    protected void initialize() {
        super.initialize();
        speedP = SmartDashboard.getNumber("DriveStraightGyroPID P", 0);
        speedI = SmartDashboard.getNumber("DriveStraightGyroPID I", 0);
        speedD = SmartDashboard.getNumber("DriveStraightGyroPID D", 0);
        Robot.drivetrain.setRamp(SmartDashboard.getNumber("DriveStraight RampSeconds", 2.5));
        speedPIDController = new PIDController(0, 0, 0, new GyroPIDSource(), new GyroPIDOutput());
        speedPIDController.setSetpoint(distance);
        speedPIDController.setAbsoluteTolerance(3);
        speedPIDController.setPID(speedP, speedI, speedD);
        speedPIDController.enable();
    }

    @Override
    protected void execute() {
        double output = speedPIDOutput;
        if (Math.abs(output) < 0.15) {
            if (speedPIDController.onTarget()) {
                output = 0;
            } else {
                output = 0.15 * Math.signum(output);
            }
        }
        output = Math.min(Math.max(-1, output), 1);
        double left = output + getGyroPIDOutput();
        double right = output - getGyroPIDOutput();
        Robot.drivetrain.tankDrive(left, right);
    }

    @Override
    protected boolean isFinished() {
        if (speedPIDController.onTarget() && !inRange) {
            timeInRange = Timer.getFPGATimestamp();
            inRange = true;
        } else if (!inRange) {
            inRange = false;
        }
        return inRange && Timer.getFPGATimestamp() - timeInRange > 0.5;
    }

    @Override
    protected void end() {
        super.end();
        Robot.drivetrain.setRamp(0);
        speedPIDController.disable();
    }

    private class GyroPIDSource implements PIDSource {
        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {
        }

        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public double pidGet() {
            return Robot.drivetrain.getAbsoluteAngle();
        }
    }

    private class GyroPIDOutput implements PIDOutput {
        @Override
        public void pidWrite(double output) {
            speedPIDOutput = output;
        }
    }
}
