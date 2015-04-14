package view.projects;

import javax.swing.JPanel;
import javax.swing.JDesktopPane;

import java.awt.FlowLayout;
import java.awt.BorderLayout;

import javax.swing.SpringLayout;

import java.awt.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Employee;
import model.Project;

import com.toedter.calendar.JDateChooser;

import controller.EmployeeController;
import controller.ProjectController;
import view.Button;
import view.Message;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import javax.swing.border.LineBorder;

public class EditProject extends JPanel implements ActionListener {
	private JButton btnSubmit, btnRight, btnLeft, btnDblRight, btnDblLeft;
	private JTextField txtProjects;
	private JList listAll, listEmp;
	private DefaultListModel allModel, empModel;
	private JFrame parent;
	private JDateChooser dateStart, dateEnd;
	private ProjectController projectController;
	private EmployeeController employeeController;
	private String prevKey;

	public EditProject(JFrame parent, Project project) {
		this.parent = parent;
		
		allModel=new DefaultListModel();
		empModel=new DefaultListModel();
		
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		JPanel panMain = new JPanel();
		add(panMain, BorderLayout.CENTER);
		panMain.setLayout(new BorderLayout(0, 0));

		JPanel panContent = new JPanel() {
			public boolean isOptimizedDrawingEnabled() {
				return false;
			}
		};
		panMain.add(panContent, BorderLayout.CENTER);
		panContent.setBackground(Color.WHITE);
		SpringLayout sl_panContent = new SpringLayout();
		panContent.setLayout(sl_panContent);

		dateStart = new JDateChooser(project.getStartDate());
		dateStart.getCalendarButton().addActionListener(this);
		sl_panContent.putConstraint(SpringLayout.EAST, dateStart, -464,
				SpringLayout.EAST, panContent);
		dateStart.setBackground(Color.WHITE);

		panContent.add(dateStart);

		dateEnd = new JDateChooser(project.getEndDate());
		dateEnd.getCalendarButton().addActionListener(this);
		sl_panContent.putConstraint(SpringLayout.EAST, dateEnd, -464,
				SpringLayout.EAST, panContent);
		dateEnd.setBackground(Color.WHITE);
		panContent.add(dateEnd);

		JLabel lblProject = new JLabel("Project:");
		sl_panContent.putConstraint(SpringLayout.NORTH, lblProject, 45,
				SpringLayout.NORTH, panContent);
		panContent.add(lblProject);

		txtProjects = new JTextField(project.getName());
		prevKey = project.getName();
		sl_panContent.putConstraint(SpringLayout.WEST, txtProjects, 0,
				SpringLayout.WEST, dateStart);
		sl_panContent.putConstraint(SpringLayout.SOUTH, txtProjects, 0,
				SpringLayout.SOUTH, lblProject);
		sl_panContent.putConstraint(SpringLayout.EAST, txtProjects, -464,
				SpringLayout.EAST, panContent);
		panContent.add(txtProjects);
		txtProjects.setBackground(Color.WHITE);

		JLabel lblAll = new JLabel("List of Employees:");
		sl_panContent.putConstraint(SpringLayout.WEST, lblProject, 0,
				SpringLayout.WEST, lblAll);
		sl_panContent.putConstraint(SpringLayout.NORTH, lblAll, 140,
				SpringLayout.NORTH, panContent);
		sl_panContent.putConstraint(SpringLayout.WEST, lblAll, 109,
				SpringLayout.WEST, panContent);
		panContent.add(lblAll);

		btnRight = new JButton(">");
		btnRight.addActionListener(this);
		sl_panContent.putConstraint(SpringLayout.NORTH, btnRight, 140,
				SpringLayout.SOUTH, dateEnd);
		btnRight.setForeground(Color.white);
		btnRight.setBackground(new Color(32, 130, 213));
		panContent.add(btnRight);

		btnLeft = new JButton("<");
		btnLeft.addActionListener(this);
		btnLeft.setForeground(Color.white);
		btnLeft.setBackground(new Color(32, 130, 213));
		sl_panContent.putConstraint(SpringLayout.NORTH, btnLeft, 5,
				SpringLayout.SOUTH, btnRight);
		sl_panContent.putConstraint(SpringLayout.WEST, btnLeft, 0,
				SpringLayout.WEST, btnRight);
		sl_panContent.putConstraint(SpringLayout.EAST, btnLeft, 0,
				SpringLayout.EAST, btnRight);
		panContent.add(btnLeft);

		btnDblRight = new JButton(">>");
		btnDblRight.addActionListener(this);
		btnDblRight.setForeground(Color.white);
		btnDblRight.setBackground(new Color(32, 130, 213));
		sl_panContent.putConstraint(SpringLayout.WEST, btnDblRight, 0,
				SpringLayout.WEST, btnRight);
		btnDblRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		sl_panContent.putConstraint(SpringLayout.NORTH, btnDblRight, 5,
				SpringLayout.SOUTH, btnLeft);
		sl_panContent.putConstraint(SpringLayout.EAST, btnDblRight, 0,
				SpringLayout.EAST, btnRight);
		panContent.add(btnDblRight);

		btnDblLeft = new JButton("<<");
		btnDblLeft.addActionListener(this);
		btnDblLeft.setForeground(Color.white);
		btnDblLeft.setBackground(new Color(32, 130, 213));
		sl_panContent.putConstraint(SpringLayout.NORTH, btnDblLeft, 5,
				SpringLayout.SOUTH, btnDblRight);
		sl_panContent.putConstraint(SpringLayout.WEST, btnDblLeft, 0,
				SpringLayout.WEST, btnRight);
		sl_panContent.putConstraint(SpringLayout.EAST, btnDblLeft, 0,
				SpringLayout.EAST, btnRight);
		panContent.add(btnDblLeft);

		
		listAll = new JList(allModel);
		listAll.setBorder(new LineBorder(new Color(0, 0, 0)));
		// listAll.setIgnoreRepaint(true);
		sl_panContent.putConstraint(SpringLayout.EAST, listAll, 350,
				SpringLayout.WEST, panContent);
		sl_panContent.putConstraint(SpringLayout.WEST, btnRight, 20,
				SpringLayout.EAST, listAll);
		sl_panContent.putConstraint(SpringLayout.EAST, btnRight, 70,
				SpringLayout.EAST, listAll);
		sl_panContent.putConstraint(SpringLayout.WEST, listAll, 110,
				SpringLayout.WEST, panContent);
		sl_panContent.putConstraint(SpringLayout.SOUTH, listAll, 325,
				SpringLayout.SOUTH, lblAll);
		sl_panContent.putConstraint(SpringLayout.NORTH, listAll, 6,
				SpringLayout.SOUTH, lblAll);
		panContent.add(listAll);

		JLabel lblEmp = new JLabel("List of Project Employees:");
		sl_panContent.putConstraint(SpringLayout.NORTH, lblEmp, 0,
				SpringLayout.NORTH, lblAll);
		sl_panContent.putConstraint(SpringLayout.WEST, lblEmp, 249,
				SpringLayout.EAST, lblAll);
		sl_panContent.putConstraint(SpringLayout.EAST, lblEmp, -235,
				SpringLayout.EAST, panContent);
		panContent.add(lblEmp);

		listEmp = new JList(empModel);
		listEmp.setBorder(new LineBorder(new Color(0, 0, 0)));
		// listEmp.setIgnoreRepaint(true);
		sl_panContent.putConstraint(SpringLayout.NORTH, listEmp, 0,
				SpringLayout.NORTH, listAll);
		sl_panContent.putConstraint(SpringLayout.WEST, listEmp, 20,
				SpringLayout.EAST, btnRight);
		sl_panContent.putConstraint(SpringLayout.SOUTH, listEmp, 0,
				SpringLayout.SOUTH, listAll);
		sl_panContent.putConstraint(SpringLayout.EAST, listEmp, 260,
				SpringLayout.EAST, btnRight);
		panContent.add(listEmp);

		JLabel lblStartDate = new JLabel("Start Date:");
		sl_panContent.putConstraint(SpringLayout.WEST, dateStart, 14,
				SpringLayout.EAST, lblStartDate);
		sl_panContent.putConstraint(SpringLayout.SOUTH, dateStart, 0,
				SpringLayout.SOUTH, lblStartDate);
		sl_panContent.putConstraint(SpringLayout.NORTH, lblStartDate, 77,
				SpringLayout.NORTH, panContent);
		sl_panContent.putConstraint(SpringLayout.SOUTH, lblProject, -15,
				SpringLayout.NORTH, lblStartDate);
		sl_panContent.putConstraint(SpringLayout.WEST, lblStartDate, 0,
				SpringLayout.WEST, lblProject);
		panContent.add(lblStartDate);

		JLabel lblEndDate = new JLabel("End Date:");
		sl_panContent.putConstraint(SpringLayout.WEST, dateEnd, 20,
				SpringLayout.EAST, lblEndDate);
		sl_panContent.putConstraint(SpringLayout.SOUTH, dateEnd, 0,
				SpringLayout.SOUTH, lblEndDate);
		sl_panContent.putConstraint(SpringLayout.NORTH, lblEndDate, 15,
				SpringLayout.SOUTH, lblStartDate);
		sl_panContent.putConstraint(SpringLayout.WEST, lblEndDate, 0,
				SpringLayout.WEST, lblProject);
		panContent.add(lblEndDate);

		JPanel panFooter = new JPanel();
		panFooter.setBorder(new EmptyBorder(0, 0, 50, 0));
		panFooter.setBackground(Color.WHITE);
		panMain.add(panFooter, BorderLayout.SOUTH);
		sl_panContent.putConstraint(SpringLayout.NORTH, panFooter, 0,
				SpringLayout.NORTH, lblProject);
		sl_panContent.putConstraint(SpringLayout.WEST, panFooter, 10,
				SpringLayout.WEST, panContent);

		btnSubmit = new JButton("Submit");
		panFooter.add(btnSubmit);
		btnSubmit.setForeground(Color.WHITE);
		btnSubmit.setBackground(new Color(32, 130, 213));
		btnSubmit.setFont(new Font("Arial", Font.PLAIN, 18));
		btnSubmit.addActionListener(this);
		
		projectController = projectController.getInstance();
		employeeController = employeeController.getInstance();
		
		
		Iterator it=employeeController.getAll();
		while(it.hasNext()){
			Employee emp=(Employee) it.next();
			
			allModel.addElement(emp.getName());
		}
		
		//init();
	}
	
