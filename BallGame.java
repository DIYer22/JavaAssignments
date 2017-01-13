
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.concurrent.Delayed;



class Ball{  // �����
	private double x = Math.random()*500; //С������λ��λ��
	private double y = Math.random()*500;
	private double dx = Math.random();  // x��y�����ٶ�
	private double dy = Math.random();
	private double _dx = dx;
	private double _dy = dy;
	private int r = 20; // С����
	private int left = 1000; // �ƶ��̶�����
	private double _left = left;
	private double n;
	public void move(Rectangle2D bounds) { // �˶�
		
		if(left > 0){ 
			left = left -1;
			
			// �������ٵ�Ч��
			n = Math.sqrt(left/_left);
			System.out.println((left/_left));
			
			dx = _dx*n*dx/Math.abs(dx);
			dy = _dy*n*dy/Math.abs(dy);
		}else{ // С��ֹͣ
			dx = 0;
			dy = 0;
		}

		
		//�����߽� ���� ����Ӧ�ٶ�ȡ��
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
	
	
	public Ellipse2D getBall() {  // �������λ�� 
		
		return new Ellipse2D.Double(x, y, r, r);
	}
}

// ����BallPanel�࣬�̳�JPanel��������ϻ������
class BallPanel extends JPanel{
	// ���ڴ洢Boll������
	private ArrayList<Ball> ballList = new ArrayList<Ball>();
	
	public void add(Ball b) {
		ballList.add(b);
	}
	
	public void paint(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		// ��ÿһ���� ballList �����С��
		for (Ball ball : ballList) {
			g.fill(ball.getBall());
		}
	}
}

//����BallRunnable�࣬ʵ��Runnable�ӿ�
class BallRunnable implements Runnable{

	private Ball ball;
	private JPanel jpanel;
	
	private static int time = 3;  // ��λ֡���ʱ�� 
	
	public BallRunnable(Ball ball, JPanel panel) {
		this.ball = ball;
		jpanel = panel;
	}
	
	//����ѭ������
	@Override
	public void run() {
		while(true){  // ����
			ball.move(jpanel.getBounds());
			jpanel.repaint();  // �ػ�
			try {  // ����InterruptedException
				Thread.sleep(time);
			} catch (InterruptedException e) {

	
			}
		}	
	}
	
}


//  BallFrame �������� ,����ʼ��������������ť
class BallFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private BallPanel panel;
	JButton start;
	JButton exit;
	JPanel buttonPanel;
	
	public BallFrame() {  // �GUI
		setTitle("Ball Game");
		setBounds(100,50,500,500);;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		panel = new BallPanel();
		panel.setBackground(Color.white);
		
		
		buttonPanel = new JPanel();
		start = new JButton("��ʼ");
        		Action beginAction = new Action("begin");
		start.addActionListener(beginAction); // ���¼�
		buttonPanel.add(start);
		
		exit = new JButton("����");
		Action exitAction = new Action("exit");
		exit.addActionListener(exitAction);
		buttonPanel.add(exit);
		add(panel,BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		setVisible(true);
	}
	// �¼�����
	public class Action implements ActionListener{
	    private String str;
	    Action(String s){
	        str = s;
	    }

	    public void actionPerformed(ActionEvent event){
			// ����һ���߳� �������߳�
			if(str == "begin"){
				Ball ball = new Ball();
				panel.add(ball);  // BallPanel�����
			    new Thread(new BallRunnable(ball,panel)).start();
	 		}
			// ��������
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


			
			

