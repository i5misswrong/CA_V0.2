package CA;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Vector;

import javax.swing.JPanel;


public class DrawSecond extends JPanel implements Runnable{
	//创建画板类 完成行人、地图的绘制和重绘 完成行人的初始化
	Data data=new Data();//初始化数据类
	Vector<Peo> peoVector=new Vector<Peo>();//创建行人集合  保证线程安全 采用vector
	volatile int mapMat[][]=new int[data.w][data.w];//创建行人坐标数组 使用volatile锁死线程
	public DrawSecond(){
		int peoNum=12;//行人数量
		int peoId=1;//行人ID
		
//		---------------------------------
//		----此处为测试用 在特定位置产生行人-----
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
//		-------------这是随机生成行人-------------
		for (int i=0;i<peoNum;i++){
			int x=((int)(Math.random()*10)*data.pw);//在1-10产生随机数  X pw就得到确定坐标
			int y=((int)(Math.random()*10)*data.pw);
			Peo peo=new Peo(x,y);//新建行人
			mapMat[y/data.pw][x/data.pw]=peoId;//设置行人坐标的值 唯一
			peoId++;//Id++
			peo.setMapMat(mapMat);//将数组设置到peo类
			peo.setPeople(peoVector);//将集合设置到peo类
			Thread t=new Thread(peo);//创建线程
			t.start();//启动线程
			peoVector.add(peo);//将行人添加到集合
		}
//		-----------------------------------
		
	}
	public void paint (Graphics g){//重写绘图方法
		
		g.setColor(Color.WHITE);//设置颜色
		//super.paint(g);
		g.fillRect(0, 0, data.w,data.w);//画出面板
		this.drawMap(g);//绘制地图
//		----------当行人在出口时的动作----------------
		for(int i=0;i<peoVector.size();i++){
			Peo peo=peoVector.get(i);//获取行人
			if(peo.isGoExit()){//如果没有到达出口
				this.drawPeo(g, peo.getX(), peo.getY());//画出行人
			}
			else{
				//如果到达出口
				mapMat[peo.getY()/data.pw][peo.getX()/data.pw]=0;//将行人坐标设置为0
				peoVector.remove(i);//将行人从集合移除
			}
		}
	}
	public void drawMap(Graphics g){
		//绘制地图方法
		//下面注释为  面板位于窗口中间  左上角坐标往右下方平移50px
		//g.fillRect(data.fwx, data.fwx, data.w, data.w);
//		g.setColor(Color.black);
//		for (int i=1;i<(data.w/data.pw);i++){
//			g.drawLine(data.pw*i+data.fwx, data.fwx, data.pw*i+data.fwx, data.w+data.fwx);
//		}
//		for (int i=1;i<(data.w/data.pw);i++){
//			g.drawLine(data.fwx, data.pw*i+data.fwx, data.w+data.fwx, data.pw*i+data.fwx);
//		}
//		Graphics2D g2=(Graphics2D)g;
//		Stroke stroke=new BasicStroke(5.0f);//设置线宽为3.0
//		g2.setStroke(stroke);
//		g2.drawLine(data.fwx,data.fwx, data.fwx,data.fwx+data.w);
//		g2.drawLine(data.fwx,data.fwx+data.w, data.fwx+data.w,data.fwx+data.w);
//		g2.drawLine(data.fwx+data.w,data.fwx, data.fwx+data.w,data.fwx+data.w);
//		g2.drawLine(data.fwx,data.fwx, data.fwx+data.exitX,data.fwx);
//		g2.drawLine(data.fwx+data.exitX+data.exitL,data.fwx, data.fwx+data.w,data.fwx);
		g.setColor(Color.black);
		//绘制中间线条
		for (int i=1;i<(data.w/data.pw);i++){//竖线
			g.drawLine(data.pw*i, 0, data.pw*i, data.w);//每隔pw 便绘制一个线条 
		}
		for (int i=1;i<(data.w/data.pw);i++){//横线
			g.drawLine(0, data.pw*i, data.w, data.pw*i);
		}
		Graphics2D g2=(Graphics2D)g;//绘制宽线条
		Stroke stroke=new BasicStroke(5.0f);//设置线宽
		g2.setStroke(stroke);//？
		//绘制边框
		g2.drawLine(0,0, 0,data.w);
		g2.drawLine(0,data.w, data.w,data.w);
		g2.drawLine(data.w,0, data.w,data.w);
		g2.drawLine(0,0, data.exitX,0);//最上面左边边框
		g2.drawLine(data.exitX+data.exitL,0, data.w,0);//最上面右边边框
	}
	public void drawPeo(Graphics g,int x,int y){//绘制行人
			g.setColor(Color.red);
			g.fill3DRect(x,y, data.pw, data.pw,false);
	}
	public void run(){
		while(true){
			this.repaint();//重绘
		}
		
	}
}
