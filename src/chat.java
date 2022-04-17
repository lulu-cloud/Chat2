
import javax.swing.*;
import static javax.swing.JFrame.*; //引入JFrame的静态常量
import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.Format;
import java.text.SimpleDateFormat;


class MyExtendsJFrame extends JFrame implements ActionListener,Runnable{
    //自定义窗口类，
    //1.继承窗口类，用于设计窗体；
    //2.实现ActionListener，用于响应按钮点击事件
    //3.实现Runnable，重写run方法，用于执行接收线程
    JLabel background;//背景控件1
    JLabel aboveground;//背景控件2
    JLabel modelground;//背景控件3
    JLabel modelground2;//背景控件4
    JLabel bottleground;//背景控件4
    JScrollPane pane;//滚动条控件
    JTextField textSend;
    JTextArea textRecv;
    JButton buttonSend;
    JButton buttonSendFile;
    JButton buttonclose;
    JButton min; //最小化
    JButton max; //最大化
    JButton close; //关闭
    JButton clear; //清空聊天区域
    int mouseAtX = 0;
    int mouseAtY = 0;
    JTextArea Ip;//IP地址
    JTextArea Port;//端口号
    JTextArea IpTips;//IP地址
    JTextArea PortTips;//端口号
    JTextArea PortTips1;//端口号
    JTextArea Port1;//端口号2
    JTextArea Tips;//端口号
    JTextArea time;//当前时间

    public MyExtendsJFrame(){
        setTitle("聊天软件");
        setBounds(160,100,630,670);
        setUndecorated(true);
        setLayout(null);
        init();   //添加控件的操作封装成一个函数
        setVisible(true);//放在添加组件后面执行
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                /*
                 * 获取点击鼠标时的坐标
                 */
                mouseAtX = e.getPoint().x;
                mouseAtY = e.getPoint().y;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
                setLocation((e.getXOnScreen()-mouseAtX),(e.getYOnScreen()-mouseAtY));//设置拖拽后，窗口的位置
            }
        });
    }
    void init(){//添加的控件

        Icon img=new ImageIcon(".\\background.jpg");     //创建图标对象
        background = new JLabel(img);//设置背景图片
        background.setBounds(0,40,630,415);//设置背景控件大小
        getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));//背景图片控件置于最底层
        ((JPanel)getContentPane()).setOpaque(false); //控件透明

        img=new ImageIcon(".\\above.jpg");     //创建图标对象
        aboveground = new JLabel(img);//设置背景图片
        aboveground.setBounds(0,0,630,40);//设置背景控件大小
        getLayeredPane().add(aboveground, new Integer(Integer.MIN_VALUE));//背景图片控件置于最底层
        ((JPanel)getContentPane()).setOpaque(false); //控件透明


        img=new ImageIcon(".\\model.jpg");     //创建图标对象
        modelground = new JLabel(img);//设置背景图片
        modelground.setBounds(0,455,630,34);//设置背景控件大小
        getLayeredPane().add(modelground, new Integer(Integer.MIN_VALUE));//背景图片控件置于最底层
        ((JPanel)getContentPane()).setOpaque(false); //控件透明

        img=new ImageIcon(".\\model2.jpg");     //创建图标对象
        modelground2 = new JLabel(img);//设置背景图片
        modelground2.setBounds(0,455,630,2);//设置背景控件大小
        getLayeredPane().add(modelground2, new Integer(Integer.MIN_VALUE));//背景图片控件置于最底层
        ((JPanel)getContentPane()).setOpaque(false); //控件透明

        img=new ImageIcon(".\\bottle.jpg");     //创建图标对象
        bottleground = new JLabel(img);//设置背景图片
        bottleground.setBounds(0,489,630,182);//设置背景控件大小
        getLayeredPane().add(bottleground, new Integer(Integer.MIN_VALUE));//背景图片控件置于最底层
        ((JPanel)getContentPane()).setOpaque(false); //控件透明


        min=new JButton();//添加最小化按钮
        min.setBounds(530,0,32,32); //设置按钮大小
        img=new ImageIcon(".\\min.png");
        min.setIcon(img);	      //设置图标
        min.setContentAreaFilled(false);
        min.setBorderPainted(false);
        min.addActionListener(this);//添加单击事件关联
        add(min);//添加播放按钮至窗口中

        max=new JButton();//添加最小化按钮
        max.setBounds(564,0,32,32); //设置按钮大小
        img=new ImageIcon(".\\max.png");
        max.setIcon(img);	      //设置图标
        max.setContentAreaFilled(false);
        max.setBorderPainted(false);
        max.addActionListener(this);//添加单击事件关联
        add(max);//添加播放按钮至窗口中

        close=new JButton();//添加最小化按钮
        close.setBounds(598,0,32,32); //设置按钮大小
        img=new ImageIcon(".\\close.png");
        close.setIcon(img);	      //设置图标
        close.setContentAreaFilled(false);
        close.setBorderPainted(false);
        close.addActionListener(this);//添加单击事件关联
        add(close);//添加播放按钮至窗口中

        clear=new JButton();//添加最小化按钮
        clear.setBounds(55,457,32,32); //设置按钮大小
        img=new ImageIcon(".\\delete.png");
        clear.setIcon(img);	      //设置图标
        clear.setContentAreaFilled(false);
        clear.setBorderPainted(false);
        clear.addActionListener(this);//添加单击事件关联
        add(clear);//添加播放按钮至窗口中



        textSend=new JTextField(20);
        textSend.setBounds(0,490,630,130);
        textSend.setOpaque(false);//聊天区域控件透明
        textSend.addActionListener(this);
        add(textSend);

        textRecv=new JTextArea();
        textRecv.setBounds(0,40,630,415);
        textRecv.setFont(new Font("黑体",Font.BOLD,15));
        textRecv.setOpaque(false);//聊天区域控件透明
        add(textRecv);

        pane=new JScrollPane(textRecv);
        pane.setBounds(0,40,630,415);
        pane.setOpaque(false);
        pane.getViewport().setOpaque(false);
        add(pane);

        IpTips=new JTextArea("IP:");
        IpTips.setBounds(5,15,30,20);
        IpTips.setFont(new Font("黑体",Font.BOLD,17));
        IpTips.setOpaque(false);//聊天区域控件透明
        add(IpTips);


        Ip=new JTextArea("172.21.12.204");
        Ip.setBounds(40,15,200,20);
        Ip.setFont(new Font("黑体",Font.PLAIN,17));
