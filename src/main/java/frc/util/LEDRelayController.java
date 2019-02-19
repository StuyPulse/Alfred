package frc.util;
import edu.wpi.first.wpilibj.Relay;

public class LEDRelayController extends Relay {

    private LEDRelayController(int channel) {
        super(channel);
        setLEDNeutral();
    }   
    //Set neutral and sets forward preform opposite functions. Nuetral turns it off and forward turns it on
    //These methods might need to have a name change because they make the method seem like 
    //setter methods, implying a parameter, when they do not have one
    public void setLEDNeutral() {
        set(Relay.Value.kOff);
    }

    //Don't know why it is kForward but it worked on Wildcard
    public void setLEDForward() {
        set(Relay.Value.kForward);
    }
}