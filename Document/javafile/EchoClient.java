import java.net.*;
import java.io.*;
//Ŭ���̾�Ʈ
public class EchoClient{
	Socket s;
	BufferedReader br;
	BufferedWriter bw;
	BufferedReader brMsg;
	String msg;
	String returnMsg;
	boolean isStop = false;
	public EchoClient(String ip,int port) throws UnknownHostException ,IOException{
		//3.���� ����
		s = new Socket(ip,port);
		//4.��Ʈ�� ����
		br= new BufferedReader (new InputStreamReader(s.getInputStream()));
		bw= new BufferedWriter( new OutputStreamWriter(s.getOutputStream()));
		while(! isStop){
			//5.�޽��� �۽�
			brMsg = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("���� �޽���:");
			msg = brMsg.readLine();

			bw.write(msg);
			bw.newLine();
			bw.flush();
			//8.�޽��� ����
			returnMsg =br.readLine();
			if(returnMsg.equals("exit")){
					System.out.println("Ŭ���̾�Ʈ ���� ����");
					isStop =true;
			}else{
			System.out.println("���ƿ¸޽���" + returnMsg);
			}
		}
		//9.��������
		if(s != null) s.close();
		if(br != null) br.close();
		if(bw !=null) bw.close();
		if(brMsg != null) brMsg.close();

	}//������
	public static void main(String[] args) throws UnknownHostException ,IOException{	
		new EchoClient("192.168.42.95",3000);
		//���ڸ� ip :127,0,0,1 �̸�:localhost
	}
}
