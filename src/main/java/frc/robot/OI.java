/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.CVAutoCommand;
import frc.robot.commands.FloopCloseCommand;
import frc.robot.commands.FloopOpenCommand;
import frc.util.Gamepad;
import frc.util.Gamepad.GamepadSwitchMode;

public class OI {
    public Gamepad driverGamepad;
    public Gamepad operatorGamepad;

    public OI() {
        driverGamepad = new Gamepad(RobotMap.DRIVER_GAMEPAD_PORT, GamepadSwitchMode.SWITCH_X);
        operatorGamepad = new Gamepad(RobotMap.OPERATOR_GAMEPAD_PORT, GamepadSwitchMode.SWITCH_X);
        /******************************************
        * Driver Code
        ******************************************/
        // TODO: Make these real!
        // driverGamepad.getLeftButton().whileHeld(new CVLaneCommand());
        // driverGamepad.getBottomButton().whileActive(new GearShiftCommand());
        driverGamepad.getTopButton().whileHeld(new CVAutoCommand());

        /******************************************
        * Operator Code
         ******************************************/
        //TODO: Make these real!
        // operatorGamepad.getRightTrigger().whileHeld(new RollersAcquireCommand(1));
        // operatorGamepad.getLeftTrigger().whileHeld(new RollersDeacquireCommand());
        // operatorGamepad.getRightBumper().whileHeld(new RollersAcquireCommand(.5));
        // operatorGamepad.getLeftBumper().whileHeld(new RollersDeacquireCommand());
        // operatorGamepad.getTopButton().whileHeld(new FangsUpCommand());
        // operatorGamepad.getBottomButton().whileHeld(new FangsDownCommand());
        operatorGamepad.getRightButton().whileHeld(new FloopCloseCommand());
        operatorGamepad.getLeftButton().whileHeld(new FloopOpenCommand());
    }
}