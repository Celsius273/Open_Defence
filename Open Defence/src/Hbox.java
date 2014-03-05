import java.awt.*;
public class Hbox { //this is the polygonal hitbox used by all towers and enemies, to make the game run faster, MOST of these hitboxes will have 6 or less sides...

	private Polygon a;
	private Polygon ar;
	private int cx, cy;
	public Hbox(){
		
	}
	public Hbox(Polygon ap, int ccx, int ccy){
		ar=ap; //final polygon
		a=ap;
		cx=ccx;
		cy=ccy;
	}
	public Hbox (int r, int ccx, int ccy){//this will create a... hexagon
		cx=ccx;
		cy=ccy;
		double r3=Math.pow(3,0.5);
		int[]xp={(int) Math.round(cx-(r/2.0)),(int) Math.round(cx+(r/2.0)),cx+r,(int) Math.round(cx+(r/2.0)),(int) Math.round(cx-(r/2.0)),cx-r};
		int[]yp={(int) Math.round(cy+(r*r3/2)), (int) Math.round(cy+(r*r3/2)),cy,(int) Math.round(cy-(r*r3/2)), (int) Math.round(cy-(r*r3/2)),cy};
		Polygon a=new Polygon(xp,yp,6);
		Polygon ar=new Polygon(xp,yp,6);
	}
	public int getcx(){ //specify get cx or cy, also these should be final...
		return cx;
	}
	public int getcy(){
		return cy;
	}
	public Polygon getbox(){
		return a;
	}
	public Polygon getrbox(){ //get reference hitbox, only the actual hitbox will be used for collisions
		return ar;
	}
	public void setcen(int c1, int c2){
		cx=c1;
		cy=c2;
	}
	public void setbox(int[] xp, int[] yp, int c1, int c2, int ax, int ay){ //Please for the love of god make the lengths of xp and yp the same!
		cx=c1;
		cy=c2;
		//Polygon ap=new Polygon(xp,yp,xp.length);
		int[]xxp=new int[xp.length];
		int[]yyp=new int[yp.length];
		for (int i=0;i<xp.length;i++){
			xxp[i]=xp[i]+ax;
			yyp[i]=yp[i]+ay;
		}
		a=new Polygon(xxp,yyp,xxp.length);
		ar=new Polygon(xxp,yyp,xxp.length);
	}
	
	public void setbox (int r, int c1, int c2){
		cx=c1;
		cy=c2;
		double r3=Math.pow(3,0.5);
		int[]xp={(int) Math.round(cx-(r/2.0)),(int) Math.round(cx+(r/2.0)),cx+r,(int) Math.round(cx+(r/2.0)),(int) Math.round(cx-(r/2.0)),cx-r};
		int[]yp={(int) Math.round(cy+(r*r3/2)), (int) Math.round(cy+(r*r3/2)),cy,(int) Math.round(cy-(r*r3/2)), (int) Math.round(cy-(r*r3/2)),cy};
		a=new Polygon(xp,yp,6);
		ar=new Polygon(xp,yp,6);
	}
	
	
	public void rotate(int aa){//aand... aa is in degrees
		a=MC.rotate(ar, cx, cy,aa); //ok, so remember that the rotate method in mc takes degrees, not radians!
	}
	public void translate(int x, int y){ //to translate to the desired coordinate, just type in x-cx and x-cy
		cx+=x;
		cy+=y;
		for (int i=0;i<a.npoints;i++){
			a.xpoints[i]+=x;
			ar.xpoints[i]+=x;
			a.ypoints[i]+=y;
			ar.ypoints[i]+=y;
		}
	}

}
