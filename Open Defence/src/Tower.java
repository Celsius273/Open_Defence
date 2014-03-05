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
import java.util.HashMap;
public class Tower {
	protected int hp,maxhp,x,y, rx, ry, cost, def, r, sr, ts; //so x and y pertain to the corners of the image, so if the image is for example, a dot in the middle of a transparent 40x40 square then the coordinates are obviously not on the dot
	//and rx and ry are the coordinates of the center of rotation.... this will be hardcoded to match the one for the hitbox
	
	//no attack value for tower, since it's just going to spawn bullets and explosions that WILL have damage values
	protected int rax,ray;
	protected String fid="";
	protected String name="";
	protected ArrayList<BufferedImage> ia;
	protected BufferedImage im;
	protected Hbox hh=new Hbox();
	
	protected int id;
	
	protected ArrayList<String> targetlist=new ArrayList<String>();
	
	protected boolean isgif; //determines if it will use the first or second draw method...
	//this value is always hardcoded when initializing the... other tower classes below
	
	public Tower (){
		
	}
	public Tower  (int h, int rrx, int rry, ArrayList<BufferedImage> iaa, Hbox ha){ //rx and ry are the rotation coordinates of the tower relative to the upper left corner
		hh=ha;
		hp=h;
		ia=iaa;
		im=null;
		x=0;
		y=0;
		rx=rrx;
		ry=rry;
	}
	public Tower  (int h, int rrx, int rry, BufferedImage iaa, Hbox ha){
		hh=ha;
		hp=h;
		im=iaa;
		ia=null;
		x=0;
		y=0;
		rx=rrx;
		ry=rry;
	}
	
