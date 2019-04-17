package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Robot extends TimedRobot {
	NetworkTable fieldMap;
	NetworkTableEntry encoderDist;
	NetworkTableEntry gyroAngle;
	NetworkTableEntry origGyroAngle;

	@Override
	protected void robotInit() {
	    fieldMap = NetworkTableInstace.getDefault().getTable("FieldMap");
	    encoderDist = fieldMap.getEntry("EncoderDistance");
	    gyroAngle = fieldMap.getEntry("GyroAngle");
	    origGyroAngle = fieldMap.getEntry("OriginalGyroAngle"); //Gyro angle at the start of match
	    robotLength = fieldMap.getEntry("RobotLength");
	    origGyroAngle.setDefaultDouble(drivetrain.getGyroAngle());
	}

	@Override
	protected void robotPeriodic() {
	    encoderDist.setDouble(drivetrain.getGreyhillDistance());
	    gyroAngle.setDouble(drivetrain.getGyroAngle());
	    robotLength.setDefaultDouble(35.0);
	    robotWidth.setDefaultDouble(24.0);
	}
}

//> Executing teask: gradlew deploy -PteamNumber=694 --offline -Dorg.gradle.java.home="C:\Users\Public\frc2019\jdk" <
