package view.purchaseOrder;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import view.PopUp;

import com.toedter.calendar.JDateChooser;
import controller.PurchaseOrderController;
import controller.SupplierController;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import model.Supplier;

public class FilterPO extends PopUp implements ActionListener {
	private JButton btnFilter;
	private JTextField txtTotal;
	private JDateChooser dateChooser;
	private JComboBox cmbItem, cmbSupplier, cmbTotal, cmbUnit;
	private JTextField txtInvoice;
        
        SupplierController supplierController;
        PurchaseOrderController purchaseOrderController;

	public FilterPO(JFrame parent) {

		super(parent);

		JPanel panMain = new JPanel();
		panMain.setBackground(Color.white);
		panMain.setSize(new Dimension(750, 400));
		panMain.setPreferredSize(new Dimension(750, 400));
		add(panMain);
		panMain.setLayout(new BorderLayout(0, 0));

		JPanel panContent = new JPanel();
		panContent.setBackground(Color.white);
		panMain.add(panContent, BorderLayout.CENTER);
		SpringLayout sl_panContent = new SpringLayout();
		panContent.setLayout(sl_panContent);

		JLabel lblItem = new JLabel("Item Classification:");
		lblItem.setFont(new Font("Arial", Font.PLAIN, 24));
		sl_panContent.putConstraint(SpringLayout.NORTH, lblItem, 50,
				SpringLayout.NORTH, panContent);
		sl_panContent.putConstraint(SpringLayout.WEST, lblItem, 57,
				SpringLayout.WEST, panContent);
		panContent.add(lblItem);

		JLabel lblSupplier = new JLabel("Supplier:");
		lblSupplier.setFont(new Font("Arial", Font.PLAIN, 24));
		sl_panContent.putConstraint(SpringLayout.NORTH, lblSupplier, 20,
				SpringLayout.SOUTH, lblItem);
		sl_panContent.putConstraint(SpringLayout.WEST, lblSupplier, 0,
				SpringLayout.WEST, lblItem);
		panContent.add(lblSupplier);

		JLabel lblDate = new JLabel("Date:");
		lblDate.setFont(new Font("Arial", Font.PLAIN, 24));
		sl_panContent.putConstraint(SpringLayout.NORTH, lblDate, 20,
				SpringLayout.SOUTH, lblSupplier);
		sl_panContent.putConstraint(SpringLayout.WEST, lblDate, 0,
				SpringLayout.WEST, lblItem);
		panContent.add(lblDate);

		JLabel lblGrandTotal = new JLabel("Grand Total:");
		lblGrandTotal.setFont(new Font("Arial", Font.PLAIN, 24));
		sl_panContent.putConstraint(SpringLayout.NORTH, lblGrandTotal, 20,
				SpringLayout.SOUTH, lblDate);
		sl_panContent.putConstraint(SpringLayout.WEST, lblGrandTotal, 0,
				SpringLayout.WEST, lblItem);
		panContent.add(lblGrandTotal);

		cmbItem = new JComboBox();
		cmbItem.setBackground(Color.white);
		cmbItem.setFont(new Font("Arial", Font.PLAIN, 24));
		sl_panContent.putConstraint(SpringLayout.NORTH, cmbItem, 50,
				SpringLayout.NORTH, panContent);
		sl_panContent.putConstraint(SpringLayout.WEST, cmbItem, 26,
				SpringLayout.EAST, lblItem);
		sl_panContent.putConstraint(SpringLayout.EAST, cmbItem, 431,
				SpringLayout.EAST, lblItem);
		panContent.add(cmbItem);

		cmbSupplier = new JComboBox();
		cmbSupplier.setBackground(Color.white);
		cmbSupplier.setFont(new Font("Arial", Font.PLAIN, 24));
		sl_panContent.putConstraint(SpringLayout.NORTH, cmbSupplier, 0,
				SpringLayout.NORTH, lblSupplier);
		sl_panContent.putConstraint(SpringLayout.WEST, cmbSupplier, 0,
				SpringLayout.WEST, cmbItem);
		sl_panContent.putConstraint(SpringLayout.EAST, cmbSupplier, 0,
				SpringLayout.EAST, cmbItem);
		panContent.add(cmbSupplier);

		cmbTotal = new JComboBox();
		sl_panContent.putConstraint(SpringLayout.NORTH, cmbTotal, 0, SpringLayout.NORTH, lblGrandTotal);
		sl_panContent.putConstraint(SpringLayout.EAST, cmbTotal, -294, SpringLayout.EAST, panContent);
		cmbTotal.setBackground(Color.white);
		cmbTotal.setFont(new Font("Arial", Font.PLAIN, 24));
		panContent.add(cmbTotal);

		dateChooser = new JDateChooser();
		dateChooser.setBackground(Color.white);
		dateChooser.setFont(new Font("Arial", Font.PLAIN, 24));
		dateChooser.setOpaque(false);
		dateChooser.setBorder(null);
		dateChooser.setFont(new Font("Arial", Font.PLAIN, 15));
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBackground(Color.WHITE);
		dateChooser.setPreferredSize(new Dimension(150, 30));
		sl_panContent.putConstraint(SpringLayout.NORTH, dateChooser, 0,
				SpringLayout.NORTH, lblDate);
		sl_panContent.putConstraint(SpringLayout.WEST, dateChooser, 0,
				SpringLayout.WEST, cmbItem);
		sl_panContent.putConstraint(SpringLayout.EAST, dateChooser, 0,
				SpringLayout.EAST, cmbItem);
		panContent.add(dateChooser);

		txtTotal = new JTextField();
		sl_panContent.putConstraint(SpringLayout.NORTH, txtTotal, 0, SpringLayout.NORTH, cmbTotal);
		sl_panContent.putConstraint(SpringLayout.WEST, txtTotal, 6, SpringLayout.EAST, cmbTotal);
		sl_panContent.putConstraint(SpringLayout.EAST, txtTotal, 0, SpringLayout.EAST, cmbItem);
		txtTotal.setFont(new Font("Arial", Font.PLAIN, 24));
		panContent.add(txtTotal);
		txtTotal.setColumns(10);
		
		JLabel lblInvoice = new JLabel("Invoice #:");
		sl_panContent.putConstraint(SpringLayout.WEST, lblInvoice, 0, SpringLayout.WEST, lblItem);
		lblInvoice.setFont(new Font("Arial", Font.PLAIN, 24));
		panContent.add(lblInvoice);
		
		txtInvoice = new JTextField();
		sl_panContent.putConstraint(SpringLayout.NORTH, lblInvoice, 0, SpringLayout.NORTH, txtInvoice);
		sl_panContent.putConstraint(SpringLayout.NORTH, txtInvoice, 14, SpringLayout.SOUTH, cmbTotal);
		sl_panContent.putConstraint(SpringLayout.WEST, txtInvoice, 0, SpringLayout.WEST, cmbItem);
		sl_panContent.putConstraint(SpringLayout.EAST, txtInvoice, 0, SpringLayout.EAST, cmbItem);
		txtInvoice.setFont(new Font("Arial", Font.PLAIN, 24));
		txtInvoice.setColumns(10);
		panContent.add(txtInvoice);
		
		cmbUnit = new JComboBox();
		sl_panContent.putConstraint(SpringLayout.WEST, cmbTotal, 6, SpringLayout.EAST, cmbUnit);
		sl_panContent.putConstraint(SpringLayout.NORTH, cmbUnit, 0, SpringLayout.NORTH, lblGrandTotal);
		sl_panContent.putConstraint(SpringLayout.WEST, cmbUnit, 92, SpringLayout.EAST, lblGrandTotal);
		sl_panContent.putConstraint(SpringLayout.EAST, cmbUnit, 175, SpringLayout.EAST, lblGrandTotal);
		cmbUnit.setFont(new Font("Arial", Font.PLAIN, 24));
		cmbUnit.setBackground(Color.WHITE);
		panContent.add(cmbUnit);

		JPanel panFooter = new JPanel();
		panFooter.setBackground(Color.white);
		panMain.add(panFooter, BorderLayout.SOUTH);

		btnFilter = new JButton("Filter");
		panFooter.add(btnFilter);
		btnFilter.setForeground(Color.white);
		btnFilter.setBackground(new Color(32, 130, 213));
		btnFilter.setFont(new Font("Arial", Font.PLAIN, 32));
		panFooter.add(btnFilter);

		this.getClose().addActionListener(this);
		btnFilter.addActionListener(this);
                
                
                supplierController = SupplierController.getInstance();
                purchaseOrderController = PurchaseOrderController.getInstance();
                populate();
                
		setContent(panMain);
		this.repaint();
		this.revalidate();
		this.setVisible(true);

	}
        
        public void populate(){
            Iterator iterator = supplierController.getAll();
            ArrayList<String> data = new ArrayList();
            while(iterator.hasNext()){
                data.add( ((Supplier)iterator.next()) .getName());
            }
            cmbSupplier.setModel(new DefaultComboBoxModel(data.toArray()));
            data.removeAll(data);
            
            cmbItem.setModel(new DefaultComboBoxModel(new String[] { "Hardware","Software", "Gen" }));
            cmbUnit.setModel(new DefaultComboBoxModel(new String[] { ">",">=", "<=", "<" }));
        }

	@Override
	public void actionPerformed(ActionEvent e) {
//		 TODO Auto-generated method stub
		if (e.getSource() == btnFilter) {
			this.dispose();
		} else if (e.getSource() == getClose()) {
			this.dispose();
		}
	}
}
