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

public final class Floop extends Subsystem {

    private Solenoid floopSolenoid;
    private Solenoid pusherSolenoid;

    public Floop() {
        floopSolenoid = new Solenoid(RobotMap.FLOOP_CHANNEL);
        pusherSolenoid = new Solenoid(RobotMap.PUSHER_CHANNEL);
    }

    public void open() {
        floopSolenoid.set(false);
    }

    public void close() {
        floopSolenoid.set(true);
    }

    public void toggle() {
        floopSolenoid.set(!isOpen());
    }

    public boolean isOpen() {
        return floopSolenoid.get();
    }

    public void push() {
        pusherSolenoid.set(true);
    }

    public void pull() {
        pusherSolenoid.set(false);
    }

    public boolean pushed() {
        return pusherSolenoid.get();
    }

    public void prepareForRollers() {
        pull();
        open();
    }

    @Override
    public void initDefaultCommand() {
    }
}