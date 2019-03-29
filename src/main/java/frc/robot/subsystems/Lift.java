/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.LiftMoveCommand;

public final class Lift extends Subsystem {

    private WPI_TalonSRX masterTalon;
    private WPI_VictorSPX followerTalon;

    private DigitalInput bottomLimitSensor;

    private DoubleSolenoid tiltSolenoid;
    private Solenoid brakeSolenoid;

    public boolean rampDisabled;
    public boolean isLimitSensorOverrided;
    private boolean wantSlow;

    public Lift() {
        masterTalon = new WPI_TalonSRX(RobotMap.LIFT_MASTER_TALON_MOTOR_PORT);
        followerTalon = new WPI_VictorSPX(RobotMap.LIFT_FOLLOWER_VICTOR_MOTOR_PORT);

        followerTalon.follow(masterTalon);

        masterTalon.setNeutralMode(NeutralMode.Brake);
        followerTalon.setNeutralMode(NeutralMode.Brake);

        masterTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

        tiltSolenoid = new DoubleSolenoid(1, RobotMap.LIFT_TILT_SOLENOID_FORWARD_PORT,
                RobotMap.LIFT_TILT_SOLENOID_REVERSE_PORT);
        brakeSolenoid = new Solenoid(1, RobotMap.LIFT_BRAKE_SOLENOID_PORT);

        bottomLimitSensor = new DigitalInput(RobotMap.LIFT_BOTTOM_LIMIT_SENSOR_PORT);

        enableRamping();

        // Encoders
        masterTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new LiftMoveCommand());
    }

    public void resetEncoder() {
        masterTalon.setSelectedSensorPosition(0, 0, 0);
    }

    public void setHeight(double height) {
        masterTalon.setSelectedSensorPosition((int) (height / RobotMap.LIFT_ENCODER_RAW_MULTIPLIER), 0, 0);
    }

    public double getRawEncoderUnits() {
        return masterTalon.getSelectedSensorPosition();
    }

    public double getHeight() {
        return -1.0 * getRawEncoderUnits() * RobotMap.LIFT_ENCODER_RAW_MULTIPLIER;
    }

    public boolean isAtBottom() {
        if (!isLimitSensorOverrided) {
            boolean atBottom = !bottomLimitSensor.get();
            if (atBottom) {
                setHeight(RobotMap.LIFT_MIN_HEIGHT);
            }
            return atBottom; // The sensor is inverted
        }
        return false;
    }

    public void toggleOpticalSensorOverride() {
        isLimitSensorOverrided = !isLimitSensorOverrided;
    }

    public void stop() {
        masterTalon.set(0);
        enableBrake();
    }

    public void moveNoRamp(double speed) {
        if (Math.abs(speed) < RobotMap.LIFT_MIN_SPEED 
            && Math.abs(Robot.oi.operatorGamepad.getLeftX()) < RobotMap.LIFT_DEADBAND_OVERRIDE_THRESHOLD) {
            stop();
        } else if (isAtBottom() && speed < 0) {
            stop();
        } else {
            releaseBrake();
            masterTalon.set(speed * RobotMap.LIFT_SPEED_MULTIPLIER);
        }
    }

    // rampMultiplier takes a distance from a hard limit, and calculates
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
        System.out.println("moveRamp");
        double currentHeight = getHeight();
        double speed = desiredSpeed;
        if (desiredSpeed < 0 && currentHeight < RobotMap.LIFT_RAMP_HEIGHT_THRESHOLD) {
            // If you want to move the lift down, get the distance from the bottom and
            // adjust speed proportionally.
            double distanceFromBottom = currentHeight;
            speed = rampMultiplier(distanceFromBottom) * desiredSpeed;
            speed = Math.min(speed, -RobotMap.LIFT_MIN_SPEED);
        }
        // If the current height isn't within the height range for ramping, move without
        // ramping.
        moveNoRamp(speed);
    }

    public void move(double speed) {
        // if (rampDisabled) {
            moveNoRamp(speed);
        // } else {
            // moveRamp(speed);
        // }
    }

    public void tiltForward() {
        tiltSolenoid.set(Value.kReverse);
    }

    public void tiltBack() {
        tiltSolenoid.set(Value.kForward);
    }

    public boolean isTiltedForward() {
        return tiltSolenoid.get() == Value.kReverse;
    }

    public void toggle() {
        if (isTiltedForward()) {
            tiltBack();
        } else {
            tiltForward();
        }
    }

    public void enableBrake() {
        brakeSolenoid.set(false);
    }

    public void releaseBrake() {
        brakeSolenoid.set(true);
    }

    public void enableRamping() {
        rampDisabled = false;
        masterTalon.configOpenloopRamp(RobotMap.LIFT_RAMP_RATE);
        followerTalon.configOpenloopRamp(RobotMap.LIFT_RAMP_RATE);
    }

    public void disableRamping() {
        rampDisabled = true;
        masterTalon.configOpenloopRamp(0);
        followerTalon.configOpenloopRamp(0);
    }
    
    public double getMotorOutput() {
        return masterTalon.getMotorOutputPercent();
    }

    public double getMotorVoltage() {
        return masterTalon.getBusVoltage();
    }

    public double getMotorCurrent() {
        return masterTalon.getOutputCurrent();
    }

    public boolean getWantSlow() {
        return wantSlow;
    }

    public void toggleWantSlow() {
        wantSlow = !wantSlow;
    }
}