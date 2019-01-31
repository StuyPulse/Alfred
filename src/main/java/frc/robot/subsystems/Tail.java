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

/**
 * Add your docs here.
 */
public class Tail extends Subsystem {

    CANSparkMax tailMotor;
    Solenoid releaseAbomSolenoid;
    Solenoid elevateSolenoid;

    public Tail() {
        tailMotor = new CANSparkMax(RobotMap.TAIL_MOTOR_PORT, MotorType.kBrushless);
        elevateSolenoid = new Solenoid(RobotMap.RAISE_TAIL_SOLENOID_PORT);
        releaseAbomSolenoid = new Solenoid(RobotMap.RELEASE_ABOM_SOLENOID_PORT);

        tailMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new TailClimbCommand());
    }

    // Speed of the Tail Motor
    public void setSpeed(double speed) {
        // ASK ENGINEERING IF THE MOTOR GOES FORWARDS OR BACKWARDS
        tailMotor.set(speed);
    }

    // Stops the Tail Motor
    public void stop() {
        tailMotor.set(0.0);
    }

    // Gets the speed of the Tail Motor
    public double getSpeed() {
        // ASK ENGINEERING IF THE MOTOR GOES FORWARDS OR BACKWARDS
        return tailMotor.get();
    }

    // Abom Stuff
    public void releaseAbom() {
        releaseAbomSolenoid.set(true);
    }

    // Resets Solenoid that deploys abom
    public void retractSolenoid() {
        releaseAbomSolenoid.set(false);
    }

    // Raises the tail for climbing
    public void raiseTail() {
        elevateSolenoid.set(true);
    }

    // Resets the raise Solenoid
    public void resetElevator() {
        elevateSolenoid.set(false);
    }
}
