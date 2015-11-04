import Common.Point2D;

import java.util.List;

public interface IRadarBase
{
    void Signal();
    List<Point2D> SetTriangle();
    Point2D SetRadarPosition(Point2D carPosition);
    List<Point2D> GetNearestObject(List<Point2D> objects);
    double RelativeSpeedInKPH(Point2D radarPos, Point2D nearestPos);
}
