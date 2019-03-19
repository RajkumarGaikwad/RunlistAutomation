package RunlistAutomation;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class RunlistConfigWrapper {
	String sfRecordId;
	String name;
	ArrayList<RunlistItemWrapper> runlistItems;
	
	public RunlistConfigWrapper(JSONObject runlistConfigJsonObj) {
		this.sfRecordId = runlistConfigJsonObj.getString("Id");
		this.name = runlistConfigJsonObj.getString("Name");
		this.runlistItems = new ArrayList<RunlistItemWrapper>();
        JSONObject runlistItemsJsonObj = (JSONObject) runlistConfigJsonObj.get("Runlist_Items__r");
        JSONArray runlistItemsJsonArray  = (JSONArray) runlistItemsJsonObj.get("records");
        if(runlistItemsJsonArray!=null && runlistItemsJsonArray.length()>0){
            for (int idx = 0; idx < runlistItemsJsonArray.length(); idx++) {
           	 JSONObject runlistItmObj = (JSONObject) runlistItemsJsonArray.get(idx);
           	 RunlistItemWrapper runlistItem = new RunlistItemWrapper(runlistItmObj);
           	 this.runlistItems.add(runlistItem);  
            }
        }
	}
}
