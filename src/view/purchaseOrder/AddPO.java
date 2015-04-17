package view.purchaseOrder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.ItemData;
import model.PurchaseOrder;
import model.Supplier;
import view.Button;
import view.Observer;
import view.Button.ButtonBuilder;

import com.toedter.calendar.JDateChooser;

import controller.PurchaseOrderController;
import controller.SupplierController;

import java.util.ArrayList;
import java.util.Iterator;

import view.Message;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

public class AddPO extends JPanel implements ActionListener, Observer {
	private JPanel panDefinition, panLeft, panCenter, panRight, panSupplier,
	panDate, panAddItem, panItemTable, panClass, panHeader, panFooter;
	private JLabel lblSupplier, lblClass, lblDate, lblItem;
	private JButton btnAddItem, btnSubmit;
	private JComboBox cmbSupplier, cmbClass;
	private DefaultTableModel model;

	private JTable table;
	private JScrollPane scrollPane;
	private JPanel panSubmit;
	private JPanel panGrandTotal;
	private JLabel lblGrandTotal;
	private JDateChooser dateChooser;
	private Date selectedDate;

	private PurchaseOrderController poController;
	private SupplierController supplierController;
	private JPanel panCurrency;
	private JLabel lblCurrency;
	private JComboBox cmbCurrency;

