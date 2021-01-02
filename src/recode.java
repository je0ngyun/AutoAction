import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.Timer;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

class recode implements NativeKeyListener,NativeMouseListener{

	/*flag*/
	/* 1.key press event
	 * 2.key release event
	 * 3.mouse press event
	 * 4.mouse release event
	 * 5.time delay
	 * */

	mainFrame frame = mainFrame.getInstance();
	macro macroInstance = macro.getInstance();
    private double time = 0;
    private double limit; //timedelay
    private Boolean delay; //timedelay use flag
    private Boolean flag = true; //Global listener use flag
    Timer timer;

	recode(Boolean delay,double limit){
		 LogManager.getLogManager().reset();
		 Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		 logger.setLevel(Level.OFF);
		 startListener();
		 this.delay = delay;
		 this.limit = limit;
		 if(delay) {
		    timer = new Timer(100, e->increase());
		    timer.start();
		 }
	}
	
	//time delay measure
    public void increase() {
    	if(flag) {
    	  time = time+0.1;
    	}
    }

	public void nativeMousePressed(NativeMouseEvent e) {
		if(flag) {
		    value v;
		    if(time>limit) {
	           v = new value(5,time);
		       macroInstance.adddata(v);
		    }
		    v = new value(3,e.getX(),e.getY());
		    macroInstance.adddata(v);
		    time = 0;
		}
	}
	
	public void nativeMouseReleased(NativeMouseEvent e) {
		if(flag) {
		    value v;
		    if(time>limit) {
		       v = new value(5,time);
			   macroInstance.adddata(v);
		    }
		    v = new value(4,e.getX(),e.getY());
		    macroInstance.adddata(v);
		    time = 0;
		}
	}
	
	public void nativeKeyPressed(NativeKeyEvent e) {
		if(flag) {
		  //recode stop condition(F8key)
		  if(e.getRawCode() == 119) {
		  	  flag = false;
			  if(delay) {
				  timer.stop();
			  }
			  frame.setState(frame.NORMAL);
			  frame.createList();
			  frame.flagTotrue();
		  }
		  else {
			  value v;
			  if(time>limit) {
			     v = new value(5,time);
				 macroInstance.adddata(v);
			  }
		      v = new value(1,e.getRawCode(),e.getKeyText(e.getKeyCode()));
		      macroInstance.adddata(v);
		      time = 0;
		  }
		}
	}
	
	public void nativeKeyReleased(NativeKeyEvent e) {
		if(flag) {
		  value v;
			 if(time>limit) {
			   v = new value(5,time);
			   macroInstance.adddata(v);
			 }
		  v = new value(2,e.getRawCode(),e.getKeyText(e.getKeyCode()));
		  macroInstance.adddata(v);
		  time = 0;
		}
	}
	public void nativeKeyTyped(NativeKeyEvent e) {}
	public void nativeMouseClicked(NativeMouseEvent e) {}


	/*Global Listener Registration*/
	public void startListener() {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
		GlobalScreen.addNativeMouseListener(this);
		GlobalScreen.addNativeKeyListener(this);
	}

}