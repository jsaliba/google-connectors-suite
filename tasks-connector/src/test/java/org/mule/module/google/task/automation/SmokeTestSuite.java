/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.google.task.automation;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.module.google.task.automation.testcases.DeleteTaskListTestCases;
import org.mule.module.google.task.automation.testcases.GetTaskByIdTestCases;
import org.mule.module.google.task.automation.testcases.GetTaskListByIdTestCases;
import org.mule.module.google.task.automation.testcases.InsertTaskListTestCases;
import org.mule.module.google.task.automation.testcases.InsertTaskTestCases;
import org.mule.module.google.task.automation.testcases.SmokeTests;

@RunWith(Categories.class)
@IncludeCategory(SmokeTests.class)
@SuiteClasses({
	DeleteTaskListTestCases.class, GetTaskByIdTestCases.class,
	GetTaskListByIdTestCases.class, InsertTaskListTestCases.class,
	InsertTaskTestCases.class
})
public class SmokeTestSuite {

}
