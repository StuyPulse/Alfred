/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.commands.ClimbCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Add your docs here.
 */
public class Tail extends Subsystem {

    Victor tailMotor;
    Solenoid releaseAbomSolenoid;
    Solenoid elevateSolenoid;

    public Tail() {
        tailMotor = new Victor(RobotMap.TAIL_MOTOR);
        elevateSolenoid = new Solenoid(RobotMap.RAISE_TAIL_SOLENOID);
        releaseAbomSolenoid = new Solenoid(RobotMap.RELEASE_ABOM_SOLENOID);
    }

    @Override
    public void initDefaultCommand() {
        // Default Command is the Climb Command
        setDefaultCommand(new ClimbCommand());
    }

    // Speed of the Tail Motor
    public void setSpeed(double speed) {
        tailMotor.set(speed);
    }

    // Stops the Tail Motor
    public void stop() {
        tailMotor.set(0);
    }

    // Gets the speed of the Tail Motor
    public double getSpeed() {
        return tailMotor.get();
    }

    // Motor Safety
    public boolean isAlive() {
        return tailMotor.isAlive();
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
