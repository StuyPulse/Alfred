/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public final class Rollers extends Subsystem {

    private CANSparkMax motor;

    public Rollers() {
        motor = new CANSparkMax(RobotMap.ROLLER_MOTOR_PORT, MotorType.kBrushless);
        motor.setInverted(true);
        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    @Override
    public void initDefaultCommand() {
    }

    public void acquire() {
        setSpeed(-1.0);
    }

    public void deacquire() {
        setSpeed(1.0);
    }

    public void stop() {
        setSpeed(0.0);
    }

    public void setSpeed(double speed) {
        motor.set(speed);
    }

    public void enableRamping() {
        motor.setOpenLoopRampRate(0.5);
    }

    public void disableRamping() {
        motor.setOpenLoopRampRate(0.0);
    }
}