package RunlistAutomation;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.opencsv.CSVReader;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class CustomSettingRecordHandler extends PartnerAPIConnector implements IrunlistItemActionHandler{
	

    static PartnerConnection connection;
	
	@Override
	public void execute(RunlistItemWrapper runlistItem,AuthInfo authInfo) throws Exception {
        
        try {
            connection = doPartnerConnect(authInfo);
            
            //read records
            //QueryResult stepQueryResult = connection.query("SELECT Id, ObjectName__c FROM RunlistStep__c WHERE ParentRunlist__r.IsCurrent__c = TRUE");
            //SObject[] steps = stepQueryResult.getRecords();
            QueryResult attachmentQueryResult = connection.query("SELECT Body FROM Attachment WHERE ParentId = '" + runlistItem.sfRecordId + "'");
        	SObject[] attachments = attachmentQueryResult.getRecords();
            String home = System.getProperty("user.home");
        	System.out.println(home);
        	try {
	            
        		byte[] decodedString = Base64.getDecoder().decode(((String) attachments[0].getField("Body")).getBytes(StandardCharsets.UTF_8));
				
        		CSVReader csvReader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(decodedString)));
	            List<String[]> allData = csvReader.readAll(); 
	            
	            // load Data 
	            SObject czc = new SObject((String) runlistItem.customSettingName);
	            ArrayList<SObject> czcList = new ArrayList<SObject>();
	            
	            Integer i = 0;
	            Integer fieldCount = 0;
	            ArrayList<String> fieldNames = new ArrayList<String>();// = new List<String>();
	            for (String[] row : allData) { 
	            	czc = new SObject((String) runlistItem.customSettingName);
	            	if(i == 0) {
		                for (String cell : row) { 
		                    fieldNames.add(cell.toString());
		                } 
	            	} else {
	            		fieldCount = 0;
	            		for (String cell : row) { 
	            			czc.setSObjectField(fieldNames.get(fieldCount), cell.toString());
	            			fieldCount++;
	            		}
		            	czcList.add(czc);
	            	} 
	            	i++;
	            } 
	            //insert records
	            SaveResult[] saveres = new SaveResult[] {};
	            saveres = connection.create((SObject [])czcList.toArray(new SObject[0]));
	            System.out.println("Save Result: " + saveres[1].isSuccess());
	            csvReader.close();
        	}catch(Exception e) {
        		System.out.println("failed: " + e.getMessage() + " line# " + e.getStackTrace().toString());
        	}
        } catch (ConnectionException e1) {
            e1.printStackTrace();
        }
	}
	
}
