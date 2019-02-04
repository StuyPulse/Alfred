package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class LiftMoveCommand extends Command {

    private final double THRESHOLD = 0.6; 
    private final double ERROR_RANGE = 0.01;

    private enum Direction {
        UP, DOWN, NO_AUTO_COMP;
    }
    private enum Level {
        LEVEL_ZERO, LEVEL_ONE, LEVEL_TWO, LEVEL_THREE;
    }

    private Direction autoCompDir;
    private Level targetLevel;
    

    public LiftMoveCommand() {
        requires(Robot.lift);
        autoCompDir = Direction.NO_AUTO_COMP;
        targetLevel = Level.LEVEL_ZERO;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

        if(targetLevel == Level.LEVEL_ZERO) {
            Robot.lift.move(Robot.oi.operatorGamepad.getLeftY());
        }

        setAutoComp();
        calibrateAutoComp();
        runAutoComp();
    }

    // value is used many times, setautoCompDir() and
    // calibrateautoCompDir()
    private boolean isLeftAnalogPressed() {
        return Robot.oi.operatorGamepad.getRawLeftAnalogButton();
    }

    private void setAutoComp() {
        if (isLeftAnalogPressed()) {
            double leftY = Robot.oi.operatorGamepad.getLeftY();
            if (leftY > THRESHOLD) {
                // if LEFT STICK is HELD and pushed UP
                autoCompDir = Direction.UP;
                // if LEFT STICK is HELD and pushed DOWN
            }else if (leftY < -THRESHOLD) {
                autoCompDir = Direction.DOWN;
            }
            // while LEFT STICK is not HELD
        }else {
            autoCompDir = Direction.NO_AUTO_COMP;
            targetLevel = Level.LEVEL_ZERO;
        }
    }

    private void calibrateAutoComp() {
        if (targetLevel == Level.LEVEL_ZERO && isLeftAnalogPressed()) {
            double leftY = Robot.oi.operatorGamepad.getLeftY();
            if (autoCompDir == Direction.UP && leftY <= THRESHOLD) {
                if (Robot.lift.getHeight() < RobotMap.LEVEL_1_HEIGHT) {
                    targetLevel = Level.LEVEL_ONE;
                } else if (Robot.lift.getHeight() < RobotMap.LEVEL_2_HEIGHT) {
                    targetLevel = Level.LEVEL_TWO;
                } else {
                    targetLevel = Level.LEVEL_THREE;
                }
            } else if(autoCompDir == Direction.DOWN && leftY >= -THRESHOLD) {
                if (Robot.lift.getHeight() > RobotMap.LEVEL_3_HEIGHT) {
                    targetLevel = Level.LEVEL_THREE;
                } else if (Robot.lift.getHeight() > RobotMap.LEVEL_2_HEIGHT) {
                    targetLevel = Level.LEVEL_TWO;
                } else {
                    targetLevel = Level.LEVEL_ONE;
                }
            } 
        }
    }

    private void moveHeight(double numInches) {
        double height = Robot.lift.getHeight();
        if (autoCompDir == Direction.UP && height < numInches) {
            Robot.lift.move(1);
        } else if (autoCompDir == Direction.DOWN && height > numInches) {
            Robot.lift.move(-1);
        } else if (autoCompDir != Direction.NO_AUTO_COMP) {
            if (Math.abs(height - numInches) <= ERROR_RANGE) {
                autoCompDir = Direction.NO_AUTO_COMP;
                targetLevel = Level.LEVEL_ZERO;
            }
        }
    }

    private void runAutoComp() {
        switch(targetLevel) {
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
