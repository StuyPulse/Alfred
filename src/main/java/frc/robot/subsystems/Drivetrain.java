/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

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
 
  private SpeedControllerGroup leftSpeedGroup,
                               rightSpeedGroup;
                               
  private DifferentialDrive differentialDrive;

  private CANEncoder leftEncoder,
                     rightEncoder;

  public static AHRS navX;

  public Drivetrain(){
    //Left Side Motors
    leftTopMotor = new CANSparkMax(-1,MotorType.kBrushless);
    leftMiddleMotor = new CANSparkMax(-1,MotorType.kBrushless);
    leftBottomMotor = new CANSparkMax(-1,MotorType.kBrushless);

    //Right Side Motors
    rightTopMotor = new CANSparkMax(-1,MotorType.kBrushless);
    rightMiddleMotor = new CANSparkMax(-1,MotorType.kBrushless);
    rightBottomMotor = new CANSparkMax(-1,MotorType.kBrushless);

    //Encoders
    leftEncoder = leftMiddleMotor.getEncoder();
    rightEncoder = rightMiddleMotor.getEncoder();

    //Speed Groups
    leftSpeedGroup = new SpeedControllerGroup(leftTopMotor, leftMiddleMotor, leftBottomMotor);
    rightSpeedGroup = new SpeedControllerGroup(rightTopMotor, rightMiddleMotor, rightBottomMotor);

    //navx
    navX = new AHRS(SPI.Port.kMXP);
    //Drive
    differentialDrive = new DifferentialDrive(leftSpeedGroup,rightSpeedGroup);

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
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  public void curvatureDrive(double speed, double angle){
    differentialDrive.curvatureDrive(speed, angle,false);
  }
  public void stop(){
    differentialDrive.tankDrive(0, 0);
  }
  public double getLeftEncoderTicks(){
    return leftEncoder.getPosition();
  }
  public double getRightEncoderTicks(){
    return rightEncoder.getPosition();
  }
  public double getLeftDistance(){
    return leftEncoder.getPosition()*6*Math.PI;
  }
  public double getRightDistance(){
    return rightEncoder.getPosition()*6*Math.PI;
  }
  public double getDistance(){
    return Math.max(getLeftDistance(),getRightDistance());
  }
  public double getGyroAngle() {
    return navX.getAngle();
  }
}
