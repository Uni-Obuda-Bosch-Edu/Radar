package busInterface;
import Common.Point2D;

import java.util.List;

public interface Radar_Out {
    void setRadarDetectedObject(Point2D nearestObjectPosition, Double relativeSpeed);
    void setObjectsByTriangle(List<Point2D> objectsByTriangle);
}
