package RunlistAutomation;

import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

class ClientURIBuilder {
	
	public ClientURIBuilder(AuthInfo authInfo) {
		this.authInfo = authInfo;
	}
	
	//store authentication information
	AuthInfo authInfo;
	
	public HttpResponse postData(String path, Map<String,String> param) throws Exception {
		
		HttpClient httpclient = HttpClientBuilder.create().build();
		
		URIBuilder builder = new URIBuilder(this.authInfo.instanceURl);
		builder.setPath(path);
			
		 for(String key :  param.keySet()) {
			 builder.setParameter(key,param.get(key));
		 }
	 
		 final HttpGet get = new HttpGet(builder.build());
		
		 get.setHeader("Authorization", "Bearer " + this.authInfo.accessToken);
		
		return httpclient.execute(get);
	

}
}