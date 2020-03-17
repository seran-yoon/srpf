package chatting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


//------------------------------------------------------------------
//키보드로 전송문자열을 입력받아서 서버로 전송하는 쓰레드
class WriteThread{ //메시지를 보낼때만 서버를 연결하면 되기 때문에 Thread를 extends하지 않아도 됨
	Socket socket;
	ClientFrame cf;
	String str;
	String id;

	public WriteThread(ClientFrame cf) {
		this.cf = cf;
		this.socket = cf.socket;
	}//end - public WriteThread(ClientFrame cf)
	
	public void sendMsg() {
		//키보드로부터 읽어오기 위한 스트림 객체 생성
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = null;
		
		try {
			//서버로 문자열을 전송하기 위한 스트림 객체를 생성
			pw = new PrintWriter(socket.getOutputStream(), true);
			
			//첫번째 데이터는 id. 상대방에게 id와 함께 내 ip주소를 전송
			if(cf.isFirst == true){
				InetAddress iaddr = socket.getLocalAddress(); //내 socket주소를 가져옴
				String ip = iaddr.getHostAddress();
				getId();
				str = "[" + id + "]님 로그인 (" + ip + ")";
				System.out.println("ip : " + ip + " / id : " + id); //콘솔창에서도 확인
			}else{
				str = "[" + id + "]" + cf.txtF.getText();
			}
			//입력받은 문자열을 서버로 보냄
			pw.println(str);
		} catch (IOException ie) {
			System.out.println(ie.getMessage());
		}finally {
			try {
				if(br != null)
					br.close();
			} catch (IOException ie) {
				System.out.println(ie.getMessage());
			}
		}
		
	}//end - private void sendMsg()
	
	public void getId() {
		id = Id.getId();
	}//end - public void getId()

}//end - class WriteThread


//------------------------------------------------------------------
//서버가 보내온 문자열을 전송받는 쓰레드
class ReadThread extends Thread{ //언제 메시지가 올지 모르니 서버를 항상 대기시켜놔야 해서 Thread를 extends 함
	Socket socket;
	ClientFrame cf;
	
	public ReadThread(Socket socket, ClientFrame cf) {
		this.cf = cf;
		this.socket = socket;
	}
	
	public void run(){
		BufferedReader br = null;
		try {
			//서버로부터 전송된 문자열을 읽어오기 위한 스트림 객체를 생성
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true){
				//소켓으로부터 문자열을 읽어온다
				String str = br.readLine();
				if(str == null){ //null일 경우 접속이 끊어진 경우
					System.out.println("접속이 끊어졌습니다.");
					break;
				}
				//전송받은 문자열을 화면에 출력한다
				cf.txtA.append(str + "\n");
				System.out.println("[server]" + str);
			}
		} catch (IOException ie) {
			System.out.println(ie.getMessage());
		} finally {
			try {
				if(br != null)
					br.close();
				if(socket != null)
					socket.close();
			} catch (IOException ie) {
				System.out.println(ie.getMessage());
			}
		}
		
	}//end - public void run()
	
}//end - class ReadThread extends Thread

//------------------------------------------------------------------
public class MultiChatClient {

	public static void main(String[] args) {
		
		Socket socket = null;
		ClientFrame cf;
		
		try {
			socket = new Socket("192.168.219.101", 3000); //127.0.0.1 = localhost //3000은 port번호
			System.out.println("연결 성공!");
			//연결이 성공하면 클라이언트 화면을 보여준다
			cf = new ClientFrame(socket);
			//메시지를 수신하기 위해서 쓰레드를 구동시킨다
			new ReadThread(socket, cf).start();
		} catch (IOException ie) {
			System.out.println(ie.getMessage());
		}
		
	}

}
