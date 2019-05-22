package RunlistAutomation;

import org.json.JSONObject;

public class RunlistItemWrapper{
	String sfRecordId;
	String name;
	String action;
	String actionType;
	String activityType;
	String apexCode;
	String pageLayout;
	String profile;
	String recordType;
	String objectName;
	String customSettingName;
	//KK
	boolean active;
	String description;
	String errCondnForm;
	String errLoc;
	String errMsg;
	String ruleName;
	String addRemoveField;
	String priorField;
	
	public RunlistItemWrapper() {}
	
	public RunlistItemWrapper(JSONObject runlistItmObj) {
		
		this.sfRecordId = runlistItmObj.getString("Id");
		
		this.name = runlistItmObj.getString("Name");
		
		if(!runlistItmObj.isNull("Action__c")) {
			this.action = runlistItmObj.getString("Action__c");
		}
		if(!runlistItmObj.isNull("Action_Type__c")) {
			this.actionType = runlistItmObj.getString("Action_Type__c");
		}
		if(!runlistItmObj.isNull("Activity_Type__c")) {
			this.activityType = runlistItmObj.getString("Activity_Type__c");
		}
		if(!runlistItmObj.isNull("Apex_Code__c")) {
			this.apexCode = runlistItmObj.getString("Apex_Code__c");
		}
		
		if(!runlistItmObj.isNull("Page_Layout__c")) {
			this.pageLayout=runlistItmObj.getString("Page_Layout__c");
		}
		
		if(!runlistItmObj.isNull("Profile__c")) {
			this.profile=runlistItmObj.getString("Profile__c");
		}
		
		if(!runlistItmObj.isNull("Record_Type__c")) {
			this.recordType=runlistItmObj.getString("Record_Type__c");
		}
		if(!runlistItmObj.isNull("Object__c")) {
			this.objectName=runlistItmObj.getString("Object__c");
		}
		if(!runlistItmObj.isNull("ObjectName__c")) {
			this.customSettingName=runlistItmObj.getString("ObjectName__c");
		}
		//KK
		if(!runlistItmObj.isNull("Active__c")) {
			System.out.println("inside if--- ");
			this.active = runlistItmObj.getBoolean("Active__c");
		}
		if(!runlistItmObj.isNull("Description__c")) {
			this.description = runlistItmObj.getString("Description__c");
			System.out.println("inside if--- " + this.description);
		}
		if(!runlistItmObj.isNull("errorConditionFormula__c")) {
			this.errCondnForm = runlistItmObj.getString("errorConditionFormula__c");
		}
		if(!runlistItmObj.isNull("errorLocation__c")) {
			this.errLoc = runlistItmObj.getString("errorLocation__c");
		}
		if(!runlistItmObj.isNull("errorMessage__c")) {
			this.errMsg = runlistItmObj.getString("errorMessage__c");
		}
		if(!runlistItmObj.isNull("Rule_Name__c")) {
			this.ruleName = runlistItmObj.getString("Rule_Name__c");
		}
		
		if(!runlistItmObj.isNull("Add_Remove_Field__c")) {
			this.addRemoveField = runlistItmObj.getString("Add_Remove_Field__c");
		}
		if(!runlistItmObj.isNull("Prior_Field__c")) {
			this.priorField = runlistItmObj.getString("Prior_Field__c");
		}
		
		//Add_Remove_Field__c
		//Prior_Field__c
	}
}
