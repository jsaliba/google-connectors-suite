/**
 * Mule Google Calendars Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.google.calendar.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.calendar.model.AclRule;
import org.mule.module.google.calendar.model.Calendar;

public class UpdateAclRuleTestCases extends GoogleCalendarTestParent {
	

	
	@Before
	public void setUp() {
		try {
			addToMessageTestObject((Map<String, Object>) context.getBean("updateAclRule"));
			
			// Insert calendar and get reference to retrieved calendar
			Calendar calendar = runFlowAndGetPayload("create-calendar");
			
			// Replace old calendar instance with new instance
			addToMessageTestObject("calendarRef", calendar);
			addToMessageTestObject("calendarId", calendar.getId());
			
			String roleBefore = getValueFromMessageTestObject("roleBefore"); 
			addToMessageTestObject("role", roleBefore);
		
			// Insert the ACL rule
						
			AclRule returnedAclRule = runFlowAndGetPayload("insert-acl-rule");
			addToMessageTestObject("aclRule", returnedAclRule);	
			addToMessageTestObject("ruleId", returnedAclRule.getId());
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	@Category({ RegressionTests.class})
	@Test
	public void testUpdateAclRule() {
		try {
			String roleAfter = getValueFromMessageTestObject("roleAfter");
			
			AclRule aclRule = getValueFromMessageTestObject("aclRule");
			aclRule.setRole(roleAfter);
			addToMessageTestObject("aclRuleRef", aclRule);			
			
			aclRule = runFlowAndGetPayload("update-acl-rule");
			String roleAfterUpdate = aclRule.getRole();
			assertEquals(roleAfter, roleAfterUpdate);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	@After
	public void tearDown() {
		try {
			String calendarId = getValueFromMessageTestObject("calendarId");
			deleteCalendar(calendarId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	
}
