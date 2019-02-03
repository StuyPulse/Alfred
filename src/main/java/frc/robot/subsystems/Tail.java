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
// The tail mechanism extends Abom, allowing us to climb.
public final class Tail extends Subsystem {

    CANSparkMax tailMotor;
    Solenoid ratchetSolenoid;

    public Tail() {
        tailMotor = new CANSparkMax(RobotMap.TAIL_MOTOR_PORT, MotorType.kBrushless);
        ratchetSolenoid = new Solenoid(RobotMap.RATCHET_SOLENOID_PORT);

        tailMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new TailClimbCommand());
    }

    public void setSpeed(double speed) {
        // TODO: ASK ENGINEERING IF THE MOTOR GOES FORWARDS OR BACKWARDS
        tailMotor.set(speed);
    }

    public void stop() {
        tailMotor.set(0.0);
    }

    public void releaseRatchet() {
        ratchetSolenoid.set(true);
    }

    public void reengageRatchet() {
        ratchetSolenoid.set(false);
    }

    public boolean ratchetMoved() {
        return ratchetSolenoid.get();

    }
}