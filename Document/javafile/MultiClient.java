import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
class MultiClientThread extends Thread {
    private MultiClient mc;
    public MultiClientThread( MultiClient mc ){
        this.mc = mc; 
    }
    public void run(){
        String message = null;
        String[] receivedMsg = null;
        boolean isStop = false;
        while(!isStop){
            try{
                message = (String)mc.getOis().readObject();
                receivedMsg = message.split( "#" );
            }catch(Exception e){
                e.printStackTrace();
                isStop = true;
            }
            System.out.println(receivedMsg[0]+","+receivedMsg[1]);
            if(receivedMsg[1].equals( "exit" )){
                if(receivedMsg[0].equals(mc.getId())){
                    mc.exit();
                }else{
                    mc.getJta().append(
                    receivedMsg[0] +"���� ���� �ϼ̽��ϴ�."+
                    System.getProperty("line.separator"));
                    mc.getJta().setCaretPosition(
                    mc.getJta().getDocument().getLength());
                }
            }else{               
                mc.getJta().append(
                receivedMsg[0] +" : "+receivedMsg[1]+
                System.getProperty("line.separator"));
                mc.getJta().setCaretPosition(
                    mc.getJta().getDocument().getLength());
                
            }
        }
    }
}
public class MultiClient implements ActionListener {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private JFrame jframe;
    private JTextField jtf;
    private JTextArea jta;
    private JLabel jlb1, jlb2;
    private JPanel jp1, jp2;
    private String ip;
    private String id;
    private JButton jbtn;
    public MultiClient( String argIp, String argId ) {
        ip = argIp;
        id = argId;
        jframe = new JFrame( "Multi Chatting" );
        jtf = new JTextField( 30 );
        jta = new JTextArea( "", 10, 50 );
        jlb1 = new JLabel( "Usage ID : [[ " + id + "]]" );
        jlb2 = new JLabel( "IP : " + ip );
        jbtn = new JButton( "����" );
        jp1 = new JPanel();
        jp2 = new JPanel();
        jlb1.setBackground( Color.yellow );
        jlb2.setBackground( Color.green );
        jta.setBackground( new Color( 150, 0, 150 ) );
        jp1.setLayout( new BorderLayout() );
        jp2.setLayout( new BorderLayout() );

        jp1.add( jbtn, BorderLayout.EAST );
        jp1.add( jtf, BorderLayout.CENTER );
        jp2.add( jlb1, BorderLayout.CENTER );
        jp2.add( jlb2, BorderLayout.EAST );

        jframe.add( jp1, BorderLayout.SOUTH );
        jframe.add( jp2, BorderLayout.NORTH );
        JScrollPane jsp = new JScrollPane( jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        jframe.add( jsp, BorderLayout.CENTER );

        jtf.addActionListener( this );
        jbtn.addActionListener( this );
        jframe.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent e) {
                try {
                    oos.writeObject(id+"#exit");
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
                System.exit(0);
            }
            public void windowOpened(WindowEvent e) {
                jtf.requestFocus();
            }
        });
        jta.setEditable( false );
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
        jframe.pack();
        jframe.setLocation(  (screenWidth - jframe.getWidth()) / 2,  (screenHeight - jframe.getHeight()) / 2);
        jframe.setResizable( false );
        jframe.setVisible( true );
    }
    public void actionPerformed( ActionEvent e ) {
        Object obj = e.getSource();
        String msg = jtf.getText();
        if ( obj == jtf ) {
            if ( msg == null || msg.length()==0 ) {
                JOptionPane.showMessageDialog( jframe, "����������", "���", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    oos.writeObject( id + "#" + msg );
                } catch ( IOException ee ) {
                    ee.printStackTrace();
                }
                jtf.setText("");
            }
        } else if ( obj == jbtn ) {
            try {
                oos.writeObject( id + "#exit" );
            } catch ( IOException ee ) {
                ee.printStackTrace();
            }
            System.exit( 0 );
        }
    }
    public void exit(){
        System.exit( 0 );
    }
    public void init() throws IOException {
        socket = new Socket( "192.168.42.95" , 5000 );			// ip
        System.out.println( "connected..." );
        oos = new ObjectOutputStream( socket.getOutputStream() );
        ois = new ObjectInputStream( socket.getInputStream() );
        MultiClientThread ct = new MultiClientThread( this );
        Thread t = new Thread( ct );
        t.start();
    }
	public ObjectInputStream getOis(){
        return ois;
    }
    public JTextArea getJta(){
        return jta;
    }
    public String getId(){
        return id;
    }
    public static void main(String args[]) throws IOException {
        JFrame.setDefaultLookAndFeelDecorated(true);
        MultiClient cc = new MultiClient( args[0], args[1] );
        cc.init();
    }
}
