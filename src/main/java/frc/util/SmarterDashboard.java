package frc.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableEntry;


// 694 original
public class SmarterDashboard {
    private static final double DEFAULT_DEFAULT = 0;
    private static final boolean DEFAULT_CHECK_SMARTDASH = true;

    public static double getNumber(String name) {
        return getNumber(name, DEFAULT_DEFAULT);
    }

    public static double getNumber(String name, double def) {
        return getNumber(name, def, DEFAULT_CHECK_SMARTDASH);
    }

    public static double getNumber(String name, double def, boolean checkSmartDashboard) {
        if(checkSmartDashboard) {
            NetworkTableEntry entry = SmartDashboard.getEntry(name);

            if(entry.exists()) {
                return entry.getDouble(def);
            } else {
                SmartDashboard.putNumber(name, def);
                return def;
            }

        } else {
            return def;
        }
    }
}