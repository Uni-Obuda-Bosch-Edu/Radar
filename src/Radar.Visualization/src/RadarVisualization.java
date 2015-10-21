import virtualDataBus.Container;

public class RadarVisualization {

    public static void main (String[] arguments)
    {
        final Container _container = Container.getInstance();
        final Radar _radar = new Radar(_container);


        _radar.Connect();

        //TODO

        _radar.Disconnect();
    }
}
