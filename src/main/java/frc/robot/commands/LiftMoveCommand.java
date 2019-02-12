package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LiftMoveCommand extends Command {

    private final double THRESHOLD = 0.6;
    private final double ERROR_RANGE = 0.01;

    private enum Direction {
        UP, DOWN, NULL;
    }

    private enum Level {
        LEVEL_ZERO, LEVEL_ONE, LEVEL_TWO, LEVEL_THREE;
    }

    private Direction autoCompDir;
    private Level targetLevel;

    public LiftMoveCommand() {
        requires(Robot.lift);
        autoCompDir = Direction.NULL;
        targetLevel = Level.LEVEL_ZERO;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

        // if(level == 0) {
        //     double speed = Robot.oi.operatorGamepad.getLeftY();
        //     if(Math.abs(speed) > .2) {
        //         Robot.lift.moveLift(speed);
        //         System.out.println("Speedy" + speed);
        //     } else {
        //         Robot.lift.moveLift(0);
        //         System.out.println("Stopped" + speed);
        //     }
        //         System.out.println(speed);
        // // }

        // setAutoComp();
        // calibrateAutoComp();
        // runAutoComp();
    }

    private boolean isAutoCompActive() {
        return Robot.oi.operatorGamepad.getRawLeftAnalogButton();
    }

    private void setAutoCompDirection() {
        if (isAutoCompActive()) {
            double leftY = Robot.oi.operatorGamepad.getLeftY();
            if (leftY > THRESHOLD) {
                // if LEFT STICK is HELD and pushed UP
                autoCompDir = Direction.UP;
                // if LEFT STICK is HELD and pushed DOWN
            } else if (leftY < -THRESHOLD) {
                autoCompDir = Direction.DOWN;
            }
            // while LEFT STICK is not HELD
        } else {
            autoCompDir = Direction.NULL;
            targetLevel = Level.LEVEL_ZERO;
        }
    }

    // private void setTargetLevel() {
    //     // preparing to set a target and the left analog is still pressed
    //     if (targetLevel == Level.LEVEL_ZERO && isAutoCompActive()) {
    //         double leftY = Robot.oi.operatorGamepad.getLeftY();
    //         // the direction is going up and joystick is back to center
    //         if (autoCompDir == Direction.UP && leftY <= THRESHOLD) {
    //             // set target to the next setpoint height above
    //             if (Robot.lift.getHeight() < RobotMap.LEVEL_1_HEIGHT) {
    //                 targetLevel = Level.LEVEL_ONE;
    //             } else if (Robot.lift.getHeight() < RobotMap.LEVEL_2_HEIGHT) {
    //                 targetLevel = Level.LEVEL_TWO;
    //             } else {
    //                 targetLevel = Level.LEVEL_THREE;
    //             }
    //             // the direction is going down and joystick is back to center
    //         } else if (autoCompDir == Direction.DOWN && leftY >= -THRESHOLD) {
    //             // set target to the next setpoint height below
    //             if (Robot.lift.getHeight() > RobotMap.LEVEL_3_HEIGHT) {
    //                 targetLevel = Level.LEVEL_THREE;
    //             } else if (Robot.lift.getHeight() > RobotMap.LEVEL_2_HEIGHT) {
    //                 targetLevel = Level.LEVEL_TWO;
    //             } else {
    //                 targetLevel = Level.LEVEL_ONE;
    //             }
    //         }
    //     }
    // }

    // private void moveHeight(double numInches) {
    //     double height = Robot.lift.getHeight();
    //     if (autoCompDir == Direction.UP && height < numInches) {
    //         // Going up
    //         Robot.lift.move(1);
    //     } else if (autoCompDir == Direction.DOWN && height > numInches) {
    //         // Going down
    //         Robot.lift.move(-1);
    //     } else if (autoCompDir != Direction.NULL) {
    //         if (Math.abs(height - numInches) <= ERROR_RANGE) {
    //             // Reached the destation, so reset for next cycle
    //             autoCompDir = Direction.NULL;
    //             targetLevel = Level.LEVEL_ZERO;
    //         }
    //     }
    // }

    // private void runAutoComp() {
    //     // Move to the target height
    //     switch (targetLevel) {
    //     case LEVEL_ONE:
    //         moveHeight(RobotMap.LEVEL_1_HEIGHT);
    //         break;
    //     case LEVEL_TWO:
    //         moveHeight(RobotMap.LEVEL_2_HEIGHT);
    //         break;
    //     case LEVEL_THREE:
    //         moveHeight(RobotMap.LEVEL_3_HEIGHT);
    //         break;
    //     }
    // }

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
