
/* http://first.wpi.edu/FRC/roborio/release/docs/java/ */

package frc.util;

import java.util.Set;
import java.lang.Number;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.PersistentException;

// @deprecated
public class NetworkTableClient {
    private NetworkTableInstance inst; // Instance contains IP/Related information
    private NetworkTable table; // Current Data Table
    private String tableName; // Name of Data Table

    // Custom Instance and Custom DataTable
    NetworkTableClient(String DataTable, NetworkTableInstance Instance) {
        inst = Instance;
        openTable(DataTable);
    }

    // Default Instance and Custom DataTable
    NetworkTableClient(String DataTable) {
        this(DataTable, NetworkTableInstance.getDefault());
    }

    public void openTable(String DataTableName) {
        tableName = DataTableName;
        table = inst.getTable(DataTableName);
    }

    public String getTableName() {
        return tableName;
    }

    // Load entries from a file into the network table
    public String[] loadEntries(String FileName) throws PersistentException {
        return table.loadEntries(FileName);
    }

    // Save the network table to a file
    public void saveEntries(String FileName) throws PersistentException {
        table.saveEntries(FileName);
    }

    // Get names of all entrys
    public Set<String> getKeys() {
        return table.getKeys();
    }

    public NetworkTableEntry getEntry(String EntryName) {
        return table.getEntry(EntryName);
    }

    // Boolean Checks 
    public boolean isEntryValid(String EntryName) {
        return getEntry(EntryName).isValid();
    }

    public boolean isEntryPersistent(String EntryName) {
        return getEntry(EntryName).isPersistent();
    }

    public boolean containsKey(String EntryName) {
        return table.containsKey(EntryName);
    }

    /* Read information from Network Table */
    public static final boolean DEFAULT_BOOLEAN = false;

    public boolean getBoolean(String EntryName) {
        return getEntry(EntryName).getBoolean(DEFAULT_BOOLEAN);
    }

    public static final double DEFAULT_DOUBLE = 0;

    public double getDouble(String EntryName) {
        return getEntry(EntryName).getDouble(DEFAULT_DOUBLE);
    }

    public static final Number DEFAULT_NUMBER = DEFAULT_DOUBLE;

    public Number getNumber(String EntryName) {
        return getEntry(EntryName).getNumber(DEFAULT_NUMBER);
    }

    public static final String DEFAULT_STRING = "[ENTRY NOT FOUND]";

    public String getString(String EntryName) {
        return getEntry(EntryName).getString(DEFAULT_STRING);
    }

    // Write Information to the Network Table 
    // Returns False if the entry exists with a different type 
    public boolean setBoolean(String EntryName, boolean Value) {
        return getEntry(EntryName).setBoolean(Value);
    }

    public boolean setDouble(String EntryName, double Value) {
        return getEntry(EntryName).setDouble(Value);
    }

    public boolean setNumber(String EntryName, Number Value) {
        return getEntry(EntryName).setNumber(Value);
    }

    public boolean setString(String EntryName, String Value) {
        return getEntry(EntryName).setString(Value);
    }

    // Make value persistent through program restarts. 
    public void setPersistent(String EntryName) {
        getEntry(EntryName).setPersistent();
    }

    public void clearPersistent(String EntryName) {
        getEntry(EntryName).clearPersistent();
    }
}