//        Ip.setOpaque(false);//聊天区域控件透明
        add(Ip);

        PortTips=new JTextArea("Port0:");
        PortTips.setBounds(250,15,55,20);
        PortTips.setFont(new Font("黑体",Font.BOLD,17));
        PortTips.setOpaque(false);//聊天区域控件透明
        add(PortTips);

        Port=new JTextArea("2013");
        Port.setBounds(310,15,40,20);
//        Port.setOpaque(false);//聊天区域控件透明
        add(Port);

        PortTips1=new JTextArea("Port1:");
        PortTips1.setBounds(365,15,55,20);
        PortTips1.setFont(new Font("黑体",Font.BOLD,17));
        PortTips1.setOpaque(false);//聊天区域控件透明
        add(PortTips1);

        Port1=new JTextArea("2014");
        Port1.setBounds(430,15,40,20);
//        Port.setOpaque(false);//聊天区域控件透明
        add(Port1);

        buttonSend=new JButton("发送(s)");
        buttonSend.setBounds(500,630,100,30);
        buttonSend.setBackground(new Color(54,178,212));
        buttonSend.setFont(new Font("黑体",Font.PLAIN,17));
        buttonSend.addActionListener(this);//添加关联
        add(buttonSend);

        buttonclose=new JButton("关闭(c)");
        buttonclose.setBounds(395,630,100,30);
        buttonclose.setBackground(Color.white);
        buttonclose.setFont(new Font("黑体",Font.PLAIN,17));
        buttonclose.addActionListener(this);//添加关联
        add(buttonclose);

        Tips=new JTextArea("Port0用于发送文字，Port1用于发送文件。");
        Tips.setBounds(20, 640, 270, 30);
        Tips.setFont(new Font("宋体",Font.BOLD,13));
        Tips.setOpaque(false);//聊天区域控件透明
        add(Tips);

        time=new JTextArea("time");
        time.setBounds(550, 463, 45, 30);
        time.setFont(new Font("宋体",Font.BOLD,15));
        time.setOpaque(false);//聊天区域控件透明
        add(time);

        buttonSendFile=new JButton("File");
        buttonSendFile.setBounds(15,457,32,32);//改动为29
        img=new ImageIcon(".\\file_pic.png");
        buttonSendFile.setIcon(img);	      //设置图标
        buttonSendFile.setContentAreaFilled(false);
        buttonSendFile.setBorderPainted(false);
        buttonSendFile.addActionListener(this);//添加关联
        add(buttonSendFile);
        add(background);
        add(aboveground);
        add(modelground2);
        add(bottleground);



    }
    public void actionPerformed(ActionEvent e){	//发送按钮响应
        if(e.getSource()==max)
        {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }


        if(e.getSource()==min)
        {
            setExtendedState(JFrame.ICONIFIED);
        }


        if(e.getSource()==close)
        {
            System.exit(0);
        }

        if(e.getSource()==buttonclose)
        {
            System.exit(0);
        }

        if(e.getSource()==clear)
        {
            textRecv.setText("");
        }
        int recvPort=new Integer(Port.getText());
        if(buttonSend==e.getSource()){
            System.out.println("buttonSend");
            Date d=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String cc=sdf.format(d);
            byte data[]=textSend.getText().getBytes();//获取发送文本框字符
            try{
                InetAddress address=InetAddress.getByName(Ip.getText());//设置目的IP地址
                // System.out.println(Ip.getText());
                DatagramPacket SendPacket=new DatagramPacket(data,data.length,address,recvPort);
                //基于上一步的目标地址，创建UDP数据包，目标端口为2013
                DatagramSocket Post=new DatagramSocket();//创建UDP发送对象
                Post.send(SendPacket); //发送数据
                String str=textSend.getText();//获取发送窗口字符串
                textRecv.setText(textRecv.getText()+"                                                "+cc+'\n'+
                                                    "                                                Send:"+str+'\n');  //显示数据
            }
            catch(Exception e1){}
        }

        if(e.getSource()==textSend)
        {
            buttonSend.doClick();
        }

        if(buttonSendFile==e.getSource()) {
            byte fileBuf[] = null;//读取文件数组
            recvPort=new Integer(Port1.getText());
            FileDialog openFile=new FileDialog(this,"打开文件", FileDialog.LOAD);
            openFile.setVisible(true);
            FileInputStream  fileread=null;
            File file=new File(openFile.getDirectory(),openFile.getFile());
            try{//读取文件
                fileread=new FileInputStream (file);
                fileBuf=new byte[(int)file.length()];
                fileread.read(fileBuf);//直接把fileBuf打包，并调用send方法发送即可
                //UDP包不能太大，暂时只考虑10K以内小文件一次性收发，可不拆包
                fileread.close();
                InetAddress address=InetAddress.getByName(Ip.getText());//设置目的IP地址
                DatagramPacket SendPacket=new DatagramPacket(fileBuf,fileBuf.length,address,recvPort);
                //基于上一步的目标地址，创建UDP数据包
                DatagramSocket Post=new DatagramSocket();//创建UDP发送对象
                Post.send(SendPacket); //发送数据
            }
            catch(IOException ee){
                System.out.println("错误"+ee.getMessage());
            }
            finally{
                if(fileread!=null){
                    try{	 fileread.close();   }
                    catch(IOException ee){
                        System.out.println("错误"+ee.getMessage());
                    }
                }
            }//读取文件结束

        }


    }


    public void run()	{//接收线程
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        String cc=sdf.format(d);
        time.setText(cc);
        byte data[]=new byte[1024];
        String name=Thread.currentThread().getName();
        if(name.equals("Text")==true){
            System.out.println("Text");
            try{
                DatagramSocket Recv=new DatagramSocket(2013);//创建UDP接收对象
                DatagramPacket pack=new DatagramPacket(data,data.length);//创建UDP接收数据包
                while(true)
                {
                    Recv.receive(pack); //接收一个数据包
                    String s=new String(pack.getData(),0,pack.getLength());  //读取数据包
                    textRecv.setText(textRecv.getText()+"Recv:"+s+'\n');  //显示数据
                }
            }
            catch(Exception e1){}
        }
        if(name.equals("File")==true){
            if(name.equals("File")==true){
                System.out.println("Flie");
            }
            try{
                DatagramSocket Recv=new DatagramSocket(2014);//创建UDP接收对象
                DatagramPacket pack=new DatagramPacket(data,data.length);//创建UDP接收数据包
                while(true){
                    Recv.receive(pack);
                    FileDialog saveFile=new FileDialog(this,"保存文件",FileDialog.SAVE);
                    saveFile.setVisible(true);
                    FileOutputStream fileWrite=null;
                    File file=new File(saveFile.getDirectory(),saveFile.getFile());
                    try{//写文件
                        fileWrite=new FileOutputStream (file);
                        fileWrite.write(pack.getData());
                        fileWrite.close();
                    }
                    catch(IOException ee){
                        System.out.println("错误"+ee.getMessage());
                    }
                    finally{
                        if(fileWrite!=null){
                            try{fileWrite.close();}
                            catch(IOException ee){System.out.println("错误"+ee.getMessage());}
                        }
                    }//写文件结束
                }
            }
            catch(Exception e1){}
        }
    }
}



class chat{
    static ExecutorService exe=Executors.newSingleThreadExecutor();
    public static void main(String args[]) {

        MyExtendsJFrame frame=new MyExtendsJFrame();//创建聊天程序窗口
        Thread tText=new Thread(frame);//创建接收线程
        tText.setName("Text") ;
        tText.start(); //开启线程
        exe.submit(tText);

        Thread tFile=new Thread(frame);//创建接收线程
        tFile.setName("File") ;
        tFile.start(); //开启线程
        exe.submit(tFile);
    }
}

