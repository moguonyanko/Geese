package test.org.geese.ci.classifier;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.geese.util.Logging;
import org.geese.util.StringUtil;

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
	public void can_output_logging_each_level(){
		try{
			Logging.info("test info");
			Logging.warn("test warn");
			Logging.error("test error");
		}catch(Exception e){
			fail();
		}
	}
	
	@Test
	public void can_check_String_is_null_or_empty(){
		String actual0 = "";
		assertTrue(StringUtil.isNullOrEmpty(actual0));
		String actual1 = null;
		assertTrue(StringUtil.isNullOrEmpty(actual1));
		String actual2 = "A";
		assertFalse(StringUtil.isNullOrEmpty(actual2));
	}
	
	@After
	public void tearDown(){
	}
}