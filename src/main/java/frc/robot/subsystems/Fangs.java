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

/**
 * Add your docs here.
 */
public class Fangs extends Subsystem {

    private DoubleSolenoid fangSolenoid;

    public Fangs() {
        fangSolenoid = new DoubleSolenoid(
            RobotMap.FANG_SOLENOID_OPEN_PORT,
            RobotMap.FANG_SOLENOID_CLOSE_PORT
        );
    }

    public boolean isUp() {
        return fangSolenoid.get() == DoubleSolenoid.Value.kForward;
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        super.periodic();
    }

    public void lower() {
        // TODO: Figure out which setting opens/which closes
        fangSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void raise() {
        fangSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggleFangs() {
        if(isUp()) {
            lower();
        } else {
            raise();
        }
    }
}
