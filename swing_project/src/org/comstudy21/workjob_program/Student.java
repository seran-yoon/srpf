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

	JPanel buttonPan = new JPanel(); //��ư ȭ��
	JPanel menuPan = new JPanel(); //�Է�â ȭ��
	JPanel gendandrePan = new JPanel(new GridLayout(1, 2)); //gender, resume ���ļ� �����ֱ� ����
	JPanel buttonPan2 = new JPanel();

	Box menuBox = Box.createVerticalBox(); //�Է�â�ȿ� �� �ڽ�(�������� ����)

	// ���̺�
	Vector data = null;
	Vector<String> cols = null;

	DefaultTableModel model;
	JTable table;
	JScrollPane js;

	String str[] = { "��ȣ", "�̸�", "�̸���", "��ȭ��ȣ", "�������", "����", "�̷¼�����", "��� ����", "��� �ٹ� ����", "���� Ÿ��", "Ư�̻���" };

	// ��ư
	JButton btn01 = new JButton("��ü����");
	JButton btn02 = new JButton("�߰�");
	JButton btn03 = new JButton("����");
	JButton btn04 = new JButton("����");
	JButton btn05 = new JButton("�˻�");
	JButton btn06 = new JButton("���ΰ�ħ");

	// ��������
	Box insertBox = Box.createVerticalBox(); //�������� �ڽ�

	Border insertBd = BorderFactory.createTitledBorder(border, "���� ����");

	JPanel insertPan = new JPanel();
	JPanel idPan = new JPanel();
	JPanel namePan = new JPanel();
	JPanel emailPan = new JPanel();
	JPanel phonePan = new JPanel();
	JPanel bdayPan = new JPanel();

	JLabel idL = new JLabel("��ȣ               ");
	JLabel nameL = new JLabel("�̸�               ");
	JLabel emailL = new JLabel("�̸���           ");
	JLabel edotL = new JLabel("@");
	JLabel phoneL = new JLabel("��ȭ��ȣ       ");
	JLabel pdotL01 = new JLabel("-");
	JLabel pdotL02 = new JLabel("-");
	JLabel bdayL = new JLabel("�������      ");
	JLabel year = new JLabel("��");
	JLabel month = new JLabel("��");
	JLabel day = new JLabel("��");

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

	// ����
	Border genderBd = BorderFactory.createTitledBorder(border, "����");

	JPanel genderPan = new JPanel();
	JPanel genderrbPan = new JPanel();

	ButtonGroup genderGr = new ButtonGroup();

	JRadioButton mrb = new JRadioButton("����", false);
	JRadioButton frb = new JRadioButton("����", false);

	// �̷¼�����
	Border resumeBd = BorderFactory.createTitledBorder(border, "�̷¼� ����");

	JPanel resumePan = new JPanel();
	JPanel resumerbPan = new JPanel();

	ButtonGroup resumeGr = new ButtonGroup();

	JRadioButton Yrb = new JRadioButton("YES", false);
	JRadioButton Nrb = new JRadioButton("NO", false);

	// �������
	Border salaryBd = BorderFactory.createTitledBorder(border, "��� ����");

	JPanel salaryPan = new JPanel();
	JPanel wonPan = new JPanel();

	String[] sal = { "----------", "2000~2200", "2200~2400", "2400~2600", "2600~2800", "2800~3000", "3000~3500" };
	JComboBox<String> salList = new JComboBox<String>(sal);
	JScrollPane salS = new JScrollPane(salList);

	JLabel wonL = new JLabel("����");

	// �������
	Border areaBd = BorderFactory.createTitledBorder(border, "��� �ٹ� ����");

	JPanel areaPan = new JPanel();
	JPanel ckbPan = new JPanel(new GridLayout(3, 3));

	JCheckBox[] ckb = new JCheckBox[9];
	String[] ckbName = { "����", "���", "��õ", "����/��û", "�뱸/�λ�", "����/����", "���", "����", "����" };
	{
		for (int i = 0; i < ckb.length; i++) {
			ckb[i] = new JCheckBox(ckbName[i]);
			add(ckb[i]);
		}
	}

	// ����Ÿ��
	Border typeBd = BorderFactory.createTitledBorder(border, "���� Ÿ��");

	JPanel typePan = new JPanel();
	JPanel typerbPan = new JPanel();

	ButtonGroup typeGr = new ButtonGroup();

	JRadioButton nrb = new JRadioButton("����", false);
	JRadioButton crb = new JRadioButton("���", false);
	JRadioButton corb = new JRadioButton("��� ����", false);

	// Ư�̻���
	Border memoBd = BorderFactory.createTitledBorder(border, "Ư�̻���");

	JTextArea memoA = new JTextArea(3, 30);
	JScrollPane memoS = new JScrollPane(memoA);

	JPanel memoPan = new JPanel();

	// ����
	StudentDto dto = new StudentDto();
	StudentDao dao = new StudentDao();

	public Student() {

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("����������� ������ ���� ���α׷�");
		setSize(1300, 800);
		setContentPane(contentPane);

		init(); // UI ����

		start(); // �̺�Ʈ ����

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
		table.getTableHeader().setReorderingAllowed(false); //���̺� �̵� �ȵǰ�
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

	//���� �޼ҵ�
	public void jTableRefresh() {

		Vector<Vector> data = dao.getStudentList(null);
		model.setDataVector(data, cols);
		model.fireTableDataChanged();
	}

	// ���̺��� ȸ�������� ������ �Է�â�� �������ִ� �޼ҵ�
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

		// �Է�â�� ����
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
		if (gender.equals("����")) {
			mrb.setSelected(true);
		} else if (gender.equals("����")) {
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
			} //�ߺ����� ���� ��µ�
		}
		if (type.equals("����")) {
			nrb.setSelected(true);
		} else if (type.equals("���")) {
			crb.setSelected(true);
		} else if (type.equals("��� ����")) {
			corb.setSelected(true);
		}
		memoA.setText(memo);

	}

	//�Է�â���� �Է��� ������ ��� dto�� ������ ���̺� ���̴� �޼ҵ�
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
			gender = "����";
		} else if (frb.isSelected()) {
			gender = "����";
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
			type = "����";
		} else if (crb.isSelected()) {
			type = "���";
		} else if (corb.isSelected()) {
			type = "��� ����";
		}
		String memo = memoA.getText();

		//dto�� ��´�
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
	
	//�Է�â �������ִ� �޼ҵ�
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
		
		btn01.addActionListener(this); //��ü����
		btn02.addActionListener(this); //�߰��ϱ�
		btn03.addActionListener(this); //�����ϱ�
		btn04.addActionListener(this); //�����ϱ�
		btn05.addActionListener(this); //�˻��ϱ�
		btn06.addActionListener(this); //���ΰ�ħ�ϱ�
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btn01){ //��ü����
			dao.selectAll();
		} else if(e.getSource() == btn02){ //�߰�
			StudentDto getdto = getviewData();
			dao.insert(getdto);
		} else if(e.getSource() == btn03){ //����
			StudentDto getdto = getviewData();
			dao.update(getdto);
		} else if(e.getSource() == btn04){ //����
			StudentDto getdto = getviewData();
			int x = JOptionPane.showConfirmDialog(this, "�����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);
			if(x == JOptionPane.YES_OPTION){
				dao.delete(getdto);
			}else if(x == JOptionPane.NO_OPTION){
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		} else if(e.getSource() == btn05){ //�˻�    
			String sName = JOptionPane.showInputDialog("�˻��� �л��� �̸��� �Է����ּ���");
			
			//defaulttablemodel�� �ִ� ���� ������ �����
			for(int i=0;i<model.getRowCount();){
				model.removeRow(i);
			}
			if(sName.trim().length()>0){//���� �ԷµǾ����� �˻�
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
		}else if(e.getSource() == btn06){ //���ΰ�ħ
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
		
		String Id = (String)table.getValueAt(row, 0); //�ش� ���� ��ü�� ������ ��
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


