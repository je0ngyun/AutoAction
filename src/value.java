
class value {

	/*flag*/
	/* 1.key press event
	 * 2.key release event
	 * 3.mouse press event
	 * 4.mouse release event
	 * 5.time delay
	 * */

   private int x = 0;
   private int y = 0;
   private int flag = 0;
   private int keycode = 0;
   private String keychar = "0";
   private double time = 0;
   
   value(int flag,int keycode,String keychar){
	   this.flag = flag;
	   this.keycode = keycode;
	   this.keychar = keychar;
   }
   value(int flag,int x,int y){
	   this.flag = flag;
	   this.x = x;
	   this.y = y;
   }
   value(int flag,double time){
	   this.flag = flag;
	   this.time = time;
   }
   
   //Constructor for fileIO
   value(int flag,int x,int y,int keycode,double time,String keychar){
	   this.flag = flag;
	   this.x = x;
	   this.y = y;
	   this.keycode = keycode;
	   this.time = time;
	   this.keychar = keychar;
   }
   
   public int getflag() {
	  return flag;
   }
   public int getX() {
		  return x;
   }
   public int getY() {
		  return y;
   }
   public int getkeycode() {
		  return keycode;
   }
   public double gettime() {
	      return time;
   }
   public String getkeychar() {
	      return keychar;
   }

   public String toString() {
	   if(flag == 1) {
	  	 return "키보드"+keychar+"키 눌림";
	   }
	   if(flag == 2){
		 return "키보드"+keychar+"키 떼짐";
	   }
	   if(flag == 3) {
		 return "마우스 눌림"+"("+x+" , "+y+")";
	   }
	   if(flag == 4) {
		 return "마우스 떼짐"+"("+x+" , "+y+")";
	   }
	   //시간지연
	   return String.format("시간 지연 ( %.1f초 )",time);   
   }
}
