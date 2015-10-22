import org.junit.Assert;
import org.junit.Test;
import virtualDataBus.Container;

import java.awt.*;
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
        List<Point> _testTriangle = new ArrayList<>();
        _testTriangle.add(new Point(10,12));
        _testTriangle.add(new Point(0,22));
        _testTriangle.add(new Point(20,22));

        assertEquals(_radarBase.SetTriangle(),_testTriangle);
    }

    @Test
    //magáért beszél
    public void SetRadarPosition_Test()
    {
        assertEquals(_radarBase.SetRadarPosition(new Point(10,20)),new Point(10,22));
    }

    @Test
    //autó pozíciója (10,10)
    public void GetNearestObject_Test()
    {
        List<Point> coords = new ArrayList<>();
        coords.add(new Point(3,6));
        coords.add(new Point(4,7));
        coords.add(new Point(5,2));
        coords.add(new Point(9,1));
        coords.add(new Point(8,5));
        assertEquals(_radarBase.GetNearestObject(coords),coords.get(4));
    }

    @Test
    public void RelativeSpeedTest()
    {
        List<Point> coords = new ArrayList<>();
        coords.add(new Point(3,6));
        coords.add(new Point(4,7));
        coords.add(new Point(5,2));
        coords.add(new Point(9,1));
        coords.add(new Point(8,5));
        _radarBase.RelativeSpeedInKPH(new Point(20,10),new Point(25,10));
        _radarBase.RelativeSpeedInKPH(new Point(27,10),new Point(40,10));
        assertEquals(_radarBase.RelativeSpeedInKPH(_radarBase.SetRadarPosition(new Point(27,10)),
                new Point(40,10)), -38, 7.3);
    }
}
