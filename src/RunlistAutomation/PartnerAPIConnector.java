package RunlistAutomation;

import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;

class PartnerAPIConnector extends APIConnector{
	
	public PartnerConnection doPartnerConnect(AuthInfo authInfo) throws Exception {
		super.doConnect(authInfo);
		return Connector.newConnection(config);
	}

	@Override
	String getServiceEndPoint() {
		return "/services/Soap/u/45.0";
	}

}
