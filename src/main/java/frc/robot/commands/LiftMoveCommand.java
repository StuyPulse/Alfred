package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

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
    // TODO: Implement OI
    // double speed = Robot.m_oi.operatorGamepad.getY();
    // AutoComp assumes level 1 = 5 inches up, level 2 = 10 inches up, level 3 = 15 inches up (CHANGE THIS)
    if(level == 0) { Robot.lift.moveLift(Robot.oi.operatorGamepad.getLeftY()); }
        setAutoComp();
        calibrateAutoComp();
        runAutoComp();
    }

    private void runAutoComp() {
      if(level == 1) {
          moveToNumInches(5);
      } else if(level == 2) {
          moveToNumInches(10);
      } else if (level == 3) {
          moveToNumInches(15);
      }
    }

    private void moveToNumInches(double numInches) {
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
        if(isleftAnalogPressed() && Robot.oi.operatorGamepad.getLeftY() < -0.25) {
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
            if (Robot.lift.getHeight() < 5) {
                level = 1;
            } else if (Robot.lift.getHeight() < 10) {
                level = 2;
            } else {
                level = 3;
            }
        }

        if(autoComp == -1 && isLeftAnalogPressed() && Robot.oi.operatorGamepad.getLeftY() >= -0.25) {
            if(Robot.lift.getHeight() > 15) {
                level = 3;
            } else if(Robot.lift.getHeight() > 10) {
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
