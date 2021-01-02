import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;

class fileIO {
	macro macroInstance = macro.getInstance();
	mainFrame frame = mainFrame.getInstance();
	// ¼ø¼­ int flag,int x,int y,int keycode,double time,String keychar

	fileIO(int flag) {
		if (flag == 1) { // file load
			JFileChooser jfc = new JFileChooser();
			jfc.showOpenDialog(null);
			File file = jfc.getSelectedFile();
			try {
				Scanner scan = new Scanner(file);
				while (scan.hasNext()) {
					int V_flag = scan.nextInt();
					int x = scan.nextInt();
					int y = scan.nextInt();
					int keycode = scan.nextInt();
					double time = scan.nextDouble();
					String keychar = scan.nextLine();
					value value = new value(V_flag, x, y, keycode, time, keychar);
					macroInstance.adddata(value);
				}
				frame.createList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (flag == 2) {// file save
			JFileChooser jfc = new JFileChooser();
			jfc.setSelectedFile(new File("C:\\macro.m"));
			jfc.showSaveDialog(null);
			File file = jfc.getSelectedFile();
			try {
				PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)), true);
				for (Object value : macroInstance.getvec()) {
					printWriter.print(((value) value).getflag());
					printWriter.print(" ");
					printWriter.print(((value) value).getX());
					printWriter.print(" ");
					printWriter.print(((value) value).getY());
					printWriter.print(" ");
					printWriter.print(((value) value).getkeycode());
					printWriter.print(" ");
					printWriter.print(((value) value).gettime());
					printWriter.print(" ");
					printWriter.println(((value) value).getkeychar());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
