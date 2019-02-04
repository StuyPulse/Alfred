/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.DrivetrainDriveCommand;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private CANSparkMax leftTopMotor,
                        leftMiddleMotor,
                        leftBottomMotor,
                        rightTopMotor,
                        rightMiddleMotor,
                        rightBottomMotor;

    private SpeedControllerGroup leftSpeedGroup, rightSpeedGroup;

    private DifferentialDrive differentialDrive;

    private CANEncoder leftEncoder, rightEncoder;

    public static AHRS navX;

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
        leftEncoder = leftMiddleMotor.getEncoder();
        rightEncoder = rightMiddleMotor.getEncoder();

        // Speed Groups
        leftSpeedGroup = new SpeedControllerGroup(leftTopMotor, leftMiddleMotor, leftBottomMotor);
        rightSpeedGroup = new SpeedControllerGroup(rightTopMotor, rightMiddleMotor, rightBottomMotor);

        // Followers
        leftTopMotor.follow(leftMiddleMotor);
        leftBottomMotor.follow(leftMiddleMotor);
        rightTopMotor.follow(rightMiddleMotor);
        rightBottomMotor.follow(rightMiddleMotor);

        //Gear Shift
        gearShift = new Solenoid(RobotMap.GEAR_SHIFT_CHANNEL);
        // navx
        navX = new AHRS(SPI.Port.kMXP);
        // Drive
        differentialDrive = new DifferentialDrive(leftSpeedGroup, rightSpeedGroup);

        leftTopMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        leftMiddleMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        leftBottomMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightTopMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightMiddleMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightBottomMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        rightTopMotor.setInverted(true);
        rightMiddleMotor.setInverted(true);
        rightBottomMotor.setInverted(true);
        leftTopMotor.setInverted(true);
        leftMiddleMotor.setInverted(true);
        leftBottomMotor.setInverted(true);

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

    public void tankDrive(double left, double right) {
        differentialDrive.tankDrive(left, right);
    }

    public void stop() {
        differentialDrive.tankDrive(0, 0);
    }

    public double getLeftEncoderTicks() {
        return leftEncoder.getPosition();
    }

    public double getRightEncoderTicks() {
        return rightEncoder.getPosition();
    }

    public double getLeftDistance() {
        return leftEncoder.getPosition() * RobotMap.WHEEL_INCHES_PER_REVOLUTION;
    }

    public double getRightDistance() {
        return rightEncoder.getPosition() * RobotMap.WHEEL_INCHES_PER_REVOLUTION;
    }

    public double getDistance() {
        return Math.max(getLeftDistance(), getRightDistance());
    }

    public double getGyroAngle() {
        return navX.getAngle();
    }

    public boolean isMoving() {
        return (rightSpeedGroup.get() > 0 || leftSpeedGroup.get() > 0);
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

    public void enableCurrentLimit() {
        leftMiddleMotor.setSmartCurrentLimit(RobotMap.DRIVETRAIN_CURRENT_LIMIT);
        rightMiddleMotor.setSmartCurrentLimit(RobotMap.DRIVETRAIN_CURRENT_LIMIT);
    }

    public void disableCurrentLimit() {
       leftMiddleMotor.setSmartCurrentLimit(RobotMap.DRIVETRAIN_FREE_LIMIT);
       rightMiddleMotor.setSmartCurrentLimit(RobotMap.DRIVETRAIN_FREE_LIMIT);
    }

    /*public void setAutonCurrentLimit() {
        leftMiddleMotor.setSmartCurrentLimit(RobotMap.DRIVETRAIN_AUTON_CURRENT_LIMIT);
        rightMiddleMotor.setSmartCurrentLimit(RobotMap.DRIVETRAIN_AUTON_CURRENT_LIMIT);
    }*/

    public double getLeftCurrent() {
        return leftBottomMotor.getOutputCurrent() + leftMiddleMotor.getOutputCurrent() + leftTopMotor.getOutputCurrent();
    }

    public double getRightCurrent() {
        return rightBottomMotor.getOutputCurrent() + rightMiddleMotor.getOutputCurrent() + rightTopMotor.getOutputCurrent();
    }

    public double getTotalCurrent() {
        return getLeftCurrent() + getRightCurrent();
    }
}