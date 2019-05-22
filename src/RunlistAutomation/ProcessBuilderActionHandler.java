package RunlistAutomation;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.sforce.soap.tooling.sobject.Flow;


class ProcessBuilderActionHandler  implements IrunlistItemActionHandler {
	
	//run list item information
	RunlistItemWrapper runlistItem;
	//authentication details
	AuthInfo authInfo;


	@Override
	public void execute(RunlistItemWrapper runlistItem, AuthInfo authInfo) throws Exception {
		this.runlistItem = runlistItem;
		this.authInfo = authInfo;
		
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
		String flowId = "3010o000000J4CFAA0";
		
		
		HttpClient httpclient = HttpClientBuilder.create().build();
		
		String endPoint = authInfo.instanceURl + "/services/data/v44.0/tooling/sobjects/flow/"+flowId;
		 // Login requests must be POSTs
        HttpPost request = new HttpPost(endPoint);
        // add header
        request.setHeader("Authorization", "Bearer " + authInfo.accessToken);
        request.setHeader("Content-type", "application/json");
        
        String jsonString ="";// "{'Name':'testname'}";

    	request.setEntity(new StringEntity(jsonString));
    	
        //execute login post
        HttpResponse queryResponse = httpclient.execute(request);   
		
        if (queryResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new InvalidDataException("Authentication Failed :"+queryResponse.getStatusLine().getStatusCode());
        }else {
        	//success
        	System.out.println(runlistItem.action + "Success!! ");
        }
	}

}
