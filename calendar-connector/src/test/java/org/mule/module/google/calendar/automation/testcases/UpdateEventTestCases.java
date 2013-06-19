package org.mule.module.google.calendar.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.calendar.model.Calendar;
import org.mule.module.google.calendar.model.Event;

public class UpdateEventTestCases extends GoogleCalendarTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("updateEvent");
		
			// Insert the calendar
			Calendar calendar = insertCalendar((Calendar) testObjects.get("calendarRef"));
			
			// Update test objects
			testObjects.put("calendar", calendar);
			testObjects.put("calendarId", calendar.getId());
		
			String beforeText = testObjects.get("summaryBefore").toString();
			
			testObjects.put("text", beforeText);
			MessageProcessor flow = lookupFlowConstruct("quick-add-event");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Event event = (Event) response.getMessage().getPayload();
			testObjects.put("event", event);
			testObjects.put("eventId", event.getId());			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, SanityTests.class})
	@Test
	public void testUpdateEvent() {
		try {
			String summaryAfter = testObjects.get("summaryAfter").toString();
			Event event = (Event) testObjects.get("event");
			event.setSummary(summaryAfter);
			testObjects.put("calendarEventRef", event);
			
			
			MessageProcessor flow = lookupFlowConstruct("update-event");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Event afterEvent = (Event) response.getMessage().getPayload();
			String afterText = afterEvent.getSummary();
			assertEquals(afterText, summaryAfter);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String calendarId = testObjects.get("calendarId").toString();
			deleteCalendar(calendarId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
