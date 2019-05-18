/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap.Drivetrain;
import frc.util.Scalars;

public abstract class DrivetrainCommand extends Command {

    // Helper class used to get gamepad controls
    public static class Controller {
        public static double getTriggers() {
            double mSpeed;
            
            mSpeed = Scalars.circular(Robot.oi.driverGamepad.getRawRightTriggerAxis());
            mSpeed -= Scalars.circular(Robot.oi.driverGamepad.getRawLeftTriggerAxis());
            
            return mSpeed;
        }

        @SuppressWarnings("unused")
        public static double getJoystick() {
            return Scalars.pow(Robot.oi.driverGamepad.getLeftX(), Drivetrain.Controls.JOYSTICK_SCALAR);;
        }
    }
    
    // Variables to feed to curvature drive
    protected double mSpeed = 0;
    protected double mTurn = 0; 
    protected boolean mQuickTurn = true;

    public DrivetrainCommand() {
        requires(Robot.drivetrain);
    }

    @Override
    protected void execute() {
        setCameraMode();
        setSpeed();
        setTurn();
        setQuickTurn();
        updateSmartdashboard();
        updateDrivetrain();
    }

    /* Current mode of the drivetrain */
    public enum States { START, DRIVER, CV, OTHER; }
    protected static States mState = States.START;

    /* Updating limelight camera */
    protected abstract void setCameraMode();

    /* Updating Speed */
    protected abstract void setSpeed();

    /* Updating Turning */
    protected abstract void setTurn();

    /* Updating Quick Turn */
    protected abstract void setQuickTurn();

    /* Updating SmartDashboard */
    protected void updateSmartdashboard() {
        if(Drivetrain.SMARTDASHBOARD_DEBUG) {
            SmartDashboard.putNumber("Drivetrain Speed", mSpeed);
            SmartDashboard.putNumber("Drivetrain Turn", mTurn);
            SmartDashboard.putBoolean("Drivetrain QuickTurn", mQuickTurn);
            SmartDashboard.putString("Drivetrain Mode", mState.name());
        }
    }

    // Sub commands for each curvature drive variable
    protected void updateDrivetrain() {
        Robot.drivetrain.curvatureDrive(mSpeed, mTurn, mQuickTurn);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
