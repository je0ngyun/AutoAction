import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;


class macro { //singleton object
	public static macro macro = new macro();
	private Vector<value>info = new Vector<value>();
	Robot robot;
	private boolean loop = true;

	   /*flag*/
	   /* 1.key press event
	    * 2.key release event
	    * 3.mouse press event
	    * 4.mouse release event
	    * 5.time delay
	    * */

	public void run() {
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			System.out.print(e1.toString());
		}
		
		while(loop) {
	    try {
	    	for(value v : info) {
	    	   if(loop == false) break;
	    	   if(v.getflag() == 1) {//1.key press event make
	    		   int keycode = v.getkeycode();
	    		   switch(keycode) {
	    		   case 160 : robot.keyPress(16); //shift
	    		   break;
	    		   case 162 : robot.keyPress(17); //ctrl
	    		   break;
	    		   case 164 : robot.keyPress(18); //art
	    		   break;
	    		   case 13 : robot.keyPress(KeyEvent.VK_ENTER); //enter
	    		   break;
	    		   default : robot.keyPress(v.getkeycode());
	    		   }
	    	   } 
               if(v.getflag() == 2) {//2.key release event make
            	   int keycode = v.getkeycode();
	    		   switch(keycode) {
	    		   case 160 : robot.keyRelease(16); //shift
	    		   break;
	    		   case 162 : robot.keyRelease(17); //ctrl
	    		   break;
	    		   case 164 : robot.keyRelease(18); //art
	    		   break;
	    		   case 13 : robot.keyRelease(KeyEvent.VK_ENTER); //enter
	    		   break;
	    		   default : robot.keyRelease(v.getkeycode());
	    		   }
	    	   }
               if(v.getflag() == 3) {//3.mouse press event make
            	   robot.mouseMove(v.getX(),v.getY());
	    		   robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	    	   }
               if(v.getflag() == 4) {//4.mouse release event make
            	   robot.mouseMove(v.getX(),v.getY());
            	   robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	    	   }
               if(v.getflag() == 5) {//6.timedelay
            	   Thread.sleep((long) (v.gettime()*1000));    
	    	   }
	    	}
	    		
		} catch (Exception e) {
			e.printStackTrace();
		}
	    }
	}
    
	//매크로 종료후 재시작을 위해 1초뒤 다시 시작 flag를 true로 바꿈
	public void loopTofalse() {
		loop = false;
		try { 
			Thread.sleep(1000); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		loop = true;
	}
	public void adddata(value v) {
    	info.add(v);
    }
    public void deldata(int index) {
    	info.remove(index);
    }
    public void delAll() {
    	info.removeAllElements();
    }
    public Vector getvec() {
		return info;
    }
    public static macro getInstance() {
		return macro;
    }
}
