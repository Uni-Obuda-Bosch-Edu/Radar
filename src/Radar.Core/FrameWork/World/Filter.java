package World;

import Interfaces.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Filter implements IUltraSoundFilter, IVideoCameraFilter, IRadarFilter {
	
	private static void print(String str) {
		System.out.println(str);
	}
	
	private List<IWorldObject> WorldObjects;
	
	public List<IWorldObject> GetObjects()
	{
		return WorldObjects;
	}
	
	public Filter()
	{
		Create();
	}
	
	public Filter(String FileName) throws ParserConfigurationException, SAXException, IOException
	{
		Create(FileName);
	}
	
	private void Create()
	{
		WorldObjects=new ArrayList<>();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(null);
		try {
			File file = fileChooser.getSelectedFile();
			NodeList objects = CreateNodes(file);
			getObjectsFromNodes(objects);
		} catch (ParserConfigurationException e) {
			print("Incorrect file format, please choose an XML file.");
			Create();
		} catch (SAXException e) {
			print("Wrong xml file, please choose another XML file.");
			Create();
		} catch (IOException e) {
			print("File does not exists, please choose an XML file.");
			Create();
		} catch (FactoryConfigurationError e){
			print(e.getMessage());
		} catch (IllegalArgumentException e){
			print("You didn't choose any file.");
		}
	}
	
	private void Create(String fileName) throws ParserConfigurationException, SAXException, IOException
	{
		WorldObjects=new ArrayList<>();
		File file=new File(fileName);
		NodeList objects = CreateNodes(file);
		getObjectsFromNodes(objects);
	}

	private NodeList CreateNodes(File file) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		db = dbf.newDocumentBuilder();
		Document dom = (Document) db.parse(file);
		
		Element scene = dom.getDocumentElement();
		NodeList sceneContent = scene.getElementsByTagName("Objects");
		Node objectsRoot = sceneContent.item(0); 
		NodeList objects = objectsRoot.getChildNodes();
		return objects;
	}

	private void getObjectsFromNodes(NodeList objects) {
		Node object;
		NamedNodeMap attributes;
		int ID=0;
		String Name="";
		double[] Pos=new double[2];
		double[][] Transform=new double[2][2];
		WorldObjectTypes.Type Type;
		WorldObjectTypes.Sign Sign;
		WorldObjectTypes.Lane Lane;
		WorldObjectTypes.Misc Misc;
		
		for (int i = 0; i < objects.getLength(); ++i) {
			object = objects.item(i);
			attributes = object.getAttributes();
			
			if (attributes == null) // Warn: it finds newline characters as nodes!
				continue;
			
			for (int j = 0; j < attributes.getLength(); ++j) {
				switch(attributes.item(j).getNodeName()){
				case "id": ID=Integer.parseInt(attributes.item(j).getNodeValue());
				break;
				case "name": Name=attributes.item(j).getNodeValue();
				break;
				default:
					break;
				}
			}
			
			NodeList child = object.getChildNodes();
			for(int j=0;j<child.getLength();++j)
			{
				if(child.item(j).getNodeName().compareTo("#text")!=0)
				{
					NamedNodeMap childattributes=child.item(j).getAttributes();
					switch(child.item(j).getNodeName())
					{
					case "Position":
						Pos=new double[2];
						Pos[0]=Double.parseDouble(childattributes.item(0).getNodeValue());
						Pos[1]=Double.parseDouble(childattributes.item(1).getNodeValue());
						
						break;
					case "Transform":
						Transform=new double[2][2];
						Transform[0][0]=Double.parseDouble(childattributes.item(0).getNodeValue());
						Transform[0][1]=Double.parseDouble(childattributes.item(1).getNodeValue());
						Transform[1][0]=Double.parseDouble(childattributes.item(2).getNodeValue());
						Transform[1][1]=Double.parseDouble(childattributes.item(3).getNodeValue());
						break;
						default:
							break;
						
					}
				}	
			}
			Type=GetType(Name);
			if(Type==WorldObjectTypes.Type.Sign)
			{
				Misc=WorldObjectTypes.Misc.None;
				Lane=WorldObjectTypes.Lane.None;
				Sign=GetSign(Name);
			}
			else if(Type==WorldObjectTypes.Type.Lane)
			{
				Misc=WorldObjectTypes.Misc.None;
				Sign=WorldObjectTypes.Sign.None;
				Lane=GetLane(Name);
			}
			else
			{
				Misc=GetMisc(Name);
				Lane=WorldObjectTypes.Lane.None;
				Sign=WorldObjectTypes.Sign.None;
			}
			
			WorldObjects.add(new WorldObj(ID,Name,Type,Sign,Lane, Misc,Pos,Transform));
		}
	}
	
	private WorldObjectTypes.Type GetType(String type)
	{
		if(type.substring(5,15).compareTo("road_signs")==0)
		{
			return WorldObjectTypes.Type.Sign;
		}
		else if(type.substring(5,15).compareTo("road_tiles")==0)
		{
			return WorldObjectTypes.Type.Lane;
		}
		else
		{
			return WorldObjectTypes.Type.Misc;
		}
	}
	
	private WorldObjectTypes.Misc GetMisc(String name)
	{
		switch(name.substring(10,name.length()-4))
		{
		case "crosswalks/crosswalk_5_stripes":
			return WorldObjectTypes.Misc.CrossWalk;
		case "parking/parking_0":
			return WorldObjectTypes.Misc.Parking0;
		case "parking/parking_90":
			return WorldObjectTypes.Misc.Parking90;
		case "parking/parking_bollard":
			return WorldObjectTypes.Misc.Parking_Bollard;
		case "people/man03":
			return WorldObjectTypes.Misc.Man;
		case "trees/tree_top_view":
			return WorldObjectTypes.Misc.Tree;
			default:
				return WorldObjectTypes.Misc.None;
			
		}
	}
	
	private WorldObjectTypes.Sign GetSign(String name)
	{
		String type=name.substring(16,18);
		if(type.compareTo("di")==0)
		{
			String x=name.substring(26,name.length()-5);
			switch(x)
			{
			case "209-30":
				return WorldObjectTypes.Sign.Direction_Forward;
			case "211-10":
				return WorldObjectTypes.Sign.Direction_Left;
			case "211-20":
				return WorldObjectTypes.Sign.Direction_Right;
			case "214-10":
				return WorldObjectTypes.Sign.Direction_ForwardLeft;
			case "214-20":
				return WorldObjectTypes.Sign.Direction_ForwardRight;
			case "215":
				return WorldObjectTypes.Sign.Direction_RoundAbout;
				}
			}
			else if(type.compareTo("pa")==0)
			{
				String x=name.substring(24,name.length()-5);
				switch(x)
				{
				case "314_10":
					return WorldObjectTypes.Sign.Parking_Left;
				case "314_20":
					return WorldObjectTypes.Sign.Parking_Right;
				}
			}
			else if(type.compareTo("pr")==0)
			{
				String x=name.substring(25,name.length()-5);
				switch(x)
				{
				case "205":
					return WorldObjectTypes.Sign.Priority_Yield;
				case "206":
					return WorldObjectTypes.Sign.Priority_Stop;
				case "306":
					return WorldObjectTypes.Sign.Priority_Highway;
				}
			}
			else if(type.compareTo("sp")==0)
			{
				String x=name.substring(22,name.length()-5);
				switch(x)
				{
				case "274_51":
					return WorldObjectTypes.Sign.Speed_10;
				case "274_52":
					return WorldObjectTypes.Sign.Speed_20;
				case "274_54":
					return WorldObjectTypes.Sign.Speed_40;
				case "274_55":
					return WorldObjectTypes.Sign.Speed_50;
				case "274_57":
					return WorldObjectTypes.Sign.Speed_70;
				case "274_59":
					return WorldObjectTypes.Sign.Speed_90;
				case "274_60":
					return WorldObjectTypes.Sign.Speed_100;
					
				}
			}
		return WorldObjectTypes.Sign.None;
	}
		

	private WorldObjectTypes.Lane GetLane(String name)
	{
		String type=name.substring(23,24);
		if(type.compareTo("s")==0)
		{
			String x=name.substring(39,name.length()-5);
			switch(x)
			{
			case "s":
				return WorldObjectTypes.Lane.Simple_Straight;
			case "45l":
				return WorldObjectTypes.Lane.Simple_45Left;
			case "45r":
				return WorldObjectTypes.Lane.Simple_45Right;
			case "65l":
				return WorldObjectTypes.Lane.Simple_65Left;
			case "65r":
				return WorldObjectTypes.Lane.Simple_65Right;
			case "90l":
				return WorldObjectTypes.Lane.Simple_90Left;
			case "90r":
				return WorldObjectTypes.Lane.Simple_90Right;
			}
		}
		else if(type.compareTo("a")==0)
		{
			String x=name.substring(34,name.length()-5);
			switch(x)
			{
			case "crossroads_2":
				return WorldObjectTypes.Lane.Advanced_CrossRoads;
			case "rotary":
				return WorldObjectTypes.Lane.Advanced_Rotary;
			case "t_junction_l":
				return WorldObjectTypes.Lane.Advanced_JunctionLeft;
			case "t_junction_r":
				return WorldObjectTypes.Lane.Advanced_JunctionRight;
			}
		}
		
		return WorldObjectTypes.Lane.None;
	}


	private double sign (I2DPoint p1, I2DPoint p2, I2DPoint p3)
	{ 
		return (p1.getX() - p3.getX()) * (p2.getY() - p3.getY()) - (p2.getX() - p3.getX()) * (p1.getY() - p3.getY());  
	}

	private Boolean PointInTriangle (I2DPoint pt, I2DPoint v1, I2DPoint v2, I2DPoint v3)
	{
		Boolean b1, b2, b3;

		b1 = sign(pt, v1, v2) <= 0.0;
		b2 = sign(pt, v2, v3) <= 0.0;
		b3 = sign(pt, v3, v1) <= 0.0;

		return ((b1 == b2) && (b2 == b3));
	}
	
	
	private List<IWorldObject> getObjectsByTriangle(I2DPoint a, I2DPoint b, I2DPoint c) {
		List<IWorldObject> found=new ArrayList<IWorldObject>();
		for(IWorldObject obj : WorldObjects)
		{
			if(PointInTriangle(obj.getPosition(),a,b,c))
			{
				found.add(obj);
			}
		}
		
		
		return found;
	}
	
	@Override
	public List<IWorldObject> getRelevantObjectsForRadar(I2DPoint a, I2DPoint b, I2DPoint c) {
		return getObjectsByTriangle(a, b, c);
	}


	@Override
	public List<IWorldObject> getRelevantObjectsForVideoCamera(I2DPoint a, I2DPoint b, I2DPoint c) {
		return getObjectsByTriangle(a, b, c);
	}

	@Override
	public List<IWorldObject> getRelevantObjectsForUltraSound(I2DPoint a, I2DPoint b, I2DPoint c) {
		return getObjectsByTriangle(a, b, c);
	}
}

