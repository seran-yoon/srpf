package chatting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

//**socket server 생성

//------------------------------------------------------------------------------------------
//클라이언트로부터 전송된 문자열을 받아서 다른 클라이언트에게 문자열을 보내주는 쿨래스
class EchoTrhead extends Thread{
	Socket socket;
	Vector<Socket> vec;
	
	public EchoTrhead(Socket socket, Vector<Socket> vec) {
		this.socket = socket;
		this.vec = vec;
	}//end - public EchoTrhead(Socket socket, Vector<Socket> vec)
	
	public void run() {
		BufferedReader br = null;
		try{
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String str = null;//데이터 내용을 담는 변수
			while(true){
				//클라이언트로부터 연락이 오는지 확인해야 함 -> 클라이언트로부터 문자열 받기
				str = br.readLine(); 
				//상대가 접속을 끊으면 break;
				if(str == null){
					//vector에서 접속을 종료한 상대방을 제거한다
					System.out.println("상대방이 연결을 끊었습니다");
					vec.remove(socket);
					break; //가장 근접한 반복문인 while문이 빠져나감
				}
				//메시지가 있으면 연결된 소켓들을 통해서 다른 클라이언트에게 문자열을 보낸다
				sendMsg(str);
			}
		}catch(IOException ie){
			System.out.println(ie.getMessage());
		}finally {
			try {//null이 아니라는것은 아직 열려있는 상태이므로 닫아줘야 함(socket, vector 둘다)
				if(br != null) 
					br.close();
				if(socket != null)
					socket.close();
			} catch (IOException ie) {
				System.out.println(ie.getMessage());
			}
		}
	}//end - public void run()
	
	//------------------------------------------------------------------------------------------
	//전송받은 문자열을 다른 클라이언트들에게 보내주는 메서드
	private void sendMsg(String str) {
		try {
			//for문을 사용하되 현재의 socket이 데이터를 보낸 클라이언트인 경우를 제외하고 나머지 socket들에게 데이터를 보내줌
			for(Socket socket:vec){
				if(socket != this.socket){
					PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
					
					pw.println(str);//콘솔창에도 메시지 주고받는거 보여주기 위함 -> 의미없음
					pw.flush();
					//단 여기서 얻어온 소켓들은 남의 것들이기 때문에 여기서 닫으면 안됨
				}
			}
		} catch (IOException ie) {
			System.out.println(ie.getMessage());
		}
	}//end - private void sendMsg()
	
	
}//end - class EchoTrhead extends Thread

//------------------------------------------------------------------------------------------
public class MultiChatServer {

	public static void main(String[] args) {
		ServerSocket server = null;
		Socket socket = null;
		
		Vector<Socket> vec = new Vector<Socket>();
		
		try {
			server = new ServerSocket(3000); //3000은 port번호
			while(true){
				System.out.println("접속 대기중...");
				socket = server.accept(); //server(ServerSocket)는 문지기! client와 연결이되면 그 주소값을 새로운 socket을 만들어 연결시켜줌 
				
				//클라이언트와 연결된 socket을 vector에 담는다
				vec.add(socket);
				
				new EchoTrhead(socket, vec).start(); //쓰레드를 구동한다
			}
		} catch (IOException ie) {
			System.out.println(ie.getMessage());
		}

	}//end - public static void main(String[] args)

}//end - public class MultiChatServer
