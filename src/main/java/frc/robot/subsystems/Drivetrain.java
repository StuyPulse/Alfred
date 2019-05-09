/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.DrivetrainDriveCommand;
import frc.util.NEOEncoder;
import frc.util.FRCLogger;

public final class Drivetrain extends Subsystem implements FRCLogger.Loggable{
    
    private CANSparkMax highLeftTopMotor,
                        highLeftMiddleMotor,
                        lowLeftTopMotor,
                        lowLeftMiddleMotor,
                        leftBottomMotor,
                        highRightTopMotor,
                        highRightMiddleMotor,
                        lowRightTopMotor,
                        lowRightMiddleMotor,
                        rightBottomMotor;


    private SpeedControllerGroup highLeftSpeedGroup, highRightSpeedGroup, lowLeftSpeedGroup, lowRightSpeedGroup;

    private DifferentialDrive lowDifferentialDrive, highDifferentialDrive, currentDifferentialDrive;

    private NEOEncoder leftNEOEncoder, rightNEOEncoder;
    private Encoder leftGreyhill, rightGreyhill;

    private AHRS navX;
    
    private Solenoid gearShift;
    
    public Drivetrain() {
        // Left Side Motors
        highLeftTopMotor = new CANSparkMax(RobotMap.LEFT_TOP_MOTOR_PORT, MotorType.kBrushless);
        highLeftMiddleMotor = new CANSparkMax(RobotMap.LEFT_MIDDLE_MOTOR_PORT, MotorType.kBrushless);
        lowLeftTopMotor = new CANSparkMax(RobotMap.LEFT_TOP_MOTOR_PORT, MotorType.kBrushless);
        lowLeftMiddleMotor = new CANSparkMax(RobotMap.LEFT_MIDDLE_MOTOR_PORT, MotorType.kBrushless);
        leftBottomMotor = new CANSparkMax(RobotMap.LEFT_BOTTOM_MOTOR_PORT, MotorType.kBrushless);

        // Right Side Motors
        highRightTopMotor = new CANSparkMax(RobotMap.RIGHT_TOP_MOTOR_PORT, MotorType.kBrushless);
        highRightMiddleMotor = new CANSparkMax(RobotMap.RIGHT_MIDDLE_MOTOR_PORT, MotorType.kBrushless);
        lowRightTopMotor = new CANSparkMax(RobotMap.RIGHT_TOP_MOTOR_PORT, MotorType.kBrushless);
        lowRightMiddleMotor = new CANSparkMax(RobotMap.RIGHT_MIDDLE_MOTOR_PORT, MotorType.kBrushless);
        rightBottomMotor = new CANSparkMax(RobotMap.RIGHT_BOTTOM_MOTOR_PORT, MotorType.kBrushless);

        // NEO/SPARK MAX Encoders
        leftNEOEncoder = new NEOEncoder(highLeftMiddleMotor.getEncoder());
        rightNEOEncoder = new NEOEncoder(highRightMiddleMotor.getEncoder());

        // Greyhill Encoders
        leftGreyhill = new Encoder(RobotMap.DRIVETRAIN_LEFT_ENCODER_CHANNEL_A, RobotMap.DRIVETRAIN_LEFT_ENCODER_CHANNEL_B);
        rightGreyhill = new Encoder(RobotMap.DRIVETRAIN_RIGHT_ENCODER_CHANNEL_A, RobotMap.DRIVETRAIN_RIGHT_ENCODER_CHANNEL_B);
    
        leftGreyhill.setDistancePerPulse(RobotMap.DRIVETRAIN_GREYHILL_INCHES_PER_PULSE);
        rightGreyhill.setDistancePerPulse(-1.0 * RobotMap.DRIVETRAIN_GREYHILL_INCHES_PER_PULSE);

        highLeftTopMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        highLeftMiddleMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        lowLeftTopMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        lowLeftMiddleMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        leftBottomMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);

        highRightTopMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        highRightMiddleMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        lowRightTopMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        lowRightMiddleMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightBottomMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);

        highRightTopMotor.setInverted(true);
        highRightMiddleMotor.setInverted(true);
        lowRightTopMotor.setInverted(true);
        lowRightMiddleMotor.setInverted(true);
        rightBottomMotor.setInverted(true);

        highLeftTopMotor.setInverted(true);
        highLeftMiddleMotor.setInverted(true);
        lowLeftTopMotor.setInverted(true);
        lowLeftMiddleMotor.setInverted(true);
        leftBottomMotor.setInverted(true);

        highRightTopMotor.setSmartCurrentLimit(RobotMap.DRIVETRAIN_CURRENT_LIMIT);
        highRightMiddleMotor.setSmartCurrentLimit(RobotMap.DRIVETRAIN_CURRENT_LIMIT);
        lowRightTopMotor.setSmartCurrentLimit(RobotMap.DRIVETRAIN_CURRENT_LIMIT);
        lowRightMiddleMotor.setSmartCurrentLimit(RobotMap.DRIVETRAIN_CURRENT_LIMIT);
        rightBottomMotor.setSmartCurrentLimit(RobotMap.DRIVETRAIN_CURRENT_LIMIT);
        
        highLeftTopMotor.setSmartCurrentLimit(RobotMap.DRIVETRAIN_CURRENT_LIMIT);
        highLeftMiddleMotor.setSmartCurrentLimit(RobotMap.DRIVETRAIN_CURRENT_LIMIT);
        lowLeftTopMotor.setSmartCurrentLimit(RobotMap.DRIVETRAIN_CURRENT_LIMIT);
        lowLeftMiddleMotor.setSmartCurrentLimit(RobotMap.DRIVETRAIN_CURRENT_LIMIT);
        leftBottomMotor.setSmartCurrentLimit(RobotMap.DRIVETRAIN_CURRENT_LIMIT);

        // Speed Groups
        lowLeftSpeedGroup = new SpeedControllerGroup(lowLeftTopMotor, lowLeftMiddleMotor, leftBottomMotor);
        lowRightSpeedGroup = new SpeedControllerGroup(lowRightTopMotor, lowRightMiddleMotor, rightBottomMotor);
        highLeftSpeedGroup = new SpeedControllerGroup(highLeftTopMotor, highLeftMiddleMotor);
        highRightSpeedGroup = new SpeedControllerGroup(highRightTopMotor, highRightMiddleMotor);
      
        //Gear Shift
        gearShift = new Solenoid(RobotMap.GEAR_SHIFT_CHANNEL);
        // navx
        navX = new AHRS(SPI.Port.kMXP);
        // Drive
        highDifferentialDrive = new DifferentialDrive(highLeftSpeedGroup, highRightSpeedGroup);
        lowDifferentialDrive = new DifferentialDrive(lowLeftSpeedGroup, lowRightSpeedGroup);

        highDifferentialDrive.setSafetyEnabled(false);
        lowDifferentialDrive.setSafetyEnabled(false);
        //Default currentDrive set to low.
        currentDifferentialDrive = lowDifferentialDrive;

        enableRamping();
      }

    @Override
    public void initDefaultCommand() {
         setDefaultCommand(new DrivetrainDriveCommand());
    }

    public void curvatureDrive(double speed, double angle) {
        currentDifferentialDrive.curvatureDrive(speed, angle, false);
    }

    public void curvatureDrive(double speed, double angle, boolean turn) {
        currentDifferentialDrive.curvatureDrive(speed, angle, turn);
    }

    public void stop() {
        currentDifferentialDrive.tankDrive(0,0);
    }

    private double getLeftNEOEncoderTicks() {
        return leftNEOEncoder.getPosition();
    }

    private double getRightNEOEncoderTicks() {
        return rightNEOEncoder.getPosition();
    }

    private double getLeftNEODistance() {
        return getLeftNEOEncoderTicks() * RobotMap.NEO_ENCODER_RAW_MULTIPLIER;
    }

    private double getRightNEODistance() {
        return getRightNEOEncoderTicks() * RobotMap.NEO_ENCODER_RAW_MULTIPLIER;
    }

    public double getNEODistance() {
        return Math.max(Math.abs(getLeftNEODistance()), Math.abs(getRightNEODistance()));
    }

    public void resetNEOEncoders() {
        leftNEOEncoder.resetEncoder();
        rightNEOEncoder.resetEncoder();
    } 
    public double getLeftGreyhillTicks() {
        return leftGreyhill.get();
    }

    public double getRightGreyhillTicks() {
        return rightGreyhill.get();
    }

    public double getLeftGreyhillDistance() {
        return leftGreyhill.getDistance();
    }

    public double getRightGreyhillDistance() {
        return rightGreyhill.getDistance();
    }

    public double getGreyhillDistance() {
        return Math.max(getLeftGreyhillDistance(), getRightGreyhillDistance());
    }

    public void resetGreyhills() {
        leftGreyhill.reset();
        rightGreyhill.reset();
    }

    public double getGyroAngle() {
        return navX.getAngle();
    }

    public boolean isMoving() {
        //not 0,but 0.07 because joysticks are typically not at 0 when start
        return Math.abs(lowRightSpeedGroup.get()) > 0.07 || Math.abs(lowLeftSpeedGroup.get()) > 0.07 || Math.abs(highRightSpeedGroup.get()) > 0.07 || Math.abs(highLeftSpeedGroup.get()) > 0.07;
    }

    public void highGearShift() {
        gearShift.set(false);
        System.out.println("Switched to HIGH GEAR");
        stop();
        currentDifferentialDrive = highDifferentialDrive;
    }

    public void lowGearShift() {
        gearShift.set(true);
        System.out.println("Switched to LOW GEAR");
        stop();
        currentDifferentialDrive = lowDifferentialDrive;
    }

    public void toggleGearShift(){
        gearShift.set(!(gearShift.get()));
    }

    public void enableRamping() {
        highRightTopMotor.setOpenLoopRampRate(RobotMap.DRIVETRAIN_RAMP_RATE);
        highRightMiddleMotor.setOpenLoopRampRate(RobotMap.DRIVETRAIN_RAMP_RATE);
        lowRightTopMotor.setOpenLoopRampRate(RobotMap.DRIVETRAIN_RAMP_RATE);
        lowRightMiddleMotor.setOpenLoopRampRate(RobotMap.DRIVETRAIN_RAMP_RATE);
        rightBottomMotor.setOpenLoopRampRate(RobotMap.DRIVETRAIN_RAMP_RATE);
        
        highLeftTopMotor.setOpenLoopRampRate(RobotMap.DRIVETRAIN_RAMP_RATE);
        highLeftMiddleMotor.setOpenLoopRampRate(RobotMap.DRIVETRAIN_RAMP_RATE);
        lowLeftTopMotor.setOpenLoopRampRate(RobotMap.DRIVETRAIN_RAMP_RATE);
        lowLeftMiddleMotor.setOpenLoopRampRate(RobotMap.DRIVETRAIN_RAMP_RATE);
        leftBottomMotor.setOpenLoopRampRate(RobotMap.DRIVETRAIN_RAMP_RATE);
    }

    public void disableRamping() {
        highRightTopMotor.setOpenLoopRampRate(0.0);
        highRightMiddleMotor.setOpenLoopRampRate(0.0);
        lowRightTopMotor.setOpenLoopRampRate(0.0);
        lowRightMiddleMotor.setOpenLoopRampRate(0.0);
        rightBottomMotor.setOpenLoopRampRate(0.0);
        
        highLeftTopMotor.setOpenLoopRampRate(0.0);
        highLeftMiddleMotor.setOpenLoopRampRate(0.0);
        lowLeftTopMotor.setOpenLoopRampRate(0.0);
        lowLeftMiddleMotor.setOpenLoopRampRate(0.0);
        leftBottomMotor.setOpenLoopRampRate(0.0);
    }

    // Example get log implementation
    public String getLog() {
        return getName() + ", " + 
            getCurrentCommandName() + ", " +
            getGreyhillDistance();
    }
}
