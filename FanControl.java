
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.Delayed;

public class FanControl{
	public static void main(String[] args) {
		new FanFrame();
	}
}


//����FanRunnable�࣬ʵ��Runnable�ӿ�
class FanRunnable implements Runnable{
	private FanPanel panel;
	private static int time = 30;  // ��λ֡���ʱ�� 
	
	public FanRunnable(FanPanel jpanel) { // ��Ҫ
		this.panel = jpanel;
	}
	
	//����ѭ������
	@Override
	public void run() {
		while(true){  // ����
			panel.f.move();
			panel.repaint();
			try {  // ����InterruptedException
				Thread.sleep(time);
			} catch (InterruptedException e) {

			}
		}	
	}
	
}

// ���ȵ���������
class fan{
	public int on = 1; // �Ƿ�����
	public int du = 0;  // ��ǰ�Ƕ�
	double v = 0; // ˲ʱ�ٶ�
	double max = 10.1; // ����ٶ�
	double a = 0.03;  // �ٶ�������
	public void move(){  // �Է���ÿһ֡���д���
		if(on==1){
			v+=((max-v)*a);  //����
		}else{
			v-=(v*3*a); // ����
		}
		du+=(int)v; //�ƶ�
		du = du %360;
	}
	
}

// FanPanel ÿ�����ȵ�panel
class FanPanel extends JPanel{
	public fan f= new fan() ; // ��ǰpanel�ķ���
	JPanel buttonPanel;
	JButton start = new JButton("����");
	JButton pause = new JButton("��ͣ");
	int X;
	int Y;
	public FanPanel(){
		super();
		setLayout(null);
	}
	public void after(){  // ���úô�С�� ���ܳ�ʼ��
		setBackground(Color.white);
		Rectangle2D bound = this.getBounds();
		X = (int)bound.getWidth();
		Y = (int)bound.getHeight();
		
		addButton();
		buttonPanel.setSize(145, 40);
		buttonPanel.setLocation((X-145)/2, Y-40);
		
		add(buttonPanel);	
	}
	void addButton(){  // ��Ӱ�ť���
		buttonPanel = new JPanel();

		start = new JButton("����");
		Action beginAction = new Action(1);
		start.addActionListener(beginAction); // ���¼�
		buttonPanel.add(start);
		
		pause = new JButton("ֹͣ");
		Action exitAction = new Action(0);
		pause.addActionListener(exitAction);
		buttonPanel.add(pause);
	}
	public class Action implements ActionListener{ // ������ť���
		private int a;
		 Action(int tag){
				a = tag;
		 }
			public void actionPerformed(ActionEvent event){
			f.on = a;
			}       
	}
	public void trun(int a){
		f.on=a;
	}
	public void paintComponent(Graphics g) { // ��ͼ
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			double pi = 3.141592653589;
			double du=f.du*pi/180.0; // ��ת�Ƕ�
			
			int [] xs={X/2-X/12,X/2,X/2+X/12,X/2}; //ҶƬ��Ӧ����
			int [] ys={X/2-X/3,X/2-X/3-X/30,X/2-X/3,4*X/7};
			// ��3ƬҶƬ
			g2d.rotate(du,X/2, Y/2);
			g.setColor(Color.BLUE);  // ҶƬ��ɫ
			g2d.fillPolygon(xs, ys, xs.length); // ��ҶƬ
			
			g2d.rotate(2.0/3*pi,X/2, Y/2);
			g.setColor(Color.RED);   
			g2d.fillPolygon(xs, ys, xs.length);
			
			g2d.rotate(2.0/3*pi,X/2, Y/2);
			g.setColor(Color.YELLOW);   
			g2d.fillPolygon(xs, ys, xs.length);

			g2d.rotate(2.0/3*pi,X/2, Y/2);
			
			g2d.rotate(-du,X/2, Y/2);
			g.setColor(Color.GRAY);
			int r =  X/12;
			g2d.fillOval(X/2-r/2, Y/2-r/2,r, r);// ������Բ
			
			g.setColor(Color.BLACK);
			g2d.drawString("speed:"+(int)f.v,20,20);
		}
}

//  FanFrame �ܽ��湹��
class FanFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private FanPanel[] panels=new FanPanel[3]; // fan����
	JButton start;
	JButton pause;
	JPanel buttonPanel;
	
	// ����debug ���debugЧ��
	public static < E > void log( E[] inputArray )
	   {// �������Ԫ��            
		 for ( E element : inputArray ){        
			System.out.printf( "%s ", element );
		 }
		 System.out.println();
	}
	public static < T > void log( T input )
	   { // Ԫ��            
		 System.out.println(input);
	}
	 
	
	public FanFrame() {  // �GUI
		setTitle("Fans");
		setBounds(100,50,900,500);;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		Rectangle2D bound = this.getBounds();
		log(bound.getMaxX());
		int X = (int)bound.getWidth();
		int Y = (int)bound.getHeight();
		
		int count = 0;
		for(FanPanel panel: panels){ // ��ʾÿһ���Ӵ���
			panel = new FanPanel( );
			panels[count] = panel;
			panel.setSize((int)(X/3*0.98),Y*3/4);
			panel.setLocation(count*X/3,0);
			panel.after();
			add(panel);
			new Thread(new FanRunnable(panel)).start();
			count++;
		}
		
		
		getButtonPanel();
		
		buttonPanel.setSize(145, 40);
		buttonPanel.setLocation((X-145)/2, Y-2*40);
		add(buttonPanel);
		
		setVisible(true);
	}
	public void getButtonPanel(){ // ���ð�ť
		buttonPanel = new JPanel();
		start = new JButton("����");
		Action beginAction = new Action(1);
		start.addActionListener(beginAction); // ���¼�
		buttonPanel.add(start);
		
		pause = new JButton("ֹͣ");
		Action exitAction = new Action(0);
		pause.addActionListener(exitAction);
		buttonPanel.add(pause);
	}
	public class Action implements ActionListener{ // ������ť�¼�
		private int a;
		Action(int tag){
			a = tag;
		}

		public void actionPerformed(ActionEvent event){
			for(FanPanel panel: panels){
				panel.trun(a);
			}
			
		}       
	}

}



			
			

