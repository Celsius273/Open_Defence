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

public class Bullet {
	protected int x,y,rx, ry, atk, r, ts, lp; //attack value will only be relevant for melee units
	protected ArrayList<BufferedImage> ia;
	protected BufferedImage im;
	protected Hbox hh=new Hbox();
	protected double mx,my;
	
	protected String id;
	
	protected boolean isgif, pierce; //determines if it will use the first or second draw method for isgif, pierce is self explanatory then
	//this value is always hardcoded when initializing the... other tower classes below
	
	public Bullet(){ //this is just to create something empty, of course
	}
	
	
	public void draw(Graphics2D g){ //note that a is in degrees (of course)
		double th=Math.toRadians(r);
		//rotate, draw, then un-rotate
		g.rotate(th,rx,ry);
		g.drawImage(im,x,y,null);
		g.rotate(-th,rx,ry);
		this.rotate();
	}
	public void draw(Graphics2D g, int frame){ //note that a is in degrees (of course)
		double th=Math.toRadians(r);
		//rotate, draw, then un-rotate
		g.rotate(th,rx,ry);
		g.drawImage(ia.get(frame),x,y,null);
		g.rotate(-th,rx,ry);
		this.rotate();
	}

	public int getx(){
		return x;
	}
	public int gety(){
		return y;
	}
	public int getrx(){
		return rx;
	}
	public boolean getpierce(){
		return pierce;
	}
	public void setpierce(boolean a){
		pierce=a;
	}
	public int getry(){
		return ry;
	}
	public double getmx(){
		return mx;
	}
	public double getmy(){
		return my;
	}
	public Hbox getbox(){
		return hh;
	}
	public boolean getgif(){
		return isgif;
	}
	
	public void setx(int n){
		rx=n+(rx-x);
		x=n;
	}
	public void sety(int n){
		ry=n+(ry-y);
		y=n;
	}
	public void setmx(double n){
		mx=n;
	}
	public void setmy(double n){
		my=n;
	}
	
	public void translate(int a, int b){
		x+=a;
		y+=b;
		rx+=a;
		ry+=b;
		hh.translate(a,b);
	}
	public void translateto(int a, int b){ //done...
		int c=rx-x;
		int d=ry-y;
		hh.translate(a-x,b-y);
		x=a;
		y=b;
		rx=x+c;
		ry=y+d;
		
	}
	public void move(){
		x+=mx;
		y+=my;
		rx+=mx;
		ry+=my;
		hh.translate((int) Math.round(mx),(int) Math.round(my));
	}
	
	public void rotate(){
		hh.rotate(r);
	}
	public void setr(int n){
		r=n;
	}
	public void incsetr(int n){
		r+=n;
	}
	public void die(ArrayList<Bullet> b){
		b.remove(this);
	}
	
	public int getts(){
		return ts;
	}
	public int getlp(){
		return lp;
	}
	public int getatk(){
		return atk;
	}
	
}
class B1 extends Bullet{
	public B1(int xx, int yy, double mmx, double mmy, int t, int rr){
		super();
		atk=12;
		pierce=false;
		ts=t;
		lp=100;
		r=rr;
		x=xx;
		y=yy;
		rx=x+5;
		ry=y+7;
		mx=mmx;
		my=mmy;
		//name="Grunt";
		//id=iid; //ok, we're going to have to manually increment the id of each unit...
		//fid=name+id;
		
		int[]xp={5,9,0};
		int[]yp={0,14,14};
		
		hh.setbox(xp,yp,rx,ry,x,y);
		//hh.setbox(7,rx,ry);
		
		isgif=false;
		im=MC.loadImage("bullets/b1.png");
	}
	
	
}

class Eb1 extends Bullet{
	public Eb1(int xx, int yy, double mmx, double mmy, int t, int rr){
		super();
		atk=16;
		pierce=false;
		ts=t;
		lp=100;
		r=rr;
		x=xx;
		y=yy;
		rx=x+5;
		ry=y+7;
		mx=mmx;
		my=mmy;
		//name="Grunt";
		//id=iid; //ok, we're going to have to manually increment the id of each unit...
		//fid=name+id;
		
		int[]xp={5,9,0};
		int[]yp={0,14,14};
		
		hh.setbox(xp,yp,rx,ry,x,y);
		//hh.setbox(7,rx,ry);
		
		isgif=false;
		im=MC.loadImage("bullets/eb1.png");
	}
	
	
}

class B2 extends Bullet{
	public B2(int xx, int yy, double mmx, double mmy, int t, int rr){
		super();
		atk=20;
		pierce=true;
		ts=t;
		lp=100;
		r=rr;
		x=xx;
		y=yy;
		rx=x+6;
		ry=y+40;
		mx=mmx;
		my=mmy;
		//name="Grunt";
		//id=iid; //ok, we're going to have to manually increment the id of each unit...
		//fid=name+id;
		
		int[]xp={6,12,0};
		int[]yp={0,80,80};
		
		hh.setbox(xp,yp,rx,ry,x,y);
		//hh.setbox(7,rx,ry);
		
		isgif=false;
		im=MC.loadImage("bullets/b2.png");
	}
	
	
}
