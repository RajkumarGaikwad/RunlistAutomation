package RunlistAutomation;


import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

class ExecuteAnonymousActionHandler implements IrunlistItemActionHandler {


	@Override
	public void execute(RunlistItemWrapper runlistItem,AuthInfo authInfo) throws Exception {
		
        
        @SuppressWarnings("serial")
        Map<String,String> param = new HashMap<String,String>(){
            {put("anonymousBody", runlistItem.apexCode);} 
        };
            
        HttpResponse queryResponse = new ClientURIBuilder(authInfo).postData("/services/data/v45.0/tooling/executeAnonymous/",param);
		
        if (queryResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new InvalidDataException("Error in ExecuteAnonymousActionHandler authentication :"+queryResponse.getStatusLine().getStatusCode());
        }else {
        	//success
        	System.out.println("Execute Anonymous Success!! ");
        }

	}

}
