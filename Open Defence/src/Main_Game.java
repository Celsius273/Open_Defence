import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.*;

public class Main_Game extends JPanel implements MouseListener, MouseMotionListener{
	String mode = "start"; //game mode.... this determines what data will be stored, and what will be drawn on the screen... etc etc
	private final int win_w=800;
    private final int win_h=600;
	int timer=0;
	int shift=600;
	int pshift=600;
    private int x,y; //mouse position
    //int cash=1000;
    int update_rate=30;
    
    Font f1=new Font("BankGothic Md BT Medium", 0, 11);
    Font f4=new Font("BankGothic Md BT Medium", 0, 16);
    Font f5=new Font("BankGothic Md BT Medium", 1, 36);
    Font f3=new Font("BankGothic Md BT Medium", 1, 20);
    Font f2=new Font("BankGothic Md BT Medium", 0, 24);
    
    public ArrayList<Unit> ulist= new ArrayList<Unit>();
    public ArrayList<Tower> tlist= new ArrayList<Tower>();
    
    public ArrayList<Bullet> eblist=new ArrayList<Bullet>(); //arraylist for enemy bullets
    public ArrayList<Bullet> tblist=new ArrayList<Bullet>(); //arraylist for tower bullets
    public ArrayList<Button> bulist=new ArrayList<Button>();
    public ArrayList<Explosion> exlist=new ArrayList<Explosion>();
    
    public ArrayList<Tower> tselect= new ArrayList<Tower>();
    
    //Loading the background images here
    public BufferedImage bg1=MC.loadImage("backgrounds/gamebg1.png");
    public BufferedImage s1=MC.loadImage("backgrounds/startbg.png");
    public BufferedImage stext=MC.loadImage("backgrounds/starttext.png");
    
    public int unum=0;
    
    public int tnum=0;
    
    public Color c1=new Color(150,255,255,80);
	public Color c2=new Color(255,255,255,90);
	
	public Color c3=new Color(255,0,0,90);
    
    boolean inwin=true;
    boolean canplace=true;
    
    Tower ms=null;
    
    int tt=0;
    int round=1;
    Base base=new Base(183,450);
    public boolean pause=false;
    Random rand=new Random();
    public void b_init(String m){ //to initialize buttons... ofc
    	ulist.clear();
    	tlist.clear();
    	eblist.clear();
    	tblist.clear();
    	bulist.clear();
    	exlist.clear();
    	timer=0;
    	
    	if (m.equals("start")){
    		bulist.add(new Mswitch(40,100,200,120,"buttons/Start.gif","game"));
    	}
    	if (m.equals("game")){
    		base.givehp(3000);
    		base.setcash(20000);
    		bulist.add(new Mswitch(610,110,120,72,"buttons/Quit.gif","start"));
    		bulist.add(new Tspawn(608, 188, 56, 56, "buttons/Shooterb.gif", new Shooter(tnum, timer),0));
    		bulist.add(new Tspawn(672, 188, 56, 56, "buttons/Twinlaserb.gif", new Twinlaser(tnum, timer),1));
    		bulist.add(new Sellb(608,500,184,92, "buttons/sell.gif", null));
    	}
    	
    }
    
    public AlphaComposite makeComposite(float alpha) { //For transparent images
  	  	int type = AlphaComposite.SRC_OVER;
  	  	return(AlphaComposite.getInstance(type, alpha));
    }
    
