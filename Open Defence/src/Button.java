import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
public class Button {
	Rectangle ptrect = new Rectangle(0,0,0,0);
	protected ArrayList<BufferedImage> ia;
	
	protected String n="";
	
	protected boolean o=false;//if moused over
	protected boolean s=false;//if clicked on... prompts the button to do something
	protected int x,y;
	public Button(int a, int b, int c, int d, String imagename){
		ptrect.setBounds(a,b,c,d);
		x=a;
		y=b;
		ia=MC.LoadGifFrames(imagename);
	}
	
	public void draw(Graphics2D g){
		if (o==false){
			g.drawImage(ia.get(0),x,y,null);
		}
		else{
			g.drawImage(ia.get(1),x,y,null);
		}
	}
	
	public boolean geto(){
		return o;
	}
	public void seto(boolean b){
		o=b;
	}
	public String getn(){
		return n;
	}
	public void cselect(int x, int y){
		if (ptrect.contains(x,y)){
			o=true;
		}
		else{
			o=false;
		}
	}
	public String selects(){
		//execute any relevant actions here
		return "";
	}
	public Tower stower(){
		return null;
	}
	public boolean sbool(){
		return false;
	}

	public int getii() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

class Mswitch extends Button{ //button that switches modes
	String mm="";
	public Mswitch(int a, int b, int c, int d, String imagename, String m){
		super(a, b, c, d, imagename);
		mm=m;
		n="switch";
	}
	
	public String selects(){
		return mm;
	}
}

class Tspawn extends Button{ //button that spawns towers...
	Tower mm=new Tower();
	int ii;
	public Tspawn(int a, int b, int c, int d, String imagename, Tower m, int aa){
		super(a, b, c, d, imagename);
		mm=m;
		ii=aa;
		n="tower";
	}
	public Tower stower(){
		return mm;
	}
	public int getii(){
		return ii;
	}
}
class Bswitch extends Button{ //button that makes a boolean true/false...
	Boolean m=false;
	public Bswitch(int a, int b, int c, int d, String imagename, Boolean bo){
		super(a, b, c, d, imagename);
		m=bo;
		n="boolean";
	}
	public boolean sbool(){
		return m;
	}
}

class Sellb extends Button{
	Tower mm=new Tower();
	public Sellb(int a, int b, int c, int d, String imagename, Tower m){
		super(a, b, c, d, imagename);
		mm=m;
		n="sell";
	}
	public Tower stower(){
		return mm;
	}
}
