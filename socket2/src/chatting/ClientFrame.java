package chatting;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


//**채팅창의 화면 생성

class Id extends JFrame implements ActionListener {
	static JTextField tf = new JTextField(8);
	JButton btn = new JButton("입력");
	
	WriteThread wt;
	ClientFrame cf;
	
	//생성자
	public Id() {
		
	}

	public Id(WriteThread wt, ClientFrame cf) {
		super("아이디");
		this.wt = wt;
		this.cf = cf;
		
		setLayout(new FlowLayout());
		add(new JLabel("아이디"));
		add(tf);
		add(btn);
		
		btn.addActionListener(this);
		setBounds(300, 300, 250, 100);
		setVisible(true);
	}//end - public Id(WriteThread wt, ClientFrame cf)
	
	public void actionPerformed(ActionEvent e){
		wt.sendMsg();
		cf.isFirst = false;
		cf.setVisible(true);
		this.dispose();
	}//end - public void actionPerformed(ActionEvent e)

	static public String getId(){
		return tf.getText();
	}
	
}//end - class Id extends JFrame implements ActionListener

//------------------------------------------------------------------------
//채팅창에서 글쓰고, 글 보내주는 기능
public class ClientFrame extends JFrame implements ActionListener {
	
	JTextArea txtA = new JTextArea();
	JTextField txtF = new JTextField(15);
	JButton btnTransfer = new JButton("전송");
	JButton btnExit = new JButton("닫기");
	JPanel pl = new JPanel();
	
	boolean isFirst = true; //처음 연결된 서버인지 아닌지 확인하기 위함
	
	Socket socket;
	WriteThread wt;
	
	public ClientFrame(Socket socket) {
		super("대화 나누기");
		this.socket = socket;
		wt = new WriteThread(this);
		
		new Id(wt, this);
		
		txtA.setFont(new Font("굴림", Font.ITALIC + Font.BOLD, 24));
		txtA.setBackground(Color.YELLOW);
		add("Center", txtA);
		
		pl.add(txtF);
		pl.add(btnTransfer);
		pl.add(btnExit);
		add("South", pl);
		
		//메세지를 전송하는 클래스를 생성
		btnTransfer.addActionListener(this);
		btnExit.addActionListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE); //닫힘버튼 활성화
		setBounds(300, 300, 500, 500);
		setVisible(false); //setVisible을 false로 한 이유는 채팅창보다 id입력창이 먼저 나와야 하기 때문에
	}//end - public ClientFrame(Socket socket)

	@Override
	public void actionPerformed(ActionEvent e) {
		String id = Id.getId();
		if(e.getSource() == btnTransfer){ //전송버튼이 눌렀을 경우
			//메시지가 없이 전송버튼을 눌렀을 경우
			if(txtF.getText().equals("")){
				return;
			}
			//내가 입력한 메시지는 내 화면에 출력
			txtA.append("[" + id + "]" + txtF.getText() + "\n");
			wt.sendMsg(); //입력한 내용을 전송
			txtF.setText(""); //전송이 끝나면 입력창에 있는 메시지를 지워준다
		}else{ //닫기버튼이 눌렀을 경우
			this.dispose();
		}
		
	}//end - public void actionPerformed(ActionEvent e)

}//end - public class ClientFrame extends JFrame implements ActionListener
