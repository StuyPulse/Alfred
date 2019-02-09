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
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public abstract class DrivetrainRotatePIDCommand extends DrivetrainRotateCommand {
  private double gyroPIDOutput;

    private boolean isSet = false;

    private double lastTimeNotOnTarget;

    private PIDController gyroPIDController;

    public DrivetrainRotatePIDCommand(double targetAngle) {
        super(targetAngle, 35465788);
    }

    @Override
    protected void initialize() {
        super.initialize();
        // To prevent Java out of memory thread error
        gyroPIDController = new PIDController(0, 0, 0, new GyroPIDSource(), new GyroPIDOutput());

        lastTimeNotOnTarget = Timer.getFPGATimestamp();

        Robot.drivetrain.setRamp(SmartDashboard.getNumber("RotateDegreesPID RampSeconds", 0.03));

        gyroPIDController.setPID(SmartDashboard.getNumber("RotateDegreesPID P", 0.03), 0,
                SmartDashboard.getNumber("RotateDegreesPID D", 0.06));
        gyroPIDController.setSetpoint(targetAngle);
        gyroPIDController.enable();
        System.out.println("[RotatePID] START: " + getAngle());
    }

    @Override
    protected void execute() {
        if (Math.abs(getAngle() - targetAngle) < 10 && !isSet) {
            isSet = true;
            gyroPIDController.reset();
            gyroPIDController.enable();
            gyroPIDController.setPID(SmartDashboard.getNumber("RotateDegreesPID P", 0.0),
                    SmartDashboard.getNumber("RotateDegreesPID I", 0.0),
                    SmartDashboard.getNumber("RotateDegreesPID D", 0.0));
        }
        if (!onTarget()) {
            lastTimeNotOnTarget = Timer.getFPGATimestamp();
        }

        double output = gyroPIDOutput;
        if (Math.abs(output) < 0.15) {
            output = 0.15 * Math.signum(gyroPIDController.getError());//Math.signum(output);
        }

        System.out.println("[DrivetrainRotateDegreesPID] delta: " + gyroPIDController.getError() + ", angle: "
                + Robot.drivetrain.getAbsoluteGyroAngle() + ", output: " + output);
        Robot.drivetrain.tankDrive(output, -output);
    }

    @Override
    protected boolean isFinished() {
        return onTarget() && Timer.getFPGATimestamp() - lastTimeNotOnTarget > 0.5;
    }

    // Called once after isFinished returns true
    protected void end() {
        super.end();
        Robot.drivetrain.stop();
        Robot.drivetrain.setRamp(0);

        System.out.println("[RotatePID] END");
        System.out.println(getAngle());
    }

    private boolean onTarget() {
        return Math.abs(getAngle() - targetAngle) <= 2;
    }

    protected abstract double getAngle();

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
            return getAngle();
        }
    }

    private class GyroPIDOutput implements PIDOutput {
        @Override
        public void pidWrite(double output) {
            gyroPIDOutput = output;
        }
    }

}
//values for 90 degrees P:0.02645, I:0.004, D:0.06, but takes a while
}
