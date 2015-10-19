import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import virtualDataBus.Container;

import java.util.Timer;
import java.util.TimerTask;
import java.lang.Math;
import static oracle.jrockit.jfr.events.Bits.doubleValue;



public class Radar {
    public static final int BUS_READ_PERIOD_SEC = 1;

    private final Container _container;
    private final IRadarBase _radarBase;
    private final Timer _messagePump;

    public Radar(Container container) {
        _container = container;
        _radarBase = new RadarBase(_container, _container);
        _messagePump = new Timer();
    }

    public Radar(Container container, IRadarBase radarBase) {
        _container = container;
        _radarBase = radarBase;
        _messagePump = new Timer();
    }

    public void Connect() {
        _messagePump.schedule(new TimerTask() {
            @Override
            public void run() {
                Signal();
            }
        }, BUS_READ_PERIOD_SEC * 1000);
    }

    public void Disconnect() {
        _messagePump.cancel();
    }

    private void Signal() {

    }


    public Point GetCarPosition() {
        //dummy adat, majd lekérdezzük a bus-ról a pontos értéket
        Point coordinates=new Point(10,10);
        return coordinates;
    }

    public Point SetRadarPosition(Point CarPosition){
      
        return new Point(CarPosition.x, CarPosition.y+2);
   }

    //getobjectsbytriangle
    public List<Point> SetTriangle(){
        Point radarposition=SetRadarPosition(GetCarPosition());
        List<Point>TriangleCoordinates=new ArrayList<>();
        //A - radar pozi
        TriangleCoordinates.add(radarposition); 
        //B - bal felső sarok
        TriangleCoordinates.add(new Point(radarposition.x-10, radarposition.y+10)); 
        //C - jobb felső sarok
        TriangleCoordinates.add(new Point(radarposition.x+10, radarposition.y+10));
              
        return TriangleCoordinates;
    }

    //eldöntöm melyik van a legközelebb
    //most csak koordinátákkal dolgozom, később átírni a világtól kapott objektumoknak megfelelően
    public Point GetNearestObject(List<Point> objects){
        Point CarPosition= GetCarPosition();
        Point first=objects.get(0);
        double min=Math.sqrt(Math.pow(doubleValue(first.y-CarPosition.y),2)+Math.pow(doubleValue(first.x-CarPosition.x),2));
        int idx=0;
        
        for(int i=0; i<objects.size(); i++){
            double distance=Math.sqrt(Math.pow(doubleValue(objects.get(i).y-CarPosition.y),2)+Math.pow(doubleValue(objects.get(i).x-CarPosition.x),2));
            if(distance<min){
                min=distance;
                idx=i;
            }
        }
        
        return objects.get(idx);
    }
    
}

