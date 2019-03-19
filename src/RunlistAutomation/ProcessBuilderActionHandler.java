package RunlistAutomation;


import com.sforce.soap.tooling.sobject.Flow;
import com.sforce.soap.tooling.sobject.SObject;
import com.sforce.soap.tooling.SaveResult;

import java.util.ArrayList;

import com.sforce.soap.tooling.QueryResult;
import com.sforce.soap.tooling.ToolingConnection;


class ProcessBuilderActionHandler extends ToolingAPIConnector implements IrunlistItemActionHandler {
	
	//run list item information
	RunlistItemWrapper runlistItem;
	//authentication details
	AuthInfo authInfo;
	
	//tooling connection
	ToolingConnection connection;


	@Override
	public void execute(RunlistItemWrapper runlistItem, AuthInfo authInfo) throws Exception {
		this.runlistItem = runlistItem;
		this.authInfo = authInfo;
		this.connection = doToolingConnect(this.authInfo);
		
		if(null == runlistItem.actionType) throw new InvalidDataException("Please specify Action Type to execute ");
		if(GlobalConstant.PROCESS_BUILDER_ACTIVATE.equalsIgnoreCase(this.runlistItem.actionType)) {
			activateProcessBuilder();
		}else if(GlobalConstant.PROCESS_BUILDER_DEACTIVATE.equalsIgnoreCase(this.runlistItem.actionType)) {
			deactivateProcessBuilder();
		}
	}
	
	public void activateProcessBuilder() throws Exception {
	}
	
	public void deactivateProcessBuilder() throws Exception {
//		String flowId = "3010o000000J4CFAA0";
//		
//        QueryResult queryResult = this.connection.query("SELECT Id,FullName,Status FROM Flow WHERE Id = '" + flowId + "'");
//        ArrayList<SObject> flowRecordsToUpdate = new ArrayList<SObject>();
//        
//        for(SObject sobjRec : queryResult.getRecords()) {
//        	System.out.println("flowRecord==>"+sobjRec.toString());
//        	Flow f = (Flow)sobjRec;
//        	f.setStatus("Draft");
//        	f.
//        	//sobjRec.setSObjectField("Status","Obsolete");
//        	flowRecordsToUpdate.add(f);
//        }
//        SaveResult[] saveres = new SaveResult[] {};
//        saveres = this.connection.update((SObject [])flowRecordsToUpdate.toArray(new SObject[0]));
//        System.out.println("Save Result: " + saveres[0].isSuccess()  + "Erro :" +saveres[0].getErrors()[0].getMessage() );
//
//        System.out.println(runlistItem.action + "Success!! ");  
		
       
	}

}