	public void init(){
		
		Iterator it = employeeController.getAll();
		Project currProject = projectController.getProject();
		Iterator i = projectController.getEmployees();
		if(i != null){
			System.out.println("Enter!");
			int k = 0;
//			Employee superTemp = (Employee) i.next();
//			String superTempName = superTemp.getName();
//			System.out.println("Super temp: "+superTempName);
//			System.out.println();
			while(i.hasNext()){
				System.out.println("Enter again!");
				Employee temp = (Employee) i.next();
				empModel.addElement(temp.getName());
				String empString = (String) empModel.get(k);
				System.out.println("Employee in empmodel: "+empString);
				k++;
			}
			int index = 0;
			while(it.hasNext()){
				Employee temp2 = (Employee) it.next();
				
				if(index< empModel.size()){
					String temp3 = (String) empModel.get(index);
					if(!temp2.equals(temp3)){
						allModel.addElement(temp2.getName());
					}
				}
				index++;
			}
		}
		else{
			System.out.println("Sesame!");
			while(it.hasNext()){
				Employee emp=(Employee) it.next();
				
				allModel.addElement(emp.getName());
			}
		}
	}

	public String checkInput() {
		String text = "";
		if (txtProjects.getText().equals("")) {
			text += "Please specify project name.\n";
			txtProjects.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
		}
		if (dateStart.getDate().after(dateEnd.getDate())) {
			text += "Start date should occur before the end date.\n";
			dateStart.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			dateEnd.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
		}
		return text;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnSubmit) {
			String text = checkInput();
			if (text.equals("")) {
				
				Project project = new Project(txtProjects.getText(), dateStart.getDate(), dateEnd.getDate());
				System.out.println("Project name: "+project.getName() + " Start Date: "+ project.getStartDate() + " End Date: "+project.getEndDate());
				Iterator iterator = employeeController.getAll();
				int i = 0;
				int j = 0;
				while(iterator.hasNext()){
					Employee temp = (Employee) iterator.next();
					if(j< empModel.size()){
						String employeeModel = (String)empModel.get(i);
						if(temp.getName().equals(employeeModel)){
							project.addEmployee(temp);
						}
					System.out.println("Employee model: " + employeeModel);
					}
					i++;
					j++;
				}
				
				Iterator test = project.getEmployeeList();
				while(test.hasNext()){
					Employee testme = (Employee) test.next();
					System.out.println("Employee test: "+testme.getName());
				}

				
				projectController.editProject(project, prevKey);
				Message msg = new Message(parent, Message.SUCCESS,
						"Project added successfully.");
				projectController.init();
				
			} else {
				new Message(parent, Message.ERROR, text);
			}
		} else {
			if(e.getSource()==btnRight){
				//Employee emp=(Employee) listAll.getSelectedValue();
				String empName = (String) listAll.getSelectedValue();
				System.out.println("Add: "+empName);
				if(empName!=null){
					allModel.removeElement(empName);
					empModel.addElement(empName);
				}
			}
			if(e.getSource()==btnLeft){
				//Employee emp=(Employee) listEmp.getSelectedValue();
				String empName = (String) listEmp.getSelectedValue();
				System.out.println("Remove: "+empName);
				if(empName!=null){
					empModel.removeElement(empName);
					allModel.addElement(empName);
				}
			}
			if(e.getSource()==btnDblRight){
				for (int i = 0; i < allModel.getSize(); i++) {
					empModel.addElement(allModel.get(i));
				}
				allModel.removeAllElements();
			}
			if(e.getSource()==btnDblLeft){
				for (int i = 0; i < empModel.getSize(); i++) {
					allModel.addElement(empModel.get(i));
				}
				empModel.removeAllElements();
			}
			this.repaint();
			this.revalidate();
		}
	}
}
