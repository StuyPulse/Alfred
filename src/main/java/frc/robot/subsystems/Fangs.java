/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Fangs extends Subsystem {
    private DoubleSolenoid fangsSolenoid;

    public Fangs() {
        fangsSolenoid = new DoubleSolenoid(RobotMap.FANGS_OPEN_CHANNEL, RobotMap.FANGS_CLOSE_CHANNEL);
    }

    @Override
    public void initDefaultCommand() {
    }

    @Override
    public void periodic(){
        super.periodic();
    }
    
    public void raise() {
        // TODO: Figure out which setting opens/which closes
        fangsSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void lower() {
        fangsSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggle() {
        if( isUp()) {
            lower();
        } else {
            raise();
        }
    }

    public boolean isUp() {
        return fangsSolenoid.get() == DoubleSolenoid.Value.kForward;
    }
}
