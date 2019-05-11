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

import frc.robot.RobotMap.Drivetrain;
import frc.util.Limelight;
import frc.util.SmarterDashboard;

public class AutomaticTurnCommand extends DrivetrainDriveCommand {

    private class AlignPIDSource implements PIDSource {
        @Override
        public void setPIDSourceType(PIDSourceType source) {}

        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public double pidGet() {
            if(Limelight.hasValidTarget()) {
                return Limelight.getTargetXAngle();
            } else {
                return 0;
            }
        }
    }

    private class AlignPIDOutput implements PIDOutput {
        @Override
        public void pidWrite(double output) {
            mAlignPIDResult = output;
        }
    }

    private PIDController mAlignPID;
    private double mAlignPIDResult = 0;

    @Override
    protected void initialize() {
        setInterruptible(false);

        mAlignPID = new PIDController(
            SmarterDashboard.getNumber("Autoturn P", Drivetrain.CV.P), 
            SmarterDashboard.getNumber("Autoturn I", Drivetrain.CV.I), 
            SmarterDashboard.getNumber("Autoturn D", Drivetrain.CV.D), 
            new AlignPIDSource(), new AlignPIDOutput());

        mAlignPID.setSetpoint(0);
        mAlignPID.enable();
    }

    @Override
    protected void setCameraMode() {
        if(mState != States.CV) {
            Limelight.setPipeline(Drivetrain.Pipeline.CV);
            Limelight.setCamMode(Limelight.CamMode.VISION);
            Limelight.setLEDMode(Limelight.LEDMode.FORCE_ON);
            mState = States.CV;
        }
    }

    protected void getPlayerTurn() {
        super.setTurn();
    }

    @Override
    protected void setTurn() {
        // Set Turn to Player Input
        getPlayerTurn();
                         
        if(Limelight.hasValidTarget()) {
            mTurn += mAlignPIDResult;
        } 
    }
}
