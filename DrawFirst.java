package CA;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JFrame;
public class DrawFirst extends JFrame {
	//����������
	//��ʼ����
	Data data=new Data();
	DrawSecond ds=null;
	public DrawFirst(){//���췽��
		ds=new DrawSecond();//��ʼ��DS
		Thread t=new Thread(ds);//����DS�߳�
		t.start();//�����߳�
		this.setTitle("CA");//���ô��ڱ���
		this.setVisible(true);//���ô��ڿɼ�
		this.setSize(data.fw,data.fw);//���ô��ڴ�С
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�رհ�ť
		this.addWindowListener(new WindowAdapter() {//�رհ�ť������
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            } 
        });
		this.add(ds);//��DS��ӵ�DF
	}
}


