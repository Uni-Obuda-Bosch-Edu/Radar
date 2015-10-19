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


    public Pair GetCarPosition(){
        Pair coordinates;
        coordinates.setFirst(10);
        coordinates.setSecond(10);
        return coordinates;
    }

    public Pair
}

public class Pair<X, Y> {
    private X first;
    private Y second;

    public Pair(X first, Y second) {
        super();
        this.first = first;
        this.second = second;
    }

    public int hashCode() {
        int hashFirst = first != null ? first.hashCode() : 0;
        int hashSecond = second != null ? second.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    public boolean equals(Object other) {
        if (other instanceof Pair) {
            Pair otherPair = (Pair) other;
            return
                    ((  this.first == otherPair.first ||
                            ( this.first != null && otherPair.first != null &&
                                    this.first.equals(otherPair.first))) &&
                            (	this.second == otherPair.second ||
                                    ( this.second != null && otherPair.second != null &&
                                            this.second.equals(otherPair.second))) );
        }

        return false;
    }

    public String toString()
    {
        return "(" + first + ", " + second + ")";
    }

    public X getFirst() {
        return first;
    }

    public void setFirst(X first) {
        this.first = first;
    }

    public Y getSecond() {
        return second;
    }

    public void setSecond(Y second) {
        this.second = second;
    }
}