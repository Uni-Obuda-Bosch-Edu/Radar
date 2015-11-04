import Common.Point2D;
import org.junit.Assert;
import org.junit.Test;
import virtualDataBus.Container;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RadarBaseTests
{
    private RadarBase _radarBase;
    private Container _bus;
    @org.junit.Before
    public void setUp() throws Exception
    {
        _bus = Container.getInstance();
        _radarBase = new RadarBase(_bus,_bus);
    }

    @Test
    public void x()
    {
        Assert.assertNull(null);
    }

    @Test
    //SetTriangle teszt, radarpoz:(10,12), bal felsõ(0,22),jobb felsõ(20,22)
    //mert a kocsié (10,10)
    public void SetTriangle_Test ()
    {
        List<Point2D> _testTriangle = new ArrayList<>();
        _testTriangle.add(new Point2D(10.0,12.0));
        _testTriangle.add(new Point2D(0.0,22.0));
        _testTriangle.add(new Point2D(20.0,22.0));

        assertEquals(_radarBase.SetTriangle(),_testTriangle);
    }

    @Test
    //magáért beszél
    public void SetRadarPosition_Test()
    {
        assertEquals(_radarBase.SetRadarPosition(new Point2D(10.0,20.0)),new Point2D(10.0,22.0));
    }

    @Test
    //autó pozíciója (10,10)
    public void GetNearestObject_Test()
    {
        List<Point2D> coords = new ArrayList<>();
        coords.add(new Point2D(3,6));
        coords.add(new Point2D(4,7));
        coords.add(new Point2D(5,2));
        coords.add(new Point2D(9,1));
        coords.add(new Point2D(8,5));
        assertEquals(_radarBase.GetNearestObject(coords),coords.get(4));
    }

    @Test
    public void RelativeSpeedTest()
    {
        List<Point2D> coords = new ArrayList<>();
        coords.add(new Point2D(3,6));
        coords.add(new Point2D(4,7));
        coords.add(new Point2D(5,2));
        coords.add(new Point2D(9,1));
        coords.add(new Point2D(8,5));
        _radarBase.RelativeSpeedInKPH(new Point2D(20,10),new Point2D(25,10));
        _radarBase.RelativeSpeedInKPH(new Point2D(27,10),new Point2D(40,10));
        assertEquals(_radarBase.RelativeSpeedInKPH(_radarBase.SetRadarPosition(new Point2D(27,10)),
                new Point2D(40,10)), -38, 7.3);
    }
}
