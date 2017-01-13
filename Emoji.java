
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

//import java.io.File;

public class Emoji {
    public static void main(String[] args){
        Frame frame = new Frame();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class Frame extends JFrame{
    private JPanel buttonPanel; 
    private JPanel imagePanel;
    private JLabel imageLabel;
    private int width = 400;
    private int height = 400;



    public Frame(){
        setSize(width,height);

        buttonPanel = new JPanel();
        imagePanel = new JPanel();
        imageLabel = new JLabel();
        
        // ����
        add(buttonPanel,BorderLayout.SOUTH);
        add(imagePanel,BorderLayout.NORTH);
        imagePanel.add(imageLabel,BorderLayout.CENTER);
        imageLabel.setIcon(new ImageIcon(this.getClass().getResource("/img/welcome.png")));
        
        // ��ť
        JButton cryBotton = new JButton("����");
        JButton smileBotton = new JButton("΢Ц");
        JButton angryBotton = new JButton("����");
        JButton exitBotton = new JButton("�˳�");


        buttonPanel.add(cryBotton);
        buttonPanel.add(smileBotton);
        buttonPanel.add(angryBotton);
        buttonPanel.add(exitBotton);
        
        // ������������
        Action cryAction = new Action("cry");
        Action smileAction = new Action("smile");
        Action angryAction = new Action("angry");
        Action exitAction = new Action("exit");
        
        // ����
        cryBotton.addActionListener(cryAction);
        smileBotton.addActionListener(smileAction);
        angryBotton.addActionListener(angryAction);
        exitBotton.addActionListener(exitAction);
    }
    
    private class Action implements ActionListener{
        private String str;
        Action(String s){
            if(s != "exit") {
                str = "/img/"+ s +".png";
            }
        }
        public String getStr(){
        	return str;
        };
        public void actionPerformed(ActionEvent event){
            if(str == "exit"){
            	 System.exit(0);
            }
               
//            System.out.println(new File(this.getStr()).exists());
//            System.out.println(imageLabel);
            imageLabel.setIcon(new ImageIcon(this.getClass().getResource(this.getStr())));
//            imageLabel.setIcon(new ImageIcon("C:/Users/yl/workspace/javaClass/src/img/angry.png"));
        }
    }
}
