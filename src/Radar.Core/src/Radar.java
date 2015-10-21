import virtualDataBus.Container;

import java.util.Timer;
import java.util.TimerTask;

public class Radar {
    private final int _busReadPeriodMs;

    private final Container _container;
    private final IRadarBase _radarBase;
    private final Timer _messagePump;

    private boolean _isConnected;

    private double originalDistance = Double.NaN;

    public Radar(Container container) {
        _container = container;
        _radarBase = new RadarBase(_container, _container);
        _messagePump = new Timer();
        _busReadPeriodMs = 1000;
    }

    public Radar(Container container, IRadarBase radarBase, int busReadPeriodMs) {
        _container = container;
        _radarBase = radarBase;
        _messagePump = new Timer();
        _busReadPeriodMs = busReadPeriodMs;
    }

    public void Connect() {
        if (GetIsConnected())
            return;

        _messagePump.schedule(new TimerTask() {
            @Override
            public void run() {
                Signal();
            }
        }, GetBusReadPeriodMs());

        SetIsConnected(true);
    }

    public void Disconnect() {
        _messagePump.cancel();
        _container.setRadarDetectedObject(null, Double.NaN);
        _container.setObjectsByTriangle(null);
        SetIsConnected(false);
    }

    private void Signal() {
        _radarBase.Signal();
    }

    public boolean GetIsConnected() {
        return _isConnected;
    }

    private void SetIsConnected(boolean isConnected) {
        _isConnected = isConnected;
    }

    public int GetBusReadPeriodMs() {
        return _busReadPeriodMs;
    }
}

