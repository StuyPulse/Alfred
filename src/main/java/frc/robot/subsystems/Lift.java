
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.LiftMoveCommand;

public class Lift extends Subsystem {
    private WPI_TalonSRX masterTalon;
    private WPI_TalonSRX followerTalon;

    private DigitalInput topLimitSwitch;
    private DigitalInput bottomLimitSwitch;

    private DoubleSolenoid tiltSolenoid;
    private DoubleSolenoid brakeSolenoid;

    public boolean rampDisabled;

    public Lift() {
        masterTalon = new WPI_TalonSRX(RobotMap.LIFT_MASTER_TALON_MOTOR_PORT);
        followerTalon = new WPI_TalonSRX(RobotMap.LIFT_FOLLOWER_TALON_MOTOR_PORT);

        followerTalon.follow(masterTalon);

        masterTalon.setNeutralMode(NeutralMode.Brake);
        followerTalon.setNeutralMode(NeutralMode.Brake);

        masterTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

        tiltSolenoid = new DoubleSolenoid(RobotMap.LIFT_TILT_SOLENOID_FORWARD_PORT,
                RobotMap.LIFT_TILT_SOLENOID_REVERSE_PORT);
        brakeSolenoid = new DoubleSolenoid(RobotMap.LIFT_BRAKE_SOLENOID_FORWARD_PORT,
                RobotMap.LIFT_BRAKE_SOLENOID_REVERSE_PORT);

        topLimitSwitch = new DigitalInput(RobotMap.LIFT_TOP_LIMIT_SWITCH_PORT);
        bottomLimitSwitch = new DigitalInput(RobotMap.LIFT_BOTTOM_LIMIT_SWITCH_PORT);

        enableRamping();
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new LiftMoveCommand());
    }

    public void resetEncoder() {
        masterTalon.setSelectedSensorPosition(0, 0, 0);
    }

    public void setEncoder(double height) {
        masterTalon.setSelectedSensorPosition((int) (height / RobotMap.LIFT_ENCODER_RAW_MULTIPLIER), 0, 0);
    }

    public boolean isAtTop() {
        boolean atTop = topLimitSwitch.get();
        if (atTop) {
            setEncoder(RobotMap.LIFT_MAX_HEIGHT);
        }
        return atTop;
    }

    public boolean isAtBottom() {
        boolean atBottom = bottomLimitSwitch.get();
        if (atBottom) {
            setEncoder(RobotMap.LIFT_MIN_HEIGHT);
        }
        return atBottom;
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

    public void moveNoRamp(double speed) {
        if (Math.abs(speed) < RobotMap.LIFT_MIN_SPEED) {
            stopLift();
        } else if (isAtTop() || isAtBottom()) {
            stopLift();
        } else {
            releaseBrake();
            masterTalon.set(ControlMode.PercentOutput, speed);
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
        moveNoRamp(speed);
    }

    public void moveLift(double speed) {
        if (rampDisabled) {
            moveNoRamp(speed);
        } else {
            moveRamp(speed);
        }
    }

    public void tiltFoward() {
        tiltSolenoid.set(Value.kForward);
    }

    public void tiltBack() {
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
    }

    public void disableRamping() {
        rampDisabled = true;
    }
}
