/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.AbomToggleCommand;
import frc.robot.commands.AutomaticDriveCommand;
import frc.robot.commands.AutomaticTurnCommand;
import frc.robot.commands.DrivetrainHighGearCommand;
import frc.robot.commands.DrivetrainLowGearCommand;
import frc.robot.commands.DrivetrainNudgeCommand;
import frc.robot.commands.FloopCloseCommand;
import frc.robot.commands.FloopOpenCommand;
import frc.robot.commands.FloopPullCommand;
import frc.robot.commands.FloopPushCommand;
import frc.robot.commands.FloopStartScoreCommand;
import frc.robot.commands.FloopStopScoreCommand;
import frc.robot.commands.LiftMoveToHeightCommand;
import frc.robot.commands.LiftSlowToggleCommand;
import frc.robot.commands.LiftToggleCommand;
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

    public OI() {
        
        driverGamepad = new Gamepad(RobotMap.DRIVER_GAMEPAD_PORT, GamepadSwitchMode.PS4);
        operatorGamepad = new Gamepad(RobotMap.OPERATOR_GAMEPAD_PORT, GamepadSwitchMode.SWITCH_X);

        /******************************************
         * Driver Code
         ******************************************/
        driverGamepad.getLeftButton().whileHeld(new AutomaticTurnCommand());
        driverGamepad.getTopButton().whileHeld(new AutomaticDriveCommand());
        driverGamepad.getRightButton().whenPressed(new LiftMoveToHeightCommand(RobotMap.START_HEIGHT));
        driverGamepad.getBottomButton().whenPressed(new DrivetrainLowGearCommand());
        driverGamepad.getBottomButton().whenReleased(new DrivetrainHighGearCommand());
        driverGamepad.getDPadLeft().whenPressed(new DrivetrainNudgeCommand(-1));
        driverGamepad.getDPadRight().whenPressed(new DrivetrainNudgeCommand(1));

        /******************************************  
         * Operator Code
         ******************************************/
        operatorGamepad.getRightTrigger().whileHeld(new RollersManualAcquireCommand());
        operatorGamepad.getLeftTrigger().whileHeld(new RollersManualDeacquireCommand());

        operatorGamepad.getRightBumper().whileHeld(new RollersConstantAcquireCommand());
        operatorGamepad.getRightBumper().whenReleased(new RollersRampDownAcquireCommand(1));
        operatorGamepad.getLeftBumper().whileHeld(new RollersConstantDeacquireCommand());

        operatorGamepad.getTopButton().whileHeld(new FloopPushCommand());
        operatorGamepad.getTopButton().whenReleased((new FloopPullCommand()));
        operatorGamepad.getRightButton().whileHeld(new FloopCloseCommand());
        operatorGamepad.getRightButton().whenReleased(new FloopOpenCommand());
        operatorGamepad.getBottomButton().whileHeld(new FloopStartScoreCommand());
        operatorGamepad.getBottomButton().whenReleased(new FloopStopScoreCommand());

        operatorGamepad.getDPadLeft().whenPressed(new LiftToggleCommand());

        operatorGamepad.getRightAnalogButton().whenPressed(new AbomToggleCommand());
        operatorGamepad.getLeftAnalogButton().whenPressed(new LiftSlowToggleCommand());

        //FOR LEFT JOYSTICK: LiftMoveCommand (default of lift subsystem)
        //FOR RIGHT JOYSTICK: TailClimbCommand (default of tail subsystem)
    }
}