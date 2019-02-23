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

public final class Drivetrain extends Subsystem {
    
    private CANSparkMax leftTopMotor, 
                        leftMiddleMotor, 
                        leftBottomMotor, 
                        rightTopMotor,
                        rightMiddleMotor,
                        rightBottomMotor;

    private SpeedControllerGroup leftSpeedGroup, rightSpeedGroup;

    private DifferentialDrive differentialDrive;

    private NEOEncoder leftNEOEncoder, rightNEOEncoder;
    private Encoder leftGreyhill, rightGreyhill;

    private AHRS navX;
    
    private Solenoid gearShift;

    public double absoluteGyroError;

    public Drivetrain() {
        // Left Side Motors
        leftTopMotor = new CANSparkMax(RobotMap.LEFT_TOP_MOTOR_PORT, MotorType.kBrushless);
        leftMiddleMotor = new CANSparkMax(RobotMap.LEFT_MIDDLE_MOTOR_PORT, MotorType.kBrushless);
        leftBottomMotor = new CANSparkMax(RobotMap.LEFT_BOTTOM_MOTOR_PORT, MotorType.kBrushless);

        // Right Side Motors
        rightTopMotor = new CANSparkMax(RobotMap.RIGHT_TOP_MOTOR_PORT, MotorType.kBrushless);
        rightMiddleMotor = new CANSparkMax(RobotMap.RIGHT_MIDDLE_MOTOR_PORT, MotorType.kBrushless);
        rightBottomMotor = new CANSparkMax(RobotMap.RIGHT_BOTTOM_MOTOR_PORT, MotorType.kBrushless);

        // NEO/SPARK MAX Encoders
        leftNEOEncoder = new NEOEncoder(leftMiddleMotor.getEncoder());
        rightNEOEncoder = new NEOEncoder(rightMiddleMotor.getEncoder());

        // Greyhill Encoders
        leftGreyhill = new Encoder(RobotMap.DRIVETRAIN_LEFT_ENCODER_CHANNEL_A, RobotMap.DRIVETRAIN_LEFT_ENCODER_CHANNEL_B);
        rightGreyhill = new Encoder(RobotMap.DRIVETRAIN_RIGHT_ENCODER_CHANNEL_A, RobotMap.DRIVETRAIN_RIGHT_ENCODER_CHANNEL_B);
    
        leftGreyhill.setDistancePerPulse(RobotMap.DRIVETRAIN_GREYHILL_INCHES_PER_PULSE);
        rightGreyhill.setDistancePerPulse(-1.0 * RobotMap.DRIVETRAIN_GREYHILL_INCHES_PER_PULSE);

        leftTopMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        leftMiddleMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        leftBottomMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightTopMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        rightMiddleMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightBottomMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        //TODO: Ask engineering about motor polarity
        rightTopMotor.setInverted(true);
        rightMiddleMotor.setInverted(true);
        rightBottomMotor.setInverted(true);
        leftTopMotor.setInverted(true);
        leftMiddleMotor.setInverted(true);
        leftBottomMotor.setInverted(true);
        // Speed Groups
        leftSpeedGroup = new SpeedControllerGroup(leftTopMotor, leftMiddleMotor, leftBottomMotor);
        rightSpeedGroup = new SpeedControllerGroup(rightTopMotor, rightMiddleMotor, rightBottomMotor);

        //Gear Shift
        gearShift = new Solenoid(RobotMap.GEAR_SHIFT_CHANNEL);
        // navx
        navX = new AHRS(SPI.Port.kMXP);
        // Drive
        differentialDrive = new DifferentialDrive(leftSpeedGroup, rightSpeedGroup);  }

    @Override
    public void initDefaultCommand() {
         setDefaultCommand(new DrivetrainDriveCommand());
    }

    public void curvatureDrive(double speed, double angle) {
        differentialDrive.curvatureDrive(speed, angle, false);
    }

    public void curvatureDrive(double speed, double angle, boolean turn) {
        differentialDrive.curvatureDrive(speed, angle, turn);
    }

    public void tankDrive(double left, double right) {
        differentialDrive.tankDrive(left, right);
    }

    public void stop() {
        differentialDrive.tankDrive(0, 0);
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
        return Math.abs(rightSpeedGroup.get()) > 0.07 || Math.abs(leftSpeedGroup.get()) > 0.07;
    }

    public void highGearShift() {
        gearShift.set(false);
    }

    public void lowGearShift() {
        gearShift.set(true);
    }

    public void toggleGearShift(){
        gearShift.set(!(gearShift.get()));
    }

    public void setRamp(double rampSeconds) {
        leftTopMotor.setRampRate(rampSeconds);
        rightTopMotor.setRampRate(rampSeconds);
        leftMiddleMotor.setRampRate(rampSeconds);
        rightMiddleMotor.setRampRate(rampSeconds);
        leftBottomMotor.setRampRate(rampSeconds);
        rightBottomMotor.setRampRate(rampSeconds);
    }

    public void resetGyro() {
        absoluteGyroError += getGyroAngle();
        navX.reset();
    }

    public void resetGyroError() {
        absoluteGyroError = 0;
    }

    public double getAbsoluteGyroAngle() {
        return absoluteGyroError + getGyroAngle();
    }
}