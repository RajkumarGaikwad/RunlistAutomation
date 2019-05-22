package RunlistAutomation;

import com.sforce.soap.metadata.CustomObject;
import com.sforce.soap.metadata.Metadata;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.metadata.ReadResult;

class CustomMetadataTypeActionHandler extends MetadataAPIConnector implements IrunlistItemActionHandler{
	
	 @Override
	 public void execute(RunlistItemWrapper runlistItem,AuthInfo authInfo) throws Exception{
		 
		 MetadataConnection metadataConnection = doMetadataConnect(authInfo);	 
		
		 ReadResult readResult = metadataConnection.readMetadata("CustomObject", 
				 new String[] {"Pin_Code_Config__mdt"});
			 
		 Metadata[] mdInfo = readResult.getRecords();
		 
		 System.out.println("Number of component info returned: " + mdInfo.length);
			 for (Metadata md : mdInfo) {
			 if (md != null) {
				 CustomObject obj = (CustomObject) md;
				 System.out.println("Custom object full name: " + obj.getFullName());
				 System.out.println("Label: " + obj.getLabel());
				 System.out.println("Number of custom fields: " + obj.getFields().length);
				 System.out.println("Sharing model: " + obj.getSharingModel());
			 } else {
				 System.out.println("Empty metadata.");
			 }
			 }
			 
	 }
}
