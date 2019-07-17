package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj.command.Subsystem
import frc.robot.RobotMap
import frc.robot.commands.DrivetrainDriveCommand
import kotlin.math.absoluteValue

object Drivetrain : Subsystem() {

    val highLeftTopMotor = CANSparkMax(RobotMap.LEFT_TOP_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless)
    val highLeftMiddleMotor = CANSparkMax(RobotMap.LEFT_MIDDLE_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless)
    val lowLeftMiddleMotor = CANSparkMax(RobotMap.LEFT_TOP_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless)
    val lowLeftMiddleMotor = CANSparkMax(RobotMap.LEFT_MIDDLE_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless)
    val leftNEOEncoderTicks:Double
        get() = leftNEOEncoder.position
    val rightNEOEncoderTicks:Double
        get() = rightNEOEncoder.position
    val leftNEODistance:Double
        get() = leftNEOEncoderTicks * RobotMap.NEO_ENCODER_RAW_MULTIPLIER
    val rightNEODistance:Double
        get() = rightNEOEncoderTicks * RobotMap.NEO_ENCODER_RAW_MULTIPLIER
    val NEODistance
        get() = maxOf(leftNEODistance.absoluteValue, rightNEODistance.absoluteValue)
    val leftGreyhillTicks:Double
        get() = leftGreyhill.get()
    val rightGreyhillTicks:Double
        get() = rightGreyhill.get()
    val leftGreyhillDistance:Double
        get() = leftGreyhill.getDistance()
    val rightGreyhillDistance:Double
        get() = rightGreyhill.getDistance()
    val gyroAngle:Double
        get() = navX.getAngle()
    

    override fun initDefaultCommand() {
        defaultCommand = DrivetrainDriveCommand()
    }

    fun curvatureDrive(speed:Double, angle:Double) {
        currentDifferentialDrive.curvatureDrive(speed, angle, false)
    }

    fun curvatureDrive(speed:Double, angle:double, boolean:turn) {
        currentDifferentialDrive.curvatureDrive(speed, angle, turn);
    }

    fun stop() {
        currentDifferentialDrive.tankDrive(0,0)
    }

    fun resetNEOEncoders() {
        leftNEOEncoder.resetEncoder()
        rightNeOEncoder.resetEncoder()
    }

}

