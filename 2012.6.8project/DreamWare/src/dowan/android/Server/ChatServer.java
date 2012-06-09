package dowan.android.Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//�곥깵�껁깉�듐꺖��
public class ChatServer {

	// �▲궎��
	public static void main(String[] args) {
		ChatServer server = new ChatServer();
		server.start(6001);
	}
	// �뗥쭓
	public void start(int port) {
		ServerSocket server;// �듐꺖�먦궫�긱긿��
		Socket socket;// �썬궞�껁깉
		ChatServerThread thread;// �밤꺃�껁깋

		try {
			server = new ServerSocket(port);
			System.err.println("�곥깵�껁깉�듐꺖�먨츪烏뚪뼀冶�" + port);
			while (true) {
				try {
					// �η텥孃끾찣
					socket = server.accept();

					// �곥깵�껁깉�듐꺖�먦궧�с긿�됮뼀冶�
					thread = new ChatServerThread(socket);
					thread.start();
				} catch (IOException e) {
				}
			}
		} catch (IOException e) {
			
		}
	}

	
}
