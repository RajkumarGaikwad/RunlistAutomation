package RunlistAutomation;

import com.sforce.soap.tooling.ToolingConnection;

class ToolingAPIConnector extends APIConnector{
	
	public ToolingConnection doToolingConnect(AuthInfo authInfo) throws Exception {
		super.doConnect(authInfo);
		return com.sforce.soap.tooling.Connector.newConnection(config);
		
	}

	@Override
	String getServiceEndPoint() {
		return "/services/Soap/T/45.0";
	}
	
}
