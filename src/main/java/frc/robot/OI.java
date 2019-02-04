/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.AbomChargeCommand;
import frc.robot.commands.AbomStopChargeCommand;
import frc.robot.commands.AutomaticDriveCommand;
import frc.robot.commands.AutomaticTurnCommand;
import frc.robot.commands.DrivetrainHighGearCommand;
import frc.robot.commands.DrivetrainLowGearCommand;
import frc.robot.commands.FangsLowerCommand;
import frc.robot.commands.FangsRaiseCommand;
import frc.robot.commands.FloopCloseCommand;
import frc.robot.commands.FloopOpenCommand;
import frc.robot.commands.RollersConstantAcquireCommand;
import frc.robot.commands.RollersConstantDeacquireCommand;
import frc.robot.commands.RollersManualAcquireCommand;
import frc.robot.commands.RollersManualDeacquireCommand;
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
        driverGamepad.getLeftButton().whileHeld(new AutomaticTurnCommand());
        driverGamepad.getTopButton().whileHeld(new AutomaticDriveCommand());
        driverGamepad.getBottomButton().whenPressed(new DrivetrainLowGearCommand());
        driverGamepad.getBottomButton().whenReleased(new DrivetrainHighGearCommand());

        /******************************************
        * Operator Code
        ******************************************/
        operatorGamepad.getRightTrigger().whileHeld(new RollersManualAcquireCommand());
        operatorGamepad.getLeftTrigger().whileHeld(new RollersManualDeacquireCommand());

        operatorGamepad.getRightBumper().whileHeld(new RollersConstantAcquireCommand());
        operatorGamepad.getLeftBumper().whileHeld(new RollersConstantDeacquireCommand());

        operatorGamepad.getTopButton().whileHeld(new FangsRaiseCommand());
        operatorGamepad.getBottomButton().whileHeld(new FangsLowerCommand());
        operatorGamepad.getRightButton().whileHeld(new FloopCloseCommand());
        operatorGamepad.getLeftButton().whileHeld(new FloopOpenCommand());

        // operatorGamepad.getLeftAnalogButton().whenPressed(); TODO: Make command
        // operatorGamepad.getRightAnalogButton().whenPressed(new AbomPumpCommand)

        operatorGamepad.getDPadUp().whenPressed(new AbomChargeCommand());
        operatorGamepad.getDPadDown().whenPressed(new AbomStopChargeCommand());
    }
}