    public Main_Game(){
    	addMouseListener( this );
	    addMouseMotionListener( this );
		setPreferredSize(new Dimension(win_w,win_h));
		
		b_init(mode);
		
		Thread mthread = new Thread(){
			public void run(){ 
				
		    	while(true){ //Pretty much what happens every tick of the game
		    		//main block of code will be here!
		    		
		    		for (int i=0;i<bulist.size();i++){
	    				bulist.get(i).cselect(x,y); //enables buttons to be selected
	    			}
		    		if (mode.equals("start")){
		    			
		    		}
		    		if (mode.equals("game")){
		    			if (base.gethp()<=0){
		    				mode="start";
		    				b_init(mode);
		    			}
		    			
		    			if (tselect.isEmpty()==false){
		    				if (MC.checkinter(tselect.get(0),tlist)==false && base.cash()>=tselect.get(0).getcost()){
		    					canplace=true;
		    				}
		    				else{
		    					canplace=false;
		    				}
		    				tselect.get(0).translateto(x-tselect.get(0).getrax(),y-tselect.get(0).getray());
		    			}

			    		
			    		
			    		if (y>575 && inwin == true && shift<600){
			    			shift+=8;
			    		}
			    		else if (y>550 && inwin == true && shift<600){
			    			shift+=2;
			    		}
			    		if (y<25 && inwin == true && shift>0){
			    			shift-=8;
			    		}
			    		else if (y<50 && inwin == true && shift>0){
			    			shift-=2;
			    		}
			    		
			    		if (timer % 30 == 0 && timer > 300){ // be sure to increment unum by 1 or else game will be broken...
			    			ulist.add(new Grunt(unum,rand.nextInt(600),0-shift,0,5));
			    			unum+=1;
			    			ulist.add(new Grunt(unum,rand.nextInt(600),0-shift,0,5));
			    			unum+=1;
			    			//ulist.add(new Grunt(unum,rand.nextInt(600),0-shift,0,6));
			    			//unum+=1;
			    			
			    		}
			    		if (timer % 47 == 5 &&  timer > 500){
			    			ulist.add(new Sgrunt(unum,rand.nextInt(600),0-shift,0,8));
			    			unum+=1;
			    		}
			    		if (timer % 100 == 5 &&  timer > 700 || timer % 100 == 20 && timer >700){
			    			ulist.add(new Sgrunt(unum,rand.nextInt(600),0-shift,0,8));
			    			unum+=1;
			    			ulist.add(new Sgrunt(unum,rand.nextInt(600),0-shift,0,8));
			    			unum+=1;
			    			ulist.add(new Sgrunt(unum,rand.nextInt(600),0-shift,0,8));
			    			unum+=1;
			    			ulist.add(new Sgrunt(unum,rand.nextInt(600),0-shift,0,8));
			    			unum+=1;
			    			ulist.add(new Sgrunt(unum,rand.nextInt(600),0-shift,0,8));
			    			unum+=1;
			    			ulist.add(new Sgrunt(unum,rand.nextInt(600),0-shift,0,8));
			    			unum+=1;
			    			ulist.add(new Sgrunt(unum,rand.nextInt(600),0-shift,0,8));
			    			unum+=1;
			    			ulist.add(new Sgrunt(unum,rand.nextInt(600),0-shift,0,8));
			    			unum+=1;
			    		}
			    		
			    		for (int i=0;i<tlist.size();i++){
			    			try{
			    				if (tlist.get(i).gettlist().isEmpty() == false && tlist.get(i).gettlist().get(0)!=null){
			    					int ttr;
			    					try{
			    						ttr=(int) Math.round(Math.toDegrees(-Math.atan2(-tlist.get(i).getx()+MC.idtounit(tlist.get(i).gettlist().get(0),ulist).getx(), -tlist.get(i).gety()+MC.idtounit(tlist.get(i).gettlist().get(0),ulist).gety())+135));
			    					}
			    					catch (NullPointerException e){
			    						ttr=0;
			    					}
			    					
			    					tlist.get(i).setr(ttr);
			    					tlist.get(i).fire(timer, ulist, tblist);
			    				}
			    				
			    			}
			    			catch (IndexOutOfBoundsException e){
			    				//tlist.get(i).setr(0); we don't need to reset!
			    			}
			    			
			    			
			    			tlist.get(i).translate(0,pshift-shift);
			    			for (int i2=0;i2<ulist.size();i2++){
			    				if (MC.distance(tlist.get(i),ulist.get(i2))<=tlist.get(i).getsr() && MC.containsid2(ulist.get(i2).getid(),tlist.get(i).gettlist())==false){
			    					tlist.get(i).addtarget(ulist.get(i2).getid());
			    				}
			    			}
			    			for (int i2=0;i2<tlist.get(i).gettlist().size();i2++){
			    				try{
			    					if (MC.distance(tlist.get(i),MC.idtounit(tlist.get(i).gettlist().get(i2),ulist)) > tlist.get(i).getsr()){
				    					tlist.get(i).gettlist().remove(tlist.get(i).gettlist().get(i2));
				    					break;
				    				}
			    				}
			    				catch (NullPointerException e){
			    					break;
			    				}
			    				
			    			}
			    			if (tlist.get(i).gethp()<=0){
			    				for (int i2=0;i2<ulist.size();i2++){
			    					if (ulist.get(i2).gettlist().contains(tlist.get(i).getid())){
			    						ulist.get(i2).gettlist().remove(tlist.get(i).getid());
			    					}
			    				}
			    				tlist.remove(tlist.get(i));
			    				ms=null;
			    				break;
			    			}
		    			}
			    		for (int i=0;i<tblist.size();i++){
			    			tblist.get(i).move();
			    			if (tblist.get(i).gety()+shift>1200 || tblist.get(i).gety()+shift<0 || tblist.get(i).getx()>600 || tblist.get(i).getx()<0){
			    			//if (tblist.get(i).getx()>600 || tblist.get(i).getx()<0 || tblist.get(i).gety()>600 || tblist.get(i).getx()>600){
			    				tblist.remove(tblist.get(i));
			    				break;
			    			}
			    			//if (tblist.get(i).)
			    			for (int i2=0;i2<ulist.size();i2++){
			    				if (MC.intersect(ulist.get(i2).getbox().getbox(), tblist.get(i).getbox().getbox())==true){
			    					ulist.get(i2).sethp(-tblist.get(i).getatk());
			    					if (tblist.get(i).getpierce()==false){
			    						tblist.remove(tblist.get(i));
					    				break;
			    					}
			    					
			    				}
			    			}
			    			
			    		}
			    		for (int i=0;i<eblist.size();i++){
			    			eblist.get(i).move();
			    			if (eblist.get(i).gety()+shift>1200 || eblist.get(i).gety()+shift<0){
			    			//if (tblist.get(i).getx()>600 || tblist.get(i).getx()<0 || tblist.get(i).gety()>600 || tblist.get(i).getx()>600){
			    				eblist.remove(eblist.get(i));
			    				break;
			    			}
			    			//if (tblist.get(i).)
			    			for (int i2=0;i2<tlist.size();i2++){
			    				if (MC.intersect(tlist.get(i2).getbox().getbox(), eblist.get(i).getbox().getbox())==true){
			    					tlist.get(i2).sethp(-eblist.get(i).getatk());
			    					if (eblist.get(i).getpierce()==false){
			    						eblist.remove(eblist.get(i));
					    				break;
			    					}
			    					
			    				}
			    			}
			    			
			    		}
			    		for (int i=0;i<ulist.size();i++){ //please fix all the hardcoding
			    			ulist.get(i).move();
			    			
			    			//int ttr=(int) Math.round(Math.toDegrees(-Math.atan2(-ulist.get(i).getx()+ulist.get(i).getx()+ulist.get(i).getmx(), -ulist.get(i).gety()+MC.idtotower(ulist.get(i).gettlist().get(0),tlist).gety())+135));;
			    			int ttr=(int) Math.round(Math.toDegrees(-Math.atan2(ulist.get(i).getmx(),ulist.get(i).getmy())+135));
			    			try{
			    				if (ulist.get(i).gettlist().size()>0){
			    					ttr=(int) Math.round(Math.toDegrees(-Math.atan2(-ulist.get(i).getx()-ulist.get(i).getrax()+MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getx()+MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getrax(), -ulist.get(i).gety()-ulist.get(i).getray()+MC.idtotower(ulist.get(i).gettlist().get(0),tlist).gety()+MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getray())+135));
			    				}
	    						
	    					}
	    					catch (NullPointerException e){
	    						ttr=(int) Math.round(Math.toDegrees(-Math.atan2(ulist.get(i).getmx(),ulist.get(i).getmy())+135));
	    					}
			    			
			    			//the following if, else if and else statements should really only apply to melee enemies
			    			if (ulist.get(i).getname().equals("Grunt")){
			    				if (ulist.get(i).gettlist().size()>0 && MC.intersect(ulist.get(i).getbox().getbox(),MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getbox().getbox())==false){
				    				if (!ulist.get(i).gettlist().get(0).equals("Base")){
				    					int tx=MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getx()+MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getrax();
				    					int ty=MC.idtotower(ulist.get(i).gettlist().get(0),tlist).gety()+MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getray();
				    					double tmx=MC.aim(ulist.get(i).getx()+ulist.get(i).getrax(),ulist.get(i).gety()+ulist.get(i).getray(),tx,ty,5)[0];
				    					double tmy=MC.aim(ulist.get(i).getx()+ulist.get(i).getrax(),ulist.get(i).gety()+ulist.get(i).getray(),tx,ty,5)[1];  
				    					
				    					ulist.get(i).setmx(tmx);
					    				ulist.get(i).setmy(tmy);
				    				}
				    				
				    				
				    				
				    			}
				    			else if (ulist.get(i).gettlist().size()>0 && MC.intersect(ulist.get(i).getbox().getbox(),MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getbox().getbox())==true){
				    				ulist.get(i).setmx(0);
				    				ulist.get(i).setmy(0); 
				    				if (timer%3==0){
				    					MC.idtotower(ulist.get(i).gettlist().get(0),tlist).sethp(-ulist.get(i).getatk());
				    				}
				    				
				    			}
				    			else{
				    				ulist.get(i).setmx(0);
				    				ulist.get(i).setmy(5);
				    			}
			    			}
			    			else if (ulist.get(i).getname().equals("Sgrunt")){
			    				
			    				if (ulist.get(i).gettlist().size()>0 && MC.distance(ulist.get(i).getrx(),ulist.get(i).getry(),MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getrx(),MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getry()) >ulist.get(i).getar()){
				    				//if (!ulist.get(i).gettlist().get(0).equals("Base")){
				    					int tx=MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getx()+MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getrax();
				    					int ty=MC.idtotower(ulist.get(i).gettlist().get(0),tlist).gety()+MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getray();
				    					double tmx=MC.aim(ulist.get(i).getx()+ulist.get(i).getrax(),ulist.get(i).gety()+ulist.get(i).getray(),tx,ty,5)[0];
				    					double tmy=MC.aim(ulist.get(i).getx()+ulist.get(i).getrax(),ulist.get(i).gety()+ulist.get(i).getray(),tx,ty,5)[1];  
				    					
				    					ulist.get(i).setmx(tmx);
					    				ulist.get(i).setmy(tmy);
				    				//}
				    				
				    				
				    				
				    			}
				    			else if (ulist.get(i).gettlist().size()>0 && MC.distance(ulist.get(i).getrx(),ulist.get(i).getry(),MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getrx(),MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getry()) <=ulist.get(i).getar()){
				    				ulist.get(i).setmx(0);
				    				ulist.get(i).setmy(0); 
				    				if (timer%7==0){
				    					//MC.idtotower(ulist.get(i).gettlist().get(0),tlist).sethp(-ulist.get(i).getatk());
				    					double aimx=MC.aim(ulist.get(i).getrx()   ,ulist.get(i).getry()   ,MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getrx(),MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getry(),10)[0];
				    					double aimy=MC.aim(ulist.get(i).getrx()   ,ulist.get(i).getry()   ,MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getrx(),MC.idtotower(ulist.get(i).gettlist().get(0),tlist).getry(),10)[1];
				    					
				    					//int tttr=(int) Math.round(Math.toDegrees(-Math.atan2(-x+MC.idtounit(targetlist.get(0),ulist).getx(), -y+MC.idtounit(targetlist.get(0),ulist).gety())+135));
				    					int tttr=(int) Math.round(Math.toDegrees(-Math.atan2(aimx, aimy)+135));
				    					eblist.add(new Eb1(ulist.get(i).getrx(),ulist.get(i).getry(),aimx,aimy,i,tttr));
				    				}
				    				
				    			}
				    			else{
				    				ulist.get(i).setmx(0);
				    				ulist.get(i).setmy(8);
				    			}
			    			}
			    			
			    			ulist.get(i).setr(ttr);
			    			//ulist.get(i).setr((int) Math.round(Math.toDegrees(-Math.atan2(-ulist.get(i).getx()+x, -ulist.get(i).gety()+y)+135)));
			    			
			    			ulist.get(i).translate(0,pshift-shift);
			    			
			    			
			    			
			    			/* here's the exciting portion where i code the enemy's behavior!*/
			    			if (ulist.get(i).bhv().equals("normal")){
			    				//if (MC.distance(ulist.get(i).getrx(),ulist.get(i).getry(),base.getrx(),base.getry())>ulist.get(i).getsr()){
			    					for (int i2=0;i2<tlist.size();i2++){
				    					if (MC.distance(tlist.get(i2),ulist.get(i))<=ulist.get(i).getsr() && MC.containsid2(tlist.get(i2).getid(),ulist.get(i).gettlist())==false && tlist.get(i2).gety()>ulist.get(i).gety()){
					    					ulist.get(i).addtarget(tlist.get(i2).getid());
					    				}
				    				}
				    				for (int i2=0;i2<ulist.get(i).gettlist().size();i2++){
					    				try{
					    					if (MC.distance(MC.idtotower(ulist.get(i).gettlist().get(i2),tlist),ulist.get(i)) > ulist.get(i).getsr()){
						    					ulist.get(i).gettlist().remove(ulist.get(i).gettlist().get(i2));
						    					break;
						    				}
					    				}
					    				catch (NullPointerException e){
					    					break;
					    				}
					    				
					    			}
			    				//}
			    				//else{
			    				//	ulist.get(i).gettlist().add(0,base.getid());
			    			///	}
			    				
			    				
			    			}
			    			/**/
			    			
			    			
			    			
			    			if (ulist.get(i).gety()+shift>1250 || ulist.get(i).gethp()<=0){
			    				for (int i2=0;i2<tlist.size();i2++){
			    					if (tlist.get(i2).gettlist().contains(ulist.get(i).getid())){
			    						tlist.get(i2).gettlist().remove(ulist.get(i).getid());
			    					}
			    				}
			    				if (ulist.get(i).gethp()<=0){
			    					base.givecash(ulist.get(i).getcost());
			    				}
			    				else{
			    					base.sethp(-ulist.get(i).gethp());
			    				}
			    				exlist.add(new Explosion(ulist.get(i).getx()-53,ulist.get(i).gety()-53,53,10,timer,0,"explosions/exp1.gif",false));
			    				ulist.remove(ulist.get(i));
			    				break;
			    			}
		    			}
			    		for (int i=0;i<exlist.size();i++){
			    			
			    			if (exlist.get(i).gethe()==false){
			    				for (int i2=0;i2<tlist.size();i2++){
			    					if (MC.intersect(exlist.get(i).getbox().getbox(),tlist.get(i2).getbox().getbox())==true){
			    						tlist.get(i2).sethp(-exlist.get(i).getdmg());
			    					}
			    				}
			    			}
			    			else{
			    				for (int i2=0;i2<ulist.size();i2++){
			    					if (MC.intersect(exlist.get(i).getbox().getbox(),ulist.get(i2).getbox().getbox())==true){
			    						ulist.get(i2).sethp(-exlist.get(i).getdmg());
			    					}
			    				}
			    			}
			    			
			    			if (timer-exlist.get(i).getts()>=exlist.get(i).getls()){
			    				exlist.remove(i);
			    				break;
			    			}
			    		}
			    		
			    		base.translate(0,pshift-shift);
			    		
			    		pshift=shift;
		    		}
		    		
		    		
		    		
		    		repaint();
		    		if (pause==false){
		    			timer+=1;
		    		}
		    		else{
		    			timer+=0;
		    		}
		    		//base.sethp(-10);
		    		try{
		            	Thread.sleep(1000/update_rate);
		            }
		            catch(InterruptedException e){
		            	JOptionPane.showMessageDialog(null,"Please don't interrupt me while sleeping !","Disturbed",JOptionPane.ERROR_MESSAGE);
		            }
		    	}
			}
		};
        mthread.start();//start the thread's execution
    }
    
