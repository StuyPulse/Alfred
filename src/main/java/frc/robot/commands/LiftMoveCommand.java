package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class LiftMoveCommand extends Command {

    private int autoCompDirection;
    private level targetLevel;
    private enum level {
        LEVEL_ZERO,LEVEL_ONE, LEVEL_TWO, LEVEL_THREE;
    }
    

    public LiftMoveCommand() {
        requires(Robot.lift);
        autoCompDirection = 0;
        targetLevel = level.LEVEL_ZERO;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

        if(targetLevel == level.LEVEL_ZERO) {
            Robot.lift.move(Robot.oi.operatorGamepad.getLeftY());
        }

        setautoCompDirection();
        calibrateautoCompDirection();
        runautoCompDirection();
    }

    private void runautoCompDirection() {
        switch(targetLevel){
            case LEVEL_ONE :
                moveHeight(RobotMap.LEVEL_1_HEIGHT);
            break;
            case LEVEL_TWO :
                moveHeight(RobotMap.LEVEL_2_HEIGHT);
            break;
            case LEVEL_THREE :
                moveHeight(RobotMap.LEVEL_3_HEIGHT);
            break;
        }
    }

    private void moveHeight(double numInches) {
        if (autoCompDirection == 1 && Robot.lift.getHeight() < numInches) {
            Robot.lift.move(1);
        } else if(autoCompDirection == -1 && Robot.lift.getHeight() > numInches) {
            Robot.lift.move(-1);
        } else if(autoCompDirection != 0) {
            autoCompDirection = 0;
            targetLevel = level.LEVEL_ZERO;
        }
    }

    // value is used many times, setautoCompDirection() and calibrateautoCompDirection()
    private boolean isLeftAnalogPressed() {
        return Robot.oi.operatorGamepad.getRawLeftAnalogButton();
    }

    private void setautoCompDirection() {
        // if LEFT STICK is HELD and pushed UP
        if(isLeftAnalogPressed() && Robot.oi.operatorGamepad.getLeftY() > 0.6) {
            autoCompDirection = 1;
        }

        // if LEFT STICK is HELD and pushed DOWN
        if(isLeftAnalogPressed() && Robot.oi.operatorGamepad.getLeftY() < -0.6) {
            autoCompDirection = -1;
        }

        // while LEFT STICK is not HELD
        if(!isLeftAnalogPressed()) {
            autoCompDirection = 0;
            targetLevel = level.LEVEL_ZERO;
        }
    }

    private void calibrateautoCompDirection() {
        if (autoCompDirection == 1 && targetLevel == level.LEVEL_ZERO && isLeftAnalogPressed() && Robot.oi.operatorGamepad.getLeftY() <= 0.6) {
            if (Robot.lift.getHeight() < RobotMap.LEVEL_1_HEIGHT) {
                targetLevel = level.LEVEL_ONE;
            } else if (Robot.lift.getHeight() < RobotMap.LEVEL_2_HEIGHT) {
                targetLevel = level.LEVEL_TWO;
            } else {
                targetLevel = level.LEVEL_THREE;
            }
        }else{
            if(autoCompDirection == -1 && isLeftAnalogPressed() && Robot.oi.operatorGamepad.getLeftY() >= -0.25) {
                if(Robot.lift.getHeight() > RobotMap.LEVEL_3_HEIGHT) {
                    level = 3;
                } else if(Robot.lift.getHeight() > RobotMap.LEVEL_2_HEIGHT) {
                    level = 2;
                } else {
                    level = 1;
                }
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
