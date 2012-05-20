import java.net.*;
import java.io.*;
//클라이언트
public class EchoClient{
	Socket s;
	BufferedReader br;
	BufferedWriter bw;
	BufferedReader brMsg;
	String msg;
	String returnMsg;
	boolean isStop = false;
	public EchoClient(String ip,int port) throws UnknownHostException ,IOException{
		//3.소켓 생성
		s = new Socket(ip,port);
		//4.스트림 생성
		br= new BufferedReader (new InputStreamReader(s.getInputStream()));
		bw= new BufferedWriter( new OutputStreamWriter(s.getOutputStream()));
		while(! isStop){
			//5.메시지 송신
			brMsg = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("보낼 메시지:");
			msg = brMsg.readLine();

			bw.write(msg);
			bw.newLine();
			bw.flush();
			//8.메시지 수신
			returnMsg =br.readLine();
			if(returnMsg.equals("exit")){
					System.out.println("클라이언트 접속 종료");
					isStop =true;
			}else{
			System.out.println("돌아온메시지" + returnMsg);
			}
		}
		//9.소켓종료
		if(s != null) s.close();
		if(br != null) br.close();
		if(bw !=null) bw.close();
		if(brMsg != null) brMsg.close();

	}//생성자
	public static void main(String[] args) throws UnknownHostException ,IOException{	
		new EchoClient("192.168.42.95",3000);
		//내자리 ip :127,0,0,1 이름:localhost
	}
}