	public void mouseDragged(MouseEvent e) {
		x=e.getX();
		y=e.getY();
	}
	public void mouseMoved(MouseEvent e) {
		x=e.getX();
		y=e.getY();
	}
	public void mouseClicked(MouseEvent e) {
		for (int i=0;i<bulist.size();i++){
			if (bulist.get(i).geto()==true && bulist.get(i).getn().equals("switch")){
				mode=bulist.get(i).selects();
				b_init(mode);
			}
			if (bulist.get(i).geto()==true && bulist.get(i).getn().equals("tower") && tselect.isEmpty()==true && ms==null){
				tt=bulist.get(i).getii();
				tselect.add(bulist.get(i).stower());
			}
			if (bulist.get(i).geto()==true && bulist.get(i).getn().equals("sell") && ms!=null){
				for (int i2=0;i2<tlist.size();i2++){
					if (tlist.get(i2).getid().equals(ms.getid())){
						for (int i3=0;i3<ulist.size();i3++){
							if (ulist.get(i3).gettlist().contains(tlist.get(i2).getid())){
								ulist.get(i3).gettlist().remove(tlist.get(i2).getid());
							}
						}
						tlist.remove(tlist.get(i2));
					}
				}
				
				base.givecash(7*(ms.getcost()/10));
				ms=null;
			}
		}
		if (tselect.isEmpty()==false){
			if (canplace==true && x<560){
				if (tt==0){
					Tower ta=new Shooter(tnum, timer);
					//if (ta.getcost()<=base.cash()){
						base.givecash(-ta.getcost());
						ta.translateto(x-ta.getrax(),y-ta.getray());
						tlist.add(ta);
					//}
					//else{
						//ta=null;
					//}
				}
				else if (tt==1){
					Tower ta=new Twinlaser(tnum, timer);
					//if (ta.getcost()<=base.cash()){
						base.givecash(-ta.getcost());
						ta.translateto(x-ta.getrax(),y-ta.getray());
						tlist.add(ta);
				}
				tnum+=1;
				tselect.clear();
			}
			if (x>560 && y>500){
				tselect.clear();
			}
			
			//change the tower added according to different values of tt
			
		}
		//else if (tselect.isEmpty()==false && canplace==false){
			//tselect.clear();
		//}
		
		if (ms==null && MC.checkinter(x,y,tlist)!=null && tselect.isEmpty()==true){
			ms=MC.checkinter(x,y,tlist);
		}
		if (ms!=null && MC.checkinter(x,y,tlist)==null){
			ms=null;
		}
		
	}
	public void mouseEntered(MouseEvent e) {
		inwin=true;
	}
	public void mouseExited(MouseEvent e) {
		inwin=false;
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {	
	}
    
	public void paintComponent(Graphics g){                                                                                    
		//This is pretty much just, paint everything in the arraylists
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D)g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0,0,win_w,win_h);
		if (mode.equals("start")){
			g2d.drawImage(s1,0,0,null);
			g2d.drawImage(stext,40,0,null);
			
			for (int i=0;i<bulist.size();i++){
				bulist.get(i).draw(g2d);
			}
		}
		if (mode.equals("game")){
			g2d.setColor(Color.YELLOW);
			g2d.drawImage(bg1,0,0-shift,null);
			
			//all the fillpolygons are for viewing the hitbox, and thus for debug purposes
			for (int i=0;i<ulist.size();i++){
				ulist.get(i).draw(g2d);
				g2d.fillPolygon(ulist.get(i).getbox().getbox());
			}
			for (int i=0;i<tlist.size();i++){
				tlist.get(i).draw(g2d);
				//g2d.drawString(tlist.get(i).getid(),tlist.get(i).getx(),tlist.get(i).gety()-8);
				g2d.fillPolygon(tlist.get(i).getbox().getbox());
			}
			for (int i=0;i<eblist.size();i++){
				eblist.get(i).draw(g2d);
			}
			for (int i=0;i<tblist.size();i++){
				tblist.get(i).draw(g2d);
				//g2d.setColor(Color.YELLOW);
				g2d.fillPolygon(tblist.get(i).getbox().getbox());
			}
			for (int i=0;i<exlist.size();i++){
    			exlist.get(i).draw(g2d,timer);
    		}
			//base.draw(g2d);
			
			
			g2d.setColor(c1);
		    g2d.fillRect(600, 0, 200, 600);
		    g2d.setColor(c2);
		    g2d.fillRect(600,0,200,180);
		    
		    for (int i=0;i<bulist.size();i++){
		    	if (ms==null && bulist.get(i).getn().equals("tower")){
		    		bulist.get(i).draw(g2d);
		    	}
		    	
		    	if (ms!=null && bulist.get(i).getn().equals("sell")){
		    		bulist.get(i).draw(g2d);
		    		g2d.setFont(f4);
		    		g2d.setColor(Color.BLACK);
		    		//if (tselect.isEmpty()==false){
		    		g2d.drawString(Integer.toString(7*ms.getcost()/10),730,550);
		    		//}
		    		
		    	}
		    	if (bulist.get(i).getn().equals("switch")){
		    		bulist.get(i).draw(g2d);
		    	}
				
				
				
			}
		    
			
			if (tselect.isEmpty()==false){
				if (canplace==true){
		    		 g2d.setColor(c2);
		    	}
		    	else{
		    		g2d.setColor(c3);
		    	}
				tselect.get(0).draw(g2d);
				g2d.fillOval(tselect.get(0).getrx()-tselect.get(0).getsr(),tselect.get(0).getry()-tselect.get(0).getsr(),tselect.get(0).getsr()*2,tselect.get(0).getsr()*2);
				g2d.setColor(c3);
				g2d.fillRect(620,500,160,80);
				g2d.setFont(f4);
				g2d.setColor(Color.BLACK);
		        g2d.drawString("Put away tower", 640, 530);
				
			}
			
			g2d.setColor(Color.RED);
			g2d.drawString("Timer    "+Integer.toString(timer),50,50);
			g2d.setFont(f4);
			g2d.setColor(Color.BLACK);
			g2d.drawString("Round:  "+Integer.toString(round), 610, 30);
	        g2d.drawString("Cash:  $"+Integer.toString(base.cash()), 610, 60);
	        g2d.drawString("Base hp  :"+Integer.toString(base.gethp()), 610, 90);
			
			if (ms!=null){
				g2d.setColor(c2);
				g2d.fillOval(ms.getrx()-ms.getsr(),ms.getry()-ms.getsr(),ms.getsr()*2,ms.getsr()*2);
				g2d.setColor(Color.BLACK);
				g2d.drawString("Health:    "+Integer.toString(ms.gethp()), 610, 220);
			}
			g2d.setColor(Color.RED);
			
			
		}
		
		repaint();
		
	}
	
	public static void main(String[] args) {
		// almost all relevant methods are included within MC.java
		// MC stands for Method Class, otherwise this file would be HUGE
		// like, MOUNTAINS AND MOUNTAINS OF CODE
		
		//this is just to add the game into a jframe.... that's really it
		JFrame jr=new JFrame("Main window");
		Main_Game the_main=new Main_Game();
		jr.add(the_main);
		jr.setVisible(true);
		jr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jr.pack();
	}
	

}