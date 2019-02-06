/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

// The Rollers acquire and deacquire cargos at a set speed.
// They are located at the front of the bot.

public final class Rollers extends Subsystem {
    private WPI_TalonSRX motor;

    public Rollers() {
        motor = new WPI_TalonSRX(RobotMap.ROLLER_MOTOR_PORT);
    }

    @Override
    public void initDefaultCommand() {
        // TODO: Fix RollersRampingCommand to not take in a parameter after OI works
        // setDefaultCommand(new RollersRampingCommand());
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

}
