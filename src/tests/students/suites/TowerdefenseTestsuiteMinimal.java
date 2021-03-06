package tests.students.suites;

import tests.students.testcases.MainMenuTest;
import tests.students.testcases.PathTest;
import tests.tutors.testcases.EntityTestMinimal;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class TowerdefenseTestsuiteMinimal {
	
	public static Test suite() {
		
		TestSuite suite = new TestSuite("Student tests for Towerdefense - Minimal");
		suite.addTest(new JUnit4TestAdapter(MainMenuTest.class));
		suite.addTest(new JUnit4TestAdapter(PathTest.class));
		suite.addTest(new JUnit4TestAdapter(EntityTestMinimal.class));
		return suite;
	}
}
