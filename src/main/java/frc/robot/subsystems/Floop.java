/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

// The Floop reaches into the hole of the hatch 
// and expands outward to hold it.

public final class Floop extends Subsystem {

    private Solenoid floopSolenoid;

    public Floop() {
        floopSolenoid = new Solenoid(RobotMap.FLOOP_CHANNEL);
    }

    public void open() {
        floopSolenoid.set(true);
    }

    public void close() {
        floopSolenoid.set(false);
    }

    public void toggle() {
        if(isOpen()) {
            close();
        } else {
            open();
        }
    }

    public boolean isOpen() {
        return floopSolenoid.get();
    }

    @Override
    public void initDefaultCommand() {
    }
}
