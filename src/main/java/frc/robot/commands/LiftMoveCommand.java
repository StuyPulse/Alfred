package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class LiftMoveCommand extends Command {

    private final double THRESHOLD = 0.4;

    private enum Direction{
        UP, DOWN, NULL;
    }
    private enum Level{
        ZERO, ONE, TWO, THREE;
    }
    
    private Direction autoCompDir;
    private Level targetLevel;
    
    public LiftMoveCommand() {
        requires(Robot.lift);
        autoCompDir = Direction.NULL;
        targetLevel = Level.ZERO;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

        if(targetLevel == Level.ZERO) {
            Robot.lift.move(Robot.oi.operatorGamepad.getLeftY());
        }

        if (Robot.oi.operatorGamepad.getRawStartButton()) {
            Robot.lift.overrideOpticalSensor();
        }

        setAutoComp();
        calibrateAutoComp();
        runAutoComp();
    }

    // value is used many times, setAutoComp() and calibrateAutoComp()
    private boolean isLeftAnalogPressed() {
        return Robot.oi.operatorGamepad.getRawLeftAnalogButton();
    }

    private void setAutoComp() {
        // if LEFT STICK is HELD and pushed UP
        if(isLeftAnalogPressed() && Robot.oi.operatorGamepad.getLeftY() > THRESHOLD) {
            autoCompDir = Direction.UP;
        }

        // if LEFT STICK is HELD and pushed DOWN
        if(isLeftAnalogPressed() && Robot.oi.operatorGamepad.getLeftY() < -THRESHOLD) {
            autoCompDir = Direction.DOWN;
        }

        // while LEFT STICK is not HELD
        if(!isLeftAnalogPressed()) {
            autoCompDir = Direction.NULL;
            targetLevel = Level.ZERO;
        }
    }

    private void calibrateAutoComp() {
        if (autoCompDir == Direction.UP && targetLevel == Level.ZERO && isLeftAnalogPressed() && Robot.oi.operatorGamepad.getLeftY() <= THRESHOLD) {
            if (Robot.lift.getHeight() < RobotMap.LEVEL_1_HEIGHT) {
                targetLevel = Level.ONE;
            } else if (Robot.lift.getHeight() < RobotMap.LEVEL_2_HEIGHT) {
                targetLevel = Level.TWO;
            } else {
                targetLevel = Level.THREE;
            }
        }

        if(autoCompDir == Direction.DOWN && targetLevel == Level.ZERO && isLeftAnalogPressed() && Robot.oi.operatorGamepad.getLeftY() >= -THRESHOLD) {
            if(Robot.lift.getHeight() > RobotMap.LEVEL_3_HEIGHT) {
                targetLevel = Level.THREE;
            } else if(Robot.lift.getHeight() > RobotMap.LEVEL_2_HEIGHT) {
                targetLevel = Level.TWO;
            } else {
                targetLevel = Level.ONE;
            }
        }
    }

    private void runAutoComp() {
        if(targetLevel == Level.ONE && autoCompDir == Direction.UP) {
            moveHeight(RobotMap.LEVEL_1_HEIGHT - RobotMap.LIFT_LEVEL_OFFSHOOT);
        } else if(targetLevel == Level.TWO && autoCompDir == Direction.UP) {
            moveHeight(RobotMap.LEVEL_2_HEIGHT - RobotMap.LIFT_LEVEL_OFFSHOOT);
        } else if(targetLevel == Level.THREE && autoCompDir == Direction.UP) {
            moveHeight(RobotMap.LEVEL_3_HEIGHT - RobotMap.LIFT_LEVEL_OFFSHOOT);
        } else if(targetLevel == Level.ONE && autoCompDir == Direction.DOWN) {
            moveHeight(RobotMap.LEVEL_1_HEIGHT + RobotMap.LIFT_LEVEL_OFFSHOOT);
        } else if(targetLevel == Level.TWO && autoCompDir == Direction.DOWN) {
            moveHeight(RobotMap.LEVEL_2_HEIGHT + RobotMap.LIFT_LEVEL_OFFSHOOT);
        } else if(targetLevel == Level.THREE && autoCompDir == Direction.DOWN) {
            moveHeight(RobotMap.LEVEL_3_HEIGHT + RobotMap.LIFT_LEVEL_OFFSHOOT);
        }
    }

    private void moveHeight(double numInches) {
        if (autoCompDir == Direction.UP && Robot.lift.getHeight() < numInches) {
            Robot.lift.move(1);
        } else if(autoCompDir == Direction.DOWN && Robot.lift.getHeight() > numInches) {
            Robot.lift.move(Robot.liftSpeedGoingDown);
        } else if(autoCompDir != Direction.NULL) {
            autoCompDir = Direction.NULL;
            targetLevel = Level.ZERO;
            Robot.oi.operatorGamepad.gamepadRumble(0.25);
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