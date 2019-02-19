/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Abom;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Fangs;
import frc.robot.subsystems.Floop;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Rollers;
import frc.robot.subsystems.Tail;
import frc.util.LEDRelayController;

public class Robot extends TimedRobot {
    
    public static Drivetrain drivetrain;
    public static OI oi;
    public static Floop floop;
    public static Abom abom;
    public static Tail tail;
    public static Lift lift;
    public static Compressor compressor;
    public static Rollers rollers;
    public static Fangs fangs;

    public static double liftSpeedGoingDown;

    public static DigitalInput IRsensor;

    public static LEDRelayController relayController;

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    @Override
    public void robotInit() {
        drivetrain = new Drivetrain();
        floop = new Floop();
        abom = new Abom();
        tail = new Tail();
        lift = new Lift();
        compressor = new Compressor();
        rollers = new Rollers();
        fangs = new Fangs();
        oi = new OI();
        IRsensor = new DigitalInput(RobotMap.IR_SENSOR_PORT);
        relayController = new LEDRelayController(RobotMap.LED_CHANNEL);

        SmartDashboard.putData("Auto mode", chooser);
        SmartDashboard.putBoolean("Enable compressor", true);

        CameraServer.getInstance().startAutomaticCapture(0);
        SmartDashboard.putNumber("TURN_DIV", 35);
        SmartDashboard.putNumber("MOVE_TURN_MUL", 6 );

        SmartDashboard.putNumber("TURN_MIN_SPEED", 0.2);
        SmartDashboard.putNumber("TURN_MIN_ANGLE", 1);

        SmartDashboard.putBoolean("VALID_TARGET", false);
        SmartDashboard.putBoolean("VALID_HEIGHT", false);
        SmartDashboard.putBoolean("VALID_RATIO", false);
        SmartDashboard.putBoolean("VALID_SKEW", false);

        SmartDashboard.putNumber("CAM_MODE", 1);
    }

    @Override
    public void robotPeriodic() {
        controlCompressor();
        SmartDashboard.putBoolean("IR Sensor", isGamePieceDetected());
        liftSpeedGoingDown = SmartDashboard.getNumber("Lift Auto Complete Speed Going Down", 0.5);
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = chooser.getSelected();

        if (autonomousCommand != null) {
            autonomousCommand.start();
        }
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        Robot.floop.open();
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {
        // if(!isGamePieceDetected()) {
        //     relayController.setLEDForward();
        // } else {
        //     relayController.setLEDNeutral();
        // }
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Drivetrain Left Greyhill Encoder Val: ", Robot.drivetrain.getLeftGreyhillDistance());
        SmartDashboard.putNumber("Drivetrain Right Greyhill Encoder Val: ",
                Robot.drivetrain.getRightGreyhillDistance());
        SmartDashboard.putNumber("Drivetrain Left Greyhill Raw Val: ", Robot.drivetrain.getLeftGreyhillTicks());
        SmartDashboard.putNumber("Drivetrain Right Greyhill Raw Val: ",
                Robot.drivetrain.getRightGreyhillTicks());
        SmartDashboard.putNumber("Lift Encoder Val: ", Robot.lift.getHeight());
        SmartDashboard.putBoolean("Lift Bottom Optical Sensor: ", Robot.lift.isAtBottom());
        SmartDashboard.putBoolean("Is Lift Optical Sensor Overrided: ", Robot.lift.isOpticalSensorOverrided);
        SmartDashboard.putNumber("Tom's Metric for Tail: ", Robot.tail.getTomsMetric());
        // if(isGamePieceDetected()) {
        //     //Once a game piece is detected, it blinks two times and stops.
        //     blinkLED();
        // }
        // else {
        //     //Stops the LEDs as long as it doesn't detect a game piece.
        //     relayController.setLEDNeutral();
        // }
    }

    @Override
    public void testPeriodic() {
    }

    public void controlCompressor() {
        if (!drivetrain.isMoving() && SmartDashboard.getBoolean("Enable compressor", false)) {
            compressor.start();
        } else {
            compressor.stop();
        }
    }

    private void setUpDoubleSolenoids() {
        lift.tiltBack();
        fangs.lower();
    }

    private boolean isGamePieceDetected() {
        return IRsensor.get();
    }

    // private void blinkLED() {
    //     double startTime = Timer.getFPGATimestamp();
    //     if(Timer.getFPGATimestamp() - startTime > 4) {
    //         relayController.setLEDForward();
    //     }
    //     else if((int)(Timer.getFPGATimestamp() - startTime) % 2 == 0) {
    //         relayController.setLEDForward();
    //     }
    //     else {
    //         relayController.setLEDNeutral();
    //     }
    // }
}