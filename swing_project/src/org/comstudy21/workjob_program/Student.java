package org.comstudy21.workjob_program;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.comstudy21.dao.StudentDao;
import org.comstudy21.dto.StudentDto;

public class Student extends JFrame implements MouseListener, ActionListener {

	JPanel contentPane = new JPanel(new BorderLayout());

	Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

	JPanel buttonPan = new JPanel(); //버튼 화면
	JPanel menuPan = new JPanel(); //입력창 화면
	JPanel gendandrePan = new JPanel(new GridLayout(1, 2)); //gender, resume 합쳐서 보여주기 위함
	JPanel buttonPan2 = new JPanel();

	Box menuBox = Box.createVerticalBox(); //입력창안에 들어갈 박스(수직으로 쌓임)

	// 테이블
	Vector data = null;
	Vector<String> cols = null;

	DefaultTableModel model;
	JTable table;
	JScrollPane js;

	String str[] = { "번호", "이름", "이메일", "전화번호", "생년월일", "성별", "이력서유무", "희망 연봉", "희망 근무 지역", "구직 타입", "특이사항" };

	// 버튼
	JButton btn01 = new JButton("전체보기");
	JButton btn02 = new JButton("추가");
	JButton btn03 = new JButton("수정");
	JButton btn04 = new JButton("삭제");
	JButton btn05 = new JButton("검색");
	JButton btn06 = new JButton("새로고침");

	// 개인정보
	Box insertBox = Box.createVerticalBox(); //개인정보 박스

	Border insertBd = BorderFactory.createTitledBorder(border, "개인 정보");

	JPanel insertPan = new JPanel();
	JPanel idPan = new JPanel();
	JPanel namePan = new JPanel();
	JPanel emailPan = new JPanel();
	JPanel phonePan = new JPanel();
	JPanel bdayPan = new JPanel();

	JLabel idL = new JLabel("번호               ");
	JLabel nameL = new JLabel("이름               ");
	JLabel emailL = new JLabel("이메일           ");
	JLabel edotL = new JLabel("@");
	JLabel phoneL = new JLabel("전화번호       ");
	JLabel pdotL01 = new JLabel("-");
	JLabel pdotL02 = new JLabel("-");
	JLabel bdayL = new JLabel("생년월일      ");
	JLabel year = new JLabel("년");
	JLabel month = new JLabel("월");
	JLabel day = new JLabel("일");

	JTextField idF = new JTextField(20);
	JTextField nameF = new JTextField(20);
	JTextField emailF01 = new JTextField(9);
	JTextField emailF02 = new JTextField(9);
	JTextField phoneF01 = new JTextField(5);
	JTextField phoneF02 = new JTextField(6);
	JTextField phoneF03 = new JTextField(6);

	String[] yearArr;
	{
		yearArr = new String[30];
		for (int i = 0; i < yearArr.length; i++) {
			if(i>0)
				yearArr[i] = (i-1) + 1980 + "";
			else
				yearArr[i] = "-----";
		}
	}
	JComboBox<String> yearList = new JComboBox<>(yearArr);
	JScrollPane yearS = new JScrollPane(yearList);

	String[] monthArr;
	{
		monthArr = new String[13];
		for (int i = 0; i < monthArr.length; i++) {
			if(i>0)
				monthArr[i] = i + 0 + "";
			else
				monthArr[i] = "----";
		}
	}
	JComboBox<String> monthList = new JComboBox<>(monthArr);
	JScrollPane monthS = new JScrollPane(monthList);

	String[] dayArr;
	{
		dayArr = new String[32];
		for (int i = 0; i < dayArr.length; i++) {
			if(i>0)
				dayArr[i] = i + 0 + "";
			else
				dayArr[i] = "----";
		}
	}
	JComboBox<String> dayList = new JComboBox<>(dayArr);
	JScrollPane dayS = new JScrollPane(dayList);

	// 성별
	Border genderBd = BorderFactory.createTitledBorder(border, "성별");

	JPanel genderPan = new JPanel();
	JPanel genderrbPan = new JPanel();

	ButtonGroup genderGr = new ButtonGroup();

	JRadioButton mrb = new JRadioButton("남성", false);
	JRadioButton frb = new JRadioButton("여성", false);

	// 이력서유무
	Border resumeBd = BorderFactory.createTitledBorder(border, "이력서 유무");

	JPanel resumePan = new JPanel();
	JPanel resumerbPan = new JPanel();

	ButtonGroup resumeGr = new ButtonGroup();

