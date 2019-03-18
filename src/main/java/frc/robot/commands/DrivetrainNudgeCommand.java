/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

/**
 * Add your docs here.
 */
public class DrivetrainNudgeCommand extends DrivetrainDriveCommand {
    int time = 1000;
    int dir = 1;

    public DrivetrainNudgeCommand(int time, int dir) {
        this.time = time;
        this.dir = dir;
    }
    public DrivetrainNudgeCommand(int dir) {
        this.dir = dir;
    }
    @Override
    protected void initialize() {
        setInterruptible(false);
    }

    @Override
    public void execute(){
        speed = 0.4;
        turn = dir*0.3;
        quickTurn = true;
        updateDrivetrain();
        time--;
    }
    @Override
    public boolean isFinished(){
        return time == 0;
    }
}
