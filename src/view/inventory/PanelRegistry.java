package view.inventory;

import com.toedter.calendar.JDateChooser;

import controller.ContractController;

import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import model.Employee;
import model.HardwareItem;
import model.ITAsset;
import model.ITAsset.ITAssetBuilder;
import model.InventoryItem;
import model.NonITAsset;
import model.SoftwareItem;
import controller.EmployeeController;
import controller.ITAssetController;
import controller.InventoryItemController;
import controller.WarrantyController;

import java.util.Date;

import view.inventory.itemstorage.ItemStorageContract;
import view.inventory.itemstorage.ItemStorageGenInfo;
import view.inventory.itemstorage.ItemStorageGeneral;
import view.inventory.itemstorage.ItemStorageIT;
import view.inventory.itemstorage.ItemStorageNonIT;
import view.inventory.itemstorage.ItemStorageSoftware;
import view.inventory.itemstorage.ItemStorageWarranty;
import view.inventory.itemtile.TypeItemTile;
import model.Contract;
import model.Warranty;

public class PanelRegistry implements PanelRegistration {

	private String type;
	private static PanelRegistry instance = null;
	private ArrayList<ItemPanelParticipant> participantList;
	private TabInventory tabInventory;

	private PanelRegistry() {
		participantList = new ArrayList<ItemPanelParticipant>();
		type = "IT";
	}

	public void clearParticipants() {
		participantList.clear();
	}

	@Override
	public void registerParticipant(ItemPanelParticipant itemPanelParticipant) {
		// TODO Auto-generated method stub
		participantList.add(itemPanelParticipant);
		System.out.println("registered");
		System.out.println("Current Size: " + participantList.size());
	}

	@Override
	public void unregisterParticipant(ItemPanelParticipant itemPanelParticipant) {
		// TODO Auto-generated method stub
		participantList.remove(itemPanelParticipant);
	}

	@Override
	public void retrieveInformationFromAll() {
		// TODO Auto-generated method stub
		ArrayList generalInfo = new ArrayList();
		ArrayList typeInfo = new ArrayList();
		ArrayList warrantyInfo = new ArrayList();
		ArrayList contractInfo = new ArrayList();

		boolean stat = true;
		int i = 0;
		while (i < participantList.size() && stat) {
			stat=participantList.get(i).checkInput();
			i++;
		}

		if (stat) {
			for (i = 0; i < participantList.size(); i++) {
				if (i == 0) {
					Iterator iter = participantList.get(i)
							.retrieveInformation();
					while (iter.hasNext()) {
						generalInfo.add(iter.next());
					}
				} else if (i == 1) {
					Iterator iter2 = participantList.get(i)
							.retrieveInformation();
					while (iter2.hasNext()) {
						typeInfo.add(iter2.next());
					}
				} else if (i == 2) {
					Iterator iter3 = participantList.get(i)
							.retrieveInformation();
					while (iter3.hasNext()) {
						warrantyInfo.add(iter3.next());
					}
				} else if (i == 3) {
					Iterator iter4 = participantList.get(i)
							.retrieveInformation();
					while (iter4.hasNext()) {
						contractInfo.add(iter4.next());
					}
				}
			}

			float unitPrice = Float.parseFloat(generalInfo.get(2).toString());
			System.out.println(typeInfo.get(0).toString());
			Date deliveryDate;

			deliveryDate = (Date) typeInfo.get(0);

			for (int j = 0; j < generalInfo.size(); j++) {
				System.out.println(generalInfo.get(j));

			}

			for (int k = 0; k < typeInfo.size(); k++) {
				System.out.println(typeInfo.get(k));
			}

			System.out.println(type);

			if (type.equals("IT")) {

				int assetTag = Integer.parseInt(typeInfo.get(2).toString());

				if (!warrantyInfo.isEmpty()) {
					Warranty warranty = new Warranty(0,
							(Date) warrantyInfo.get(0),
							(Date) warrantyInfo.get(1));
				}
				if (!contractInfo.isEmpty()) {
					Contract contract = new Contract(0,
							(Date) contractInfo.get(1),
							(Date) contractInfo.get(2),
							Float.parseFloat((String) contractInfo.get(0)));
				}

				ITAsset itAsset = new ITAsset.ITAssetBuilder()
						.addName(generalInfo.get(0).toString())
						.addDescription(generalInfo.get(1).toString())
						.addUnitPrice(unitPrice)
						.addInvoiveNo(generalInfo.get(3).toString())
						.addLocation(generalInfo.get(4).toString())
						.addStatus(generalInfo.get(5).toString())
						.addClassification("IT")
						.addAssetTag(assetTag)
						.addServiceTag(typeInfo.get(3).toString())
						.addDeliveryDate(deliveryDate)
						.addContract(
								new Contract(0, (Date) contractInfo.get(1),
										(Date) contractInfo.get(2),
										Float.parseFloat((String) contractInfo
												.get(0))))
						.addWarranty(
								new Warranty(0, (Date) warrantyInfo.get(0),
										(Date) warrantyInfo.get(1))).build();
				InventoryItemController.getInstance().addInventoryItem(itAsset);

			} else if (type.equals("Non-IT")) {

				NonITAsset nonItAsset = new NonITAsset.NonITAssetBuilder()
						.addName(generalInfo.get(0).toString())
						.addDescription(generalInfo.get(1).toString())
						.addUnitPrice(unitPrice)
						.addInvoiveNo(generalInfo.get(3).toString())
						.addLocation(generalInfo.get(4).toString())
						.addStatus(generalInfo.get(5).toString())
						.addClassification("Non-IT").build();
				InventoryItemController.getInstance().addInventoryItem(
						nonItAsset);
			} else if (type.equals("Software")) {

				SoftwareItem software = new SoftwareItem.SoftwareBuilder()
						.addName(generalInfo.get(0).toString())
						.addDescription(generalInfo.get(1).toString())
						.addUnitPrice(unitPrice)
						.addInvoiveNo(generalInfo.get(3).toString())
						.addLocation(generalInfo.get(4).toString())
						.addStatus(generalInfo.get(5).toString())
						.addClassification("Software")
						.addLicenseKey(typeInfo.get(2).toString()).build();
				InventoryItemController.getInstance()
						.addInventoryItem(software);
			} else if (type.equals("Others")) {

				InventoryItem general = new InventoryItem.InventoryItemBuilder()
						.addName(generalInfo.get(0).toString())
						.addDescription(generalInfo.get(1).toString())
						.addUnitPrice(unitPrice)
						.addInvoiveNo(generalInfo.get(3).toString())
						.addLocation(generalInfo.get(4).toString())
						.addStatus(generalInfo.get(5).toString())
						.addClassification("Others").build();
				InventoryItemController.getInstance().addInventoryItem(general);
			}

			tabInventory.revertToMain();

		}
	}

