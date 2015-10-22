import java.awt.*;
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


    public List<Point> SetTriangle()
    {   List<Point> a = new ArrayList<>();
        return a;    };
    public Point SetRadarPosition(Point carPosition)
    {   return new Point(10,10);};
    public Point GetNearestObject(java.util.List<Point> objects)
    {   return new Point(10,10);    };
    public double RelativeSpeedInKPH(Point radarPos, Point nearestPos)
    {   return 1.0;    };
}
