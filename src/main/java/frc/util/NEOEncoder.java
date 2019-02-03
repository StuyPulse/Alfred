package frc.util;

import com.revrobotics.CANEncoder;

public final class NEOEncoder {
    // Stores value of encoder when reseting.
    private double encoderResetValue;
    private CANEncoder encoder;

    public NEOEncoder(CANEncoder encoder) {
        this.encoder = encoder;
        encoderResetValue = encoder.getPosition();
    }

    public void resetEncoder() {
        encoderResetValue = encoder.getPosition();
    }

    public double getPosition() {
        return encoder.getPosition() - encoderResetValue;
    }
}