	private JFrame parent;
	private JLabel lblGrandValue;
	private DecimalFormat df;
	private SimpleDateFormat dateFormat; 
	private String sDate;
	private JPanel panSupplierAddress;
	private JLabel lblAddress;
	private JLabel lblAddressContent;
	private JPanel panApproved;
	private JLabel lblApproved;
	private JLabel lblApprovedDate;
	private JDateChooser approvedDateChooser;
	private JTextField txtApprovedBy;
	public AddPO(JFrame parent) {

		this.parent = parent;
		poController = PurchaseOrderController.getInstance();
		//poTableModel = new POTableModel(poController);
		df = new DecimalFormat("#,###,###,###,##0.00");
		df.setMaximumFractionDigits(2);
		dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
		supplierController = SupplierController.getInstance();

		setLayout(new BorderLayout(0, 0));
		panDefinition = new JPanel();
		panDefinition.setPreferredSize(new Dimension(10, 80));
		add(panDefinition, BorderLayout.NORTH);
		panDefinition.setBackground(Color.white);
		panDefinition.setLayout(new BorderLayout(0, 0));

		panLeft = new JPanel();
		panLeft.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panDefinition.add(panLeft, BorderLayout.WEST);
		panLeft.setBackground(Color.white);
		panLeft.setLayout(new BorderLayout(0, 0));

		panSupplier = new JPanel();
		panSupplier.setBackground(Color.white);
		panLeft.add(panSupplier, BorderLayout.NORTH);
		GridBagLayout gbl_panSupplier = new GridBagLayout();
		gbl_panSupplier.columnWidths = new int[]{45, 200, 0};
		gbl_panSupplier.rowHeights = new int[]{30, 14, 0};
		gbl_panSupplier.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panSupplier.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panSupplier.setLayout(gbl_panSupplier);


		lblSupplier = new JLabel("Supplier :");
		GridBagConstraints gbc_lblSupplier = new GridBagConstraints();
		gbc_lblSupplier.anchor = GridBagConstraints.WEST;
		gbc_lblSupplier.fill = GridBagConstraints.VERTICAL;
		gbc_lblSupplier.insets = new Insets(0, 0, 5, 5);
		gbc_lblSupplier.gridx = 0;
		gbc_lblSupplier.gridy = 0;
		panSupplier.add(lblSupplier, gbc_lblSupplier);

		cmbSupplier = new JComboBox();
		cmbSupplier.addItemListener(new ItemChangeListener());

		GridBagConstraints gbc_cmbSupplier = new GridBagConstraints();
		gbc_cmbSupplier.anchor = GridBagConstraints.NORTHWEST;
		gbc_cmbSupplier.insets = new Insets(0, 0, 5, 5);
		gbc_cmbSupplier.gridx = 1;
		gbc_cmbSupplier.gridy = 0;
		panSupplier.add(cmbSupplier, gbc_cmbSupplier);
		cmbSupplier.setBackground(Color.white);
		cmbSupplier.setPreferredSize(new Dimension(200, 30));

		lblAddress = new JLabel("Address :");
		GridBagConstraints gbc_lblAddress = new GridBagConstraints();
		gbc_lblAddress.insets = new Insets(0, 0, 0, 5);
		gbc_lblAddress.gridx = 0;
		gbc_lblAddress.gridy = 1;
		panSupplier.add(lblAddress, gbc_lblAddress);

		lblAddressContent = new JLabel("");
		GridBagConstraints gbc_lblAddressContent = new GridBagConstraints();
		gbc_lblAddressContent.anchor = GridBagConstraints.WEST;
		gbc_lblAddressContent.insets = new Insets(0, 0, 0, 5);
		gbc_lblAddressContent.gridx = 1;
		gbc_lblAddressContent.gridy = 1;
		panSupplier.add(lblAddressContent, gbc_lblAddressContent);

		panClass = new JPanel();
		panClass.setBackground(Color.white);
		panLeft.add(panClass, BorderLayout.WEST);
		panClass.setLayout(new BorderLayout(0, 0));

		lblClass = new JLabel("Item Classification :");
		lblClass.setHorizontalTextPosition(SwingConstants.LEFT);
		lblClass.setHorizontalAlignment(SwingConstants.LEFT);
		panClass.add(lblClass, BorderLayout.WEST);

		cmbClass = new JComboBox();
		cmbClass.setPreferredSize(new Dimension(100, 30));
		cmbClass.setModel(new DefaultComboBoxModel(new String[] { "Hard",
				"Soft", "Gen" }));
		cmbClass.setBackground(Color.white);
		panClass.add(cmbClass);

		panRight = new JPanel();
		panRight.setBackground(Color.white);
		panDefinition.add(panRight, BorderLayout.EAST);
		panRight.setLayout(new BorderLayout(0, 0));

		panDate = new JPanel();
		panDate.setBackground(Color.white);
		panRight.add(panDate, BorderLayout.NORTH);

		lblDate = new JLabel("Date :");
		panDate.add(lblDate);

		dateChooser = new JDateChooser();
		dateChooser.setOpaque(false);
		dateChooser.setDate(new Date());
		dateChooser.setBorder(null);
		dateChooser.setDateFormatString("MMMM dd, yyyy");
		dateChooser.setBackground(Color.WHITE);
		dateChooser.setPreferredSize(new Dimension(150, 30));

		panDate.add(dateChooser);

		panCurrency = new JPanel();
		panCurrency.setBackground(Color.white);
		panLeft.setBackground(Color.white);
		panRight.add(panCurrency, BorderLayout.WEST);

		lblCurrency = new JLabel("Currency :");
		panCurrency.add(lblCurrency);

		String currencyTypes[]={"AUD", "EUR", "PHP", "JPY","USD" };
		cmbCurrency = new JComboBox(currencyTypes);
		cmbCurrency.setBackground(Color.white);
		cmbCurrency.setPreferredSize(new Dimension(110, 30));
		panCurrency.add(cmbCurrency);

		panCenter = new JPanel();
		panCenter.setBackground(Color.white);
		add(panCenter, BorderLayout.CENTER);
		panCenter.setLayout(new BorderLayout(0, 0));

		btnAddItem = new Button.ButtonBuilder().img("src/assets/Round/Add.png",
				30, 30).build();
		btnAddItem.setActionCommand("add");
		btnAddItem.addActionListener(this);

		panHeader = new JPanel();
		panHeader.setBackground(Color.white);
		panCenter.add(panHeader, BorderLayout.NORTH);
		panHeader.setLayout(new BorderLayout(0, 0));

		panAddItem = new JPanel();
		panAddItem.setBackground(Color.white);
		panHeader.add(panAddItem, BorderLayout.WEST);
		panAddItem.setSize(new Dimension(100, 80));
		panAddItem.setPreferredSize(new Dimension(125, 60));
		panAddItem.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));

		lblItem = new JLabel("Item/s");
		panAddItem.add(lblItem);
		panAddItem.add(btnAddItem);

		panFooter = new JPanel();
		panFooter.setBackground(Color.white);
		panCenter.add(panFooter, BorderLayout.SOUTH);
		panFooter.setLayout(new BorderLayout(0, 0));

		panSubmit = new JPanel();
		panSubmit.setBackground(Color.white);
		panFooter.add(panSubmit, BorderLayout.SOUTH);

		btnSubmit = new JButton("Submit");
		btnSubmit.setForeground(Color.WHITE);
		btnSubmit.setFont(new Font("Arial", Font.PLAIN, 18));
		btnSubmit.setBackground(new Color(32, 130, 213));
		btnSubmit.addActionListener(this);
		panSubmit.add(btnSubmit);

		panGrandTotal = new JPanel();
		panFooter.add(panGrandTotal, BorderLayout.EAST);
		FlowLayout flowLayout_1 = (FlowLayout) panGrandTotal.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEADING);
		panGrandTotal.setBackground(Color.white);

		lblGrandTotal = new JLabel("Grand Total :");
		panGrandTotal.add(lblGrandTotal);

		lblGrandValue = new JLabel("0.00");
		panGrandTotal.add(lblGrandValue);

		panApproved = new JPanel();
		panApproved.setBackground(Color.WHITE);
		panFooter.add(panApproved, BorderLayout.WEST);
		GridBagLayout gbl_panApproved = new GridBagLayout();
		gbl_panApproved.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panApproved.rowHeights = new int[]{0, 14, 0, 0};
		gbl_panApproved.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panApproved.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panApproved.setLayout(gbl_panApproved);

		lblApproved = new JLabel("Approved By :");
		GridBagConstraints gbc_lblApproved = new GridBagConstraints();
		gbc_lblApproved.anchor = GridBagConstraints.EAST;
		gbc_lblApproved.insets = new Insets(0, 0, 5, 5);
		gbc_lblApproved.gridx = 0;
		gbc_lblApproved.gridy = 1;
		panApproved.add(lblApproved, gbc_lblApproved);

		lblApprovedDate = new JLabel("Date :");
		GridBagConstraints gbc_lblApprovedDate = new GridBagConstraints();
		gbc_lblApprovedDate.anchor = GridBagConstraints.WEST;
		gbc_lblApprovedDate.insets = new Insets(0, 0, 5, 0);
		gbc_lblApprovedDate.gridx = 7;
		gbc_lblApprovedDate.gridy = 1;
		panApproved.add(lblApprovedDate, gbc_lblApprovedDate);

		txtApprovedBy = new JTextField();
		GridBagConstraints gbc_txtApprovedBy = new GridBagConstraints();
		gbc_txtApprovedBy.gridwidth = 5;
		gbc_txtApprovedBy.insets = new Insets(0, 0, 0, 5);
		gbc_txtApprovedBy.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApprovedBy.gridx = 0;
		gbc_txtApprovedBy.gridy = 2;
		panApproved.add(txtApprovedBy, gbc_txtApprovedBy);
		txtApprovedBy.setColumns(10);

		approvedDateChooser = new JDateChooser();
		approvedDateChooser.setDateFormatString("MMMM dd, yyyy");
		approvedDateChooser.setDate(new Date());
		GridBagConstraints gbc_approvedDateChooser = new GridBagConstraints();
		gbc_approvedDateChooser.fill = GridBagConstraints.BOTH;
		gbc_approvedDateChooser.gridx = 7;
		gbc_approvedDateChooser.gridy = 2;
		panApproved.add(approvedDateChooser, gbc_approvedDateChooser);

		panItemTable = new JPanel();
		panItemTable.setBackground(Color.white);
		panCenter.add(panItemTable, BorderLayout.CENTER);
		panRight.add(panCurrency, BorderLayout.WEST);

		scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(Color.white);
		scrollPane.setBackground(Color.WHITE);
		panCenter.add(scrollPane);

		model = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {

				return false;
			}

			public boolean isFocusable(int rowIndex, int mColIndex) {
				return false;
			}

			public boolean isCellSelectable(int rowIndex, int mColIndex) {

				return false;
			}
		};
		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.setCellSelectionEnabled(false);
		table.getTableHeader().setReorderingAllowed(false);
		//table.setAutoResizeMode(JTable.);
		table.setRowHeight(55);
		initializeModel();
		populateSupplierNames();
		supplierController.registerObserver(this);// dev
		poController.registerObserver(this);

		clear();
	}

	/** initialize the table model **/
	public void initializeModel() {
		model.setColumnCount(5);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		String headers[] = { "Item", "Description", "Quantity", "Unit Price","Amount" };
		model.setColumnIdentifiers(headers);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(40);
		table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

		table.getColumnModel().getColumn(3).setPreferredWidth(90);
		table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

		table.getColumnModel().getColumn(4).setPreferredWidth(90);
		table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub

		if (event.getSource() == btnAddItem) {
			AddPOItem i = new AddPOItem(parent,(String) cmbClass.getSelectedItem(), poController);
		} else if (event.getSource() == btnSubmit) {

			if(table.getRowCount() == 0)
			{
				Message msg = new Message(parent, Message.ERROR,"Purchase Order has no items!");
			}
			else if (checkFields().equals("")) {
				selectedDate = dateChooser.getDate();
				Supplier supplier = (Supplier) supplierController.getObject((String) cmbSupplier.getSelectedItem());// dev
				poController.addPurchaseOrder(new PurchaseOrder(selectedDate, 0,
						cmbClass.getSelectedItem().toString(),supplier, "",(String) cmbCurrency.getSelectedItem() ));// dev
				Message msg = new Message(parent, Message.SUCCESS,"Purchase Order added successfully.");
				clear();
			} 
			else if(checkFields().equals(""))
			{
				Message msg = new Message(parent, Message.SUCCESS,checkFields());
			}
			else {
				JOptionPane.showMessageDialog(null, "No date");

			}
		}
	}

	public void clear() {
		cmbSupplier.setSelectedIndex(0);
		cmbClass.setSelectedIndex(0);
		lblGrandValue.setText("");
		dateChooser.setDate(new Date());
		approvedDateChooser.setDate(new Date());
		txtApprovedBy.setText("");
		
		clearTable();
		poController.init();
		cmbCurrency.setSelectedIndex(0);
	}

	public void clearTable()
	{
		for (int i = 0; i < table.getModel().getRowCount(); i++) {
			for (int j = 0; j < table.getModel().getRowCount(); j++) {
				table.getModel().setValueAt(null, i, j);
			}
		}

		model.setRowCount(0);
	}
	public String checkFields() {
		String msg = "";

		if (dateChooser.getDate() == null) {
			msg += "No Purchase Order Date Selected! \n";
		}
		else if(approvedDateChooser.getDate() == null)
		{
			msg += "No Approved Date Selected! \n";
		}
		else if(txtApprovedBy.getText().equals(""))
		{
			msg += "Please fill in the field for Approved By.";
		}
		return msg;
	}

	public void populateSupplierNames() {
		Iterator<Supplier> iterator = supplierController.getAll();
		ArrayList<String> supplierNames = new ArrayList();
		while (iterator.hasNext()) {
			supplierNames.add(iterator.next().getName());
		}
		cmbSupplier.setModel(new DefaultComboBoxModel(supplierNames.toArray()));
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		//clear();
		clearTable();
		PurchaseOrder po = poController.getPurchaseOrder();
		Iterator data = poController.getPurchaseOrder().getItems();
		ItemData item;
		while (data.hasNext()) {
			item = (ItemData) data.next();

			/*** populate the table ***/
			model.setRowCount(model.getRowCount() + 1);
			model.setValueAt(item.getName(), model.getRowCount() - 1, 0);
			model.setValueAt(item.getDescription(), model.getRowCount() - 1, 1);
			model.setValueAt(po.getQuantity(item), model.getRowCount() - 1, 2);
			model.setValueAt(df.format(item.getUnitPrice()), model.getRowCount() - 1, 3);
			model.setValueAt(df.format(po.computeTotal(item)), model.getRowCount() - 1, 4);
		}
		lblGrandValue.setText(String.valueOf(df.format(po.computeGrandTotal())));
	}

	class ItemChangeListener implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				Object item = event.getItem();
				// DEV do something with object to get the address of the supplier :D 
				lblAddressContent.setText(item.toString());
			}
		}   
	}
}


