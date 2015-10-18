import busInterface.Engine_Out;
import busInterface.Public_In;

public class RadarBase implements IRadarBase {
    private final Public_In _inBus;
    private final Engine_Out _outBus;

    public RadarBase(Public_In inBus, Engine_Out outBus) {
        this._inBus = inBus;
        this._outBus = outBus;
    }
}