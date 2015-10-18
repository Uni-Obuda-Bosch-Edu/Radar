import virtualDataBus.Container;

public class Radar {
    private final Container _container;
    private final IRadarBase _radarBase;

    public Radar(Container container)
    {
        _container = container;
        _radarBase = new RadarBase(_container, _container);
    }

    public Radar(Container container, IRadarBase radarBase)
    {
        _container = container;
        _radarBase = radarBase;
    }

    public void Connect()
    {

    }

    public void Disconnect()
    {

    }
}
