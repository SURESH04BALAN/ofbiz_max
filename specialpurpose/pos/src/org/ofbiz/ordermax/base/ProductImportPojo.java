package org.ofbiz.ordermax.base;

import org.ofbiz.base.util.Debug;

public class ProductImportPojo {
	
	public static final String module = ProductImportPojo.class.getName();
	
	public String ProductId;
	public String OrigProductId;
	public String BarcodeItem;
	public String Item;
	public String SellingPrice;	
	public String PurchasePrice;	
	public String WholeSalePrice;	
	public String DEPARTMENT;	
	public String productTypeId;	
	public String priceCurrencyUomId;	
	public String supplierPartyId;	
	public String INV_ITEM_ID;	
	public String facilityId;	
	public String availableToPromise;	
	public String onHand;	
	public String minimumStock;	
	public String reorderQuantity;	
	public String daysToShip;
	public String Brand;
	static public int inventoryId = 12000;

	public void SetItem(int index, String item) throws Exception{
		
		item = item.trim();
		
		switch (index) {
		case 0 :
			if(item.isEmpty()){
				ProductId = new String("");;		
				OrigProductId = new String("");	
				throw new Exception("Product Id is empty");
			}
			else{
				ProductId = item;		
				OrigProductId = item;
			}
			break;
		case 1 :					
			BarcodeItem= item;					
			break;

		case 2 :					
			if(item.isEmpty()){
				Item=new String("");
				throw new Exception("Product Name is empty");
			}
			else{
				Item= item;
			}
								
			break;

		case 3 :					
			Item= item;					
			break;
			
		case 4 :					
			SellingPrice= item;					
			break;
		case 5 :
			PurchasePrice= item;					
			break;
		case 6 :
			WholeSalePrice= item;					
			break;
		case 7 :
			DEPARTMENT= item;					
			break;
		case 8 :
			
			Brand = item;					
			break;
			
		case 9 :
			if(item.isEmpty())
				productTypeId= "FINISHED_PRODUCT";
			else
				productTypeId= item;					
			break;
		case 10 :					
			if(item.isEmpty())
				throw new Exception("Currency Id is empty");
			else
				priceCurrencyUomId= item;					
			break;

		case 11 :
			supplierPartyId= item;					
			break;
		case 12 :
			
			if(!item.isEmpty()){
				INV_ITEM_ID= item;
				getMaxInventoryNumb(INV_ITEM_ID);					
			}
			
			break;
		case 13 :				

			if(item.isEmpty()) 
				throw new Exception("Facility Id is empty");
			else
				facilityId= item;
			break;
		case 14 :					
			if(item.isEmpty())
				availableToPromise= "1";
			else
				availableToPromise= item;					
			break;
			
		case 15 :					
			if(item.isEmpty())
				onHand= "1";
			else
				onHand= item;					
			break;
		case 16 :					
			if(item.isEmpty())
				minimumStock= "1";
			else
				minimumStock= item;					
			break;

		case 17 :					
			if(item.isEmpty())
				reorderQuantity= "1";
			else
				reorderQuantity= item;					
			break;

		case 18 :
			daysToShip= item;					
			break;					
		}
	}
	
	public boolean isEmpty(){
		return 	ProductId.isEmpty()
		&& BarcodeItem.isEmpty()
		&& Item.isEmpty()
		&& SellingPrice.isEmpty()	
		&& PurchasePrice.isEmpty()	
		&& WholeSalePrice.isEmpty()
		&& DEPARTMENT.isEmpty()	
		&& productTypeId.isEmpty()
		&& priceCurrencyUomId.isEmpty()
		&& supplierPartyId.isEmpty()
		&& INV_ITEM_ID.isEmpty()
		&& facilityId.isEmpty()
		&& availableToPromise.isEmpty()
		&& onHand.isEmpty()
		&& minimumStock.isEmpty()
		&& reorderQuantity.isEmpty()
		&& daysToShip.isEmpty();
	}        						

	int getMaxInventoryNumb(String invId){
		if(invId.length() > 6){
			
			String id = invId.toUpperCase();//.substring(3, 7);
			int invNum = 0;
			
			try{
				invNum = new Integer(id).intValue();
			}
			catch(Exception e){
//				Debug.logError(e, module);
				id = invId.toUpperCase().substring(3, 7);
								
				try{
					invNum = new Integer(id).intValue();
				}
				catch(Exception e1){
					Debug.logError(e1, module);
				}				
			}
			
			if( invNum > inventoryId )
				inventoryId = invNum;
		}
		
		return inventoryId;
	}

}
