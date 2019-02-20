/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.util.Limelight;

public class AutomaticTurnCommand extends DrivetrainDriveCommand {

    @Override
    protected void initialize() {
        setInterruptible(false);
    }

    @Override
    protected void setTurn() {
        // Set the turn value to the joysticks x value
        super.setTurn();

        // Get turn constants 
        double smallAngleThreshold = SmartDashboard.getNumber("TURN_MIN_ANGLE",1);
        double smallAngleSpeed = SmartDashboard.getNumber("TURN_MIN_SPEED",0.5);
         
        // Add corrective values to turn based on how fast the robot is moving
        if( Limelight.hasValidTarget() /*&& Math.abs(Limelight.getTargetXAngle()) > smallAngleThreshold */){
            //turn += getTurnValue(Limelight.getTargetXAngle(), smallAngleSpeed);
            /*Math.max(SmartDashboard.getNumber("MOVE_TURN_DIV", 2) * speed,1)*/
            double turn_MUL = SmartDashboard.getNumber("MOVE_TURN_MUL", 6) * speed;
            double sgn = Math.signum(Limelight.getTargetXAngle());
            turn += Math.max(turn_MUL, 1) * sgn * 
                    Math.sqrt(Math.abs(Limelight.getTargetXAngle())) /
                    (SmartDashboard.getNumber("TURN_DIV", 35));
            
        }
    }
    // private double getTurnValue(double targetXAngle, double minTurn){
    //     double turnDampingConstant = SmartDashboard.getNumber("TURN_DIV", 120);
    //     // This number gets larger when the robot is already moving.
    //     double turnDriveDamper = SmartDashboard.getNumber("MOVE_TURN_DIV", 2) * speed;
    //     // This value iis the final drive damping constant, it can never be < 1.
    //     double turnDriveDamperCorrected = Math.max(turnDriveDamper,1);

    //     double finalDampingConstant = 1 * turnDampingConstant;
        
    //     double outputTurn = targetXAngle/finalDampingConstant;

    //     double outputPolarity = Math.signum(outputTurn);
    //     return outputTurn;
    //     //return outputPolarity * Math.max(Math.abs(outputTurn),minTurn);
        
    // }
}
