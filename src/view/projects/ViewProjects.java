package view.projects;

import controller.ProjectController;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;

import model.Project;
import model.Supplier;
import model.SupplierContact;
import view.CellEdit;
import view.Observer;
import view.PanelCell;
import view.ViewTemplate;
import view.supplier.SupplierCellEdit;

public class ViewProjects extends ViewTemplate implements Observer {

//	SupplierController supplierController;
	ProjectController projectController;
	private TabProject tab;
	private JFrame parent;

	public ViewProjects(JFrame parent, TabProject tab) {
		super();
		this.parent=parent;
		this.tab = tab;
		if (this.tab == null)
			System.out.println("VIEW CONST TAB NULL");
		else
			System.out.println("Gio");
		projectController = ProjectController.getInstance();
		projectController.registerObserver(this);
		
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		setColCount(4);
		String headers[] = { "Project", "Start Date", "End Date", "" };
		getModel().setColumnIdentifiers(headers);
		setColRendEdit(new PanelCell(), new PanelCell());
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update() {

		clearTable();
		Project project;
		Iterator data = projectController.getAll();
		while (data.hasNext()) {

            project = (Project) data.next();

            System.out.println("Project THINGY" + project.getName());


            getModel().setRowCount(getModel().getRowCount() + 1);
            getModel().setValueAt(project.getName(),
            getModel().getRowCount() - 1, 0);
            
            getModel().setValueAt(project.getStartDate().toString(), getModel().getRowCount() - 1, 1);
            
            getModel().setValueAt(project.getEndDate().toString(), getModel().getRowCount() - 1, 2);
            getModel().setValueAt(new ProjectCellEdit(project, tab),
    				getModel().getRowCount() - 1, 3);

        }
        if (this.tab == null) {
            System.out.println("VIEW TAB NULL");
        } else {
            System.out.println("Gio");
        }
        
	}

	public void filterPopulate(Iterator data) {
		clearTable();
		while (data.hasNext()) {
			getModel().setRowCount(getModel().getRowCount() + 1);
			getModel().setValueAt(data.next(), getModel().getRowCount() - 1, 0);
			getModel().setValueAt(data.next(), getModel().getRowCount() - 1, 1);
			getModel().setValueAt(data.next(), getModel().getRowCount() - 1, 2);
			
		}
	}
}