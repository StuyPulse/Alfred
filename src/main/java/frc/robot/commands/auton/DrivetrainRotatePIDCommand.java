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

public abstract class DrivetrainRotatePIDCommand extends DrivetrainRelativeRotateCommand {
    
    private double gyroPIDOutput;
    private boolean isSet = false;
    private double lastTimeNotOnTarget;
    private PIDController gyroPIDController;
    private double rotateP;
    private double rotateI;
    private double rotateD;
    private boolean inRange;
    private double timeInRange;

    public DrivetrainRotatePIDCommand(double targetAngle) {
        super(targetAngle, 0.5);
    }

    @Override
    protected void initialize() {
        super.initialize();

        rotateP = SmartDashboard.getNumber("DriveStraightGyroPID P", 0);
        rotateI = SmartDashboard.getNumber("DriveStraightGyroPID I", 0);
        rotateD = SmartDashboard.getNumber("DriveStraightGyroPID D", 0);
        gyroPIDController = new PIDController(0, 0, 0, new GyroPIDSource(), new GyroPIDOutput());

        lastTimeNotOnTarget = Timer.getFPGATimestamp();

        Robot.drivetrain.setRamp(SmartDashboard.getNumber("RotateDegreesPID RampSeconds", 0.03));
        gyroPIDController.setPID(rotateP, rotateI, rotateD);
        gyroPIDController.setSetpoint(targetAngle);
        gyroPIDController.enable();
    }

    @Override
    protected void execute() {
        double output = gyroPIDOutput;
        if (Math.abs(output) < 0.15) {
            output = 0.15 * Math.signum(gyroPIDController.getError());// Math.signum(output);
        }
        Robot.drivetrain.tankDrive(output, -output);
    }

    @Override
    protected boolean isFinished() {
        if (gyroPIDController.onTarget() && !inRange) {
            timeInRange = Timer.getFPGATimestamp();
            inRange = true;
        } else if (!inRange) {
            inRange = false;
        }
        return inRange && Timer.getFPGATimestamp() - timeInRange > 0.5;
    }

    protected void end() {
        super.end();
        Robot.drivetrain.setRamp(0);
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
            gyroPIDOutput = output;
        }
    }
}
