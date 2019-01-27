package frc.util;

import edu.wpi.first.wpilibj.DigitalInput;

public class IRSensor {

    /******************************************************************************
     * IRSensor Constants
     *****************************************************************************/

    private static DigitalInput cargoSensor;

    public IRSensor(int port) {
        cargoSensor = new DigitalInput(port);
    }

    public boolean isSensorTriggered() {
        return !cargoSensor.get();
    }

}
