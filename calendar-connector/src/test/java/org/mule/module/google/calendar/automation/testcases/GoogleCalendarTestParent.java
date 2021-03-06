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

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.mule.api.config.MuleProperties;
import org.mule.api.store.ObjectStore;
import org.mule.api.store.ObjectStoreException;
import org.mule.module.google.calendar.model.AclRule;
import org.mule.module.google.calendar.model.Calendar;
import org.mule.module.google.calendar.model.Event;
import org.mule.module.google.calendar.oauth.GoogleCalendarConnectorOAuthState;
import org.mule.modules.google.api.client.batch.BatchResponse;
import org.mule.modules.tests.TestParent;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GoogleCalendarTestParent extends TestParent {

	// Set global timeout of tests to 10minutes
    @Rule
    public Timeout globalTimeout = new Timeout(600000);
	
	
	@Before
	public void init() throws ObjectStoreException, Exception {
		ObjectStore objectStore = muleContext.getRegistry().lookupObject(MuleProperties.DEFAULT_USER_OBJECT_STORE_NAME);
		objectStore.store("accessTokenId", (GoogleCalendarConnectorOAuthState)context.getBean("connectorOAuthState"));
	}
	
	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILES);
	}
		
	/*
	 * Helper methods below
	 */
	
	protected Calendar getPrimaryCalendar() throws Exception {
		return getCalendar("primary");
	}
	
	protected Calendar getCalendar(String id) throws Exception {
		addToMessageTestObject("id", id);
		return runFlowAndGetPayload("get-calendar-by-id");
	}
	
	protected void clearPrimaryCalendar() throws Exception {
		clearCalendar("primary");
	}
	
	protected void clearCalendar(Calendar calendar) throws Exception {
		clearCalendar(calendar.getId());
	}
	
	protected void clearCalendar(String id) throws Exception {
		addToMessageTestObject("id", id);
		runFlowAndGetPayload("clear-calendar");
	}
	
	protected BatchResponse<Calendar> insertCalendars(List<Calendar> calendars) throws Exception {
		addToMessageTestObject("calendarsRef", calendars);
		return runFlowAndGetPayload("batch-insert-calendar");
	}
		
	protected void deleteCalendar(Calendar calendar) throws Exception {
		deleteCalendar(calendar.getId());
	}
	
	protected void deleteCalendar(String id) throws Exception {
		addToMessageTestObject("id", id);
		runFlowAndGetPayload("delete-calendar");
	}
	
	protected void deleteCalendars(List<Calendar> calendars) throws Exception {
		addToMessageTestObject("calendarsRef", calendars);
		runFlowAndGetPayload("batch-delete-calendar");
	}
	
	protected Event quickAddEvent(Calendar calendar, String eventSummary) throws Exception {
		return quickAddEvent(calendar.getId(), eventSummary);
	}
	
	protected Event quickAddEvent(String calendarId, String eventSummary) throws Exception {
		addToMessageTestObject("calendarId", calendarId);
		addToMessageTestObject("text", eventSummary);
		return runFlowAndGetPayload("quick-add-event");
	}
	
	protected Event insertEvent(Calendar calendar, Event event) throws Exception {
		return insertEvent(calendar.getId(), event);
	}
	
	protected Event insertEvent(String calendarId, Event event) throws Exception {
		addToMessageTestObject("calendarId", calendarId);
		addToMessageTestObject("calendarEventRef", event);
		return runFlowAndGetPayload("insert-event");
	}
	
	protected BatchResponse<Event> insertEvents(Calendar calendar, List<Event> events) throws Exception {
		return insertEvents(calendar.getId(), events);
	}
	
	protected BatchResponse<Event> insertEvents(String calendarId, List<Event> events) throws Exception {
		addToMessageTestObject("calendarId", calendarId);
		addToMessageTestObject("calendarEventsRef", events);
		return runFlowAndGetPayload("batch-insert-event");
	}
	
	protected void deleteEvent(String calendarId, Event event) throws Exception {
		deleteEvent(calendarId, event.getId());
	}
	
	protected void deleteEvent(Calendar calendar, Event event) throws Exception {
		deleteEvent(calendar.getId(), event.getId());
	}
	
	protected void deleteEvent(String calendarId, String eventId) throws Exception {
		addToMessageTestObject("calendarId", calendarId);
		addToMessageTestObject("eventId", eventId);
		runFlowAndGetPayload("delete-event");
	}
	
	protected void deleteEvents(Calendar calendar, List<Event> events) throws Exception {
		deleteEvents(calendar.getId(), events);
	}
	
	protected void deleteEvents(String calendarId, List<Event> events) throws Exception {
		addToMessageTestObject("calendarId", calendarId);
		addToMessageTestObject("calendarEventsRef", events);
		runFlowAndGetPayload("batch-delete-event");
	}
	
	protected void deleteAclRule(Calendar calendar, AclRule aclRule) throws Exception {
		deleteAclRule(calendar.getId(), aclRule.getId());
	}
	
	protected void deleteAclRule(String calendarId, String ruleId) throws Exception {
		addToMessageTestObject("calendarId", calendarId);
		addToMessageTestObject("ruleId", ruleId);
		runFlowAndGetPayload("delete-acl-rule");
	}
	
}
