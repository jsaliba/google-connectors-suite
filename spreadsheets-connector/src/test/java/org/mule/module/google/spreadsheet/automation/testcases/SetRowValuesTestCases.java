package org.mule.module.google.spreadsheet.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.spreadsheet.model.Cell;
import org.mule.module.google.spreadsheet.model.Row;
import org.mule.module.google.spreadsheet.model.Worksheet;

public class SetRowValuesTestCases extends GoogleSpreadsheetsTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("setRowValues");
			
			String spreadsheetTitle = (String) testObjects.get("spreadsheet");
			createSpreadsheet(spreadsheetTitle);

			String title = (String) testObjects.get("title");
			int rowCount = (Integer) testObjects.get("rowCount");
			int colCount = (Integer) testObjects.get("colCount");
			
			Worksheet worksheet = createWorksheet(spreadsheetTitle, title, rowCount, colCount);
			testObjects.put("worksheetObject", worksheet);			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testSetRowValues() {
		try {
			String spreadsheetTitle = (String) testObjects.get("spreadsheet");
			Worksheet worksheet = (Worksheet) testObjects.get("worksheetObject");
			List<Row> inputRows = (List<Row>) testObjects.get("rowsRef");
			
			testObjects.put("worksheet", worksheet.getTitle());

			MessageProcessor flow = lookupFlowConstruct("set-row-values");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<Row> allRows = getAllCells(spreadsheetTitle, worksheet.getTitle());
			
			assertTrue(allRows.size() == inputRows.size());
			
			for (Row row : inputRows) {
				assertTrue(allRows.contains(row));

				Row retrievedRow = allRows.get(allRows.indexOf(row));
				
				List<Cell> inputCells = row.getCells();
				List<Cell> retrievedCells = retrievedRow.getCells();

				for (final Cell cell : inputCells) {
					List<Cell> matchingCells = (List<Cell>) CollectionUtils.select(retrievedCells, new Predicate() {
						
						@Override
						public boolean evaluate(Object object) {
							Cell cellObject = (Cell) object;
							return (cell.getColumnNumber() == cellObject.getColumnNumber())
									&& (cell.getRowNumber() == cellObject.getRowNumber())
									&& (StringUtils.equals(cell.getValueOrFormula(), cellObject.getValueOrFormula()));
						}
					});
					assertTrue(matchingCells.size() == 1);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String spreadsheet = (String) testObjects.get("spreadsheet");
			deleteSpreadsheet(spreadsheet);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}