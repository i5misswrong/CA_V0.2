package CA;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JFrame;
public class DrawFirst extends JFrame {
	//创建窗口类
	//初始化类
	Data data=new Data();
	DrawSecond ds=null;
	public DrawFirst(){//构造方法
		ds=new DrawSecond();//初始化DS
		Thread t=new Thread(ds);//创建DS线程
		t.start();//启动线程
		this.setTitle("CA");//设置窗口标题
		this.setVisible(true);//设置窗口可见
		this.setSize(data.fw,data.fw);//设置窗口大小
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭按钮
		this.addWindowListener(new WindowAdapter() {//关闭按钮的作用
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            } 
        });
		this.add(ds);//将DS添加到DF
	}
}


