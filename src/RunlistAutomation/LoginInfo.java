package RunlistAutomation;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

class LoginInfo {
	
	static final String USERNAME = "rajpersonalorg@personal.com.dev";
    static final String PASSWORD = "castlereagh1";
    static final String LOGINURL = "https://devrajpersonal-dev-ed.my.salesforce.com";
    static final String CLIENTID = "3MVG9pe2TCoA1Pf7ShgXiepXTeo2ffw_AHk48aKHHCrM79hFK8fbA0fJ8Nn4P4Z6ESkEag7a8xDsafpjmH1aa";
    static final String CLIENTSECRET = "B5CD5770E5EEF9D65B4093FEB2A5952551F7F2C0A2217090FEE53CB06D3C99B0";
    static final String GRANTSERVICE = "/services/oauth2/token?grant_type=password";
	
	
	public AuthInfo doLogin() throws Exception {
		
		HttpClient httpclient = HttpClientBuilder.create().build();

        // Assemble the login request URL
        String loginURL = LOGINURL + GRANTSERVICE + "&client_id=" + CLIENTID + "&client_secret=" + CLIENTSECRET
                + "&username=" + USERNAME + "&password=" + PASSWORD;

        // Login requests must be POSTs
        HttpPost httpPost = new HttpPost(loginURL);
        //execute login post
        HttpResponse loginResponse = httpclient.execute(httpPost);      
       
        // verify response is HTTP OK
        if (loginResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
        	throw new InvalidDataException("Error authenticating to Force.com in Login Call : " + loginResponse.getStatusLine().getStatusCode());
        }
        
    	JSONObject jsonObject = (JSONObject) new JSONTokener(EntityUtils.toString(loginResponse.getEntity())).nextValue();
    	 // release connection
        httpPost.releaseConnection();
        System.out.println("Successful login");
        return new AuthInfo(jsonObject.getString("instance_url"), jsonObject.getString("access_token"));
        
	}

}
