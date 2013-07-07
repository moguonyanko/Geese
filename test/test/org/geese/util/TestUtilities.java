package test.org.geese.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.geese.util.Strings;

public class TestUtilities{
	
	public TestUtilities(){
	}
	
	@BeforeClass
	public static void setUpClass(){
	}
	
	@AfterClass
	public static void tearDownClass(){
	}
	
	@Before
	public void setUp(){
	}
	
	@Test
	public void can_check_String_is_null_or_empty(){
		String actual0 = "";
		assertTrue(Strings.isNullOrEmpty(actual0));
		String actual1 = null;
		assertTrue(Strings.isNullOrEmpty(actual1));
		String actual2 = "A";
		assertFalse(Strings.isNullOrEmpty(actual2));
	}
	
	@After
	public void tearDown(){
	}
}