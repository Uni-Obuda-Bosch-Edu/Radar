import virtualDataBus.Container;

import java.util.Timer;
import java.util.TimerTask;

public class Radar
{
    public static final int BUS_READ_PERIOD_SEC = 1;

    private final Container _container;
    private final IRadarBase _radarBase;
    private final Timer _messagePump;

    public Radar(Container container)
    {
        _container = container;
        _radarBase = new RadarBase(_container, _container);
        _messagePump = new Timer();
    }

    public Radar(Container container, IRadarBase radarBase)
    {
        _container = container;
        _radarBase = radarBase;
        _messagePump = new Timer();
    }

    public void Connect()
    {
        _messagePump.schedule(new TimerTask() {
            @Override
            public void run() {
                Signal();
            }
        }, BUS_READ_PERIOD_SEC *1000);
    }

    public void Disconnect()
    {
        _messagePump.cancel();
    }

    private void Signal()
    {

    }
}
