package busInterface;
import java.awt.Point;
import java.util.List;

public interface Radar_Out {
    void setRadarDetectedObject(Point nearestObjectPosition, Double relativeSpeed);
    void setObjectsbyTriangle(List<Point> objectsbyTriangle);
}
