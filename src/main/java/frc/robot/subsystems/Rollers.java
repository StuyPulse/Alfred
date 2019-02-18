/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public final class Rollers extends Subsystem {
    private WPI_VictorSPX motor;

    public Rollers() {
        motor = new WPI_VictorSPX(RobotMap.ROLLER_MOTOR_PORT);
        motor.setInverted(true);
        motor.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public void initDefaultCommand() {
    }

    public void acquire() {
        setSpeed(1.0);
    }

    public void deacquire() {
        setSpeed(-1.0);
    }

    public void stop() {
        setSpeed(0.0);
    }

    public void setSpeed(double speed) {
        motor.set(speed);
    }

    public void rampAcquire() {
        motor.configOpenloopRamp(0.5, 0);
    }
}
