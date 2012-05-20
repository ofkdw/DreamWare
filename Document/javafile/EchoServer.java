import java.net.*;
import java.io.*;
//����
class EchoThread extends Thread{
	Socket s;
	BufferedReader br;
	BufferedWriter bw;
	String msg;
	boolean isStop =false;

	public EchoThread(Socket s){
		this.s =s;
	}
	public void run(){
		try{
					//4.��Ʈ�� ����
			br= new BufferedReader (new InputStreamReader(s.getInputStream()));
			bw= new BufferedWriter( new OutputStreamWriter(s.getOutputStream()));
			while(!isStop){
				//6.�޽��� ���
				msg =br.readLine();
				if(msg.equals("exit")){
					System.out.println("Ŭ���̾�Ʈ ��������");
					isStop =true;
				}else{
					System.out.println("���� �޽���:"+msg);
				}
				//7.�޽��� �۽�
				bw.write(msg);
				bw.newLine();
				bw.flush();
			}
			//9.��������
				if(s !=null) s.close();
				if(br != null) br.close();
				if(bw != null) bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
public class EchoServer{
	ServerSocket ss;
	Socket s;

	public EchoServer(int port) throws IOException{
	//1.���� ���� ����
		ss = new ServerSocket(port);
		while(true){
		//2.���� ���� û��
			System.out.println("Ŭ���̾�Ʈ ���� �����...");
			s=ss.accept();
			System.out.println("������ Ŭ���̾�Ʈ : " +s.getInetAddress().getHostName());

			EchoThread et = new EchoThread(s);
			et.start();
		}
	}//������
	public static void main(String[] args) throws IOException{
		new EchoServer(3000);
	}
}
