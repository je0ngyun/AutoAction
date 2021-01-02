import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

class mainFrame extends JFrame implements NativeKeyListener{
	 private static mainFrame mainFrame = new mainFrame();
	 DefaultListModel<value>model = new DefaultListModel<value>();
	 recode rec;
	 macro macroInstance = macro.getInstance();
	 
	 JPanel panel; JButton load; JButton save;
	 JButton option; JButton del; JButton init; 
	 JLabel label1; JLabel label2;
	 JList list = new JList(model);
	 
	 private Boolean flag = true; //글로벌 키 리스너 관리용
	 private Boolean delay = true;//시간지연 사용 여부 
	 double timedelay = 0.1;//시간지연 사용시 defalut 값
	   
     private mainFrame(){
    	
       setTitle("AutoAction");
  	   setSize(310,390);
  	   setLocationRelativeTo(null);//창을 화면중앙에 생성
  	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  	   setResizable(false);//창크기 조절 불가

  	   //컴포넌트 생성
  	   panel = new JPanel(); panel.setLayout(null); 
  	   load = new JButton("불러오기"); load.setSize(82, 25); load.setLocation(197, 70);
  	   save = new JButton("저장하기");  save.setSize(82, 25);  save.setLocation(197, 110);
  	   option = new JButton("기록옵션"); option.setSize(82, 25);  option.setLocation(197, 150);
  	   del = new JButton("기록삭제"); del.setSize(82, 25); del.setLocation(197, 190);
  	   init = new JButton("초기화"); init.setSize(82, 25); init.setLocation(197, 230);
  	   label1 = new JLabel("● 기록시작(F7)    ▶ 매크로시작(F9)");
  	   label1.setSize(300,30); label1.setLocation(20, 10);
  	   label1.setFont(new Font("맑은고딕",Font.BOLD,14));
  	   label2 = new JLabel("■ 기록종료(F8)    ■ 매크로종료(F10)");
	   label2.setSize(300,35); label2.setLocation(21, 28);
	   label2.setFont(new Font("맑은고딕",Font.BOLD,14));
	   
  	   JScrollPane scrollPane = new JScrollPane(list);
  	   scrollPane.setPreferredSize(new Dimension(170,280));
  	   scrollPane.setLocation(15, 70);
  	   scrollPane.setSize(170, 260);
  	   panel.add(scrollPane); panel.add(load);  panel.add(save); panel.add(option);
       panel.add(del); panel.add(init); panel.add(label1); panel.add(label2);
  	   add(panel);
       
  	   //컴포넌트 이벤트 등록
  	   load.addActionListener(new evListener());
	   save.addActionListener(new evListener());
	   option.addActionListener(new evListener());
	   del.addActionListener(new evListener());
	   init.addActionListener(new evListener());
	   
	   //Log Off
	   LogManager.getLogManager().reset();
	   Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
	   logger.setLevel(Level.OFF);
	   
	   start_NativeListener();
  	   setVisible(true);

     }//end 생성자
     
     public static mainFrame getInstance() { 
    	 return mainFrame;
     }
     
     //Jlist목록 생성
     public void createList() {
    	 for(Object o : macroInstance.getvec()) {
			model.addElement((value) o);
		 }
     }
     
     //메크로 시작 스레드
     class StartMacro implements Runnable{
    	 public void run() {
    		 macroInstance.run();
    	 }
     }
     
     //메크로 종료 스레드
     class StopMacro implements Runnable{
    	 public void run() {
    		macroInstance.loopTofalse();
    	 }
     }
     
     //시작가능 상태로 만듬
     public void flagTotrue() {
    	 flag = true;
    	 rec = null;
     }
     
     //NativeKeylistenr 등록
     public void start_NativeListener() {
  		try {
  			GlobalScreen.registerNativeHook();
  		} catch (NativeHookException e) {
  			e.printStackTrace();
  		}
  		GlobalScreen.addNativeKeyListener(this);
  	 }
     
     //컴포넌트 이벤트처리
     class evListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == load) {
				model.removeAllElements();
				macroInstance.delAll();
				fileIO file = new fileIO(1);
			}
			if(e.getSource() == save) {
				fileIO file = new fileIO(2);
			}
			if(e.getSource() == option) {
				optionform opt = new optionform(mainFrame,"Option",true);
			}
			if(e.getSource() == del) {
			   int index = list.getSelectedIndex();
			    model.remove(index);
			    macroInstance.deldata(index);
			}
			if(e.getSource() == init) {
				model.removeAllElements();
				macroInstance.delAll();
			}
		}
     }
     //옵션창
     class optionform extends JDialog{
    	  optionform(mainFrame frame,String title,Boolean flag){
    		  super(frame,title,flag);
    		  setSize(200,150);
    		  setLayout(null);
    		  setLocationRelativeTo(null);
    	      setResizable(false);
    		  
    		  JTextField field = new JTextField(5);
    		  JLabel msg = new JLabel("시간지연 사용");
    		  JLabel msg2 = new JLabel("시간지연(초)");
    	      JButton confirm = new JButton("OK");
    	      Checkbox check = new Checkbox("",delay);
    	      
    	      msg.setSize(82,25); msg.setLocation(37,10);
    	      msg2.setSize(82,25); msg2.setLocation(37,40);
    	      confirm.setSize(75,20); confirm.setLocation(55, 80);
    	      field.setSize(30,20); field.setLocation(111,40);
    	      field.setText(timedelay +"");
    	      check.setSize(40,30); check.setLocation(120,8);
    	      add(msg); add(confirm); add(field); add(check); add(msg2);
    	      
    	      confirm.addActionListener(e ->{
    	    	  frame.delay = check.getState();
    	    	  frame.timedelay = Double.valueOf(field.getText());
    	    	  dispose();
    	      });     
    	      setVisible(true);
    	  }
     }
   
    //키 이벤트 처리
	public void nativeKeyPressed(NativeKeyEvent e) {
		if(flag) {
		  //기록시작 F8
		  if(e.getRawCode() == 118) {
			  mainFrame.setState(mainFrame.ICONIFIED);//창 최소화
			  flag = false;
			  rec = new recode(delay, timedelay);
			 }	
		  //매크로 시작 F9키	
		  if(e.getRawCode() == 120) {
			  mainFrame.setState(mainFrame.ICONIFIED);//창 최소화
			  Thread Startmacro = new Thread(new StartMacro());
			  Startmacro.start();
		  }
		  //매크로 종료 F10키
		  if(e.getRawCode() == 121) {
			  Thread Stopmacro = new Thread(new StopMacro());
			  Stopmacro.start();
		  }
		}
	}
	public void nativeKeyReleased(NativeKeyEvent e) {}
	public void nativeKeyTyped(NativeKeyEvent e) {}
}
