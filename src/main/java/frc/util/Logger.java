package frc.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

public class Logger {
    PrintWriter writer;
    boolean cannotLog;

    public Logger(File writeFile) {
        cannotLog = false;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(writeFile)));
            writer.println("Timestamp, Subsystem, Command, " +
                        "Motor Output, Encoder Value, Motor stalling?, Motor Current, Motor Voltage, " +
                        "Driver Trigger Left, Driver Trigger Right, Driver Left Stick Y, " + 
                        "Operator Buttons Pressed, Other Value");
            writeToFile();
        } catch (IOException e) {
            setCannotLog();
        }
    }

    public void writeDrivetrain(String value) {
        if (!cannotLog) {
            writer.println(
                getTime() + ", " + 
                Robot.drivetrain.getName() + ", " + Robot.drivetrain.getCurrentCommandName() + ", " +
                Robot.drivetrain.getLeftMotorOutput() + ":" + Robot.drivetrain.getRightMotorOutput() + ", " +
                Robot.drivetrain.getGreyhillDistance() + ", " + 
                //TODO: Needs Motor stalling value
                Robot.drivetrain.getLeftMotorCurrent() + ":" + Robot.drivetrain.getRightMotorCurrent() + ", " +
                Robot.drivetrain.getLeftMotorVoltage() + ":" + Robot.drivetrain.getRightMotorVoltage() + ", " +
                getDriverInputs(Robot.oi.driverGamepad) + ", " + 
                getOperatorButtons(Robot.oi.operatorGamepad) + ", " +
                value
            );
        }
    }

    public void writeLift(String value) {
        if (!cannotLog) {
            writer.println(
                getTime() + ", " + 
                Robot.lift.getName() + ", " + Robot.lift.getCurrentCommandName() + ", " +
                Robot.lift.getMotorOutput() + ", " +
                Robot.lift.getHeight() + ", " + 
                //TODO: Needs Motor stalling value
                Robot.lift.getMotorCurrent() + ", " +
                Robot.lift.getMotorVoltage() + ":" + Robot.lift.getMotorVoltage() + ", " +
                getDriverInputs(Robot.oi.driverGamepad) + ", " + 
                getOperatorButtons(Robot.oi.operatorGamepad) + ", " +
                value
            );
        }
    }

    public void writeSparkMotorSubsystem(Subsystem subsystem, String value, CANSparkMax motor) {
        if (!cannotLog) {
            writer.println(
                getTime() + ", " + 
                subsystem.getName() + ", " + subsystem.getCurrentCommandName() + ", " +
                motor.getAppliedOutput() + ", " +
                "No Encoder, " + 
                //TODO: Needs Motor stalling value
                motor.getOutputCurrent() + ", " +
                motor.getBusVoltage() + ", " +
                getDriverInputs(Robot.oi.driverGamepad) + ", " + 
                getOperatorButtons(Robot.oi.operatorGamepad) + ", " +
                value
            );
        }
    }

    public void writeVictorMotorSubsystem(Subsystem subsystem, String value, WPI_VictorSPX motor) {
        if (!cannotLog) {
            writer.println(
                getTime() + ", " + 
                subsystem.getName() + ", " + subsystem.getCurrentCommandName() + ", " +
                motor.getMotorOutputPercent() + ", " +
                "No Encoder, " + 
                //TODO: Needs Motor stalling value
                "No Current, " +
                motor.getBusVoltage() + ", " +
                getDriverInputs(Robot.oi.driverGamepad) + ", " + 
                getOperatorButtons(Robot.oi.operatorGamepad) + ", " +
                value
            );
        }
    }

    public void writePneumaticSubsystem(Subsystem subsystem, String value, Solenoid piston) {
        if (!cannotLog) {
            writer.println(
                getTime() + ", " + 
                subsystem.getName() + ", " + subsystem.getCurrentCommandName() + ", " +
                piston.get() + ", " +
                "No Encoder, " + 
                //TODO: Needs Motor stalling value
                "No Current, " +
                "No Voltage, " +
                getDriverInputs(Robot.oi.driverGamepad) + ", " + 
                getOperatorButtons(Robot.oi.operatorGamepad) + ", " +
                value
            );
        }
    }

    public String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        return LocalDateTime.now().minusHours(5).format(formatter);
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
        if (writer != null) {
            writer.close();
        }
    }

    public void writeToFile() {
        if (writer != null) {
            writer.flush();
        }
    }
    public void setCannotLog() {
        cannotLog = true;
    }
}