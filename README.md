# DSAT
Data structured automation testing framework

This is a basic version of a testing automation framework that eliminates the need for writing extensive and complex POM, PF, etc. testing projects. Elements in such a structure are stored in formats like CSV, JSON and other types(DB also) of structured data. Their processing is handled by the typeId, assigned by testers from the methods available(ElementActions class) in the framework for interacting with the element. You can add as many as needed, remembering to specify this in the ElementActionPerform class. If necessary, additionally indicate whether preparation of this element is required before execution, as with typeId == 7 or typeId == 12 in the StepActionPerform class.

This is the most basic version of the framework, but it is ready for use and extension, requiring knowledge of the Java programming language and abstract thinking. I will try to provide more details on the usage and extension of functionality in a video that I plan to record on this topic. I will update this guide with a link when it becomes available.

Example CSV:

id,flow,page,name,xpath,type_id,value,related_element_id 1,framework,google_main_page,search_bar,"//textarea[@name='q']",2,"$SEARCH",null 2,framework,google_main_page,search_btn,"//textarea[@name='q']",4,$VALUE,null 3,framework,google_search_page,search_result,"//a[contains(@ping,'wikipedia.org')]",1,$VALUE,null

Example JSON:

{ "description": "Example scenario", "flow_name": "framework", "steps": [ { "step_name": "google_main_page", "elements": [ "search_bar", "search_btn" ], "variables":{ "$SEARCH": "wiki" } }, { "step_name": "google_search_page", "elements": [ "search_result" ] } ] }