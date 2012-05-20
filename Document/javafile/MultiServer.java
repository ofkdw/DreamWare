import java.net.*;
import java.io.*;
import java.util.*;
class MultiServerThread implements Runnable{
    private Socket socket;
    private MultiServer ms;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    public MultiServerThread( MultiServer ms ){
        this.ms = ms;
    }
    public synchronized void run(){
        boolean isStop = false;
        try{
            socket = ms.getSocket();
            ois = new ObjectInputStream( socket.getInputStream() );
            oos = new ObjectOutputStream( socket.getOutputStream() );
            String message = null;
            while( !isStop ){
                message = ( String )ois.readObject();
                String[] str = message.split( "#" );
                if( str[1].equals( "exit" ) ){
                    broadCasting( message );
                    isStop = true;
                }else{
                    broadCasting( message );
                }
            }
            ms.getList().remove( this );
            System.out.println( socket.getInetAddress() + "정상적으로 종료하셨습니다" );
            System.out.println( "list size : " + ms.getList().size() );
        }catch(Exception e){
            ms.getList().remove( this );
            System.out.println( socket.getInetAddress() + "비정상적으로 종료하셨습니다" );
            System.out.println( "list size : "+ms.getList().size() );
        }
    }
    public void broadCasting( String message ) throws IOException{
        for( MultiServerThread ct : ms.getList() ){
           ct.send( message );
        }
    }
    public void send( String message )throws IOException{
        oos.writeObject( message );        
    }
}
public class MultiServer {
	private ArrayList< MultiServerThread > list;
	private Socket socket;
	public MultiServer() throws IOException {
		list = new ArrayList < MultiServerThread >();
		ServerSocket serverSocket = new ServerSocket( 5000 );
		MultiServerThread mst = null;
		boolean isStop = false;
		while( !isStop ){
				System.out.println( "Server read..." );
				socket = serverSocket.accept();
				mst = new MultiServerThread( this );
				list.add( mst );
				Thread t = new Thread( mst );
				t.start();
		}
	}
	public ArrayList< MultiServerThread > getList() {
		return list;
	}
	public Socket getSocket() {
		return socket;
	}
	public static void main( String args[] ) throws IOException {
		new MultiServer();
	}
}

