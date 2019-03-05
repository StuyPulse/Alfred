/* TO GET FILES FROM THE ROBORIO:
 * ssh into the roborio with: "ssh lvuser@roborio-694-frc.local"
 * cd into the Logs folder
 * type scp lvuser@roborio-694-frc.local:/home/lvuser/Logs/{file} C:\Users\{path to place we want} 
*/
package frc.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

public class Logger {
    // Writer used to write to the file
    PrintWriter writer;

    // Boolean to make sure a null logger does not crash the bot
    boolean cannotLog;

    /**
     * Constructor
     * @param writeFile The desired file we want to write into
    */
    public Logger(File writeFile) {
        cannotLog = false;

        // Write the headings, and check to see if the file exists
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(writeFile)));
            writer.println("Timestamp, Subsystem, Command, " +
                        "Motor Output, Encoder Value, Motor stalling?, Motor Current, Motor Voltage, " +
                        "Driver Trigger Left, Driver Trigger Right, Driver Left Stick Y, Driver Gear Shift, " + 
                        "Operator Buttons Pressed, Operator Trigger Left, Operator Trigger Right, " +
                        "Operator Left Stick Y, Operator Right Stick Y, Other Value");
            writeToFile();
        } catch (IOException e) {
            // Prevent writing if there is no file
            setCannotLog();
        }
    }
    
    /**
     * Logs the drivetrain motor values and the gamepad inputs
     * @param value Extra values we want to Log
     */
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
                getCurvatureDriverInputs(Robot.oi.driverGamepad) + ", " + 
                getOperatorButtons(Robot.oi.operatorGamepad) + ", " + 
                getOperatorAxisInputs(Robot.oi.operatorGamepad) + ", " +
                value
            );
        }
    }

    /**
     * Logs the lift motor values and the gamepad inputs
     * @param value Extra values we want to Log
     */
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
                getCurvatureDriverInputs(Robot.oi.driverGamepad) + ", " + 
                getOperatorButtons(Robot.oi.operatorGamepad) + ", " +
                getOperatorAxisInputs(Robot.oi.operatorGamepad) + ", " +
                value
            );
        }
    }

    /**
     * Logs the sparkMotor Subsystem's motor values and the gamepad inputs
     * @param subsystem The subsystem we want to Log
     * @param value Extra values we want to Log
     * @param motor The motor in the subsystem (Modify drivetrain or lift for subsystems with multiple motors)
     */
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
                getCurvatureDriverInputs(Robot.oi.driverGamepad) + ", " + 
                getOperatorButtons(Robot.oi.operatorGamepad) + ", " +
                getOperatorAxisInputs(Robot.oi.operatorGamepad) + ", " +
                value
            );
        }
    }

    /**
     * Logs the Victor_SPX Subsystem's motor values and the gamepad inputs
     * @param subsystem The subsystem we want to Log
     * @param value Extra values we want to Log
     * @param motor The motor in the subsystem (Modify drivetrain or lift for subsystems with multiple motors)
     */
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
                getCurvatureDriverInputs(Robot.oi.driverGamepad) + ", " + 
                getOperatorButtons(Robot.oi.operatorGamepad) + ", " +
                getOperatorAxisInputs(Robot.oi.operatorGamepad) + ", " +
                value
            );
        }
    }

    /**
     * Logs the Pneumatic Subsystem's values and the gamepad inputs
     * @param subsystem The subsystem we want to Log
     * @param value Extra values we want to Log
     * @param piston The piston in the subsystem (If more than one, make a separate method to log it)
     */
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
                getCurvatureDriverInputs(Robot.oi.driverGamepad) + ", " + 
                getOperatorButtons(Robot.oi.operatorGamepad) + ", " +
                getOperatorAxisInputs(Robot.oi.operatorGamepad) + ", " +
                value
            );
        }
    }

    /**
     * @return The current date in hours:minutes:seconds format
     */
    public String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        return LocalTime.now().minusHours(5).format(formatter);
    }

    /**
     * @return The driver's inputs, with triggers, the axis of the left stick, and gear shift
     */
    public String getCurvatureDriverInputs(Gamepad driver) {
        String output = "";
        output += driver.getRawLeftTriggerAxis() + ", " +
                  driver.getRawRightTriggerAxis() + ", " + 
                  driver.getLeftX() + ", " +
                  driver.getRawBottomButton();
        return output;
    }

    /**
     * @return The Operator's axis inputs, with triggers and the Y axis for the both sticks
     */
    public String getOperatorAxisInputs(Gamepad operator) {
        String output = "";
        output += operator.getRawLeftTriggerAxis() + ", " +
                  operator.getRawRightTriggerAxis() + ", " + 
                  operator.getLeftY() + ", " +
                  operator.getRightY();
        return output;
    }

    /**
     * @return All of the operator's button inputs
     */
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