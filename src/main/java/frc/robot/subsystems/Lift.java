/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.LiftMoveCommand;

public class Lift extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private WPI_TalonSRX masterTalon;
    private WPI_TalonSRX followerTalon;

    private DigitalInput topLimitSwitch;
    private DigitalInput bottomLimitSwitch;

    private DoubleSolenoid tiltSolenoid;
    private DoubleSolenoid brakeSolenoid;

    public boolean rampDisabled;

    public boolean isAutomatic;

    public Lift() {
        masterTalon = new WPI_TalonSRX(RobotMap.LIFT_MASTER_TALON_MOTOR_PORT);
        followerTalon = new WPI_TalonSRX(RobotMap.LIFT_FOLLOWER_TALON_MOTOR_PORT);

        followerTalon.follow(masterTalon);

        masterTalon.setNeutralMode(NeutralMode.Brake);
        followerTalon.setNeutralMode(NeutralMode.Brake);

        masterTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

        tiltSolenoid = new DoubleSolenoid(RobotMap.LIFT_TILT_SOLENOID_FORWARD_PORT, RobotMap.LIFT_TILT_SOLENOID_REVERSE_PORT);
        brakeSolenoid = new DoubleSolenoid(RobotMap.LIFT_BRAKE_SOLENOID_FORWARD_PORT, RobotMap.LIFT_BRAKE_SOLENOID_REVERSE_PORT);

        topLimitSwitch = new DigitalInput(RobotMap.LIFT_TOP_LIMIT_SWITCH_PORT);
        bottomLimitSwitch = new DigitalInput(RobotMap.LIFT_BOTTOM_LIMIT_SWITCH_PORT);

        setManual();
    }

    @Override
    public void initDefaultCommand() {
        // TODO: Implement oi
        // setDefaultCommand(new LiftMoveCommand(Robot.m_oi.operatorGamepad.getY()));
    }

    public void resetEncoder() {
        masterTalon.setSelectedSensorPosition(0, 0, 0);
    }

    public boolean isAtTop() {
        return topLimitSwitch.get();
    }

    public boolean isAtBottom() {
        return bottomLimitSwitch.get();
    }

    public int getRawHeight() {
        return masterTalon.getSelectedSensorPosition();
    }

    public double getHeight() {
        return getRawHeight() * RobotMap.LIFT_ENCODER_RAW_MULTIPLIER;
    }

    public void stopLift() {
        masterTalon.set(ControlMode.PercentOutput, 0);
        enableBrake();
    }

    public void moveLift(double speed) {
        if (Math.abs(speed) < RobotMap.LIFT_MIN_SPEED) {
            stopLift();
        } else if (isAtTop() || isAtBottom()) {
            stopLift();
        } else {
            releaseBrake();
            masterTalon.set(ControlMode.PercentOutput, speed);
        }

    }

    // rampMultiplier takes a distance and threshold from a hard limit, calculates
    // multiplier for linear ramping
    public double rampMultiplier(double distance) {
        double threshold = RobotMap.LIFT_RAMP_HEIGHT_THRESHOLD;
        if (threshold <= 0) {
            return 1.0;
        }
        if (distance > threshold) {
            return 1.0;
        }
        if (distance < 0) {
            return 1.0;
        }
        return distance / threshold;
    }

    public void moveRamp(double desiredSpeed) {
        double currentHeight = getHeight();
        double speed = desiredSpeed;
        if (desiredSpeed < 0 && currentHeight < RobotMap.LIFT_RAMP_HEIGHT_THRESHOLD) {
            double distanceFromBottom = currentHeight;
            speed = rampMultiplier(distanceFromBottom) * desiredSpeed;
            speed = Math.min(speed, -RobotMap.LIFT_MIN_SPEED);
        } else if (currentHeight > RobotMap.LIFT_MAX_HEIGHT - RobotMap.LIFT_RAMP_HEIGHT_THRESHOLD) {
            double distanceFromTop = RobotMap.LIFT_MAX_HEIGHT - currentHeight;
            speed = rampMultiplier(distanceFromTop) * desiredSpeed;
            speed = Math.max(speed, RobotMap.LIFT_MIN_SPEED);
        }
        moveLift(speed);
    }

    public void setHeight(double height) {
        releaseBrake();
        masterTalon.set(ControlMode.Position, height / RobotMap.LIFT_ENCODER_RAW_MULTIPLIER);
    }

    public void tiltLiftUp() {
        tiltSolenoid.set(Value.kForward);
    }

    public void tiltLiftDown() {
        tiltSolenoid.set(Value.kReverse);
    }

    public void enableBrake() {
        brakeSolenoid.set(Value.kForward);
    }

    public void releaseBrake() {
        brakeSolenoid.set(Value.kReverse);
    }

    public void enableRamping() {
        rampDisabled = false;
        if (!isAutomatic) {
            masterTalon.configOpenloopRamp(0.5, 0);
        } else {
            masterTalon.configOpenloopRamp(0.2, 0);
        }
    }

    public void disableOpenLoopRamping() {
        rampDisabled = true;
        masterTalon.configOpenloopRamp(0.2, 0);
    }

    public void disableAllRamping() {
        rampDisabled = true;
        masterTalon.configOpenloopRamp(0, 0);
    }

    public void setAutomatic() {
        isAutomatic = true;
    }

    public void setManual() {
        isAutomatic = false;
    }
}
