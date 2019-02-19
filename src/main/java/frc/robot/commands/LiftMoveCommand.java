package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class LiftMoveCommand extends Command {

    private final double THRESHOLD = 0.4;
    private final double MAX_OFFSET = 1;

    private double rumbleStartTime;
    private boolean lastCheckedReachedHeight;
    //saves last checked state of whether or not target height was reached

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
            Robot.lift.move(Math.pow(Robot.oi.operatorGamepad.getLeftY(), 3));
        }

        if (Robot.oi.operatorGamepad.getRawStartButton()) {
            Robot.lift.toggleOpticalSensorOverride();
        }

        if (Robot.oi.operatorGamepad.getRawSelectButton()) {
            Robot.lift.resetEncoder();
        }

        setAutoComp();
        calibrateAutoComp();
        runAutoComp();
        checkHeight();
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

    private void checkForRumble(double targetHeight) {
        if (Math.abs(Robot.lift.getHeight() - targetHeight) <= MAX_OFFSET) {
            if (!lastCheckedReachedHeight) {
                lastCheckedReachedHeight = true;
                rumbleStartTime = Timer.getFPGATimestamp();
                Robot.oi.operatorGamepad.gamepadRumble(1);
            }
            if (Timer.getFPGATimestamp() - rumbleStartTime > 1.0) {
                Robot.oi.operatorGamepad.gamepadRumble(0);
            }
        } else {
            lastCheckedReachedHeight = false;
        }
    }

    private void checkHeight() {
        if(targetLevel == Level.ONE) {
            checkForRumble(RobotMap.LEVEL_1_HEIGHT);
        } else if(targetLevel == Level.TWO) {
            checkForRumble(RobotMap.LEVEL_2_HEIGHT);
        } else if(targetLevel == Level.THREE) {
            checkForRumble(RobotMap.LEVEL_3_HEIGHT);
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