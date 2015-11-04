import Common.Point2D;
import busInterface.Public_In;
import busInterface.Radar_Out;

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
        Point2D radarPosition = SetRadarPosition(GetCarPosition());
        List<Point2D> setTriangle = SetTriangle();
        List<Point2D> nearestObjectPosition =  GetNearestObject(GetRelevantObjects());
        _outBus.setObjectsByTriangle(setTriangle);
        //_outBus.setRadarDetectedObject(nearestObjectPosition, RelativeSpeedInKPH(radarPosition, nearestObjectPosition));
        double relativeSpeed = RelativeSpeedInKPH(radarPosition, nearestObjectPosition.get(0));
                if (!Double.isNaN(relativeSpeed)) {
                       _outBus.setRadarDetectedObject(nearestObjectPosition.get(0), relativeSpeed);
                   }
    }

    @Override
    public List<Point2D> SetTriangle() {
        Point2D radarPosition = SetRadarPosition(GetCarPosition());
        List<Point2D>TriangleCoordinates=new ArrayList<>();
        //A - radar pozi
        TriangleCoordinates.add(radarPosition);
        //B - bal felsõ sarok
        TriangleCoordinates.add(new Point2D(radarPosition.getX()-RADAR_SCOPE, radarPosition.getY()+RADAR_SCOPE));
        //C - jobb felsõ sarok
        TriangleCoordinates.add(new Point2D(radarPosition.getX()+RADAR_SCOPE, radarPosition.getY()+RADAR_SCOPE));

        return TriangleCoordinates;
    }

    @Override
    public Point2D SetRadarPosition(Point2D carPosition){
        return new Point2D(carPosition.getX(), carPosition.getY()+2);
    }

    @Override
    public List<Point2D> GetNearestObject(List<Point2D> objects) {
        Point2D CarPosition= GetCarPosition();
        Point2D first=objects.get(0);
        double min=Math.sqrt(Math.pow(doubleValue(first.getY()-CarPosition.getY()),2)+Math.pow(doubleValue(first.getX()-CarPosition.getY()),2));
        int idx=0;
        List<Point2D> nearestObjects = new ArrayList<>();
        for(int i=0; i<objects.size(); i++){
            double distance=Math.sqrt(Math.pow(doubleValue(objects.get(i).getY()-CarPosition.getY()),2)+Math.pow(doubleValue(objects.get(i).getX()-CarPosition.getX()),2));
            if(distance<min){
                min=distance;
                idx=i;
                nearestObjects.add(objects.get(idx));
            }
        }

        return nearestObjects;
    }

    @Override
    public double RelativeSpeedInKPH(Point2D radarPos, Point2D nearestPos) {
        if (!Double.isNaN(originalDistance))
        {
            return originalDistance - Math.sqrt(Math.pow(doubleValue(nearestPos.getY() - radarPos.getY()), 2) + Math.pow(doubleValue(nearestPos.getX() - radarPos.getX()), 2)) * 3.6;
        }

        originalDistance = Math.sqrt(Math.pow(doubleValue(nearestPos.getY() - radarPos.getY()), 2) + Math.pow(doubleValue(nearestPos.getX() - radarPos.getX()), 2));

        return Double.NaN;
    }

    //dummy
    private List<Point2D> GetRelevantObjects() {
        return SetTriangle();
    }

    private Point2D GetCarPosition() {
        //dummy adat, majd lekérdezzük a bus-ról a pontos értéket
        Point2D coordinates=new Point2D(10.0, 10.0);
        return coordinates;
    }
}