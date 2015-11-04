package Common;
import Interfaces.I2DPoint;

public class Point2D implements I2DPoint {

	private double x,y;
	
	public Point2D(double x, double y)
	{
		this.x=x;
		this.y=y;
	}
	
	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return y;
	}

}
