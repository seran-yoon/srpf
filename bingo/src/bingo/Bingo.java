package bingo;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Bingo extends JFrame{
	
	final int SIZE = 5; //빙고판의 크기를 만들기 위해 칸의 갯수를 상수로 지정해줌
	JButton[] btnArr = null;
	
	//버튼위치에 숨겨져있는 숫자를 담을 배열 변수
	int[] bingNum = new int[SIZE*SIZE];
	
	//완성된 라인의 수를 저장할 변수
	int bingoCount = 0;
	
	//빙고판 체크여부를 확인하기 위한 배열
	private boolean[][] btnOX = new boolean[SIZE][SIZE];
	
	//빙고판 상단에 메시지를 보여줌
	JLabel msgView = new JLabel("빙고");
	
	//디폴트 생성자
	Bingo() {
		this("빙고 게임");
	}
	
	Bingo(String title) {
		super(title);
		setLayout(null); //레이아웃 매니저를 해제한다
	
		JPanel p1 = new JPanel(); //메시지를 보여주는 영역
		JPanel p2 = new JPanel(); //버튼을 배치할 영역
		
		p1.setBackground(new Color(33, 99, 255)); //RGB로 직접 지정 -> 파란색 계열임
		msgView.setFont(new Font("굴림", Font.ITALIC + Font.BOLD, 24));
		msgView.setForeground(Color.WHITE); //폰트 색을 변경해줌
		
		//버튼이 올라갈 JPanel의 레이아웃을 GridLayout으로 한다
		p2.setLayout(new GridLayout(SIZE, SIZE));
		
		MyEventHandler handler = new MyEventHandler();
		addWindowListener(handler);
		
		//5*5=25개의 버튼을 담을 객체배열을 만든다
		btnArr = new JButton[SIZE*SIZE];
		
		//버튼 위치에 숨겨져 있는 숫자를 만든다(bingNum)
		//단, 1~25까지의 숫자가 중복되게 만들면 안됨 (중복된 숫자가 나오면 다시 숫자를 뽑게 만들어야 함) 
		for(int i=0;i<SIZE*SIZE;i++){
			bingNum[i] = (int)(1 + Math.random()*(SIZE*SIZE));
			//맨처음에 뽑은 숫자는 비교할 필요가 없다
			if(i>0){
				//현재 뽑은 숫자 전의 숫자들과 비교를 한다
				for(int j=0;j<i;j++){
					if(bingNum[j] == bingNum[i]){
						i--; //다시 숫자를 뽑을 수 있게 i값을 감소시킨다
					}
				}
			}
		}
		
		//p2에 버튼을 추가
		for(int i=0;i<SIZE*SIZE;i++){
			btnArr[i] = new JButton(String.valueOf(bingNum[i])); //버튼을 만들 때 이름도 같이 넣어준다(랜덤으로 만든 수 bingNum을 버튼의 이름으로! -> int형이기 때문에 이름으로 넣어주려면 String으로 형변환 해줘야 함)
			btnArr[i].addActionListener(handler);
			
			p2.add(btnArr[i]); //만든 버튼을 JPanel에 부착한다
		}
		
		//Frame에 컴포넌트들을 배치시킨다
		p1.add(msgView);
		p1.setBounds(0, 0, 500, 40);
		p2.setBounds(0, 40, 500, 500);
		
		add(p1);
		add(p2);
		
		setBounds(500, 200, 510, 580);
		setVisible(true); //화면 출력하고 보여지게 함
		
	}
	
	class MyEventHandler extends WindowAdapter implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JButton btn = (JButton)e.getSource();
			
			//버튼을 눌렀을때 바꿔주는 이벤트를 만듬
			for(int i=0;i<SIZE*SIZE;i++){
				if((JButton)btn == btnArr[i]){
					System.out.println(">> Selected [btnArr] Button Number : " + i);
					System.out.println(">> Selected [bingNum] Button Number : " + bingNum[i]);
					btnArr[i].setFont(new Font("Arial", Font.BOLD, 20));
					
					//현재 누른 버튼에 해당하는 btnOX를 true로 바꾼다
					int cnt = 0;
					for(int x=0;x<SIZE;x++){
						for(int y=0;y<SIZE;y++){
							if(i == cnt){
								btnOX[x][y] = true;
							}
							cnt++; //2차원 배열을 1차원 배열로 바꾸기 위해서
						}
					}
					
				}
			}
			
			btn.setBackground(Color.YELLOW); //선택한 버튼의 배경색을 변경한다
			
			//빙고가 완성되었는지 검사한다
			if(checkBingo() == true){
				String str = null;
				str = "축하합니다! 5Bingo~!";
				System.out.println(str);
				
				//빙고판 상태에다가 축하메시지를 보여준다
				msgView.setText(str);
				
				//YES_NO_OPTION에서 클릭한 버튼 값을 dialogBtn에 저장해줌 
				// JOptionPane의 ConfirmDialog는 결과에 따라 정수값을 반환한다. => YES_OPTION은 0, NO_OPTION은 1, CLOSED_OPTION은 -1
				int dialogBtn = JOptionPane.showConfirmDialog(null, "게임을 다시 시작하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(dialogBtn == JOptionPane.YES_OPTION){ //yes버튼 누르면 게임을 다시 시작할 수 있도록 해줌
					dispose(); //현재의 frame을 종료시켜주고, 
					new Bingo("빙고 게임"); //새로운 frame을 띄워줌
				}else if(dialogBtn == JOptionPane.NO_OPTION || dialogBtn == JOptionPane.CLOSED_OPTION){ //no버튼을 누르거나 상단의 x표시로 dialog창을 닫아주면 그냥 암것도 안하고 다이얼로그 상자를 닫는다
					return;
				}
				
			}
			
			//빙고판의 상태를 콘솔에 출력
			displayOX();
		}
		
		//Frame의 우측 상단의 x버튼을 누르면 프로그램을 종료하게 한다
		public void windowClosing(WindowEvent e){ //WindowAdapter의 내장함수
			e.getWindow().setVisible(false);
			e.getWindow().dispose();
			System.exit(0);
		}
		
	}//end - class MyEventHandler extends WindowAdapter implements ActionListener
	
	//콘솔에 OX를 출력
	public void displayOX(){
		//배열 btnOX를 콘솔 화면에 출력한다
		//false => X, true => 0으로 바꾸어서 출력
		
		for(int i=0;i<btnOX.length;i++){
			for(int j=0;j<btnOX[i].length;j++){
//				if(btnOX[i][j] == false){
//					System.out.print("X");
//				}else{
//					System.out.print("O");
//				}
				System.out.print(btnOX[i][j] ? "O":"X"); //위의 if-else문을 삼항연산자로 줄여서 쓸 수 있음
				
			}
			System.out.println();
		}
		System.out.println("현재 맞춘 줄의 개수 : " + bingoCount);
		System.out.println("----------------------");
		msgView.setText(bingoCount + " Bingo!"); //Label에서 보여줌
		
	}
	
	//빙고가 완성되었는지 검사한다
	boolean checkBingo(){
		this.bingoCount = 0; //완성된 라인의 수
		int garoCnt = 0; //가로
		int seroCnt = 0; //세로
		int crossCnt1 = 0; //대각선(/)
		int crossCnt2 = 0; //역대각선(\)
		
		for(int i=0;i<btnOX.length;i++){
			for(int j=0;j<btnOX[i].length;j++){
				
				//5개가 연속으로 되어있는것은 green으로 배경색을 바꾼다
				//가로 검사
				if(btnOX[i][j] == true){
					garoCnt++; //O인것의 갯수를 누적시킨다
					if(garoCnt == SIZE){ //garoCnt가 누적된 숫자와 SIZE의 숫자와 같으면 1Bingo가 된것
						int begin = i*SIZE;//배경색을 칠해야 할 시작 위치
						int end = begin+SIZE;//배경색을 칠해야 할 끝나는 위치
						for(int n=begin;n<end;n++){
							btnArr[n].setBackground(Color.GREEN);
						}
					}
				}
				
				//세로 검사
				if(btnOX[j][i] == true){
					seroCnt++;
					if(seroCnt == SIZE){
						for(int n=i;n< btnArr.length;n+=5) {
							btnArr[n].setBackground(Color.GREEN);
						}
					}
				}
				
				//대각선 검사
				if(i==j && btnOX[i][j]  == true){
					crossCnt1++;
					if(crossCnt1 == SIZE){
						for(int n=0;n<btnArr.length;n+=6){
							btnArr[n].setBackground(Color.GREEN);
						}
					}
				}
				
				//역대각선 검사
				if((btnOX.length-1)-i == j && btnOX[i][j]  == true){
					crossCnt2++;
					if(crossCnt2 == SIZE){
						for(int n=(btnArr.length-4);n>0;n--){
							if(n%4 == 0){
								btnArr[n].setBackground(Color.GREEN);
							}
						}
					}
				}
				
			}//end - row for문(두번째 for문)
			
			//한줄에 대한 검사가 끝났으면 가로줄의 값이 5인지 확인
			if(garoCnt == SIZE){
				bingoCount++;
			}
			//한줄에 대한 검사가 끝났으면 줄의 세로값이 5인지 확인
			if(seroCnt == SIZE){
				bingoCount++;
			}
			
			//검사에 사용된 변수를 초기화 해준다(5번 검사를 해줘야 하기 때문에)
			garoCnt = 0; 
			seroCnt = 0;
			
		}//end - column for문(첫번째 for문)
		
		//대각선과 역대각선은 1개씩만 존재하므로 1번씩만 검사하면 됨
		if(crossCnt1 == SIZE){
			bingoCount++;
		}
		if(crossCnt2 == SIZE){
			bingoCount++;
		}
		
		return bingoCount >= SIZE; //저 조건이 맞으면 true로 보내주는거고, 조건에 안맞으면 false로 보내줌
	}//end - boolean checkBingo()
	
	
	
	public static void main(String[] args) {
		//빙고판 밑에 임의의 숫자(1~25)를 생성하여 숨겨둔다
		//숫자를 클릭하면 가로,세로,사선의 4방향에 대해서 연속하여 5개가 맞추어졌는지 검사한다
		
		new Bingo("빙고 게임");
	}

}
