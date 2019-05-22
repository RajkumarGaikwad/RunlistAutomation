package RunlistAutomation;

import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

public class RunlistExecutionOperationManager {
    
   
    public static void main(String[] args) {
    	RunlistExecutionOperationManager re = new RunlistExecutionOperationManager();
    	re.start();
    }
    public void start() {
        try {
        	
        	//1.DO LOGIN
        	AuthInfo authInfo = new LoginInfo().doLogin();
        	
        	//2.FETCH DATA FROM SALESFORCE
            String queryString  = "SELECT Id,Name,Action__c,Action_Type__c,Page_Layout__c, Profile__c,Record_Type__c, Object__c, ObjectName__c, Activity_Type__c,Apex_Code__c, Add_Remove_Field__c, Prior_Field__c "
                + "FROM Runlist_Item__c "
                + "WHERE Runlist_Configuration__c = 'a000o000046bQlX' ORDER BY Name";
            
            @SuppressWarnings("serial")
            Map<String,String> param = new HashMap<String,String>(){
                {put("q", queryString);}
            };
                
            HttpResponse queryResponse = new ClientURIBuilder(authInfo).postData("/services/data/v44.0/query/",param);
            
            if (queryResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new InvalidDataException("Error in fetching configuration from Force.com: " + queryResponse.getStatusLine().getStatusCode());
            } else {
            	//3.START RUNLIST EXECUTION AS PER CONFIGURATION
                String getResult = EntityUtils.toString(queryResponse.getEntity());
                
                JSONObject jsonObject = new JSONObject(getResult);
                
                JSONArray jsonArray = (JSONArray) jsonObject.get("records");
                
                if (jsonArray != null && jsonArray.length() > 0) {
                    for (int idx = 0; idx < jsonArray.length(); idx++) {
                        RunlistItemWrapper runlistItemWrapper = new RunlistItemWrapper((JSONObject) jsonArray.get(idx));
                        try {
                        	 if (GlobalConstant.ACTION_HANDLER_CONFIG_MAP.containsKey(runlistItemWrapper.action)) {
                                 String actionHandlerClass = GlobalConstant.ACTION_HANDLER_CONFIG_MAP.get(runlistItemWrapper.action);
                                 @SuppressWarnings("unchecked")
                                 Class<IrunlistItemActionHandler> handler = (Class<IrunlistItemActionHandler>) Class.forName(actionHandlerClass);
                                 IrunlistItemActionHandler actionHandler = (IrunlistItemActionHandler) handler
                                     .newInstance();
                                 System.out.println("Execution Starting for => : " + runlistItemWrapper.action);
                                 actionHandler.execute(runlistItemWrapper, authInfo);
                             } else {
                                 throw new InvalidDataException("No Action Hanlder configured for action : "+runlistItemWrapper.action);
                             }
                        }catch(Exception ex) {
                        	ex.getStackTrace();
                        	System.out.println("Error : " + runlistItemWrapper.action + " " +ex.getMessage());
                        }
                       
                    }
                    
                }
            }
        } catch (Exception exception) {
        	System.out.println("Exception occured : "+exception);
        	exception.printStackTrace();
        	exception.getStackTrace();
        }
        
    }
}
