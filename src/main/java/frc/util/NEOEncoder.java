/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util;

import com.revrobotics.CANEncoder;

/**
 * Add your docs here.
 */
public class NEOEncoder {
    private CANEncoder encoder;
    private double position;

    public NEOEncoder(CANEncoder encoder) {
        this.encoder = encoder;
        position = encoder.getPosition();
    }

    public double getPosition() {
        return encoder.getPosition() - position;
    }

    public void resetEncoder() {
        position = encoder.getPosition();
    }
}
