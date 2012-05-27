import java.net.*;
import java.io.*;
//서버
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
					//4.스트림 생성
			br= new BufferedReader (new InputStreamReader(s.getInputStream()));
			bw= new BufferedWriter( new OutputStreamWriter(s.getOutputStream()));
			while(!isStop){
				//6.메시지 출력
				msg =br.readLine();
				if(msg.equals("exit")){
					System.out.println("클라이언트 접속종료");
					isStop =true;
				}else{
					System.out.println("받은 메시지:"+msg);
				}
				//7.메시지 송신
				bw.write(msg);
				bw.newLine();
				bw.flush();
			}
			//9.소켓종료
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
	//1.서버 소켓 생성
		ss = new ServerSocket(port);
		while(true){
		//2.서버 소켓 청취
			System.out.println("클라이언트 접속 대기중...");
			s=ss.accept();
			System.out.println("접속한 클라이언트 : " +s.getInetAddress().getHostName());

			EchoThread et = new EchoThread(s);
			et.start();
		}
	}//생성자
	public static void main(String[] args) throws IOException{
		new EchoServer(3000);
	}
}
