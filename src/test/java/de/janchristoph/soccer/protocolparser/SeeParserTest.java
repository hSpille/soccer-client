package de.janchristoph.soccer.protocolparser;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SeeParserTest {
	private static final String SEE_STRING = "(see 181 ((flag c t) 9.5 18 0 0) ((flag r t) 61.6 2) ((flag grb) 75.9 35) ((goal r) 71.5 31)"
			+ " ((flag g r t) 68.7 25) ((flag p r c) 58 39) ((flag p r t) 47.9 20) ((ball) 0.4 -88 0 0)";
	private static final String SEE_STRING_2 = "(see 181 ((flag c t) 9.5 18 0 0) ((flag r t) 61.6 2) ((flag grb) 75.9 35) ((goal r) 71.5 31)"
			+ " ((flag g r t) 68.7 25) ((flag p r c) 58 39) ((flag p r t) 47.9 20)";

	@Test
	public void parseCylceNumber() {
		SeeParser seeParser = new SeeParser(SEE_STRING);
		Assert.assertEquals(seeParser.parseCycleNumber(), Integer.valueOf(181));
	}

	@Test
	public void parseBall() {
		Assert.assertEquals((new SeeParser(SEE_STRING)).parseBall().getDistance(), Double.valueOf(0.4));
		Assert.assertEquals((new SeeParser(SEE_STRING)).parseBall().getDirection(), Double.valueOf(-88));
	}

	@Test
	public void parseBallIfNoneInSight() {
		Assert.assertEquals((new SeeParser(SEE_STRING_2)).parseBall(), null);
	}
}