	JRadioButton Yrb = new JRadioButton("YES", false);
	JRadioButton Nrb = new JRadioButton("NO", false);

	// 희망연봉
	Border salaryBd = BorderFactory.createTitledBorder(border, "희망 연봉");

	JPanel salaryPan = new JPanel();
	JPanel wonPan = new JPanel();

	String[] sal = { "----------", "2000~2200", "2200~2400", "2400~2600", "2600~2800", "2800~3000", "3000~3500" };
	JComboBox<String> salList = new JComboBox<String>(sal);
	JScrollPane salS = new JScrollPane(salList);

	JLabel wonL = new JLabel("만원");

	// 희망지역
	Border areaBd = BorderFactory.createTitledBorder(border, "희망 근무 지역");

	JPanel areaPan = new JPanel();
	JPanel ckbPan = new JPanel(new GridLayout(3, 3));

	JCheckBox[] ckb = new JCheckBox[9];
	String[] ckbName = { "서울", "경기", "인천", "대전/충청", "대구/부산", "광주/전라", "경상", "강원", "제주" };
	{
		for (int i = 0; i < ckb.length; i++) {
			ckb[i] = new JCheckBox(ckbName[i]);
			add(ckb[i]);
		}
	}

	// 구직타입
	Border typeBd = BorderFactory.createTitledBorder(border, "구직 타입");

	JPanel typePan = new JPanel();
	JPanel typerbPan = new JPanel();

	ButtonGroup typeGr = new ButtonGroup();

	JRadioButton nrb = new JRadioButton("신입", false);
	JRadioButton crb = new JRadioButton("경력", false);
	JRadioButton corb = new JRadioButton("경력 단절", false);

	// 특이사항
	Border memoBd = BorderFactory.createTitledBorder(border, "특이사항");

	JTextArea memoA = new JTextArea(3, 30);
	JScrollPane memoS = new JScrollPane(memoA);

	JPanel memoPan = new JPanel();

	// 참조
	StudentDto dto = new StudentDto();
	StudentDao dao = new StudentDao();

	public Student() {

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("취업지원센터 구직자 관리 프로그램");
		setSize(1300, 800);
		setContentPane(contentPane);

		init(); // UI 구현

		start(); // 이벤트 실행

	}

