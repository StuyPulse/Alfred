package frc.robot.commands;

import frc.robot.commands.DrivetrainDriveCommand;
import frc.robot.RobotMap.Drivetrain;

class DrivetrainNudgeCommand extends DrivetrainDriveCommand {

    @Override
    protected void setTurn() {
        super.setTurn();
        turn *= Drivetrain.TurnSpeed.NUDGE;
    }
}