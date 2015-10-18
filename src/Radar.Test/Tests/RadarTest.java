import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import virtualDataBus.Container;

public class RadarTest
{
    Container _bus;
    Radar _radar;

    @org.junit.Before
    public void setUp() throws Exception {
        _bus = Container.getInstance();
        _radar = new Radar(_bus,new TestRadar());
    }

    @Test
    public void Engine_connect_test()
    {
        //Arrange

        //Act
        _radar.Connect();

        //Assert

    }

    public void Engine_disconnect_test()
    {
        //Arrange

        //Act
        _radar.Disconnect();

        //Assert

    }

    public void Engine_reconnect_test()
    {
        //Arrange
        //Act
        _radar.Disconnect();
        _radar.Connect();

        //Assert

    }
}
