package RunlistAutomation;

import com.sforce.soap.tooling.QueryResult;
import com.sforce.soap.tooling.SaveResult;
import com.sforce.soap.tooling.ToolingConnection;
import com.sforce.soap.tooling.sobject.SObject;
import com.sforce.soap.tooling.sobject.ValidationRule;

public class ValidationRuleExecutionHandler extends ToolingAPIConnector implements IrunlistItemActionHandler{
	    
	    @Override
	    public void execute(RunlistItemWrapper runlistItem,AuthInfo authInfo) throws Exception{
	    	
	    	
	    	ToolingConnection toolCon = doToolingConnect(authInfo);
	    	
	    	String queryString = "Select FullName,Metadata From ValidationRule where ValidationName = 'Acc_Name_Should_Be_15Letters'";
	        QueryResult queryResult = toolCon.query(queryString);
	        SaveResult[] saveResults;
	        ValidationRule vr = (ValidationRule)queryResult.getRecords()[0];
	        System.out.println("wrapper------" + runlistItem.actionType);
	        System.out.println("");
	        if(runlistItem.actionType.equalsIgnoreCase("Create")) {
	        	System.out.println("in create------");
	        	vr.getMetadata().setDescription(runlistItem.description);
	        	vr.getMetadata().setErrorConditionFormula(runlistItem.errCondnForm);
	        	vr.getMetadata().setErrorDisplayField(runlistItem.errLoc);
	        	vr.getMetadata().setErrorMessage(runlistItem.errMsg);
	        	vr.setValidationName(runlistItem.ruleName);
	        	vr.getMetadata().setActive(runlistItem.active);
	        	
	        	SObject[] sObjectsCr = new SObject[] { vr };
		        saveResults = toolCon.create(sObjectsCr);
	        }
	        else {
		        if(runlistItem.actionType.equalsIgnoreCase("Deactivate")) {
		        	System.out.println("deactivate------");
		        	vr.getMetadata().setActive(false);
		        	System.out.println("---in deactivate");
		        }
		        else if(runlistItem.actionType.equalsIgnoreCase("Activate")) {
		        	System.out.println("---in activate");
		        	vr.getMetadata().setActive(true);
		        }
		        else if(runlistItem.actionType.equalsIgnoreCase("Update")) {
		        	System.out.println("in update------");
		        	vr.getMetadata().setDescription(runlistItem.description);
		        	vr.getMetadata().setErrorConditionFormula(runlistItem.errCondnForm);
		        	vr.getMetadata().setErrorDisplayField(runlistItem.errLoc);
		        	vr.getMetadata().setErrorMessage(runlistItem.errMsg);
		        	vr.setValidationName(runlistItem.ruleName);
		        	vr.getMetadata().setActive(runlistItem.active);
		        }
		        //////////////////////////
		        //System.out.println("Is Active"+vr.getMetadata().getActive());
		        //System.out.println("vr-------"+vr);
		        
		        SObject[] sObjectsUpd = new SObject[] { vr };
		        saveResults = toolCon.update(sObjectsUpd);
	    	}
	        for (SaveResult saveResult : saveResults) {
	        	if (saveResult.isSuccess()) {
	            	System.out.println("Successful.");
	            	System.out.println("ID: " + saveResult.getId());
	        	} else {
	            	com.sforce.soap.tooling.Error error = saveResult.getErrors()[0];
	            	System.out.println("Failed.");
	            	System.out.println("Status code: " + error.getStatusCode());
	            	System.out.println("Message: " + error.getMessage());
	        	}
	        }
	    	}
	       
	
	

}
