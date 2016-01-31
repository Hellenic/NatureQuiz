package net.blankpace.naturequiz.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameProgressTest {

	private GameProgress gp;
	
	@Before
	public void setUp() throws Exception
	{
		gp = new GameProgress();
	}
	
	@Test
	public void testMethods()
	{
		gp.setCategoryName("CATEGORY");
		assertNotEquals("999", gp.getCategoryName());
		assertEquals("CATEGORY", gp.getCategoryName());
		
		Level level = new Level();
		level.setId(7);
		gp.setCompleted(level);
		assertFalse(gp.getCompleted(6));
		assertTrue(gp.getCompleted(7));
		
		gp.setCurrentLevelId(1);
		assertNotEquals(999, gp.getCurrentLevelId());
		assertEquals(1, gp.getCurrentLevelId());
		
		gp.setLevelCount(5);
		assertNotEquals(999, gp.getLevelCount());
		assertEquals(5, gp.getLevelCount());
		
		gp.setCompletedCount(3);
		assertNotEquals(999, gp.getCompletedCount());
		assertEquals(3, gp.getCompletedCount());
	}

	@Test
	public void testConstructor()
	{
		fail("Not yet implemented");
	}

}
