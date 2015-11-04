import Common.Point2D;

import java.util.ArrayList;
import java.util.List;

public class TestRadar implements IRadarBase {
    private boolean _isSignaled;

    public boolean GetIsSignaled(){
        return _isSignaled;
    }
    @Override
    public void Signal() {
        _isSignaled = true;
    }

    public void SetCurrentRev(double rev) {
    }


    public List<Point2D> SetTriangle()
    {   List<Point2D> a = new ArrayList<>();
        return a;    };
    public Point2D SetRadarPosition(Point2D carPosition)
    {   return new Point2D(10,10);};
    public List<Point2D> GetNearestObject(List<Point2D> objects)
    {   return objects;    };
    public double RelativeSpeedInKPH(Point2D radarPos, Point2D nearestPos)
    {   return 1.0;    };
}
