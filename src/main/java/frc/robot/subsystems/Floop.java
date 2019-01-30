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

/**
 * Add your docs here.
 */
public class Floop extends Subsystem {

    private Solenoid floopSolenoid;

    public Floop() {
        floopSolenoid = new Solenoid(RobotMap.FLOOP_CHANNEL);
    }

    public void open() {
        floopSolenoid.set(true);
    }

    public void stop() {
        floopSolenoid.set(false);
    }

    public void toggle() {
        if(floopSolenoid.get()) {
            stop();
        } else {
            open();
        }
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}
