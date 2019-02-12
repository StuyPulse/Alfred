/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.AutomaticDriveCommand;
import frc.robot.commands.AutomaticTurnCommand;
import frc.robot.commands.FangsLowerCommand;
import frc.robot.commands.FangsRaiseCommand;
import frc.robot.commands.FloopCloseCommand;
import frc.robot.commands.FloopOpenCommand;
import frc.robot.commands.LiftMoveToHeightCommand;
import frc.robot.commands.LiftTiltBackCommand;
import frc.robot.commands.LiftTiltFowardCommand;
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
        driverGamepad = new Gamepad(RobotMap.DRIVER_GAMEPAD_PORT, GamepadSwitchMode.PS4);
        operatorGamepad = new Gamepad(RobotMap.OPERATOR_GAMEPAD_PORT, GamepadSwitchMode.SWITCH_X);

        /******************************************
        * Driver Code
        ******************************************/
        driverGamepad.getLeftButton().whileHeld(new AutomaticTurnCommand());
        driverGamepad.getTopButton().whileHeld(new AutomaticDriveCommand());
        // driverGamepad.getBottomButton().whenPressed(new DrivetrainLowGearCommand());
        // driverGamepad.getBottomButton().whenReleased(new DrivetrainHighGearCommand());

        /******************************************
        * Operator Code
        ******************************************/
        operatorGamepad.getRightTrigger().whileHeld(new RollersManualAcquireCommand());
        operatorGamepad.getLeftTrigger().whileHeld(new RollersManualDeacquireCommand());

        operatorGamepad.getRightBumper().whileHeld(new RollersConstantAcquireCommand());
        operatorGamepad.getLeftBumper().whileHeld(new RollersConstantDeacquireCommand());

        operatorGamepad.getTopButton().whenPressed(new FangsRaiseCommand());
        operatorGamepad.getBottomButton().whenPressed(new FangsLowerCommand());
        operatorGamepad.getRightButton().whenPressed(new FloopCloseCommand());
        operatorGamepad.getRightButton().whenReleased(new FloopOpenCommand());
        // operatorGamepad.getLeftButton().whenPressed(new OverrideLimitSwitchCommand());
        // TODO: Create an OverrideLimitSwitchCommand!

        operatorGamepad.getDPadRight().whenPressed(new LiftTiltFowardCommand());
        operatorGamepad.getDPadLeft().whenPressed(new LiftTiltBackCommand());
        operatorGamepad.getDPadUp().whenPressed(new LiftMoveToHeightCommand(-1));
        operatorGamepad.getDPadUp().whenPressed(new LiftTiltBackCommand());
        operatorGamepad.getDPadDown().whenPressed(new LiftMoveToHeightCommand(0));
        //TODO figure out defense mode height

        // operatorGamepad.getDPadUp().whenPressed(new AbomPumpCommand());
        // operatorGamepad.getDPadDown().whenPressed(new AbomStopPumpCommand());
    }
}
