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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.calendar.model.Calendar;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class DeleteCalendarTestCases extends GoogleCalendarTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			addToMessageTestObject((Map<String, Object>) context.getBean("deleteCalendar"));

			// Create the calendar)
			Calendar calendar = runFlowAndGetPayload("create-calendar");
			addToMessageTestObject("id", calendar.getId());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testDeleteCalendar() {
		try {
			// Delete the calendar
			runFlowAndGetPayload("delete-calendar");

		}
		catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
			
		// Get the calendar, should throw an exception
		try {		
			runFlowAndGetPayload("get-calendar-by-id");
		}
		catch (Exception e) {
			if (e.getCause() instanceof GoogleJsonResponseException) {
				GoogleJsonResponseException googleException = (GoogleJsonResponseException) e.getCause();
				 // Not found
				assertTrue(googleException.getStatusCode() == 404);
				assertTrue(googleException.getStatusMessage().equals("Not Found"));
			}
			else fail();
		}
	}
	
}
