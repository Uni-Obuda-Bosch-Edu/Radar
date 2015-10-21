import java.awt.Point;
import java.util.List;

public interface IRadarBase
{
    void Signal();
    List<Point> SetTriangle();
    Point SetRadarPosition(Point carPosition);
    Point GetNearestObject(List<Point> objects);
    double RelativeSpeedInKPH(Point radarPos, Point nearestPos);
}
