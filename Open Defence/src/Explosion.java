import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.event.*;

//all explosions are gifs!
public class Explosion { //explosions are supposed to be stationary btw
	private int x,y,r,ls,ts,dmg;
	private ArrayList<BufferedImage> ia;
	protected Hbox hh=new Hbox();
	private boolean he=false;
	public Explosion(int xx, int yy,int rr,int lss, int tss, int d, String ss, boolean hhe){
		x=xx;
		y=yy;
		r=rr;
		ls=lss;
		ts=tss;
		dmg=d;
		he=hhe;
		hh.setbox(r,x+(r/2),y+(r/2));
		ia=MC.LoadGifFrames(ss);
	}
	public int getx(){
		return x;
	}
	public int gety(){
		return y;
	}
	public int getr(){ //getting the blast radius
		return r;
	}
	public int getls(){
		return ls; //lifespan
	}
	public int getts(){ //time spawned
		return ts;
	}
	public int getdmg(){ //damage
		return dmg;
	}
	public boolean gethe(){
		return he; //hurts enemies or not
	}
	public Hbox getbox(){
		return hh;
	}
	public void draw(Graphics2D g, int t){
		g.drawImage(ia.get((t-ts)%ls),x,y,null);
	}
	
}
