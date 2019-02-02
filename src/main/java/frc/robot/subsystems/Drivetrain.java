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

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.DrivetrainDriveCommand;
import frc.util.NEOEncoder;

public final class Drivetrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private CANSparkMax leftTopMotor, leftMiddleMotor, leftBottomMotor, rightTopMotor, rightMiddleMotor,
                        rightBottomMotor;

    private SpeedControllerGroup leftSpeedGroup, rightSpeedGroup;

    private DifferentialDrive differentialDrive;

    private NEOEncoder leftEncoder, rightEncoder;

    private AHRS navX;
    
    private Solenoid gearShift;

    public Drivetrain() {
        // Left Side Motors
        leftTopMotor = new CANSparkMax(RobotMap.LEFT_TOP_MOTOR_PORT, MotorType.kBrushless);
        leftMiddleMotor = new CANSparkMax(RobotMap.LEFT_MIDDLE_MOTOR_PORT, MotorType.kBrushless);
        leftBottomMotor = new CANSparkMax(RobotMap.LEFT_BOTTOM_MOTOR_PORT, MotorType.kBrushless);

        // Right Side Motors
        rightTopMotor = new CANSparkMax(RobotMap.RIGHT_TOP_MOTOR_PORT, MotorType.kBrushless);
        rightMiddleMotor = new CANSparkMax(RobotMap.RIGHT_MIDDLE_MOTOR_PORT, MotorType.kBrushless);
        rightBottomMotor = new CANSparkMax(RobotMap.RIGHT_BOTTOM_MOTOR_PORT, MotorType.kBrushless);

        // Encoders
        leftEncoder = new NEOEncoder(leftMiddleMotor.getEncoder());
        rightEncoder = new NEOEncoder(rightMiddleMotor.getEncoder());

        //Gear Shift
        gearShift = new Solenoid(RobotMap.GEAR_SHIFT_CHANNEL);

        // navx
        navX = new AHRS(SPI.Port.kMXP);

        leftTopMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        leftMiddleMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        leftBottomMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightTopMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        rightMiddleMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightBottomMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        //TODO: Ask engineering for motor polarity
        rightTopMotor.setInverted(true);
        rightMiddleMotor.setInverted(true);
        rightBottomMotor.setInverted(true);
        leftTopMotor.setInverted(true);
        leftMiddleMotor.setInverted(true);
        leftBottomMotor.setInverted(true);
        
        // Speed Groups
        leftSpeedGroup = new SpeedControllerGroup(leftTopMotor, leftMiddleMotor, leftBottomMotor);
        rightSpeedGroup = new SpeedControllerGroup(rightTopMotor, rightMiddleMotor, rightBottomMotor);

        // Drive
        differentialDrive = new DifferentialDrive(leftSpeedGroup, rightSpeedGroup);

    }

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

    public void stop() {
        differentialDrive.tankDrive(0, 0);
    }

    private double getLeftEncoderTicks() {
        return leftEncoder.getPosition();
    }

    private double getRightEncoderTicks() {
        return rightEncoder.getPosition();
    }

    public double getLeftDistance() {
        return getLeftEncoderTicks()* RobotMap.DRIVETRAIN_ENCODER_RAW_MULTIPLIER;
    }

    public double getRightDistance() {
        return getRightEncoderTicks() * RobotMap.DRIVETRAIN_ENCODER_RAW_MULTIPLIER;
    }

    public double getDistance() {
        return Math.max(getLeftDistance(), getRightDistance());
    }

    public void resetEncoders(){
        rightEncoder.resetEncoder();
        leftEncoder.resetEncoder();
    }

    public double getGyroAngle() {
        return navX.getAngle();
    }

    public boolean isMoving() {
        return (Math.abs(rightSpeedGroup.get()) > 0 || Math.abs(leftSpeedGroup.get()) > 0);
    }

    public void highGearShift() {
        //TODO: test + find the correct boolean value
        gearShift.set(false);
    }

    public void lowGearShift() {
        gearShift.set(true);
    }

    public void toggleGearShift(){
        gearShift.set(!(gearShift.get()));
    }
}