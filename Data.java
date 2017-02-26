package CA;

public class Data {
	int w=500; //房间长宽
	int fw=600;//窗口长宽
	int fwx=50; //jpanel 距离左上方坐标
	
	int h=10; //寻欢次数
	int pw=50;//行人长 宽   每个小方格的长度
	int hw=30;
	int inpx=50;//行人初始x
	int inpy=50;//行人初始y
	int roomSize=w/pw; //一共有几行几列
	int exitX=(roomSize/2)*pw;//出口x
	int exitY=0;//出口y
	int exitL=pw;//出口长度
	int speed=50;//移动速度
	int view=200;
	 
}