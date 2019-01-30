/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.AbomChargeCommand;
import frc.robot.commands.CVAutoCommand;
import frc.robot.commands.CVTurnCommand;
import frc.robot.commands.DrivetrainGearshiftCommand;
import frc.robot.commands.FangsLowerCommand;
import frc.robot.commands.FangsRaiseCommand;
import frc.robot.commands.FloopStopCommand;
import frc.robot.commands.FloopOpenCommand;
import frc.robot.commands.RollersAcquireFastCommand;
import frc.robot.commands.RollersAcquireSlowCommand;
import frc.robot.commands.RollersDeacquireSlowCommand;
import frc.robot.commands.RollersMoveSpeedCommand;
import frc.robot.commands.RollersRampingCommand;
import frc.robot.commands.RollersDeacquireFastCommand;
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
        driverGamepad.getLeftButton().whileHeld(new CVTurnCommand());
        driverGamepad.getBottomButton().whileActive(new DrivetrainGearshiftCommand());
        driverGamepad.getTopButton().whileHeld(new CVAutoCommand());

        /******************************************
        * Operator Code
        ******************************************/
        //TODO: Make these real!
        operatorGamepad.getRightTrigger().whileHeld(new RollersRampingCommand(operatorGamepad.getRawRightTriggerAxis()));
        operatorGamepad.getLeftTrigger().whileHeld(new RollersRampingCommand(operatorGamepad.getRawLeftTriggerAxis()));
        operatorGamepad.getRightBumper().whileHeld(new RollersMoveSpeedCommand(1));
        operatorGamepad.getLeftBumper().whileHeld(new RollersMoveSpeedCommand(-1));
        operatorGamepad.getTopButton().whileHeld(new FangsRaiseCommand());
        operatorGamepad.getBottomButton().whileHeld(new FangsLowerCommand());
        operatorGamepad.getRightButton().whileHeld(new FloopStopCommand());
        operatorGamepad.getLeftButton().whileHeld(new FloopOpenCommand());
        // operatorGamepad.getLeftAnalogButton().whenPressed(); TODO: Make command
        // operatorGamepad.getRightAnalogButton().whenPressed(new AbomPumpCommand)
        operatorGamepad.getDPadUp().whenPressed(new AbomChargeCommand(true));
        operatorGamepad.getDPadDown().whenPressed(new AbomChargeCommand(false));
    }
}