	public void draw(Graphics2D g){ 
		double th=Math.toRadians(r);
		//rotate, draw, then un-rotate
		g.rotate(th,rx,ry);
		g.drawImage(im,x,y,null);
		g.rotate(-th,rx,ry);
		this.rotate();
	}
	public void draw(Graphics2D g, int frame){ 
		double th=Math.toRadians(r);
		//rotate, draw, then un-rotate
		g.rotate(th,rx,ry);
		g.drawImage(ia.get(frame),x,y,null);
		g.rotate(-th,rx,ry);
		this.rotate();
	}
	public int getsr(){
		return sr;
	}
	public void setsr(int n){
		sr=n;
	}
	public int getr(){
		return r;
	}
	public int gethp(){
		return hp;
	}
	public int getmaxhp(){
		return maxhp;
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
	public int getry(){
		return ry;
	}
	public boolean getgif(){
		return isgif;
	}
	
	public void setx(int n){
		rx=n+(rx-x);
		x=n;
	}
	public void setts(int n){
		ts=n;
	}
	public int getts(){
		return ts;
	}
	public void sety(int n){
		ry=n+(ry-y);
		y=n;
	}
	public int getcost(){

		return cost;
	}
	public Hbox getbox(){
		return hh;
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
	public void translate(int a, int b){ //ok, we need a translate method that's NOT relatively additive.... since this is pretty much only used to sync a tower with the mouse!
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
	public void sethp(int n){
		hp+=n;
	}
	public void givehp(int n){
		hp=n;
	}
	public void setmaxhp(int n){
		maxhp=n;
	}
	public void setcost(int n){
		cost=n;
	}
	public void die(ArrayList<Tower> a){
		a.remove(this);
	}
	
	
	public void fire(){ //empty "fire" method, will be inherited by all other towers
		
	}
	public void fire(String a){
		
	}
	public void addtarget(String a){
		targetlist.add(a);
	}
	public String getid(){
		return fid;
	}
	
	public void setid(int n){
		fid=name+n;
		id=n;
	}
	public int getrax(){
		return rax;
	}
	public int getray(){
		return ray;
	}
	public ArrayList<String> gettlist(){
		return targetlist;
	}
	public void fire(int timer, ArrayList<Unit> ulist, ArrayList<Bullet> tblist) {
		// TODO Auto-generated method stub
		
	}
}


//now begins the initialization of towers... I'm aiming for around 12-15 towers in total, so that's 4-5 per race
class Shooter extends Tower{
	public Shooter(int i, int t){
		super();
		maxhp=200;
		hp=maxhp;
		cost=100;
		def=0;
		sr=180; //sight range
		
		x=0;
		y=0;
		rx=x+32;
		ry=y+40;
		rax=rx;
		ray=ry;
		ts=t; //i forgot what this was... oh yeah, time spawned
		
		hh.setbox(22,rx,ry);
		id=i;
		name="Shooter";
		fid=name+id;
		r=0;
		isgif=false;
		im=MC.loadImage("towers/Shooter.png");
	}
	public void fire(int i, ArrayList<Unit> ulist, ArrayList<Bullet> tblist){
		if ((i-ts)%12==0){
			
			double aimx=MC.aim(rx   ,ry   ,MC.idtounit(targetlist.get(0),ulist).getrx(),MC.idtounit(targetlist.get(0),ulist).getry(),10)[0];
			double aimy=MC.aim(rx   ,ry   ,MC.idtounit(targetlist.get(0),ulist).getrx(),MC.idtounit(targetlist.get(0),ulist).getry(),10)[1];
			
			//int tttr=(int) Math.round(Math.toDegrees(-Math.atan2(-x+MC.idtounit(targetlist.get(0),ulist).getx(), -y+MC.idtounit(targetlist.get(0),ulist).gety())+135));
			int tttr=(int) Math.round(Math.toDegrees(-Math.atan2(aimx, aimy)+135));
			tblist.add(new B1(rx,ry,aimx,aimy,i,tttr));
		}
		
	}
}
class Twinlaser extends Tower{
	public Twinlaser(int i, int t){
		super();
		Random rand=new Random();
		maxhp=300;
		hp=maxhp;
		cost=750;
		def=0;
		sr=220; //sight range
		
		x=0;
		y=0;
		rx=x+32;
		ry=y+40;
		rax=rx;
		ray=ry;
		ts=t; //i forgot what this was... oh yeah, time spawned
		
		hh.setbox(22,rx,ry);
		id=i;
		name="Twinlaser";
		fid=name+id;
		r=0;
		isgif=false;
		im=MC.loadImage("towers/Twinlaser.png");
	}
	public void fire(int i, ArrayList<Unit> ulist, ArrayList<Bullet> tblist){
		if ((i-ts)%8==0){
			
			double aimx=MC.aim(rx   ,ry   ,MC.idtounit(targetlist.get(0),ulist).getrx(),MC.idtounit(targetlist.get(0),ulist).getry(),16)[0];
			double aimy=MC.aim(rx   ,ry   ,MC.idtounit(targetlist.get(0),ulist).getrx(),MC.idtounit(targetlist.get(0),ulist).getry(),16)[1];
			
			//int tttr=(int) Math.round(Math.toDegrees(-Math.atan2(-x+MC.idtounit(targetlist.get(0),ulist).getx(), -y+MC.idtounit(targetlist.get(0),ulist).gety())+135));
			int tttr=(int) Math.round(Math.toDegrees(-Math.atan2(aimx, aimy)+135));
			tblist.add(new B2(rx,ry,aimx,aimy,i,tttr));
			
			/*
			tblist.add(new B2(rx,ry,aimx+0.3,aimy+0.3,i,tttr));
			tblist.add(new B2(rx,ry,aimx+0.3,aimy-0.3,i,tttr));
			tblist.add(new B2(rx,ry,aimx-0.3,aimy-0.3,i,tttr));
			tblist.add(new B2(rx,ry,aimx-0.3,aimy+0.3,i,tttr));
			*/
			
			//tblist.add(new B2(rx,ry,aimx+rand.next(),aimy+rand.next(),i,tttr));
			//tblist.add(new B2(rx,ry,aimx+rand.next(),aimy+rand.next(),i,tttr));
		}
		
	}
}

class Base extends Tower{
	private int cash=20000;
	public Base(int xx, int yy){
		super();
		maxhp=3000;
		hp=maxhp;
		sr=0;
		def=0;
		r=0;
		
		x=xx;
		y=yy;
		rx=x+116;
		ry=y+83;
		int[]xp={55,65,101,131,167,179,227,209,24,5};
		int[]yp={5,5,31,31,5,5,70,115,115,70};
		hh.setbox(xp,yp,rx,ry,x,y);
				
		fid="Base";
		isgif=false;
		im=MC.loadImage("towers/Base.png");
		
	}
	public int cash(){
		return cash;
	}
	public void setcash(int n){
		cash=n;
	}
	public void givecash(int n){
		cash+=n;
	}
}
//ok the base class should totally extend a tower! ofc it will also have other attributes

//out of COMPLETE laziness (actually the amount of work for this is unnecessary) there will be NO explosions whatsoever for tower and enemy deaths! like seriously... no point


class Minigun extends Tower{
	
	public Minigun() { //this is pretty much final.... I'm just hardcoding every tower here, because they need to be hardcoded
		super();
		maxhp=500;
		hp=maxhp;
		
		
		//initializing a hitbox will be left to after the sprites are done (which might take forever...)
		//ok, since the coordinates of the polygonal hitbox is relative to the upper left corner of the image..... yeah, shouldn't be hard to auto-sync the center of rotation then
		//maybe as a test we should overlay the image with the hitbox to see if things really work out
		//hh=
	}
}



/*protected int hp,maxhp,x,y, rx, ry, cost, def, r; //so x and y pertain to the corners of the image, so if the image is for example, a dot in the middle of a transparent 40x40 square then the coordinates are obviously not on the dot
//and rx and ry are the coordinates of the center of rotation.... this will be hardcoded to match the one for the hitbox

//no attack value for tower, since it's just going to spawn bullets and explosions that WILL have damage values

protected ArrayList<BufferedImage> ia;
protected BufferedImage im;
protected Hbox hh;*/


