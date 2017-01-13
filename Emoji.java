
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
        
        // 布局
        add(buttonPanel,BorderLayout.SOUTH);
        add(imagePanel,BorderLayout.NORTH);
        imagePanel.add(imageLabel,BorderLayout.CENTER);
        imageLabel.setIcon(new ImageIcon(this.getClass().getResource("/img/welcome.png")));
        
        // 按钮
        JButton cryBotton = new JButton("流泪");
        JButton smileBotton = new JButton("微笑");
        JButton angryBotton = new JButton("生气");
        JButton exitBotton = new JButton("退出");


        buttonPanel.add(cryBotton);
        buttonPanel.add(smileBotton);
        buttonPanel.add(angryBotton);
        buttonPanel.add(exitBotton);
        
        // 创建监听对象
        Action cryAction = new Action("cry");
        Action smileAction = new Action("smile");
        Action angryAction = new Action("angry");
        Action exitAction = new Action("exit");
        
        // 监听
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
