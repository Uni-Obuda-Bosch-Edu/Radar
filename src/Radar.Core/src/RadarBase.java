import busInterface.Public_In;
import busInterface.Radar_Out;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static oracle.jrockit.jfr.events.Bits.doubleValue;

public class RadarBase implements IRadarBase
{
    private static final int RADAR_SCOPE = 10;

    private final Public_In _inBus;
    private final Radar_Out _outBus;

    private double originalDistance = Double.NaN;

    public RadarBase(Public_In inBus, Radar_Out outBus)
    {
        this._inBus = inBus;
        this._outBus = outBus;
    }

    @Override
    public void Signal() {
        Point radarPosition = SetRadarPosition(GetCarPosition());
        List<Point> setTriangle = SetTriangle();
        Point nearestObjectPosition = GetNearestObject(GetRelevantObjects());
        _outBus.setObjectsByTriangle(setTriangle);
        //_outBus.setRadarDetectedObject(nearestObjectPosition, RelativeSpeedInKPH(radarPosition, nearestObjectPosition));
        double relativeSpeed = RelativeSpeedInKPH(radarPosition, nearestObjectPosition);
                if (!Double.isNaN(relativeSpeed)) {
                       _outBus.setRadarDetectedObject(nearestObjectPosition, relativeSpeed);
                   }
    }

    @Override
    public List<Point> SetTriangle() {
        Point radarPosition = SetRadarPosition(GetCarPosition());
        List<Point>TriangleCoordinates=new ArrayList<>();
        //A - radar pozi
        TriangleCoordinates.add(radarPosition);
        //B - bal felsõ sarok
        TriangleCoordinates.add(new Point(radarPosition.x-RADAR_SCOPE, radarPosition.y+RADAR_SCOPE));
        //C - jobb felsõ sarok
        TriangleCoordinates.add(new Point(radarPosition.x+RADAR_SCOPE, radarPosition.y+RADAR_SCOPE));

        return TriangleCoordinates;
    }

    @Override
    public Point SetRadarPosition(Point carPosition){
        return new Point(carPosition.x, carPosition.y+2);
    }

    @Override
    public Point GetNearestObject(List<Point> objects) {
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

    @Override
    public double RelativeSpeedInKPH(Point radarPos, Point nearestPos) {
        if (!Double.isNaN(originalDistance))
        {
            return originalDistance - Math.sqrt(Math.pow(doubleValue(nearestPos.y - radarPos.y), 2) + Math.pow(doubleValue(nearestPos.x - radarPos.x), 2)) * 3.6;
        }

        originalDistance = Math.sqrt(Math.pow(doubleValue(nearestPos.y - radarPos.y), 2) + Math.pow(doubleValue(nearestPos.x - radarPos.x), 2));

        return Double.NaN;
    }

    //dummy
    private List<Point> GetRelevantObjects() {
        return SetTriangle();
    }

    private Point GetCarPosition() {
        //dummy adat, majd lekérdezzük a bus-ról a pontos értéket
        Point coordinates=new Point(10,10);
        return coordinates;
    }
}