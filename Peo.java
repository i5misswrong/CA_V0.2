package CA;

import java.util.Arrays;
import java.util.Vector;

public class Peo implements Runnable{
	//������  ���������͹���
	Data data=new Data();//��ʼ��������
	
	int x;//��������
	int y;
	int speed=data.speed;//�����ٶ�
	volatile int mapMat[][];//��������  ������  ��volatile�������߳�
	Vector<Peo> people=new Vector<Peo>();//���˼���
	
	public Peo(int x,int y){//���췽��
		this.x=x;
		this.y=y;
	}
//	----------getter��setter����------------------
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
//	------------�����ƶ�����----------
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
		
		System.out.println("ײ������ ԭ��ֹͣ");
	}
	
	public void peoMove(int direction){//���ݸ�����������ƶ�����
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
	public int decideDirection(){//ȷ�����˷���
		int direction=0;//����
		int leaveUp=y;//��
		int leaveDown=data.w-y;//��
		int leaveLeft=x;//��
		int leaveRight=data.w-x;//��
		int ran[]=new int [4];//4�����������  ��������
		ran[0]=leaveDown;
		ran[1]=leaveLeft;
		ran[2]=leaveRight;
		ran[3]=leaveUp;
		Arrays.sort(ran);//����
//		ȡ����С���Ǹ�ֵ  �������ֵ�Ƚ�  �Ǹ���С���ĸ������ƶ�
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
	public int decideDirectionOnWall(){//���˵���ǽʱ  ����ѡȡ
		int direction=0;
		if(x==0&&y==0){//���Ͻ�
			direction=6;
		}
		else if(x==data.w-data.pw&&y==0){//���Ͻ�
			direction=4;
		}
		else if(x==0&&y==data.w-data.pw){//���½�
			direction=2;
		}
		else if(x==data.w-data.pw&&y==data.w-data.pw){//���½�
			direction=2;
		}
		else{
			if(y==0){//�ϱߵ�ǽ
				if(x>data.exitX){//�Ұ벿��
					direction=4;
				}
				else{//��벿��
					direction=6;
				}
			}
			if(y==data.w-data.pw){//�±ߵ�ǽ
				if(x>data.exitX){//�Ұ벿��
					direction=6;
				}
				else{
					direction=4;//��벿��
				}
			}
			if(x==0||x==data.w-data.pw){//�������ߵ�ǽ
				
				direction=2;
			}
		}
		return direction;
	}
	public boolean isTouchOther(int sy,int sx,int d){//��������Ƿ���ײ������x��y�ͷ���
		//���ײ ΪTRUE  ���ûײ Ϊfalse
		boolean flag=true;
	
		int peoIdCpoy=mapMat[sy][sx];//��ȡpeoId
		
		switch (d) {
		case 2://�������Ϊ��
			if(mapMat[sy-1][sx]==0){//�������ĵ�Ϊ0
				mapMat[sy-1][sx]=peoIdCpoy;//������ĵ�����Ϊpeoid
				mapMat[sy][sx]=0;//��ԭ���ĵĵ���Ϊ0
				flag=false;//����Ϊûײ
			}
			break;
//		-----------һ��ͬ��-----------
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
	public boolean isTouchWall(){//����Ƿ�ײǽ
		//���ײ  ��Ϊfalse  ���ûײ ��ΪTRUE
		boolean flag=true;
		if(x==0||y==0||x==data.w-data.pw||y==data.w-data.pw){//��������Ƿ񵽴�߽�
			flag=false;
		}
		return flag;
	}
	public boolean isGoExit(){//����Ƿ񵽴����
		//����������  ����Ϊfalse  ���û��  ����ΪTRUE
		boolean flag=true;
		if(y==0){//�Ƿ����ϱ�ǽ
			if(x==data.exitX||x==data.exitX+1||x==data.exitX-1){//�Ƿ��ڳ��ڸ���
				flag=false;
			}
		}
		return flag;
	}
	
	public void run(){
		//�㿪���а�ť  �� ���˿�ʼ�˶���ʱ����
		try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
		while(true){
			if(isGoExit()){//����Ƿ񵽴����
				if(isTouchWall()){//����Ƿ�ײǽ ���ûײ
					int direction=decideDirection();//��ȡ��һ���ķ���
					//����Ƿ���������ײ
					if(isTouchOther(y/data.pw, x/data.pw, direction)){
						//ײ��
						System.out.println("ײ��������");
					}
					else{//ûײ
						peoMove(direction);
					}
				}
				else{//���ײǽ��
					//��ȡ��һ������
					int directionOnWall=decideDirectionOnWall();
					//����Ƿ���������ײ
					if(isTouchOther(y/data.pw, x/data.pw, directionOnWall)){
						//ײ��
						System.out.println("ײ��������");
					}
					else{//ûײ
						peoMove(directionOnWall);
					}
				}
			}
			else{
				System.out.println("�ܳ�ȥһ��");
			}
			//����ÿ�ε��˶����
			try {
	            Thread.sleep(1000);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
}
