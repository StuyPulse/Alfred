package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
	NetworkTable fieldMap;
	NetworkTableEntry encoderDist;
	NetworkTableEntry gyroAngle;
    NetworkTableEntry origGyroAngle;
    NetworkTableEntry robotLength;
    NetworkTableEntry robotWidth;

	@Override
	public void robotInit() {
	    fieldMap = NetworkTableInstance.getDefault().getTable("FieldMap");
	    encoderDist = fieldMap.getEntry("EncoderValues");
	    gyroAngle = fieldMap.getEntry("GyroAngle");
	    origGyroAngle = fieldMap.getEntry("OriginalGyroAngle"); //Gyro angle at the start of match
        robotLength = fieldMap.getEntry("RobotLength");
        robotWidth = fieldMap.getEntry("RobotWidth");
		origGyroAngle.setDefaultDouble(0);
		encoderDist.setDefaultDouble(0);
	    gyroAngle.setDefaultDouble(0);
	    robotLength.setDefaultDouble(35.0);
	    robotWidth.setDefaultDouble(24.0);
	}
}

//> Executing teask: gradlew deploy -PteamNumber=694 --offline -Dorg.gradle.java.home="C:\Users\Public\frc2019\jdk" <
