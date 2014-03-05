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

public class Unit {
	protected int hp,maxhp,x,y,rx, ry, cost, atk, def, r,sr,ar; //attack value will only be relevant for melee units
	protected ArrayList<BufferedImage> ia;
	protected BufferedImage im;
	protected Hbox hh=new Hbox();
	protected double mx,my;
	protected int rax,ray;
	protected int id;
	protected String fid="";
	protected String name="";
	
	protected String bhv=""; //enemy's general behavior type... categorized as: normal, base and tower
	
	//protect String 
	protected ArrayList<String> targetlist=new ArrayList<String>();
	
	protected boolean isgif; //determines if it will use the first or second draw method...
	//this value is always hardcoded when initializing the... other tower classes below
	
	public Unit(){ //this is just to create something empty, of course
	}
	
	public Unit(int h, int cc, int aat, int ddf){
		hp=h;
		maxhp=h;
		cost=cc;
		atk=aat;
		def=ddf;
	}
	
	public void draw(Graphics2D g){ //note that a is in degrees (of course)
		double th=Math.toRadians(r);
		//rotate, draw, then un-rotate
		g.rotate(th,rx,ry);
		g.drawImage(im,x,y,null);
		g.rotate(-th,rx,ry);
		this.rotate();
		
		/* the below segment of code is really to see the hp of the unit
		g.setColor(Color.RED);
		g.fillRect(x,y-6,maxhp,6);
		g.setColor(Color.GREEN);
		g.fillRect(x,y-6,hp,6);
		*/
		/***
		 * trolololol different colour comments ftw!
		 */
	}
	public void draw(Graphics2D g, int frame){ //note that a is in degrees (of course)
		double th=Math.toRadians(r);
		//rotate, draw, then un-rotate
		g.rotate(th,rx,ry);
		g.drawImage(ia.get(frame),x,y,null);
		g.rotate(-th,rx,ry);
		this.rotate();
	}
	
	public String bhv(){
		return bhv;
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
	public int getrax(){
		return rax;
	}
	public int getray(){
		return ray;
	}
	public double getmx(){
		return mx;
	}
	public double getmy(){
		return my;
	}
	public int getcost(){
		return cost;
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
		hh.translate((a-x)/2,(b-y)/2); //apparently this happens twice, idek why
		x=a;
		y=b;
		rx=x+c;
		ry=y+d;
	}
	public void move(){
		double px=x+mx;
		double py=y+my;
		x=(int) Math.round(px);
		y=(int) Math.round(py);
		px=rx+mx;
		py=ry+my;
		rx=(int) Math.round(px);
		ry=(int) Math.round(py);
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
	public void die(ArrayList<Unit> u){
		u.remove(id);
	}
	public void sethp(int n){
		hp+=n;
	}
	public void setmaxhp(int n){
		maxhp=n;
	}
	public void setcost(int n){
		cost=n;
	}
	
	public String getid(){
		return fid;
	}
	public void setid(int i){
		fid=name+i;
	}
	public String getname(){
		return name;
	}
	public void attack(){ //empty "fire" method, will be inherited by all other towers
		
	}
	public void attack(Unit a){
		
	}
	public int getsr(){
		return sr;
	}
	public int getar(){
		return ar;
	}
	public int getatk(){
		return atk;
	}
	public void addtarget(String a){
		targetlist.add(a);
	}
	public ArrayList<String> gettlist() {
		// TODO Auto-generated method stub
		return targetlist;
	}
	
}

/*protected int hp,maxhp,x,y,rx, ry, cost, atk, def, r; //attack value will only be relevant for melee units
protected ArrayList<BufferedImage> ia;
protected BufferedImage im;
protected Hbox hh;
protected double mx,my;*/
class Grunt extends Unit{ //pretty much all of the units classes will be like this...
	public Grunt(int iid, int xx, int yy, int mxx, int myy){ //testing grunt class SUCCESS!
		super();
		hp=35;
		maxhp=35;
		atk=5;
		def=0;
		cost=20;
		
		r=180; //this should really be: set the rotation to whatever direction the enemy's moving in
		x=xx;
		y=yy;
		rx=x+12;
		ry=y+10;
		rax=12;
		ray=13;
		mx=mxx;
		my=myy;
		name="Grunt";
		id=iid; //ok, we're going to have to manually increment the id of each unit...
		fid=name+id;
		bhv="normal";
		sr=100;
		ar=0;
		
		int[]xp={12,23,12,1};
		int[]yp={0,32,21,32};
		
		hh.setbox(xp,yp,rx,ry,x,y);
		//hh.setbox(11,rx,ry);
		
		//this.translateto(xx,yy);
		isgif=false;
		im=MC.loadImage("units/Grunt.png");
	}
	
}

class Sgrunt extends Unit{ //a grunt, except he can shoot
	public Sgrunt(int iid, int xx, int yy, int mxx, int myy){ //testing grunt class SUCCESS!
		super();
		hp=45;
		maxhp=45;
		atk=5;
		def=0;
		cost=50;
		
		r=180; //this should really be: set the rotation to whatever direction the enemy's moving in
		x=xx;
		y=yy;
		rx=x+12;
		ry=y+10;
		rax=12;
		ray=13;
		mx=mxx;
		my=myy;
		name="Sgrunt";
		id=iid; //ok, we're going to have to manually increment the id of each unit...
		fid=name+id;
		bhv="normal";
		sr=180;
		ar=100;
		
		int[]xp={12,23,12,1};
		int[]yp={0,32,21,32};
		
		hh.setbox(xp,yp,rx,ry,x,y);
		//hh.setbox(11,rx,ry);
		
		//this.translateto(xx,yy);
		isgif=false;
		im=MC.loadImage("units/Sgrunt.png");
	}
}