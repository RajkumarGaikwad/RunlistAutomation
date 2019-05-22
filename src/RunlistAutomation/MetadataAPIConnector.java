package RunlistAutomation;
import com.sforce.soap.metadata.MetadataConnection;


/**
 * Login utility.
 */
public class MetadataAPIConnector extends APIConnector {
    
    public MetadataConnection doMetadataConnect(AuthInfo authInfo) throws Exception {
		super.doConnect(authInfo);
		return new MetadataConnection(config);
		
	}
   
    @Override
	String getServiceEndPoint() {
		return "/services/Soap/m/45.0";
	}
}