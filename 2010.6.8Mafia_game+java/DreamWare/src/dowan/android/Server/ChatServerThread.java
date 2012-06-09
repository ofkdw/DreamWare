package dowan.android.Server;


import java.io.*;
import java.net.*;
import java.util.*;

//�곥깵�껁깉�듐꺖�먦궧�с긿��
public class ChatServerThread extends Thread {
	private static List<ChatServerThread> threads = new ArrayList<ChatServerThread>();// �밤꺃�껁깋��
	private Socket socket;// �썬궞�껁깉

	// �녈꺍�밤깉�⒲궚��
	public ChatServerThread(Socket socket) {
		super();
		this.socket = socket;
		threads.add(this);
	}

	// ��릤
	public void run() {
		InputStream in = null;
		String message;
		int size;
		byte[] w = new byte[10240];
		try {
			// �밤깉�ゃ꺖��
			in = socket.getInputStream();
			while (true) {
				try {
					// �쀤에孃끹걾
					size = in.read(w);
					// �뉑뼪
					if (size <= 0)
						throw new IOException();
					// 沃�겳渦쇈겳
					message = new String(w, 0, size, "UTF8");
					// �ⓨ뱻�ャ깳�껁궩�쇈궦�곦에
					sendMessageAll(message);
				} catch (IOException e) {
					socket.close();
					threads.remove(this);
					return;
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	// �ⓨ뱻�ャ깳�껁궩�쇈궦�곦에
	public void sendMessageAll(String message) {
		ChatServerThread thread;
		for (int i = 0; i < threads.size(); i++) {
			thread = (ChatServerThread) threads.get(i);
			if (thread.isAlive())
				thread.sendMessage(this, message);
		}

	}

	// �▲긿�삠꺖�면�岳�
	public void sendMessage(ChatServerThread talker, String message) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())), true);
			out.println(message);
			out.flush();
		} catch (IOException e) {
		}
	}
}
