package RunlistAutomation;

interface IrunlistItemActionHandler {
	
	void execute(RunlistItemWrapper runlistItem,AuthInfo authInfo) throws Exception;
}
