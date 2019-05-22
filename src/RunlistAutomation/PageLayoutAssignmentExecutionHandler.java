package RunlistAutomation;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;

	
public class PageLayoutAssignmentExecutionHandler implements IrunlistItemActionHandler{
	 
	   /* 
	    * STEP:1 
	    * To be used as main method
	    * */
    	
	    public void execute(RunlistItemWrapper runlistItem,AuthInfo authInfo) throws Exception {
	    	 ////////////// -- PROFILES
	        HashMap<String,String> profileNameAndId=getProfileInfoMap(authInfo);
	        
	        ////////////// --RT
	        HashMap<String,String> recordTypeMap=getRecordTypeInfoMap(authInfo);
	        
	        //////////////--RUNLIST
	        
	        performPageLayoutAssignment(profileNameAndId, recordTypeMap, runlistItem);

	    
	    }
	    
	    public HashMap<String,String> getProfileInfoMap(AuthInfo authInfo) throws Exception {
	        
	        String inputQuery="SELECT Id, Name FROM Profile";
	        JSONArray jsonArray=fetchRESTResponse(authInfo,inputQuery);
	        
	        HashMap<String,String> profileNameAndId = new HashMap<String,String>();
	        for(int i=0;i<jsonArray.length();i++) {
	        	JSONObject childJsonArray= (JSONObject) jsonArray.get(i);
	        	//System.out.println(childJsonArray.getString("Name"));
	        	
	        	profileNameAndId.put(childJsonArray.getString("Name"), childJsonArray.getString("Id").substring(0,15)); 	
	        }
	        return profileNameAndId;
	    }
	    
	    public HashMap<String,String> getRecordTypeInfoMap(AuthInfo authInfo) throws Exception {
	        
	        String inputQuery="SELECT Id, Name FROM RecordType";
	        JSONArray jsonArray=fetchRESTResponse(authInfo,inputQuery);
	        
	        HashMap<String,String> recordTypeMap = new HashMap<String,String>();
	        if(jsonArray!=null) {
	        for(int i=0;i<jsonArray.length();i++) {
	        	JSONObject childJsonArray= (JSONObject) jsonArray.get(i);

	        	//System.out.println("Child Json is:"+childJsonArray.getString("Id").substring(0,15)+" , "+childJsonArray.getString("Name"));
	        	recordTypeMap.put(childJsonArray.getString("Name"), childJsonArray.getString("Id").substring(0,15)); 	
	        }
	        }
			return recordTypeMap;
	    
	    }
	    
	    //Step:2 - Here leverage Runlist Item of Raj
	    public void performPageLayoutAssignment(HashMap<String,String> profileNameAndId,HashMap<String,String> recordTypeMap,RunlistItemWrapper runlistItem) throws Exception {
	    	
	        HashMap<String,String[]> runListRecordsMapName = new HashMap<String,String[]>();
	       
	        	String[] profileNames = runlistItem.profile.split(";");
	        	String[] values=new String[profileNames.length];
	        	String recordTypeId="";
	        	if(recordTypeMap!=null &&runlistItem.recordType!="Master" && recordTypeMap.containsKey(runlistItem.recordType)) {
	        		recordTypeId=recordTypeMap.get(runlistItem.recordType);
	        	}
	        	else {
	        		recordTypeId="000000000000000";
	        	}
	        	for(int j=0;j<profileNames.length;j++) {
	        		values[j]=profileNameAndId.get(profileNames[j])+"_"+recordTypeId;
	        		//System.out.println("Value is:"+values[j]);
	        	}
	        	runListRecordsMapName.put(runlistItem.pageLayout, values); 
	        	//System.out.println("Map is:"+runListRecordsMapName.values());
	        
	        SeleniumClass se=new SeleniumClass();
	        try {
				se.performSeleniumScripts(runlistItem.objectName,runListRecordsMapName);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    //STEP:3 Generalize this method in Raj class
	    public JSONArray fetchRESTResponse(AuthInfo authInfo,String inputQuery) throws Exception {
	    	
	    	JSONObject jsonObject=new JSONObject();
	    	String getResult = null;
	    	
	    	 @SuppressWarnings("serial")
	         Map<String,String> param = new HashMap<String,String>(){
	             {put("q", inputQuery);} 
	         };
	             
	         HttpResponse queryResponse = new ClientURIBuilder(authInfo).postData("/services/data/v44.0/query/",param);
	        
	        //System.out.println("Response is:"+queryResponse);
	        
	        getResult = EntityUtils.toString(queryResponse.getEntity());
	        jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
	        //System.out.println("Response is:"+jsonObject.getJSONArray("records"));
	        return jsonObject.getJSONArray("records");
	    }
	}
