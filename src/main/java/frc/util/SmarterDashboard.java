package frc.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableEntry;

// 694 original
public class SmarterDashboard {
    // Only enable in dire situations where networking is too slow
    private static final boolean DEFAULT_OVERRIDE = false;

    public static double getNumber(String name, double def) {
        if(DEFAULT_OVERRIDE) {
            return def;
        } else {
            NetworkTableEntry entry = SmartDashboard.getEntry(name);

            if(entry.exists()) {
                return entry.getDouble(def);
            } else {
                SmartDashboard.putNumber(name, def);
                return def;
            }
        }
    }

    public static boolean getBoolean(String name, boolean def) {
        if(DEFAULT_OVERRIDE) {
            return def;
        } else {
            NetworkTableEntry entry = SmartDashboard.getEntry(name);

            if(entry.exists()) {
                return entry.getBoolean(def);
            } else {
                SmartDashboard.putBoolean(name, def);
                return def;
            }
        }
    }
}