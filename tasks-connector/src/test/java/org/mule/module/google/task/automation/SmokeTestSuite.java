package org.mule.module.google.task.automation;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.module.google.task.automation.testcases.ClearTasksTestCases;
import org.mule.module.google.task.automation.testcases.DeleteTaskListTestCases;
import org.mule.module.google.task.automation.testcases.DeleteTaskTestCases;
import org.mule.module.google.task.automation.testcases.GetTaskByIdTestCases;
import org.mule.module.google.task.automation.testcases.GetTaskListByIdTestCases;
import org.mule.module.google.task.automation.testcases.GetTaskListsTestCases;
import org.mule.module.google.task.automation.testcases.GetTasksTestCases;
import org.mule.module.google.task.automation.testcases.InsertTaskListTestCases;
import org.mule.module.google.task.automation.testcases.InsertTaskTestCases;
import org.mule.module.google.task.automation.testcases.MoveTaskTestCases;
import org.mule.module.google.task.automation.testcases.SmokeTests;
import org.mule.module.google.task.automation.testcases.UpdateTaskTestCases;

@RunWith(Categories.class)
@IncludeCategory(SmokeTests.class)
@SuiteClasses({
	ClearTasksTestCases.class, DeleteTaskListTestCases.class,
	DeleteTaskTestCases.class, GetTaskByIdTestCases.class,
	GetTaskListByIdTestCases.class, GetTasksTestCases.class,
	GetTaskListsTestCases.class, InsertTaskListTestCases.class,
	InsertTaskTestCases.class, MoveTaskTestCases.class,
	UpdateTaskTestCases.class
})
public class SmokeTestSuite {

}
