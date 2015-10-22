import org.junit.Test;
import virtualDataBus.Container;
import static org.junit.Assert.*;

public class RadarTest
{
    private final int BUS_READ_PERIOD_MS = 1000;
    Container _bus;
    Radar _radar;
    TestRadar _testRadar;

    @org.junit.Before
    public void setUp() throws Exception {
        _bus = Container.getInstance();
        _testRadar = new TestRadar();
        _radar = new Radar(_bus,_testRadar,BUS_READ_PERIOD_MS);

    }

    @org.junit.After
    public void setDown() throws Exception {
        _radar.Disconnect();
    }

    @Test
    public void Engine_connect_test()
    {
        //Arrange
        boolean initState = _radar.GetIsConnected();
        //Act
        _radar.Connect();
        try {
            Thread.sleep(_radar.GetBusReadPeriodMs());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Assert
        assertTrue(_radar.GetBusReadPeriodMs() == BUS_READ_PERIOD_MS);
        assertFalse(initState);
        assertTrue(_radar.GetIsConnected());
        assertTrue(_testRadar.GetIsSignaled());
    }
    @Test
    public void Engine_disconnect_test()
    {
        //Arrange
        boolean initState = _radar.GetIsConnected();
        //Act
        _radar.Connect();
        _radar.Disconnect();
        try {
            Thread.sleep(_radar.GetBusReadPeriodMs());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Assert
        assertTrue(_radar.GetBusReadPeriodMs()==BUS_READ_PERIOD_MS);
        assertFalse(initState);
        assertFalse(_radar.GetIsConnected());
        assertFalse(_testRadar.GetIsSignaled());
    }

    /*public void Engine_reconnect_test()
    {
        //Arrange
        //Act
        _radar.Disconnect();
        _radar.Connect();

        //Assert

    }*/
}
