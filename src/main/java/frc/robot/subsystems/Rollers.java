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

public class Rollers extends Subsystem {
    private WPI_TalonSRX motor;

    public Rollers() {
        motor = new WPI_TalonSRX(RobotMap.ROLLER_MOTOR_PORT);
    }

    @Override
    public void initDefaultCommand() {
    }

    public void acquire() {
        setSpeed(1);
    }

    public void deacquire() {
        setSpeed(-1);
    }

    public void acquireSpeed(double speed) {
        setSpeed(speed);
    }

    public void stop() {
        setSpeed(0);
    }

    public void setSpeed(double speed) {
        motor.set(speed);
    }

}
