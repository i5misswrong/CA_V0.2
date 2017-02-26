package CA;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Vector;

import javax.swing.JPanel;


public class DrawSecond extends JPanel implements Runnable{
	//���������� ������ˡ���ͼ�Ļ��ƺ��ػ� ������˵ĳ�ʼ��
	Data data=new Data();//��ʼ��������
	Vector<Peo> peoVector=new Vector<Peo>();//�������˼���  ��֤�̰߳�ȫ ����vector
	volatile int mapMat[][]=new int[data.w][data.w];//���������������� ʹ��volatile�����߳�
	public DrawSecond(){
		int peoNum=12;//��������
		int peoId=1;//����ID
		
//		---------------------------------
//		----�˴�Ϊ������ ���ض�λ�ò�������-----
//		//Peo peo1=new Peo(0,300);
//		//Peo peo2=new Peo(0,350);
//		Peo peo3=new Peo(0,400);
//		mapMat[8][0]=peoId;
//		peoId++;
//		Peo peo4=new Peo(50,350);
//		mapMat[7][1]=peoId;
//		peo3.setMapMat(mapMat);
//		peo4.setMapMat(mapMat);
//		//peo1.setPeople(peoVector);
//		//peo2.setPeople(peoVector);
//		peo3.setPeople(peoVector);
//		peo4.setPeople(peoVector);
//		
//		//Thread t1=new Thread(peo1);
//		//Thread t2=new Thread(peo2);
//		Thread t3=new Thread(peo3);
//		Thread t4=new Thread(peo4);
//		
//		//t1.start();
//		//t2.start();
//		t3.start();
//		t4.start();
//		
//		//peoVector.add(peo1);
//		//peoVector.add(peo2);
//		peoVector.add(peo3);
//		peoVector.add(peo4);
//		---------------------------------
//		-------------���������������-------------
		for (int i=0;i<peoNum;i++){
			int x=((int)(Math.random()*10)*data.pw);//��1-10���������  X pw�͵õ�ȷ������
			int y=((int)(Math.random()*10)*data.pw);
			Peo peo=new Peo(x,y);//�½�����
			mapMat[y/data.pw][x/data.pw]=peoId;//�������������ֵ Ψһ
			peoId++;//Id++
			peo.setMapMat(mapMat);//���������õ�peo��
			peo.setPeople(peoVector);//���������õ�peo��
			Thread t=new Thread(peo);//�����߳�
			t.start();//�����߳�
			peoVector.add(peo);//��������ӵ�����
		}
//		-----------------------------------
		
	}
	public void paint (Graphics g){//��д��ͼ����
		
		g.setColor(Color.WHITE);//������ɫ
		//super.paint(g);
		g.fillRect(0, 0, data.w,data.w);//�������
		this.drawMap(g);//���Ƶ�ͼ
//		----------�������ڳ���ʱ�Ķ���----------------
		for(int i=0;i<peoVector.size();i++){
			Peo peo=peoVector.get(i);//��ȡ����
			if(peo.isGoExit()){//���û�е������
				this.drawPeo(g, peo.getX(), peo.getY());//��������
			}
			else{
				//����������
				mapMat[peo.getY()/data.pw][peo.getX()/data.pw]=0;//��������������Ϊ0
				peoVector.remove(i);//�����˴Ӽ����Ƴ�
			}
		}
	}
	public void drawMap(Graphics g){
		//���Ƶ�ͼ����
		//����ע��Ϊ  ���λ�ڴ����м�  ���Ͻ����������·�ƽ��50px
		//g.fillRect(data.fwx, data.fwx, data.w, data.w);
//		g.setColor(Color.black);
//		for (int i=1;i<(data.w/data.pw);i++){
//			g.drawLine(data.pw*i+data.fwx, data.fwx, data.pw*i+data.fwx, data.w+data.fwx);
//		}
//		for (int i=1;i<(data.w/data.pw);i++){
//			g.drawLine(data.fwx, data.pw*i+data.fwx, data.w+data.fwx, data.pw*i+data.fwx);
//		}
//		Graphics2D g2=(Graphics2D)g;
//		Stroke stroke=new BasicStroke(5.0f);//�����߿�Ϊ3.0
//		g2.setStroke(stroke);
//		g2.drawLine(data.fwx,data.fwx, data.fwx,data.fwx+data.w);
//		g2.drawLine(data.fwx,data.fwx+data.w, data.fwx+data.w,data.fwx+data.w);
//		g2.drawLine(data.fwx+data.w,data.fwx, data.fwx+data.w,data.fwx+data.w);
//		g2.drawLine(data.fwx,data.fwx, data.fwx+data.exitX,data.fwx);
//		g2.drawLine(data.fwx+data.exitX+data.exitL,data.fwx, data.fwx+data.w,data.fwx);
		g.setColor(Color.black);
		//�����м�����
		for (int i=1;i<(data.w/data.pw);i++){//����
			g.drawLine(data.pw*i, 0, data.pw*i, data.w);//ÿ��pw �����һ������ 
		}
		for (int i=1;i<(data.w/data.pw);i++){//����
			g.drawLine(0, data.pw*i, data.w, data.pw*i);
		}
		Graphics2D g2=(Graphics2D)g;//���ƿ�����
		Stroke stroke=new BasicStroke(5.0f);//�����߿�
		g2.setStroke(stroke);//��
		//���Ʊ߿�
		g2.drawLine(0,0, 0,data.w);
		g2.drawLine(0,data.w, data.w,data.w);
		g2.drawLine(data.w,0, data.w,data.w);
		g2.drawLine(0,0, data.exitX,0);//��������߱߿�
		g2.drawLine(data.exitX+data.exitL,0, data.w,0);//�������ұ߱߿�
	}
	public void drawPeo(Graphics g,int x,int y){//��������
			g.setColor(Color.red);
			g.fill3DRect(x,y, data.pw, data.pw,false);
	}
	public void run(){
		while(true){
			this.repaint();//�ػ�
		}
		
	}
}
