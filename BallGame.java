
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.concurrent.Delayed;



class Ball{  // 球对象
	private double x = Math.random()*500; //小球所在位置位置
	private double y = Math.random()*500;
	private double dx = Math.random();  // x，y方向速度
	private double dy = Math.random();
	private double _dx = dx;
	private double _dy = dy;
	private int r = 20; // 小球半斤
	private int left = 1000; // 移动固定次数
	private double _left = left;
	private double n;
	public void move(Rectangle2D bounds) { // 运动
		
		if(left > 0){ 
			left = left -1;
			
			// 缓慢减速的效果
			n = Math.sqrt(left/_left);
			System.out.println((left/_left));
			
			dx = _dx*n*dx/Math.abs(dx);
			dy = _dy*n*dy/Math.abs(dy);
		}else{ // 小球停止
			dx = 0;
			dy = 0;
		}

		
		//遇到边界 反弹 即对应速度取反
		if(x < bounds.getMinX()){
			x = bounds.getMinX();
			dx = -dx;
		}
		if(x+r >= bounds.getMaxX()){
			x = bounds.getMaxX() - r;
			dx = -dx;
		}
		if(y < bounds.getMinY()){
			y = bounds.getMinY();
			dy = -dy;
		}
		if(y+r >= bounds.getMaxY()){
			y = bounds.getMaxY() - r;
			dy = -dy;
		}		
		x  = x + dx;
		y = y + dy;
	}
	
	
	public Ellipse2D getBall() {  // 返回球的位置 
		
		return new Ellipse2D.Double(x, y, r, r);
	}
}

// 定义BallPanel类，继承JPanel，在面板上画多个球
class BallPanel extends JPanel{
	// 用于存储Boll的数组
	private ArrayList<Ball> ballList = new ArrayList<Ball>();
	
	public void add(Ball b) {
		ballList.add(b);
	}
	
	public void paint(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		// 画每一个在 ballList 里面的小球
		for (Ball ball : ballList) {
			g.fill(ball.getBall());
		}
	}
}

//定义BallRunnable类，实现Runnable接口
class BallRunnable implements Runnable{

	private Ball ball;
	private JPanel jpanel;
	
	private static int time = 3;  // 单位帧间隔时间 
	
	public BallRunnable(Ball ball, JPanel panel) {
		this.ball = ball;
		jpanel = panel;
	}
	
	//启动循环动画
	@Override
	public void run() {
		while(true){  // 动画
			ball.move(jpanel.getBounds());
			jpanel.repaint();  // 重画
			try {  // 处理InterruptedException
				Thread.sleep(time);
			} catch (InterruptedException e) {

	
			}
		}	
	}
	
}


//  BallFrame 动画窗口 ,‘开始’、‘结束’按钮
class BallFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private BallPanel panel;
	JButton start;
	JButton exit;
	JPanel buttonPanel;
	
	public BallFrame() {  // 搭建GUI
		setTitle("Ball Game");
		setBounds(100,50,500,500);;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		panel = new BallPanel();
		panel.setBackground(Color.white);
		
		
		buttonPanel = new JPanel();
		start = new JButton("开始");
        		Action beginAction = new Action("begin");
		start.addActionListener(beginAction); // 绑定事件
		buttonPanel.add(start);
		
		exit = new JButton("结束");
		Action exitAction = new Action("exit");
		exit.addActionListener(exitAction);
		buttonPanel.add(exit);
		add(panel,BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		setVisible(true);
	}
	// 事件对象
	public class Action implements ActionListener{
	    private String str;
	    Action(String s){
	        str = s;
	    }

	    public void actionPerformed(ActionEvent event){
			// 创建一个线程 并启动线程
			if(str == "begin"){
				Ball ball = new Ball();
				panel.add(ball);  // BallPanel添加球
			    new Thread(new BallRunnable(ball,panel)).start();
	 		}
			// 结束程序
			else if (str == "exit") {
				System.exit(0);
			}
	    }       
	}

}



public class BallGame{
	public static void main(String[] args) {
		new BallFrame();
	}
}


			
			

