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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class DrivetrainDriveStraightCommand extends DrivetrainMoveInchesCommand {

    private PIDController rotationPIDController;
    protected double speedScaleFactor = 1;
    private double gyroPIDOutput;
    private double rotateP;
    private double rotateI;
    private double rotateD;

    public DrivetrainDriveStraightCommand(double distance, double speed) {
        super(distance, speed);
    }

    @Override
    protected void initialize() {
        super.initialize();
        //TODO: Store final values in the constants
        rotateP = SmartDashboard.getNumber("DriveStraightGyroPID P", 0);
        rotateI = SmartDashboard.getNumber("DriveStraightGyroPID I", 0);
        rotateD = SmartDashboard.getNumber("DriveStraightGyroPID D", 0);

        Robot.drivetrain.resetGyro();
        rotationPIDController = new PIDController(0, 0, 0, new GyroPIDSource(), new GyroPIDOutput());
        rotationPIDController.setSetpoint(Robot.drivetrain.getRelativeAngle());
        rotationPIDController.setPID(rotateP, rotateI, rotateD);
        rotationPIDController.enable();
    }

    @Override
    protected void execute() {
        double sign = Math.signum(this.distance - Robot.drivetrain.getGreyhillDistance());
        Robot.drivetrain.tankDrive(sign * speed * speedScaleFactor + getGyroPIDOutput(), sign * speed * speedScaleFactor - getGyroPIDOutput());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return super.isFinished();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        super.end();
        rotationPIDController.disable();
    }

    protected double getGyroPIDOutput() {
        return gyroPIDOutput;
    }

    public void setTargetAngle(double angle) {
        rotationPIDController.setSetpoint(angle);
    }

    public void setSpeedScale(double speedScaleFactor) {
        this.speedScaleFactor = speedScaleFactor;
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
            return Robot.drivetrain.getRelativeAngle();
        }
    }

    private class GyroPIDOutput implements PIDOutput {
        @Override
        public void pidWrite(double output) {
            gyroPIDOutput = output;
        }
    }
}