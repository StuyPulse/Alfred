package frc.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;

public class Logger {
    PrintWriter writer;
    StringBuilder sb;
    Boolean cannotLog;

    public Logger(File writeFile) {
        cannotLog = false;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(writeFile)));
            writer.write("Timestamp, Subsystem, Command, EncoderValue, Motor stalling?, " +
                        "Driver Trigger Left, Driver Trigger Right, Operator Buttons Pressed");
            writer.flush();
        } catch (IOException e) {
            setCannotLog();
        }
    }

    public void write(String command, String value) {
        if (!cannotLog) {
            String currentTime = getTime();
            writer.write(currentTime + ", " + "Drivetrain, " + Robot.drivetrain.getCurrentCommandName() + ", " +
            Robot.drivetrain.getGreyhillDistance() + ", " + //needs Motor stalling value
            Robot.oi.driverGamepad.getRawLeftTriggerAxis() + ", " + Robot.oi.driverGamepad.getRawRightTriggerAxis() + ", " +
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

    public void close() {
        writer.close();
    }
    public void setCannotLog() {
        cannotLog = true;
    }
}