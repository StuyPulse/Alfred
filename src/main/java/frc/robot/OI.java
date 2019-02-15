/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.AbomPumpCommand;
import frc.robot.commands.AbomStopPumpCommand;
import frc.robot.commands.AutomaticDriveCommand;
import frc.robot.commands.AutomaticTurnCommand;
import frc.robot.commands.BITHPOIN;
import frc.robot.commands.DrivetrainHighGearCommand;
import frc.robot.commands.DrivetrainLowGearCommand;
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
import frc.robot.commands.RollersRampDownAcquireCommand;
import frc.util.Gamepad;
import frc.util.Gamepad.GamepadSwitchMode;

public class OI {

    public Gamepad driverGamepad;
    public Gamepad operatorGamepad;

    private boolean abomPumping;

    public OI() {
        driverGamepad = new Gamepad(RobotMap.DRIVER_GAMEPAD_PORT, GamepadSwitchMode.PS4);
        operatorGamepad = new Gamepad(RobotMap.OPERATOR_GAMEPAD_PORT, GamepadSwitchMode.SWITCH_X);
        abomPumping = false;

        /******************************************
         * Driver Code
         ******************************************/
        driverGamepad.getLeftButton().whileHeld(new AutomaticTurnCommand());
        driverGamepad.getTopButton().whileHeld(new AutomaticDriveCommand());
        driverGamepad.getBottomButton().whenPressed(new DrivetrainLowGearCommand());
        driverGamepad.getBottomButton().whenReleased(new DrivetrainHighGearCommand());

        /******************************************
         * Operator Code
         ******************************************/
        operatorGamepad.getRightTrigger().whileHeld(new RollersManualAcquireCommand()); //Verified
        operatorGamepad.getLeftTrigger().whileHeld(new RollersManualDeacquireCommand()); //Verified

        operatorGamepad.getRightBumper().whileHeld(new RollersConstantAcquireCommand()); //Verified
        operatorGamepad.getLeftBumper().whileHeld(new RollersConstantDeacquireCommand()); //Verified

        operatorGamepad.getTopButton().whenPressed(new FangsRaiseCommand()); //Verified
        operatorGamepad.getBottomButton().whenPressed(new FangsLowerCommand()); //Verified
        operatorGamepad.getRightButton().whileHeld(new FloopCloseCommand()); //Verified
        operatorGamepad.getLeftButton().whenPressed(new BITHPOIN()); //Verified
        // operatorGamepad.getLeftButton().whenPressed(new OverrideLimitSwitchCommand());
        // TODO: Create an OverrideLimitSwitchCommand!

        operatorGamepad.getDPadRight().whenPressed(new LiftTiltFowardCommand()); //Verified
        operatorGamepad.getDPadLeft().whenPressed(new LiftTiltBackCommand()); //Verified
        //operatorGamepad.getDPadUp().whenPressed(new HuiMinsDefenseCommand()); //Verified
        operatorGamepad.getDPadDown().whenPressed(new LiftMoveToHeightCommand(0)); //Verified
        //TODO: Figure out defense mode height

        if (operatorGamepad.getRawRightAnalogButton()) {
            if (abomPumping == true){
                new AbomStopPumpCommand();
            } else {
                new AbomPumpCommand();
            }
        }


        //FOR LEFT JOYSTICK: LiftMoveCommand (default of lift subsystem)
        //FOR RIGHT JOYSTICK: TailClimbCommand (default of tail subsystem)
    }
}
