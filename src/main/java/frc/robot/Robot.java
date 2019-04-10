/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Abom;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Floop;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Rollers;
import frc.robot.subsystems.Tail;
import frc.util.LEDRelayController;
import frc.util.Limelight;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {
    // More Motor Stall stuff
    double start_time;
    double change_distance = 0.0;
    double start_encoder_value;
    double abs_raw_distance;
    double raw_distance;
    double encoder_approach_stall_threshold = 5.0;

    public static Drivetrain drivetrain;
    public static OI oi;
    public static Floop floop;
    public static Abom abom;
    public static Tail tail;
    public static Lift lift;
    public static Compressor compressor;
    public static Rollers rollers;

    public static double liftSpeedGoingDown;

    public static DigitalInput IRsensor;

    public static double autonStartTime;
    public static double autonCurrTime;

    public static LEDRelayController relayController;
    public boolean hasBeenZeroed;
    public static boolean scoreCargo;
    public static boolean rollersStalling;

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */

    @Override
    public void robotInit() {
        drivetrain = new Drivetrain();
        floop = new Floop();
        abom = new Abom();
        tail = new Tail();
        lift = new Lift();
        compressor = new Compressor();
        rollers = new Rollers();
        oi = new OI();
        IRsensor = new DigitalInput(RobotMap.IR_SENSOR_PORT);

        relayController = new LEDRelayController(RobotMap.LED_CHANNEL);

        // chooser.addOption("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);

        // CameraServer.getInstance().startAutomaticCapture(0);
        SmartDashboard.putNumber("TURN_DIV", RobotMap.TURN_DIV);
        SmartDashboard.putNumber("MOVE_TURN_MUL", RobotMap.MOVE_TURN_MUL);
        SmartDashboard.putNumber("AUTOMATIC_DRIVE_SPEED", RobotMap.AUTOMATIC_DRIVE_SPEED);
        SmartDashboard.putNumber("SPEED_WHILE_TURNING", RobotMap.SPEED_WHILE_TURNING);

        Limelight.setLEDMode(Limelight.LEDMode.FORCE_OFF);
        hasBeenZeroed = false;
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
        // SmartDashboard.putNumber("Drivetrain Left Greyhill Encoder Val: ",
        // Robot.drivetrain.getLeftGreyhillDistance());
        // SmartDashboard.putNumber("Drivetrain Right Greyhill Encoder Val: ",
        // Robot.drivetrain.getRightGreyhillDistance());
        // SmartDashboard.putNumber("Drivetrain Left Greyhill Raw Val: ",
        // Robot.drivetrain.getLeftGreyhillTicks());
        // SmartDashboard.putNumber("Drivetrain Right Greyhill Raw Val: ",
        // Robot.drivetrain.getRightGreyhillTicks());
        SmartDashboard.putBoolean("IR Sensor: ", isGamePieceDetected());
        SmartDashboard.putNumber("Lift Encoder Val: ", Robot.lift.getHeight());
        SmartDashboard.putBoolean("Lift Is At Bottom?: ", Robot.lift.isAtBottom());
        SmartDashboard.putBoolean("Is Limit Switch Overridden: ", Robot.lift.isLimitSensorOverrided);

        // liftSpeedGoingDown = SmartDashboard.getNumber("Lift Auto Complete Speed Going
        // Down", 0.5);
        SmartDashboard.putString("Match Time", returnTime());
        SmartDashboard.putString("Driver Gamepad Type", oi.driverGamepad.getGamepadType());
        SmartDashboard.putString("Operator Gamepad Type", oi.operatorGamepad.getGamepadType());
        SmartDashboard.putString("Driver Triggers: ", oi.driverGamepad.getRawLeftTriggerAxis() + ", " + oi.driverGamepad.getRawRightTriggerAxis());
        SmartDashboard.putString("Operator Triggers: ", oi.operatorGamepad.getRawLeftTriggerAxis() + ", " + oi.operatorGamepad.getRawRightTriggerAxis());
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
        Scheduler.getInstance().run();
        // Reset the gamepad types
        oi.driverGamepad.resetGamepadType();
        oi.operatorGamepad.resetGamepadType();
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
        // autonomousCommand = new LiftMoveToHeightCommand(RobotMap.LEVEL_1_HEIGHT);
        oi.driverGamepad.resetGamepadType();
        oi.operatorGamepad.resetGamepadType();
        lift.tiltForward();
        lift.setHeight(-1 * RobotMap.START_HEIGHT);
        autonStartTime = Timer.getFPGATimestamp();
        Limelight.setLEDMode(Limelight.LEDMode.FORCE_OFF);
        /*
         * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
         * switch(autoSelected) { case "My Auto": autonomousCommand = new
         * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
         * ExampleCommand(); break; }
         */

        // schedule the autonomous command (example)
        // if (autonomousCommand != null) {
        // autonomousCommand.start();
        // }
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {

        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        oi.driverGamepad.resetGamepadType();
        oi.operatorGamepad.resetGamepadType();
        Robot.floop.open();
        SmartDashboard.putBoolean("Enable compressor", false);
        // if (autonomousCommand != null) {
        // autonomousCommand.cancel();
        // }
    }

    /**
     * This function is called periodically during operator control.
     */

    @Override
    public void teleopPeriodic() {
        controlCompressor();
        double startTime = System.currentTimeMillis();
        // if(!isGamePieceDetected()) {
        // relayController.setLEDForward();
        // } else {
        // relayController.setLEDNeutral();
        // }
        Scheduler.getInstance().run();
        // SmartDashboard.putBoolean("Is Lift Optical Sensor Overrided: ",
        // Robot.lift.isOpticalSensorOverrided);
        // SmartDashboard.putNumber("Tom's Metric for Tail: ",
        // Robot.tail.getTomsMetric());
        // if (isGamePieceDetected()) {
        //     // Once a game piece is detected, it blinks two times and stops.
        //     blinkLED();
        // } else {
        //     // Stops the LEDs as long as it doesn't detect a game piece.
        //     relayController.setLEDNeutral();
        // }
        SmartDashboard.putNumber("Time Diff", System.currentTimeMillis() - startTime);
        SmartDashboard.putBoolean("Is Game piece detected", isGamePieceDetected());
        
        // Motor Stall Stuff
        double change_from_start = System.currentTimeMillis() - start_time;
        if (change_from_start > 200) {
            start_time = System.currentTimeMillis();
            // current_encoder_value needs to be replaced with distance instead
            double current_encoder_value = Math.abs(Robot.rollers.getEncoderVal());
            double change_distance = Math.abs(current_encoder_value - start_encoder_value);

            SmartDashboard.putNumber("Change In Distance Encoder", change_distance);
            if (change_distance <= encoder_approach_stall_threshold && Robot.oi.operatorGamepad.getRawRightTrigger()) {
                SmartDashboard.putBoolean("Motor Stall Status:", true);
                rollersStalling = true;
            } else {
                SmartDashboard.putBoolean("Motor Stall Status:", false);
                rollersStalling = false;
            }
            start_encoder_value = current_encoder_value;
        }
        SmartDashboard.putNumber("Motor Speed:", Robot.rollers.getSpeed());
    }

    /**
     * This function is called periodically during test mode.
     */

    @Override
    public void testPeriodic() {
    }

    public void controlCompressor() {
        if (SmartDashboard.getBoolean("Enable compressor", false)) {
            compressor.start();
        } else {
            compressor.stop();
        }
    }

    public static boolean isGamePieceDetected() {
        return !IRsensor.get(); 
    }

    public static void toggleScore() {
        scoreCargo = !scoreCargo;
    }

    public static boolean isRollersStalling() {
        return rollersStalling;
    }

    private String returnTime() {
        boolean isAuton = DriverStation.getInstance().isAutonomous();
        int dTime = (int) DriverStation.getInstance().getMatchTime();
        if (dTime == -1) {
            dTime = 0;
        }
        String minutes = Integer.toString(dTime / 60);
        String seconds = Integer.toString(dTime % 60);
        if (dTime % 60 < 10) {
            seconds = "0" + seconds;
        }
        if (isAuton) {
            return "Sandstorm: " + minutes + ":" + seconds;
        } else {
            return "Teleop: " + minutes + ":" + seconds;
        }
    }

    private void blinkLED() {
        double startTime = Timer.getFPGATimestamp();
        if (Timer.getFPGATimestamp() - startTime > 4) {
            relayController.setLEDForward();
        } else if ((int) (Timer.getFPGATimestamp() - startTime) % 2 == 0) {
            relayController.setLEDForward();
        } else {
            relayController.setLEDNeutral();
        }
    }
}