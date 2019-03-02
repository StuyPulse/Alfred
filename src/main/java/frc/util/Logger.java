package frc.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import frc.robot.Robot;

public class Logger {
    PrintWriter writer;
    StringBuilder sb;
    Boolean cannotLog;

    public Logger(File writeFile) {
        cannotLog = false;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(writeFile)));
            writer.write("Timestamp, Subsystem, Command, Motor Output, Encoder Value, Motor stalling?, " +
                        "Driver Trigger Left, Driver Trigger Right, Driver Left Stick Y, " + 
                        "Operator Buttons Pressed");
            writer.flush();
        } catch (IOException e) {
            setCannotLog();
        }
    }

    public void write(String value) {
        if (!cannotLog) {
            writer.write (
                getTime() + ", " + 
                "Drivetrain, " + Robot.drivetrain.getCurrentCommandName() + ", " +
                Robot.drivetrain.getLeftMotorOutput() + ":" + Robot.drivetrain.getRightMotorOutput() + ", " +
                Robot.drivetrain.getGreyhillDistance() + ", " + 
                //TODO: Needs Motor stalling value
                getDriverInputs(Robot.oi.driverGamepad) + ", " + 
                getOperatorButtons(Robot.oi.operatorGamepad) + ", "
            );
            writer.flush();
        }
    }

    public String getTime() {
        long time = System.currentTimeMillis();
        long second = (time / 1000) % 60;
        long minute = (time / (1000 * 60)) % 60;
        long hour = ((time / (1000 * 60 * 60)) % 24) - 5;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public String getDriverInputs(Gamepad driver) {
        String output = "";
        output += Robot.oi.driverGamepad.getRawLeftTriggerAxis() + ", " +
                  Robot.oi.driverGamepad.getRawRightTriggerAxis() + ", " + 
                  Robot.oi.driverGamepad.getLeftX();
        return output;
    }

    public String getOperatorButtons(Gamepad operator) {
        String output = "";
        if (operator.getRawDPadDown()) {
            output += "DPadDown ";
        } else if (operator.getRawDPadUp()) {
            output += "DPadUp ";
        } else if (operator.getRawDPadLeft()) {
            output += "DPadLeft ";
        } else if (operator.getRawDPadRight()) {
            output += "DPadRight ";
        }
        if (operator.getRawTopButton()) {
            output += "Top ";
        }
        if (operator.getRawBottomButton()) {
            output += "Bottom ";
        }
        if (operator.getRawLeftButton()) {
            output += "Left ";
        }
        if (operator.getRawRightButton()) {
            output += "Right ";
        }
        if (operator.getRawLeftAnalogButton()) {
            output += "LeftAnalog ";
        }
        if (operator.getRawRightAnalogButton()) {
            output += "RightAnalog ";
        }
        if (operator.getRawSelectButton()) {
            output += "Select ";
        }
        if (operator.getRawStartButton()) {
            output += "Start ";
        }
        if (operator.getRawOptionButton()) {
            output += "Option ";
        }
        return output;
    }

    public void close() {
        writer.close();
    }
    public void setCannotLog() {
        cannotLog = true;
    }
}