package view.supplier;

import controller.SupplierController;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import java.awt.BorderLayout;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import model.Supplier;
import model.SupplierContact;
import view.Button;
import view.ErrorListenerFactory;
import view.Button.ButtonBuilder;
import view.Message;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.ScrollPaneConstants;

public class EditSupplier extends JPanel implements ActionListener {

	private JLabel txtSupp;
	// private JComboBox cmbCountry, cmbState, cmbCity;
	private JTextField cmbCountry, cmbState, cmbCity;
	private JPanel temp;
	private JPanel panContact, panClose;
	private ArrayList<Contact> list;
	private ArrayList<JButton> close;
	private JButton btnSubmit;
	private SupplierController supplierController;
	private JFrame parent;
	
	private JTextField value;
	private JButton btn;
	private JComboBox type;
	
	private Supplier supp;
	private String prevKey;

	public EditSupplier(JFrame parent, Supplier supp) {
		this.parent = parent;
		this.setBackground(Color.WHITE);
		this.supp=supp;
		list = new ArrayList<Contact>();
		close = new ArrayList<JButton>();
		setLayout(new BorderLayout(0, 0));

		JPanel panContent = new JPanel();
		panContent.setBorder(new EmptyBorder(50, 50, 0, 0));
		panContent.setBackground(Color.WHITE);
		add(panContent);
		panContent.setLayout(new MigLayout("", "[100px][15px][400px]", "[27px][26px][26px][28px][28px][200px]"));

		JLabel lblSupp = new JLabel("Supplier: ");
		panContent.add(lblSupp, "cell 0 0,alignx left,growy");

		txtSupp = new JLabel(supp.getName());
		panContent.add(txtSupp, "cell 2 0,grow");
		prevKey = supp.getName();

		JLabel lblAddress = new JLabel("Address:");
		panContent.add(lblAddress, "cell 0 1,alignx left,growy");

		JLabel lblStreet = new JLabel("Street:");
		panContent.add(lblStreet, "cell 0 2,alignx right,growy");

		JLabel lblCity = new JLabel("City:");
		panContent.add(lblCity, "cell 0 3,alignx right,growy");

		JLabel lblCountry = new JLabel("Country:");
		panContent.add(lblCountry, "cell 0 4,alignx right,growy");

		cmbCountry = new JTextField(supp.getCountry());
		cmbCountry.addFocusListener(ErrorListenerFactory.getListener(cmbCountry));
		panContent.add(cmbCountry, "cell 2 2,grow");
		cmbCountry.setBackground(Color.white);

		cmbState = new JTextField(supp.getState());
		cmbState.addFocusListener(ErrorListenerFactory.getListener(cmbState));
		panContent.add(cmbState, "cell 2 3,grow");
		cmbState.setBackground(Color.white);

		cmbCity = new JTextField(supp.getCity());
		cmbCity.addFocusListener(ErrorListenerFactory.getListener(cmbCity));
		panContent.add(cmbCity, "cell 2 4,grow");
		cmbCity.setBackground(Color.white);

		JLabel lblContact = new JLabel("Contact #:");
		panContent.add(lblContact, "cell 0 5 3 1,alignx left,aligny top");

		panContact = new JPanel();
		panContact.setBackground(Color.WHITE);
		panContact.setBounds(51, 478, 457, 200);
		panContact.setLayout(new MigLayout());
		

		panClose = new JPanel();
	
		panClose.setBackground(Color.ORANGE);
//		panClose.setAlignmentX(Component.LEFT_ALIGNMENT);
		panContact.add(panClose, "");
		panClose.setLayout(new BoxLayout(panClose, BoxLayout.PAGE_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		panContent.add(scrollPane, "cell 2 5,grow");
		scrollPane.setBorder(null);
		scrollPane.setViewportView(panContact);
		panClose.setVisible(false);
		

		temp = new JPanel();
		temp.setMaximumSize(new Dimension(400, 37));
		temp.setPreferredSize(new Dimension(365, 37));
		temp.setBackground(Color.WHITE);
		FlowLayout flowLayout = (FlowLayout) temp.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		
		value=new JTextField();
		value.setPreferredSize(new Dimension(175, 25));
		value.addFocusListener(ErrorListenerFactory.getListener(value));
		temp.add(value);
		
		String opt[]={"FAX", "Telephone", "Cellphone"};
		type=new JComboBox(opt);
		type.setBorder(new EmptyBorder(0, 0, 0, 0));
		type.setBackground(Color.white);
		type.setPreferredSize(new Dimension(120, 25));
		temp.add(type);
		
		btn= new Button.ButtonBuilder().img("/assets/Round/Add.png", 30,
				30).build();
		temp.add(btn);
		btn.setActionCommand("add");
		btn.addActionListener(this);
		temp.add(btn);
		
		panContact.add(temp, "newline,alignx left,aligny top");

		JPanel panFooter = new JPanel();
		panFooter.setBackground(Color.WHITE);
		panFooter.setBorder(new EmptyBorder(0, 0, 50, 0));
		add(panFooter, BorderLayout.SOUTH);

		btnSubmit = new JButton("Submit");
		panFooter.add(btnSubmit);
		btnSubmit.setForeground(Color.WHITE);
		btnSubmit.setBackground(new Color(32, 130, 213));
		btnSubmit.setFont(new Font("Arial", Font.PLAIN, 18));
		btnSubmit.addActionListener(this);

		supplierController = SupplierController.getInstance();
		// populate();
		//System.out.println("STUPID");
		
		init();
	}
	
	private void init(){
		Iterator it=supp.getSupplierContactList();
		while(it.hasNext()){
			SupplierContact sc=(SupplierContact) it.next();
			JButton close = new Button.ButtonBuilder().img(
					"/assets/Round/Delete.png", 30, 30).build();
			close.addActionListener(this);
			this.close.add(close);

			Contact temp = new Contact();
			temp.setButton(close);
			temp.getBtn().setActionCommand("close");
			temp.setValue(sc.getValue()+"");
			temp.setType(sc.getType());

//			pan.setValue("");
//			pan.setType("FAX");

			panClose.add(temp, "newline");
			list.add(temp);
			if (!panClose.isVisible()) {
				panClose.setVisible(true);
			}
			
		}
		
		panClose.setMaximumSize(new Dimension(360,
				panClose.getComponentCount() * 37));
		
		this.repaint();
		this.revalidate();
	}
	
	public Supplier getSupplier(){
		return supp;
	}


	public boolean contactError(String val) {
		boolean stat = false;
		for (int i = 0; i < val.length(); i++) {
			if (!(val.charAt(i) >= '0' && val.charAt(i) <= '9')) {
				stat = true;
			}
		}

		return stat;

	}

	public void addContact(String value, String type) {

		if (value.equals("")) {
			this.value.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			new Message(parent, Message.ERROR, "Please set contact value.");

		} else {
			JButton close = new Button.ButtonBuilder().img(
					"/assets/Round/Delete.png", 30, 30).build();
			close.addActionListener(this);
			this.close.add(close);

			Contact temp = new Contact();
			temp.setButton(close);
			temp.getBtn().setActionCommand("close");

			temp.setValue(value);
			temp.setType(type);

			this.value.setText("");
			this.type.setSelectedIndex(0);

			panClose.add(temp);
			list.add(temp);
			
			if (!panClose.isVisible()) {
				panClose.setVisible(true);
			}
			
			panClose.setMaximumSize(new Dimension(360,
					panClose.getComponentCount() * 37));
			this.repaint();
			this.revalidate();
		}

	}

	public void clear() {
		list.clear();
		close.clear();
		txtSupp.setText("");
		cmbCountry.setText("");
		cmbState.setText("");
		cmbCity.setText("");
		panClose.removeAll();

		this.repaint();
		this.revalidate();
	}

	public String checkInput() {
		String text="";

		if (cmbCountry.getText().equals("")
				|| cmbState.getText().equals("")
				|| cmbCity.getText().equals("")) {
			if(cmbCountry.getText().equals("")) cmbCountry.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			if(cmbState.getText().equals("")) cmbState.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			if(cmbCity.getText().equals("")) cmbCity.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			text+="Please accomplish supplier location.\n";
		}
		//System.out.println(text);
		return text;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("add")) {
			addContact(value.getText(), (String)type.getSelectedItem());
		} else if (e.getSource() == btnSubmit) {
			String text=checkInput();//System.out.println(text);
			if (text.equals("")) {
				
				Supplier checkSupplier;
				Supplier supplier = new Supplier(txtSupp.getText(),
						 cmbCountry.getText(),
						cmbState.getText(), cmbCity.getText());
				// supplier.setSupplierContactList(contacts);
				
				checkSupplier = (Supplier) supplierController
						.getObject(supplier.getName());

					for (int i = 0; i < list.size(); i++) {
						//System.out.println("RISSA: "+i);
						supplier.addSupplierContact(txtSupp.getText(), list
								.get(i).getType().toString(), list.get(i).getValue());
					}

					Message msg = new Message(parent, Message.SUCCESS,
							"Supplier edited successfully.");
				
				supplierController.editSupplier(supplier, prevKey);
				//System.out.println("Putaaaa");
				supplierController.init();
				//clear();
			}
			else{
				new Message(parent, Message.ERROR,
						text);
			}

		} else {
			int index = close.indexOf(e.getSource());
			close.remove(index);
			panClose.remove(index);
			list.remove(index);
			//System.out.println(list.size());
			panClose.setMaximumSize(new Dimension(360,
					panClose.getComponentCount() * 37));
			this.repaint();
			this.revalidate();
		}
	}
}
