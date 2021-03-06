/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.google.task.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.module.google.task.model.Task;
import org.mule.module.google.task.model.TaskList;

public class GetTaskByIdTestCases extends GoogleTaskTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		testObjects = (HashMap<String, Object>) context.getBean("getTaskById");
		MuleEvent insertTaskListResponse = null;
		MuleEvent insertTaskResponse = null;
		try {
			insertTaskListResponse = lookupFlowConstruct("insert-task-list")
					.process(getTestEvent(testObjects));
			testObjects.put("taskListId", ((TaskList) insertTaskListResponse
					.getMessage().getPayload()).getId());
			insertTaskResponse = lookupFlowConstruct("insert-task").process(getTestEvent(testObjects));
			testObjects.put("taskId", ((Task) insertTaskResponse.getMessage().getPayload()).getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void tearDown() {
		try {
			lookupFlowConstruct("delete-task-list").process(getTestEvent(testObjects));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testInsertTask() {
		MuleEvent getTaskByIdResponse = null;
		try {
			getTaskByIdResponse = lookupFlowConstruct("get-task-by-id").process(getTestEvent(testObjects));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		Task task = (Task) getTaskByIdResponse.getMessage().getPayload();
		assertEquals("Make sure that the Task obtained from get-task-by-id has the right value for its notes", 
				((Task) testObjects.get("taskRef")).getNotes(), task.getNotes());
	}
}
