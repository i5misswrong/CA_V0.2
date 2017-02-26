package CA;

import java.util.Arrays;
import java.util.Vector;

public class Peo implements Runnable{
	//行人类  包含动作和规则
	Data data=new Data();//初始化数据类
	
	int x;//行人坐标
	int y;
	int speed=data.speed;//行人速度
	volatile int mapMat[][];//行人坐标  的数组  用volatile来锁死线程
	Vector<Peo> people=new Vector<Peo>();//行人集合
	
	public Peo(int x,int y){//构造方法
		this.x=x;
		this.y=y;
	}
//	----------getter和setter方法------------------
	public int[][] getMapMat() {
		return mapMat;
	}
	public void setMapMat(int[][] mapMat) {
		this.mapMat = mapMat;
	}

	
	public Vector<Peo> getPeople() {
		return people;
	}
	public void setPeople(Vector<Peo> people) {
		this.people = people;
	}

	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
//	------------行人移动方法----------
	public void moveDown(){
		y+=speed;
	}
	public void moveUp(){
		y-=speed;
	}
	public void moveLeft(){
		x-=speed;
	}
	public void moveRight(){
		x+=speed;
	}
	public void noMove(){
		
		System.out.println("撞到行人 原地停止");
	}
	
	public void peoMove(int direction){//根据给定方向调用移动方法
		switch (direction) {
		case 2:
			moveUp();
			break;
		case 6:
			moveRight();
			break;
		case 8:
			moveDown();
			break;
		case 4:
			moveLeft();
			break;
		case 5:
			noMove();
		}
	}
	public int decideDirection(){//确定行人方向
		int direction=0;//方向
		int leaveUp=y;//上
		int leaveDown=data.w-y;//下
		int leaveLeft=x;//坐
		int leaveRight=data.w-x;//右
		int ran[]=new int [4];//4个方向的数组  用于排序
		ran[0]=leaveDown;
		ran[1]=leaveLeft;
		ran[2]=leaveRight;
		ran[3]=leaveUp;
		Arrays.sort(ran);//排序
//		取出最小的那个值  与上面的值比较  那个最小往哪个方向移动
		if(ran[0]==leaveUp){
			direction=2;
		}
		else if(ran[0]==leaveRight){
			direction=6;
		}
		else if(ran[0]==leaveDown){
			direction=8;
		}
		else if(ran[0]==leaveLeft){
			direction=4;
		}
		return direction;
	}
	public int decideDirectionOnWall(){//行人到达墙时  方向选取
		int direction=0;
		if(x==0&&y==0){//左上角
			direction=6;
		}
		else if(x==data.w-data.pw&&y==0){//右上角
			direction=4;
		}
		else if(x==0&&y==data.w-data.pw){//左下角
			direction=2;
		}
		else if(x==data.w-data.pw&&y==data.w-data.pw){//右下角
			direction=2;
		}
		else{
			if(y==0){//上边的墙
				if(x>data.exitX){//右半部分
					direction=4;
				}
				else{//左半部分
					direction=6;
				}
			}
			if(y==data.w-data.pw){//下边的墙
				if(x>data.exitX){//右半部分
					direction=6;
				}
				else{
					direction=4;//左半部分
				}
			}
			if(x==0||x==data.w-data.pw){//左右两边的墙
				
				direction=2;
			}
		}
		return direction;
	}
	public boolean isTouchOther(int sy,int sx,int d){//检测行人是否相撞，传入x，y和方向
		//如果撞 为TRUE  如果没撞 为false
		boolean flag=true;
	
		int peoIdCpoy=mapMat[sy][sx];//获取peoId
		
		switch (d) {
		case 2://如果方向为上
			if(mapMat[sy-1][sx]==0){//如果上面的点为0
				mapMat[sy-1][sx]=peoIdCpoy;//将上面的点设置为peoid
				mapMat[sy][sx]=0;//将原来的的点设为0
				flag=false;//设置为没撞
			}
			break;
//		-----------一下同理-----------
		case 4:
			if(mapMat[sy][sx-1]==0){
				mapMat[sy][sx-1]=peoIdCpoy;
				mapMat[sy][sx]=0;
				flag=false;
			}
			break;
		case 6:
			if(mapMat[sy][sx+1]==0){
				mapMat[sy][sx+1]=peoIdCpoy;
				mapMat[sy][sx]=0;
				flag=false;
			}
			break;
		case 8:
			if(mapMat[sy+1][sx]==0){
				mapMat[sy+1][sx]=peoIdCpoy;
				mapMat[sy][sx]=0;
				flag=false;
			}
			break;
		default:
			break;
		}
		return flag;
	}
	public boolean isTouchWall(){//检测是否撞墙
		//如果撞  设为false  如果没撞 设为TRUE
		boolean flag=true;
		if(x==0||y==0||x==data.w-data.pw||y==data.w-data.pw){//检测行人是否到达边界
			flag=false;
		}
		return flag;
	}
	public boolean isGoExit(){//检测是否到达出口
		//如果到达出口  设置为false  如果没到  设置为TRUE
		boolean flag=true;
		if(y==0){//是否在上边墙
			if(x==data.exitX||x==data.exitX+1||x==data.exitX-1){//是否在出口附件
				flag=false;
			}
		}
		return flag;
	}
	
	public void run(){
		//点开运行按钮  到 行人开始运动的时间间隔
		try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
		while(true){
			if(isGoExit()){//检测是否到达出口
				if(isTouchWall()){//检测是否撞墙 如果没撞
					int direction=decideDirection();//获取下一步的方向
					//检测是否与行人相撞
					if(isTouchOther(y/data.pw, x/data.pw, direction)){
						//撞了
						System.out.println("撞到行人了");
					}
					else{//没撞
						peoMove(direction);
					}
				}
				else{//如果撞墙了
					//获取下一步方向
					int directionOnWall=decideDirectionOnWall();
					//检测是否与行人相撞
					if(isTouchOther(y/data.pw, x/data.pw, directionOnWall)){
						//撞了
						System.out.println("撞到行人了");
					}
					else{//没撞
						peoMove(directionOnWall);
					}
				}
			}
			else{
				System.out.println("跑出去一个");
			}
			//行人每次的运动间隔
			try {
	            Thread.sleep(1000);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
}
