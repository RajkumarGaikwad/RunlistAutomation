package RunlistAutomation;

import java.util.HashMap;
import java.util.Map;

class GlobalConstant {

	 @SuppressWarnings("serial")
		final static Map<String, String> ACTION_HANDLER_CONFIG_MAP = new HashMap<String, String>() {
	       { 
			 put("EXECUTE_ANONYMOUS", "RunlistAutomation.ExecuteAnonymousActionHandler");
			 put("BATCH_JOB_EXECUTE","RunlistAutomation.ApexBatchJobExecuteHandler");
	         put("BATCH_JOB_ABORT","RunlistAutomation.ApexBatchJobAbortHandler");
	    	 put("CUSTOM_SETTING", "RunlistAutomation.CustomSettingRecordHandler");
	         put("PAGE_LAYOUT_ASSIGNMENT", "RunlistAutomation.PageLayoutAssignmentExecutionHandler");
		    // put("PROCESS_BUILDER","RunlistAutomation.ProcessBuilderActionHandler");
		     put("VALIDATION_RULE","RunlistAutomation.ValidationRuleExecutionHandler");
	       	}
		};
	
	final static String PROCESS_BUILDER_ACTIVATE = "Activate";
	final static String PROCESS_BUILDER_DEACTIVATE = "Deactivate";
	
}
