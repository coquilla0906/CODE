package view.purchaseOrder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import view.PanelCell;
import view.Button.ButtonBuilder;

import com.toedter.calendar.JDateChooser;

import controller.PurchaseOrderController;
import controller.SupplierController;

import java.util.ArrayList;
import java.util.Iterator;

import view.Message;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

public class EditPO extends JPanel implements ActionListener, Observer {

	private JPanel panDefinition, panLeft, panCenter, panRight, panSupplier,
	panDate, panAddItem, panClass, panHeader, panFooter;
	private JLabel lblSupplier, lblClass, lblDate, lblItem;
	private JButton btnAddItem, btnSubmit;
	private JComboBox cmbSupplier;
	private DefaultTableModel model;
	//private POTableModel poTableModel;
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
	private PurchaseOrder po;
	private DecimalFormat df;
	private SimpleDateFormat dateFormat; 
	private String sDate;
	private JLabel lblAddress;
	private JLabel lblAddressContent;
	private JLabel lblClassInfo;
	public EditPO(JFrame parent, PurchaseOrder po) {

		this.parent = parent;
		this.po = po;
		df = new DecimalFormat("#,###,###,###,##0.00");
		df.setMaximumFractionDigits(2);
		dateFormat = new SimpleDateFormat("MMMM dd, yyyy");

		poController = PurchaseOrderController.getInstance();
		poController.setPurchaseOrder(po);
		supplierController = SupplierController.getInstance();


		setLayout(new BorderLayout(0, 0));

		panDefinition = new JPanel();
		panDefinition.setPreferredSize(new Dimension(10, 80));
		panDefinition.setBackground(Color.white);
		panDefinition.setLayout(new BorderLayout(0, 0));
		add(panDefinition, BorderLayout.NORTH);

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
		gbl_panSupplier.rowHeights = new int[]{30, 0, 0};
		gbl_panSupplier.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panSupplier.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panSupplier.setLayout(gbl_panSupplier);

		lblSupplier = new JLabel("Supplier :");
		GridBagConstraints gbc_lblSupplier = new GridBagConstraints();
		gbc_lblSupplier.anchor = GridBagConstraints.WEST;
		gbc_lblSupplier.insets = new Insets(0, 0, 5, 5);
		gbc_lblSupplier.gridx = 0;
		gbc_lblSupplier.gridy = 0;
		panSupplier.add(lblSupplier, gbc_lblSupplier);

		cmbSupplier = new JComboBox();
		cmbSupplier.addItemListener(new ItemChangeListener());

		GridBagConstraints gbc_cmbSupplier = new GridBagConstraints();
		gbc_cmbSupplier.anchor = GridBagConstraints.NORTHWEST;
		gbc_cmbSupplier.insets = new Insets(0, 0, 5, 0);
		gbc_cmbSupplier.gridx = 1;
		gbc_cmbSupplier.gridy = 0;
		panSupplier.add(cmbSupplier, gbc_cmbSupplier);
		cmbSupplier.setBackground(Color.white);
		cmbSupplier.setPreferredSize(new Dimension(200, 30));

		lblAddress = new JLabel("Address :");
		GridBagConstraints gbc_lblAddress = new GridBagConstraints();
		gbc_lblAddress.anchor = GridBagConstraints.WEST;
		gbc_lblAddress.insets = new Insets(0, 0, 0, 5);
		gbc_lblAddress.gridx = 0;
		gbc_lblAddress.gridy = 1;
		panSupplier.add(lblAddress, gbc_lblAddress);
		lblAddressContent = new JLabel();
		GridBagConstraints gbc_lblAddressContent = new GridBagConstraints();
		gbc_lblAddressContent.anchor = GridBagConstraints.WEST;
		gbc_lblAddressContent.gridx = 1;
		gbc_lblAddressContent.gridy = 1;
		panSupplier.add(lblAddressContent, gbc_lblAddressContent);

		/****
		 * DEV set the address of the purchase order :D 
		 * in this format street, city, country
		 ******/

		panClass = new JPanel();
		panClass.setBackground(Color.white);
		panLeft.add(panClass, BorderLayout.WEST);

		lblClass = new JLabel("Item Classification :");
		lblClass.setHorizontalTextPosition(SwingConstants.LEFT);
		lblClass.setHorizontalAlignment(SwingConstants.LEFT);
		panClass.add(lblClass);
		
		lblClassInfo = new JLabel(po.getType());
		panClass.add(lblClassInfo);

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
		dateChooser.getCalendarButton().setFont(new Font("Arial", Font.PLAIN, 11));
		dateChooser.setOpaque(false);
		dateChooser.setDate(po.getDate());
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
		setPOCurrency(po);
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
		btnSubmit.setFont(new Font("Arial", Font.PLAIN, 18));
		btnSubmit.setForeground(Color.WHITE);
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

		scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(Color.white);
		scrollPane.setBackground(Color.WHITE);
		panCenter.add(scrollPane);

		model = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				if (mColIndex == model.getColumnCount() - 1
						|| mColIndex == model.getColumnCount() - 2) {
					return true;
				}
				return false;
			}

			public boolean isFocusable(int rowIndex, int mColIndex) {
				return false;
			}

