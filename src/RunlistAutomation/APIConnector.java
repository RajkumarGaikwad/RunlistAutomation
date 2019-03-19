package RunlistAutomation;

import com.sforce.ws.ConnectorConfig;

abstract class APIConnector {
	ConnectorConfig config = new ConnectorConfig();
	
	void doConnect(AuthInfo authInfo) {
		config.setSessionId(authInfo.accessToken);
		config.setServiceEndpoint(authInfo.instanceURl + getServiceEndPoint());
	}
	
	abstract String  getServiceEndPoint();

}
