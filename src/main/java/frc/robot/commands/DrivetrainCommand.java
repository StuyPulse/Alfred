/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap.Drivetrain;
import frc.robot.Robot;

@SuppressWarnings("unused")
public abstract class DrivetrainCommand extends Command {
    // Variables to feed to curvature drive
    protected double speed = 0;
    protected double turn = 0; 
    protected boolean quickTurn = true;

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

    // Used for changing state of limelight without spamming networktable
    public enum States { START, DRIVER, CV, OTHER; }
    protected static States state = States.START;

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
            SmartDashboard.putNumber("Drivetrain Speed", speed);
            SmartDashboard.putNumber("Drivetrain Turn", turn);
            SmartDashboard.putBoolean("Drivetrain QuickTurn", quickTurn);
            SmartDashboard.putString("Drivetrain Mode", state.name());
        }
    }

    // Sub commands for each curvature drive variable
    protected void updateDrivetrain() {
        Robot.drivetrain.curvatureDrive(speed, turn, quickTurn);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
