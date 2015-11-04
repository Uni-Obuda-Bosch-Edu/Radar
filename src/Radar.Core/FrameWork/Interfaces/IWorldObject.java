package Interfaces;

import World.WorldObjectTypes;

public interface IWorldObject {
	
	int getID();
	
	String getName();
	
	WorldObjectTypes.Type getType();
	
	WorldObjectTypes.Sign getSign();
	
	WorldObjectTypes.Misc getMisc();
	
	WorldObjectTypes.Lane getLane();
	
	I2DPoint getPosition();
	
	double[][] getTransform() ;

}
