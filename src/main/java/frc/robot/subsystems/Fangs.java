/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

// The fangs mechanism is a piston that can be extended in order to pick up
// hatch panels from the carpet.
public final class Fangs extends Subsystem {
    //private Solenoid fangsSolenoid;

    public Fangs() {
        //fangsSolenoid = new Solenoid(RobotMap.FANGS_CHANNEL);
    }

    @Override
    public void initDefaultCommand() {
    }
    
    public void raise() {
        // Fangs Solenoid begins as not extended.
        // Extending it will raise fangs. (confirmed w/ Engineering)
        //fangsSolenoid.set(true);
    }

    public void lower() {
        //fangsSolenoid.set(false);
    }

    public void toggle() {
        //fangsSolenoid.set(!isUp());
    }

    public boolean isUp() {
        //return fangsSolenoid.get();
        return true;
    }
}