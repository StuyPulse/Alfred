/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.util.Limelight;

public class AutomaticTurnCommand extends DrivetrainDriveCommand {

    @Override
    protected void initialize() {
        setInterruptible(false);
    }

    @Override
    protected void setTurn() {

        super.setTurn(); 
        // Add corrective values to turn based on how fast the robot is moving
        if( Limelight.hasValidTarget() /*&& Math.abs(Limelight.getTargetXAngle()) > smallAngleThreshold */){
            //turn += getTurnValue(Limelight.getTargetXAngle(), smallAngleSpeed);
            /*Math.max(SmartDashboard.getNumber("MOVE_TURN_DIV", 2) * speed,1)*/
            double turn_MUL = SmartDashboard.getNumber("MOVE_TURN_MUL", 6) * speed;
            double sgn = Math.signum(Limelight.getTargetXAngle());
            double output =  Math.max(turn_MUL, 1) * sgn * 
                             Math.sqrt(Math.abs(Limelight.getTargetXAngle())) /
                             (SmartDashboard.getNumber("TURN_DIV", 35));
            SmartDashboard.putNumber("LIMELIGHT_MOTOR_OUTPUT", output);
            turn += output;
            
        }
    }
}
