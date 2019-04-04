/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.util.Limelight;
import frc.util.Limelight.LEDMode;

public class FloopCloseCommand extends Command {

    public FloopCloseCommand() {
        requires(Robot.floop);
    }

    @Override
    protected void initialize() {
        Robot.floop.close();
    }

    @Override
    protected void execute() {
        System.out.println("automationOn: " + Robot.floop.automationOn);
        System.out.println("gamePieceDetected: " + Robot.isGamePieceDetected());
        if (Robot.floop.automationOn && Robot.isGamePieceDetected()) {
            Robot.floop.open();
            Limelight.setLEDMode(LEDMode.FORCE_BLINK);
        }
    }

    @Override
    protected boolean isFinished() {
        //return Robot.floop.automationOn && Robot.isGamePieceDetected();
        return false;
    }
}