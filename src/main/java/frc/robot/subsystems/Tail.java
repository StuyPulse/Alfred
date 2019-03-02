/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.commands.TailClimbCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;

/*
 * The tail mechanism is the second lift of the robot.
 * It consists of a winch, a constant-force spring, and an arm with Abom on it.
 * The constant-force spring is always trying to pull the arm up on the lift.
 * However, the winch, which has a ratchet on it, keeps the arm from going up.
 * When the ratchet is released, the arm shoots up before falling to the platform!
 * We can then climb using the tail's built-in motor.
 */

public final class Tail extends Subsystem {

    CANSparkMax tailMotor;
    Solenoid ratchetSolenoid;

    public Tail() {
        tailMotor = new CANSparkMax(RobotMap.TAIL_MOTOR_PORT, MotorType.kBrushless);
        ratchetSolenoid = new Solenoid(1 , RobotMap.RATCHET_SOLENOID_PORT);

        tailMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new TailClimbCommand());
    }

    public void setSpeed(double speed) {
        tailMotor.set(speed);
    }

    public void stop() {
        tailMotor.set(0.0);
    }

    public double getTomsMetric() {
        return tailMotor.getBusVoltage() * tailMotor.getOutputCurrent();
    }

    public void disengageRatchet() {
        ratchetSolenoid.set(true);
    }

    public void engageRatchet() {
        ratchetSolenoid.set(false);
    }

    public boolean ratchetMoved() {
        return ratchetSolenoid.get();
    }
}