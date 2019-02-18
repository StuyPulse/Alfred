/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.AbomPumpControlCommand;

public final class Abom extends Subsystem {
    Solenoid abomSolenoid;
    public boolean wantPumping;

    public Abom() {
        abomSolenoid = new Solenoid(RobotMap.ABOM_SOLENOID_PORT);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new AbomPumpControlCommand());
    }

    // Toggles between out and in for the solenoid
    public void pump() {
        abomSolenoid.set(!abomSolenoid.get());
    }

    public void pumpIn() {
        abomSolenoid.set(true);
    }

    public void pumpOut() {
        abomSolenoid.set(false);
    }
    // Retracts the Solenoid
    public void stop() {
        abomSolenoid.set(false);
    }

    public boolean get() {
        return abomSolenoid.get();
    }

    public boolean shouldTakeAction(double lastPumped, double timeToActuate) {
        boolean output = lastPumped < 0 
                || (Timer.getFPGATimestamp() - lastPumped) > timeToActuate;
        if(output) System.out.println("true");
        return output;
    }
}