	private void init() {

		contentPane.add(buttonPan, BorderLayout.SOUTH);
		buttonPan.add(btn01);
		buttonPan.add(btn02);
		buttonPan.add(btn03);
		buttonPan.add(btn04);
		buttonPan.add(btn05);

		contentPane.add(menuPan, BorderLayout.WEST);
		menuPan.add(menuBox);

		menuBox.add(insertPan);
		insertPan.add(insertBox);
		insertBox.add(idPan);
		idPan.add(idL);
		idPan.add(idF);
		insertBox.add(namePan);
		namePan.add(nameL);
		namePan.add(nameF);
		insertBox.add(emailPan);
		emailPan.add(emailL);
		emailPan.add(emailF01);
		emailPan.add(edotL);
		emailPan.add(emailF02);
		insertBox.add(phonePan);
		phonePan.add(phoneL);
		phonePan.add(phoneF01);
		phonePan.add(pdotL01);
		phonePan.add(phoneF02);
		phonePan.add(pdotL02);
		phonePan.add(phoneF03);
		insertBox.add(bdayPan);
		bdayPan.add(bdayL);
		bdayPan.add(yearS);
		bdayPan.add(year);
		bdayPan.add(monthS);
		bdayPan.add(month);
		bdayPan.add(dayS);
		bdayPan.add(day);
		insertPan.setBorder(insertBd);

		menuBox.add(gendandrePan);
		gendandrePan.add(genderPan);
		genderPan.add(genderrbPan);
		genderGr.add(mrb);
		genderGr.add(frb);
		genderrbPan.add(mrb);
		genderrbPan.add(frb);
		genderPan.setBorder(genderBd);

		gendandrePan.add(resumePan);
		resumePan.add(resumerbPan);
		resumeGr.add(Yrb);
		resumeGr.add(Nrb);
		resumerbPan.add(Yrb);
		resumerbPan.add(Nrb);
		resumePan.setBorder(resumeBd);

		menuBox.add(salaryPan);
		salaryPan.add(wonPan);
		wonPan.add(salS);
		wonPan.add(wonL);
		salaryPan.setBorder(salaryBd);

		menuBox.add(areaPan);
		areaPan.add(ckbPan);
		for (int i = 0; i < ckb.length; i++) {
			ckbPan.add(ckb[i]);
		}
		areaPan.setBorder(areaBd);

		menuBox.add(typePan);
		typePan.add(typerbPan);
		typeGr.add(nrb);
		typeGr.add(crb);
		typeGr.add(corb);
		typerbPan.add(nrb);
		typerbPan.add(Box.createHorizontalStrut(15));
		typerbPan.add(crb);
		typerbPan.add(Box.createHorizontalStrut(15));
		typerbPan.add(corb);
		typePan.setBorder(typeBd);

		menuBox.add(memoPan);
		memoPan.add(memoS);
		memoPan.setBorder(memoBd);
		
		menuBox.add(buttonPan2);
		buttonPan2.add(btn06);

		data = dao.getStudentList(null);
		cols = getColumn();
		model = new DefaultTableModel(data, cols);
		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false); //테이블 이동 안되게
		js = new JScrollPane(table);
		contentPane.add(js, BorderLayout.CENTER);
	}

	private Vector<String> getColumn() {

		Vector<String> columnName = new Vector<>();
		for (String columnArr : str) {
			columnName.add(columnArr);
		}

		return columnName;
	}

	//갱신 메소드
	public void jTableRefresh() {

		Vector<Vector> data = dao.getStudentList(null);
		model.setDataVector(data, cols);
		model.fireTableDataChanged();
	}

	// 테이블의 회원정보를 가지고 입력창에 셋팅해주는 메소드
	private void viewData(StudentDto dto) {

		String id = dto.getId();
		String name = dto.getName();
		String email = dto.getEmail();
		String phone = dto.getPhone();
		String bday = dto.getBday();
		String gender = dto.getGender();
		String resume = dto.getResume();
		String salary = dto.getSalary();
		String area = dto.getArea();
		String type = dto.getType();
		String memo = dto.getMemo();

		// 입력창에 셋팅
		idF.setText(id);
		nameF.setText(name);
		String[] emails = email.split("@");
		emailF01.setText(emails[0]);
		emailF02.setText(emails[1]);
		String[] phones = phone.split("-");
		phoneF01.setText(phones[0]);
		phoneF02.setText(phones[1]);
		phoneF03.setText(phones[2]);
		String[] bdays = bday.split("-");
		yearList.setSelectedItem(bdays[0]);
		monthList.setSelectedItem(bdays[1]);
		dayList.setSelectedItem(bdays[2]);
		if (gender.equals("남성")) {
			mrb.setSelected(true);
		} else if (gender.equals("여성")) {
			frb.setSelected(true);
		}
		if (resume.equals("Y")) {
			Yrb.setSelected(true);
		} else if (resume.equals("N")) {
			Nrb.setSelected(true);
		}
		salList.setSelectedItem(salary);
		String[] areas = area.split(" ");
		for (int i = 0; i < ckb.length; i++) {
			for(int j = 0; j<areas.length; j++){
				if(areas[j].equals(ckbName[i])){
					ckb[i].setSelected(true);
				}
			} //중복으로 값이 출력됨
		}
		if (type.equals("신입")) {
			nrb.setSelected(true);
		} else if (type.equals("경력")) {
			crb.setSelected(true);
		} else if (type.equals("경력 단절")) {
			corb.setSelected(true);
		}
		memoA.setText(memo);

	}

	//입력창에서 입력한 내용을 얻어 dto에 저장해 테이블에 쌓이는 메소드
	private StudentDto getviewData() {

		String id = idF.getText().trim();
		String name = nameF.getText().trim();
		String email01 = emailF01.getText().trim();
		String email02 = emailF02.getText().trim();
		String email = email01 + "@" + email02;
		String phone01 = phoneF01.getText().trim();
		String phone02 = phoneF02.getText().trim();
		String phone03 = phoneF03.getText().trim();
		String phone = phone01 + "-" + phone02 + "-" + phone03;
		String bday01 = (String) yearList.getSelectedItem();
		String bday02 = (String) monthList.getSelectedItem();
		String bday03 = (String) dayList.getSelectedItem();
		String bday = bday01 + "-" + bday02 + "-" + bday03;
		String gender = "";
		if (mrb.isSelected()) {
			gender = "남성";
		} else if (frb.isSelected()) {
			gender = "여성";
		}
		String resume = "";
		if (Yrb.isSelected()) {
			resume = "Y";
		} else if (Nrb.isSelected()) {
			resume = "N";
		}
		String salary = (String) salList.getSelectedItem();
		String area = "";
		for (int i = 0; i < ckb.length; i++) {
			if (ckb[i].isSelected()) {
				area = area + ckbName[i] + " ";
			}
		}
		String type = "";
		if (nrb.isSelected()) {
			type = "신입";
		} else if (crb.isSelected()) {
			type = "경력";
		} else if (corb.isSelected()) {
			type = "경력 단절";
		}
		String memo = memoA.getText();

		//dto에 담는다
		dto.setId(id);
		dto.setName(name);
		dto.setEmail(email);
		dto.setPhone(phone);
		dto.setBday(bday);
		dto.setGender(gender);
		dto.setResume(resume);
		dto.setSalary(salary);
		dto.setArea(area);
		dto.setType(type);
		dto.setMemo(memo);

		return dto;
	}
	
	//입력창 리셋해주는 메소드
	private void resetData() {
		
		idF.setText("");
		nameF.setText("");
		emailF01.setText("");
		emailF02.setText("");
		phoneF01.setText("");
		phoneF02.setText("");
		phoneF03.setText("");
		yearList.setSelectedItem(yearArr[0]);
		monthList.setSelectedItem(monthArr[0]);
		dayList.setSelectedItem(dayArr[0]);
		mrb.setSelected(false);
		frb.setSelected(false);
		Yrb.setSelected(false);
		Nrb.setSelected(false);
		salList.setSelectedItem(sal[0]);
		for (int i = 0; i < ckb.length; i++) {
			ckb[i].setSelected(false);
		}
		nrb.setSelected(false);
		crb.setSelected(false);
		corb.setSelected(false);
		memoA.setText("");
		
	}

	private void start() {

		table.addMouseListener(this);
		
		btn01.addActionListener(this); //전체보기
		btn02.addActionListener(this); //추가하기
		btn03.addActionListener(this); //수정하기
		btn04.addActionListener(this); //삭제하기
		btn05.addActionListener(this); //검색하기
		btn06.addActionListener(this); //새로고침하기
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btn01){ //전체보기
			dao.selectAll();
		} else if(e.getSource() == btn02){ //추가
			StudentDto getdto = getviewData();
			dao.insert(getdto);
		} else if(e.getSource() == btn03){ //수정
			StudentDto getdto = getviewData();
			dao.update(getdto);
		} else if(e.getSource() == btn04){ //삭제
			StudentDto getdto = getviewData();
			int x = JOptionPane.showConfirmDialog(this, "삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION);
			if(x == JOptionPane.YES_OPTION){
				dao.delete(getdto);
			}else if(x == JOptionPane.NO_OPTION){
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		} else if(e.getSource() == btn05){ //검색    
			String sName = JOptionPane.showInputDialog("검색할 학생의 이름를 입력해주세요");
			
			//defaulttablemodel에 있는 기존 데이터 지우기
			for(int i=0;i<model.getRowCount();){
				model.removeRow(i);
			}
			if(sName.trim().length()>0){//값이 입력되었으면 검색
				Vector<Vector> list = dao.select(new StudentDto("",sName,null,null,null,null,null,null,null,null,null));
				
				model.addRow(list.get(0));
				model.addRow(list.get(1));
				model.addRow(list.get(2));
				model.addRow(list.get(3));
				model.addRow(list.get(4));
				model.addRow(list.get(5));
				model.addRow(list.get(6));
				model.addRow(list.get(7));
				model.addRow(list.get(8));
				model.addRow(list.get(9));
				model.addRow(list.get(10));		
			}else{
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		}else if(e.getSource() == btn06){ //새로고침
			resetData();
		}
		
		jTableRefresh();
	} 
		


	public static void main(String[] args) {
		new Student().setVisible(true);
	}

	
	@Override
	public void mouseClicked(MouseEvent e) { }
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		int row = table.getSelectedRow();
		
		String Id = (String)table.getValueAt(row, 0); //해당 행의 객체를 가지고 옴
		String Name = (String)table.getValueAt(row, 1);
		String Email = (String)table.getValueAt(row, 2);
		String Phone = (String)table.getValueAt(row, 3);
		String Bday = (String)table.getValueAt(row, 4);
		String Gender = (String)table.getValueAt(row, 5);
		String Resume = (String)table.getValueAt(row, 6);
		String Salary = (String)table.getValueAt(row, 7);
		String Area = (String)table.getValueAt(row, 8);
		String Type = (String)table.getValueAt(row, 9);
		String Memo = (String)table.getValueAt(row, 10);
		
		dto = new StudentDto(Id, Name, Email, Phone, Bday, Gender, Resume, Salary, Area, Type, Memo);
		viewData(dto);
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }
	
}


