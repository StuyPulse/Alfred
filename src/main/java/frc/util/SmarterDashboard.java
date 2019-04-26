package frc.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableEntry;


// 694 original
public class SmarterDashboard {
    private static final boolean DEFAULT_OVERRIDE = true;

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
}