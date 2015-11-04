package World;
import Common.Point2D;
import Interfaces.I2DPoint;
import Interfaces.IWorldObject;

public class WorldObj implements IWorldObject {
	private int ID;
	private String Name;
	private WorldObjectTypes.Type Type;
	private WorldObjectTypes.Sign Sign;
	private WorldObjectTypes.Lane Lane;
	private WorldObjectTypes.Misc Misc;
	private Point2D Pos;
	private double[][] Transform;
	
	public WorldObj(int id,String name, WorldObjectTypes.Type type ,WorldObjectTypes.Sign sign,WorldObjectTypes.Lane lane, WorldObjectTypes.Misc misc, double[] pos,double[][]transform)
	{
		Transform= new double[2][2];
		ID=id;
		Name=name;
		Type=type;
		Sign=sign;
		Lane=lane;
		Misc=misc;
		Transform=transform;
		Point2D shift=Transform(Transform);
		Pos=new Point2D(pos[0]+shift.getX(),pos[1]+shift.getY());
	}

	private Point2D Transform(double[][] T)
	{
		return new Point2D(40*T[0][0]+40*T[1][0],40*T[0][1]+40*T[1][1]);
		
	}
	
	public int getID() {
		return ID;
	}
	
	
	
	public String getName() {
		return Name;
	}
	
	public WorldObjectTypes.Type getType() {
		return Type;
	}
	
	public WorldObjectTypes.Sign getSign()
	{
		return Sign;
	}
	
	public WorldObjectTypes.Misc getMisc()
	{
		return Misc;
	}
	
	public WorldObjectTypes.Lane getLane()
	{
		return Lane;
	}
	
	public I2DPoint getPosition()
	{
		return Pos;
	}
	
	public double[][] getTransform() {
		return Transform;
	}
	
	
	
	
	




}
