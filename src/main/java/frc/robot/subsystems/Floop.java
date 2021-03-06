/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.commands.FloopControlCommand;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public final class Floop extends Subsystem {

    private Solenoid floopSolenoid;
    private Solenoid pusherSolenoid;

    public boolean automationOn;

    public Floop() {
        floopSolenoid = new Solenoid(RobotMap.FLOOP_CHANNEL);
        pusherSolenoid = new Solenoid(1, RobotMap.PUSHER_CHANNEL);
        automationOn = true;
    }

    public void open() {
        if (!isOpen()) {
            floopSolenoid.set(false);
        }
    }

    public void close() {
        if (isOpen()) {
            floopSolenoid.set(true);
        }
    }

    public void toggleFloop() {
        floopSolenoid.set(!isOpen());
    }

    public void toggleAutomation() {
        automationOn = !automationOn;
        System.out.println("AUTOMATION ON: " + automationOn);
    }

    public boolean isOpen() {
        return !floopSolenoid.get();
    }

    public void push() {
        if (!pushed()) {
            pusherSolenoid.set(true);
        }
    }

    public void pull() {
        if (pushed()) {
            pusherSolenoid.set(false);
        }
    }

    public boolean pushed() {
        return pusherSolenoid.get();
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new FloopControlCommand());
    }
}