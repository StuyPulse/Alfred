package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class LiftMoveCommand extends Command {

    private int autoComp;
    private int level;

    public LiftMoveCommand() {
        requires(Robot.lift);
        autoComp = 0;
        level = 0;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

        if(level == 0) {
            Robot.lift.moveLift(Robot.oi.operatorGamepad.getLeftY());
        }

        setAutoComp();
        calibrateAutoComp();
        runAutoComp();
    }

    private void runAutoComp() {
        if(level == 1) {
            moveHeight(RobotMap.LEVEL_1_HEIGHT);
        } else if(level == 2) {
            moveHeight(RobotMap.LEVEL_2_HEIGHT);
        } else if(level == 3) {
            moveHeight(RobotMap.LEVEL_3_HEIGHT);
        }
    }

    private void moveHeight(double numInches) {
        if (autoComp == 1 && Robot.lift.getHeight() < numInches) {
            Robot.lift.moveLift(1);
        } else if(autoComp == -1 && Robot.lift.getHeight() > numInches) {
            Robot.lift.moveLift(-1);
        } else if(autoComp != 0) {
            autoComp = 0;
            level = 0;
        }
    }

    // value is used many times, setAutoComp() and calibrateAutoComp()
    private boolean isLeftAnalogPressed() {
        return Robot.oi.operatorGamepad.getRawLeftAnalogButton();
    }

    private void setAutoComp() {
        // if LEFT STICK is HELD and pushed UP
        if(isLeftAnalogPressed() && Robot.oi.operatorGamepad.getLeftY() > 0.25) {
            autoComp = 1;
        }

        // if LEFT STICK is HELD and pushed DOWN
        if(isLeftAnalogPressed() && Robot.oi.operatorGamepad.getLeftY() < -0.25) {
            autoComp = -1;
        }

        // while LEFT STICK is not HELD
        if(!isLeftAnalogPressed()) {
            autoComp = 0;
            level = 0;
        }
    }

    private void calibrateAutoComp() {
        if (autoComp == 1 && level == 0 && isLeftAnalogPressed() && Robot.oi.operatorGamepad.getLeftY() <= 0.25) {
            if (Robot.lift.getHeight() < RobotMap.LEVEL_1_HEIGHT) {
                level = 1;
            } else if (Robot.lift.getHeight() < RobotMap.LEVEL_2_HEIGHT) {
                level = 2;
            } else {
                level = 3;
            }
        }

        if(autoComp == -1 && isLeftAnalogPressed() && Robot.oi.operatorGamepad.getLeftY() >= -0.25) {
            if(Robot.lift.getHeight() > RobotMap.LEVEL_3_HEIGHT) {
                level = 3;
            } else if(Robot.lift.getHeight() > RobotMap.LEVEL_2_HEIGHT) {
                level = 2;
            } else {
                level = 1;
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {

    }

}