			public boolean isCellSelectable(int rowIndex, int mColIndex) {
				if (mColIndex == 5) {
					//System.out.println("CHECKBOX");
					return true;
				}
				return false;
			}
		};

		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.setCellSelectionEnabled(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(55);
		populateSupplierNames();
		setSupplier(po);
		initializeModel();
		supplierController.registerObserver(this);// dev
		poController.registerObserver(this);

	}
	
	public PurchaseOrder getPO(){
		return po;
	}

	public void setPOCurrency(PurchaseOrder po) {
		for (int i = 0; i < cmbCurrency.getItemCount(); i++) {
			if (cmbCurrency.getItemAt(i).equals(po.getCurrency())) {
				cmbCurrency.setSelectedIndex(i);
			}
		}
	}

	public void setSupplier(PurchaseOrder po) {
		for (int i = 0; i < cmbSupplier.getItemCount(); i++) {
			if (cmbSupplier.getItemAt(i).equals(po.getSupplier().getName())) {
				cmbSupplier.setSelectedIndex(i);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == btnAddItem) {

			AddPOItem addAddPOItem = new AddPOItem(parent,
					(String)lblClassInfo.getText(), poController);
			/**
			 * *
			 * add the code statements here to add an additional item to the
			 * purchase order *
			 */

		} else if (event.getSource() == btnSubmit) {

			if (table.getRowCount() == 0) {
				Message msg = new Message(parent, Message.ERROR, "Purchase Order has no items!");
			} else if (checkFields() == false) {

				/**
				 * **
				 * DEV add the added information needed in the purhase order
				 */
				selectedDate = dateChooser.getDate();
				Supplier supplier = (Supplier) supplierController.getObject((String) cmbSupplier.getSelectedItem());// dev
				po.setDate(selectedDate);
				po.setSupplier(supplier);
				poController.editPurchaseOrder(po);// dev
				Message msg = new Message(parent, Message.SUCCESS, "Purchase Order edited successfully.");
			} else {
				JOptionPane.showMessageDialog(null, "No date");

			}


		}

	}

	/**
	 * * reset the purchase order form **
	 */
	public void clear() {
		cmbSupplier.setSelectedIndex(0);
		lblClassInfo.setText("");
		lblGrandValue.setText("");
		dateChooser.setDate(new Date());
		clearTable();
		poController.init();
	}


	/**
	 * * check for empty fields **
	 */
	public boolean checkFields() {
		boolean isEmpty = false;

		if (dateChooser.getDate() == null) {
			isEmpty = true;
		}

		return isEmpty;
	}

	/**
	 * * populate the combobox with the supplier names from the db **
	 */
	public void populateSupplierNames() {
		Iterator<Supplier> iterator = supplierController.getAll();
		ArrayList<String> supplierNames = new ArrayList();
		while (iterator.hasNext()) {
			supplierNames.add(iterator.next().getName());
		}
		cmbSupplier.setModel(new DefaultComboBoxModel(supplierNames.toArray()));
	}

	/**
	 * initialize the table model *
	 */
	public void initializeModel() {
		model.setColumnCount(8);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		String headers[] = {"Item", "Description", "Quantity", "Unit Price",
				"Amount", "Quantity Received", "Delivered", "Edit"};
		model.setColumnIdentifiers(headers);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);

		table.getColumnModel().getColumn(2).setPreferredWidth(40);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

		table.getColumnModel().getColumn(3).setPreferredWidth(90);
		table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

		table.getColumnModel().getColumn(4).setPreferredWidth(90);
		table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

		table.getColumnModel().getColumn(5).setPreferredWidth(90);
		table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

		table.getColumnModel().getColumn(6).setPreferredWidth(90);
		table.getColumnModel().getColumn(7).setPreferredWidth(90);

	}

	public void clearTable() {
		for (int i = 0; i < model.getRowCount(); i++) {
			for (int j = 0; j < model.getColumnCount(); j++) {
				model.setValueAt(null, i, j);
			}
		}

		model.setRowCount(0);
	}

	@Override
	public void update() {
		clearTable();

		Iterator data = po.getItems();
		ItemData item;
		while (data.hasNext()) {
			item = (ItemData) data.next();

			/**
			 * * set table renderers and editors **
			 */
			table.getColumnModel()
			.getColumn(table.getColumnCount() - 1)
			.setCellRenderer(new POItemCellEdit(new JCheckBox(), parent, item, po, poController));
			table.getColumnModel()
			.getColumn(table.getColumnCount() - 1)
			.setCellEditor(new POItemCellEdit(new JCheckBox(), parent, item, po, poController));

			table.getColumnModel()
			.getColumn(table.getColumnCount() - 2)
			.setCellRenderer(new POItemCellDelivered(new JCheckBox(), parent, item, po, poController));
			table.getColumnModel()
			.getColumn(table.getColumnCount() - 2)
			.setCellEditor(new POItemCellDelivered(new JCheckBox(), parent, item, po, poController));


			/**
			 * * populate the table **
			 */
			model.setRowCount(model.getRowCount() + 1);
			model.setValueAt(item.getName(), model.getRowCount() - 1, 0);
			model.setValueAt(item.getDescription(), model.getRowCount() - 1, 1);
			model.setValueAt(po.getQuantity(item), model.getRowCount() - 1, 2);
			model.setValueAt(df.format(item.getUnitPrice()), model.getRowCount() - 1, 3);
			model.setValueAt(df.format(po.computeTotal(item)), model.getRowCount() - 1, 4);
			
			model.setValueAt(po.getQuantityRcvd(item), model.getRowCount() - 1, 5);
			if(po.getQuantity(item) != po.getQuantityRcvd(item)){
				model.setValueAt(new POItemCellDelivered(new JCheckBox(), parent, item,
						po, poController), model.getRowCount() - 1, 6);
			}
			model.setValueAt(new POItemCellEdit(new JCheckBox(), parent, item,
					po, poController), model.getRowCount() - 1, 7);
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
