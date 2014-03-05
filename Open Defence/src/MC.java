import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;


public class MC {
	public static double[]aim(double x2,double y2,double x,double y, double c){ //I divided the slope of the line connecting (x,y) and (x2,y2) and divided it by the distance between (x,y) and (x2,y2), then multiplied it by a constant
		double[] velo = new double[2];
		velo[0]=(((x-x2)*c)/ ((Math.abs(x-x2)+Math.abs(y-y2))/2.0) );
		velo[1]=(((y-y2)*c)/ ((Math.abs(x-x2)+Math.abs(y-y2))/2.0) );
		return velo;
	}
	public static double distance(double x,double y,double x2,double y2){ //Distance method
		return (Math.pow((Math.pow((y2-y), 2)+Math.pow((x2-x), 2)),0.5));
	}
	public static double distance (Tower a, Unit b){
		double x=a.getx();
		double y=a.gety();
		double x2=b.getx();
		double y2=b.gety();
		return (Math.pow((Math.pow((y2-y), 2)+Math.pow((x2-x), 2)),0.5));
	}
	public static boolean containsid (String s, ArrayList<Unit> a){
		for (int i=0;i<a.size();i++){
			if (s.equals(a.get(i).getid())){
				return true;
			}
		}
		return false;
	}
	public static boolean containsid2 (String s, ArrayList<String> a){
		for (int i=0;i<a.size();i++){
			if (s.equals(a.get(i))){
				return true;
			}
		}
		return false;
	}
	public static Unit idtounit (String s, ArrayList<Unit> a){
		for (int i=0;i<a.size();i++){
			if (s.equals(a.get(i).getid())){
				return a.get(i);
			}
		}
		return null;
	}
	public static Tower idtotower (String s, ArrayList<Tower> a){
		for (int i=0;i<a.size();i++){
			if (s.equals(a.get(i).getid())){
				return a.get(i);
			}
		}
		return null;
	}
	public static boolean intersect(Polygon a, Polygon b){ 
		Boolean intersect=false;
		for(int i=0;i<a.npoints;i++){ //Testing if any of the points in polygon a are contained by polygon b
			if (b.contains(a.xpoints[i],a.ypoints[i])){
				intersect=true;
				return intersect;
			}
		}
		for(int i=0;i<b.npoints;i++){ //Testing essentially the opposite
			if (a.contains(b.xpoints[i],b.ypoints[i])){
				intersect=true;
				return intersect;
			}
		}
		for(int i=0;i<a.npoints;i++){
			Line2D.Double l1= new Line2D.Double(a.xpoints[i%a.npoints],a.ypoints[i%a.npoints],a.xpoints[(i+1)%a.npoints],a.ypoints[(i+1)%a.npoints]);
			for(int i2=0;i2<b.npoints;i2++){
				Line2D.Double l2= new Line2D.Double(b.xpoints[i2%b.npoints],b.ypoints[i2%b.npoints],b.xpoints[(i2+1)%b.npoints],b.ypoints[(i2+1)%b.npoints]);
				if (l1.intersectsLine(l2)){
					intersect=true;
					return intersect;
				}
			}
		}
		return intersect;
	}
	public static Polygon rotate(Polygon a, double x, double y, double theta){ //theta is in degrees!
		double [][] clist=new double [2][a.npoints];
		int[][] rlist=new int [2][a.npoints];
		double tr=Math.toRadians(theta);
		double [][] trlist=new double[2][2];
		trlist[0][0]=Math.cos(tr);
		trlist[0][1]=-Math.sin(tr);
		trlist[1][0]=Math.sin(tr);
		trlist[1][1]=Math.cos(tr);
		for (int i=0;i<a.npoints;i++){
			clist[0][i]=a.xpoints[i]+0.0-x;
			clist[1][i]=a.ypoints[i]+0.0-y;
			rlist[0][i]=(int) Math.round(trlist[0][0]*clist[0][i]+trlist[0][1]*clist[1][i]+x);
			rlist[1][i]=(int) Math.round(trlist[1][0]*clist[0][i]+trlist[1][1]*clist[1][i]+y);
		}
		Polygon b=new Polygon(rlist[0],rlist[1],a.npoints);
		return b;
	}
	public static Polygon translateto(Polygon a, int x, int y){
		a.translate(x-a.xpoints[0],y-a.ypoints[0]);
		return a; 
	}			
	
	public static boolean pastb(Polygon a, String pl, String xy, int p){ //just checking if a polygon is past a border or not
		boolean past=false;
		if (xy.equals("x")){
			if (pl.equals("m")){
				for (int i=0;i<a.npoints;i++){ //so testing if the x coordinates of a polygon is past a certain x coordinate
					if (a.xpoints[i]>p){
						past=true;
					}
				}
			}
			else{
				for (int i=0;i<a.npoints;i++){ //if less than a certain x coordinate
					if (a.xpoints[i]<p){
						past=true;
					}
				}
			}
		}
		else{ //now we're doing the same for the y coordinates
			if (pl.equals("m")){
				for (int i=0;i<a.npoints;i++){ //if less than a certain x coordinate
					if (a.ypoints[i]>p){
						past=true;
					}
				}
			}
			else{
				for (int i=0;i<a.npoints;i++){ //if less than a certain x coordinate
					if (a.ypoints[i]<p){
						past=true;
					}
				}
			}
		}
		return past;
	}
	
	//btw for the below 2 methods, the image is in the BIN folder.... yeah, and you can make subfolders in there to keep things organized!
	public static BufferedImage loadImage(String path){
		URL url = Main_Game.class.getResource(path);
		/*
		 * You'd have a filesystem inside the same folder as this code file -- 
		 * one path could be assdef/qwerty/flurp/snurp/derp/herp/pron/fdsgfhjrasghsdgkgf/boop.<insert image ext here>
		 */
		try {
			BufferedImage img = ImageIO.read(url);
			return img;
		} catch (IOException e){
			System.out.println("failed to read: " + path);
			return null;
		}
	}
	public static ArrayList<BufferedImage> LoadGifFrames(String path){
		ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
		try {
			Iterator<ImageReader> readers = ImageIO.getImageReadersBySuffix("gif");
			ImageReader reader = (ImageReader) readers.next();
			URL url =  Main_Game.class.getResource(path);
			//System.out.println(url); for debug purposes
			BufferedInputStream fis = (BufferedInputStream) url.openStream();
			ImageInputStream iis = ImageIO.createImageInputStream(fis);
			reader.setInput(iis, false);
			
			for (int i = 0; i < reader.getNumImages(true); i++){
				frames.add(reader.read(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return frames;
	}
	public static boolean checkinter(Tower t, ArrayList<Tower> a){
		for (int i=0;i<a.size();i++){
			if (intersect(t.getbox().getbox(), a.get(i).getbox().getbox())==true){
				return true;
			}
		}
		return false;
	}
	public static Tower checkinter(int x, int y, ArrayList<Tower> a){
		for (int i=0;i<a.size();i++){
			if (a.get(i).getbox().getbox().contains(x,y)==true){
				return a.get(i);
			}
		}
		return null;
	}
	
	//public static int 
}