	public void setTabInventory(TabInventory tabInventory) {
		this.tabInventory = tabInventory;
	}

	public void setCurrentType(String type) {

		if (type.equals("IT Assets")) {
			this.type = "IT";
			tabInventory.displayIT();
		} else if (type.equals("Non-IT Assets")) {
			this.type = "Non-IT";
			System.out.println("Pass");
			tabInventory.displayNonIT();
			System.out.println("Passes NON");
		} else if (type.equals("Software") || type.equals("others")) {
			this.type = "Software";
			tabInventory.displaySoftware();
		} else if (type.equals("Others")) {
			this.type = "Others";
			tabInventory.displayGeneral();
		}
	}
	
	public void setEditToCurrentSet(InventoryItem ii)
	{
		Object object = (Object)ii;
		setCurrentType(((InventoryItem) object).getClassification());
		System.out.println("TYPE: " + ((InventoryItem) object).getClassification());
		Iterator<Employee> employeeIter = EmployeeController.getInstance().getAll();
		ArrayList<String> employees = new ArrayList<String>();
		
		while(employeeIter.hasNext())
		{
			employees.add(employeeIter.next().getName());
		}
		
		if(((InventoryItem) object).getClassification().equals("IT"))
		{
			type = "IT";
			/*** 															***/
			InventoryItem inventoryItem = (ITAsset) object;
			
			participantList.get(0).loadPresets(
					ItemStorageGenInfo.getInstance()
					.saveName(inventoryItem.getName())
					.saveDescription(inventoryItem.getDescription())
					.saveUnitPrice(inventoryItem.getUnitPrice())
					.saveInvoiceNumber(inventoryItem.getInvoiceNo())
					.saveLocation(inventoryItem.getLocation())
					.saveStatus(inventoryItem.getStatus())
					.loadList()
			);
			
			((TypeItemTile) participantList.get(1)).loadAssigneeList(employees.iterator());
			((TypeItemTile) participantList.get(1)).setType("IT Assets");
			
			participantList.get(1).loadPresets(
					ItemStorageIT.getInstance()
					.saveAssetTag(((ITAsset) inventoryItem).getAssetTag())
					.saveServiceTag(((ITAsset) inventoryItem).getServiceTag())
					.loadList()
			);
					
			participantList.get(2).loadPresets(
					ItemStorageWarranty.getInstance()
					.saveStartDate(((ITAsset) inventoryItem).getWarrantyStartDate())
					.saveEndDate(((ITAsset) inventoryItem).getWarrantyEndDate())
					.loadList()
			);
			
			participantList.get(3).loadPresets(
					ItemStorageContract.getInstance()
					.saveMainCost(((ITAsset) inventoryItem).getContractMaintenanceCost())
					.saveStartDate(((ITAsset) inventoryItem).getContractStartDate())
					.saveEndDate(((ITAsset) inventoryItem).getContractEndDate())
					.loadList()
			);
			
			resetAllStorage();
			tabInventory.showPanel();
		}
		else if(((InventoryItem) object).getClassification().equals("Non-IT"))
		{
			type = "Non-IT";
			/*** 						TEMPORARY FIX									***/
			InventoryItem inventoryItem = (NonITAsset) object;
			participantList.get(0).loadPresets(
					ItemStorageGenInfo.getInstance()
					.saveName(inventoryItem.getName())
					.saveDescription(inventoryItem.getDescription())
					.saveUnitPrice(inventoryItem.getUnitPrice())
					.saveInvoiceNumber(inventoryItem.getInvoiceNo())
					.saveLocation(inventoryItem.getLocation())
					.saveStatus(inventoryItem.getStatus())
					.loadList()
			);
			((TypeItemTile) participantList.get(1)).loadAssigneeList(employees.iterator());
			((TypeItemTile) participantList.get(1)).setType("Non-IT Assets");
			
			resetAllStorage();
			tabInventory.showPanel();
		}
		else if(((InventoryItem) object).getClassification().equals("Software"))
		{
			type = "Software";
			InventoryItem inventoryItem = (SoftwareItem) object;
			participantList.get(0).loadPresets(
					ItemStorageGenInfo.getInstance()
					.saveName(inventoryItem.getName())
					.saveDescription(inventoryItem.getDescription())
					.saveUnitPrice(inventoryItem.getUnitPrice())
					.saveInvoiceNumber(inventoryItem.getInvoiceNo())
					.saveLocation(inventoryItem.getLocation())
					.saveStatus(inventoryItem.getStatus())
					.loadList()
			);
			
			
			((TypeItemTile) participantList.get(1)).loadAssigneeList(employees.iterator());
			((TypeItemTile) participantList.get(1)).setType("Software");
			participantList.get(1).loadPresets(
					ItemStorageSoftware.getInstance()
					.saveLicenseKey(((SoftwareItem) inventoryItem).getLicenseKey())
					.loadList()
			);
		
			
			System.out.println(((SoftwareItem) inventoryItem).getLicenseKey());
			resetAllStorage();
			tabInventory.showPanel();
		}
		else if(((InventoryItem) object).getClassification().equals("Others"))
		{
			type = "Others";
			InventoryItem inventoryItem = (InventoryItem) object;
			participantList.get(0).loadPresets(
					ItemStorageGenInfo.getInstance()
					.saveName(inventoryItem.getName())
					.saveDescription(inventoryItem.getDescription())
					.saveUnitPrice(inventoryItem.getUnitPrice())
					.saveInvoiceNumber(inventoryItem.getInvoiceNo())
					.saveLocation(inventoryItem.getLocation())
					.saveStatus(inventoryItem.getStatus())
					.loadList()
			);

			((TypeItemTile) participantList.get(1)).loadAssigneeList(employees.iterator());
			((TypeItemTile) participantList.get(1)).setType("Others");
			resetAllStorage();
			tabInventory.showPanel();
		}
		
	}
	
	public void resetAllStorage()
	{
		ItemStorageContract.getInstance().resetStorage();
		ItemStorageGeneral.getInstance().resetStorage();
		ItemStorageGenInfo.getInstance().resetStorage();
		ItemStorageIT.getInstance().resetStorage();
		ItemStorageNonIT.getInstance().resetStorage();
		ItemStorageSoftware.getInstance().resetStorage();
		ItemStorageWarranty.getInstance().resetStorage();
	}
	
	public void resetParticipantPanels()
	{
		for(int i = 0; i < participantList.size(); i++)
		{
			participantList.clear();
		}
	}

	public static PanelRegistry getInstance() {
		if (instance == null) {
			instance = new PanelRegistry();
		}
		return instance;
	}

}
