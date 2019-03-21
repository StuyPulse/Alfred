/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.util.Limelight;

public class AutomaticTurnCommand extends DrivetrainDriveCommand {
    private PIDController rotationPIDController;
    private double LimelightPIDOutput;
    @Override
    protected void initialize() {
        setInterruptible(false);
        super.initialize();
        rotationPIDController = new PIDController(0, 0, 0, new LimelightPIDSource(),
                                                           new LimelightPIDOutput());
        rotationPIDController.setSetpoint(0);
        rotationPIDController.setPID(SmartDashboard.getNumber("LimelightTurnPID P", 0),
                                     SmartDashboard.getNumber("LimelightTurnPID I", 0),
                                     SmartDashboard.getNumber("LimelightTurnPID D", 0));
        rotationPIDController.enable();
    }

    @Override
    protected void setTurn() {
        // Get Gamepad Input
        super.setTurn();

        // If Using CV
        if(Limelight.hasValidTarget()) {
            // Establish Turn Multiplier
            double turnMult = SmartDashboard.getNumber("MOVE_TURN_MUL", 5.5);
            turnMult = Math.max(turnMult * speed, 1);
            // Add PID Output, multiply it by the Speed P value.
            super.turn += getLimelightPIDOutput() * turnMult;
        }
    }
    @Override
    protected boolean isFinished(){
        return !getCVButtons();
    }

    protected void end(){
        rotationPIDController.setPID(0, 0, 0);
        rotationPIDController.disable();
    }

    // PID stuff
    protected double getLimelightPIDOutput() {
        return LimelightPIDOutput;
    }

    private class LimelightPIDSource implements PIDSource {
        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {
        }

        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public double pidGet() {
            double turnSign = Math.signum(Limelight.getTargetXAngle());
            double turnDelta = Math.sqrt(Math.abs(Limelight.getTargetXAngle()));
            return turnDelta * turnSign;
        }
    }
    private class LimelightPIDOutput implements PIDOutput {
        @Override
        public void pidWrite(double output) {
            LimelightPIDOutput = output;
        }
    }
}