/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;

import frc.robot.subsystems.Drivetrain;

import java.util.Arrays;

import com.stuypulse.stuylib.math.stream.*;
import com.stuypulse.stuylib.math.stream.filter.*;

import com.stuypulse.stuylib.input.*;
import com.stuypulse.stuylib.input.gamepads.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {

    public static NetKeyGamepad gamepad = new NetKeyGamepad(0);
    public static Drivetrain drivetrain = new Drivetrain();
    public static Compressor compressor = new Compressor();

    public static InputStream rawSpeed = () -> gamepad.getLeftY();
    public static InputStream rawAngle = () -> gamepad.getLeftX();

    public static InputStream speed = new FilteredInputStream(
        rawSpeed, new StreamFilterGroup(Arrays.asList(
            new BasicFilters.Circular(),
            new WeightedMovingAverage(64)
        )
    ));

    public static InputStream angle = new FilteredInputStream(
        rawAngle, new StreamFilterGroup(Arrays.asList(
            new BasicFilters.Circular(),
            new WeightedMovingAverage(64)
        )
    ));

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */

    @Override
    public void robotInit() {
        compressor.stop();
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like diagnostics that you want ran during disabled, autonomous,
     * teleoperated and test.
     *
     * <p>
     * This runs after the mode specific periodic functions, but before LiveWindow
     * and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {

    }

    /**
     * This function is called once each time the robot enters Disabled mode. You
     * can use it to reset any subsystem information you want to clear when the
     * robot is disabled.
     */
    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {

    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable chooser
     * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
     * remove all of the chooser code and uncomment the getString code to get the
     * auto name from the text box below the Gyro
     *
     * <p>
     * You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons to
     * the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {

    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        if(gamepad.getRawKey("c") != compressor.enabled()) {
            if(gamepad.getRawKey("c")) {
                compressor.start();
            } else {
                compressor.stop();
            }
        }
    
        double speedval = speed.get();
        double angleval = angle.get();
        
        if(gamepad.getRawKey("space")) {
            speedval = rawSpeed.get();
            angleval = rawAngle.get();
        } 

        drivetrain.curvatureDrive(speedval * 0.6, angleval * 0.8, true);
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {

    }
}