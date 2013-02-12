package de.janchristoph.soccer.protocolparser;

import org.testng.Assert;
import org.testng.annotations.Test;

import de.janchristoph.soccer.model.FlagType;
import de.janchristoph.soccer.model.GoalType;

public class SeeParserTest {
	private static final String SEE_STRING = "(see 181 ((flag c t) 9.5 18) ((flag r t) 61.6 2) ((flag g r b) 75.9 35) ((goal r) 71.5 31)"
			+ " ((flag g r t) 68.7 25) ((flag p r c) 58 39) ((flag p r t) 47.9 20) ((ball) 0.4 -88 0 0) ((flag c) 10 3) ((flag l) 12 5)"
			+ " ((flag t l 50) 30 10) ((flag l t 30) 20 12))";
	private static final String SEE_STRING_2 = "(see 72 ((flag c) 18.2 -35 0 0) ((flag c t) 35.9 33) ((flag l t) 76.7 -2) ((flag g l b) 70.8 -34)"
			+ " ((goal l) 70.8 -28) ((flag g l t) 70.8 -23) ((flag p l c) 54.1 -29) ((flag p l b) 50 -29) ((Ball) 3 138) ((line t) 69.4 -26))";
	private static final String SEE_STRING_3 = "(see 72 ((flag c) 18.2 -35 0 0) ((flag c t) 35.9 33) ((flag l t) 76.7 -2) ((flag g l b) 70.8 -34)"
			+ " ((flag g l t) 70.8 -23) ((flag p l c) 54.1 -29) ((flag p l t) 56.8 -8) ((line t) 69.4 -26))";

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
	public void parseBallIfInCapitalLetters() {
		Assert.assertEquals((new SeeParser(SEE_STRING_2)).parseBall().getDistance(), Double.valueOf(3));
		Assert.assertEquals((new SeeParser(SEE_STRING_2)).parseBall().getDirection(), Double.valueOf(138));
	}

	@Test
	public void parseBallIfNoneInSight() {
		Assert.assertEquals((new SeeParser(SEE_STRING_3)).parseBall(), null);
	}

	@Test
	public void parseGoals() {
		Assert.assertEquals((new SeeParser(SEE_STRING)).parseGoals().size(), 1);
		Assert.assertEquals((new SeeParser(SEE_STRING)).parseGoals().get(0).getType(), GoalType.RIGHT);
		Assert.assertEquals((new SeeParser(SEE_STRING)).parseGoals().get(0).getDistance(), Double.valueOf(71.5));
		Assert.assertEquals((new SeeParser(SEE_STRING)).parseGoals().get(0).getDirection(), Double.valueOf(31));
	}

	@Test
	public void parseGoalsIfNoneInSight() {
		Assert.assertEquals((new SeeParser(SEE_STRING_3)).parseGoals().size(), 0);
	}

	@Test
	public void parseEdgeFlags() {
		SeeParser seeParser = new SeeParser(SEE_STRING);
		Assert.assertEquals(seeParser.parseEdgeFlags().size(), 2);
		Assert.assertEquals(seeParser.parseEdgeFlags().get(0).getType(), FlagType.CENTER_TOP);
		Assert.assertEquals(seeParser.parseEdgeFlags().get(0).getDistance(), Double.valueOf(9.5));
		Assert.assertEquals(seeParser.parseEdgeFlags().get(0).getDirection(), Double.valueOf(18));
		Assert.assertEquals(seeParser.parseEdgeFlags().get(1).getType(), FlagType.RIGHT_TOP);
		Assert.assertEquals(seeParser.parseEdgeFlags().get(1).getDistance(), Double.valueOf(61.6));
		Assert.assertEquals(seeParser.parseEdgeFlags().get(1).getDirection(), Double.valueOf(2));
	}

	@Test
	public void parseGoalFlags() {
		SeeParser seeParser = new SeeParser(SEE_STRING);
		Assert.assertEquals(seeParser.parseGoalFlags().size(), 2);
		Assert.assertEquals(seeParser.parseGoalFlags().get(0).getType(), FlagType.GOAL_RIGHT_BOTTOM);
		Assert.assertEquals(seeParser.parseGoalFlags().get(0).getDistance(), Double.valueOf(75.9));
		Assert.assertEquals(seeParser.parseGoalFlags().get(0).getDirection(), Double.valueOf(35));
		Assert.assertEquals(seeParser.parseGoalFlags().get(1).getType(), FlagType.GOAL_RIGHT_TOP);
		Assert.assertEquals(seeParser.parseGoalFlags().get(1).getDistance(), Double.valueOf(68.7));
		Assert.assertEquals(seeParser.parseGoalFlags().get(1).getDirection(), Double.valueOf(25));
	}

	@Test
	public void parseCenterFlags() {
		SeeParser seeParser = new SeeParser(SEE_STRING);
		Assert.assertEquals(seeParser.parseCenterFlags().get(0).getType(), FlagType.CENTER);
		Assert.assertEquals(seeParser.parseCenterFlags().get(0).getDistance(), Double.valueOf(10));
		Assert.assertEquals(seeParser.parseCenterFlags().get(0).getDirection(), Double.valueOf(3));
		Assert.assertEquals(seeParser.parseCenterFlags().get(1).getType(), FlagType.LEFT_0);
		Assert.assertEquals(seeParser.parseCenterFlags().get(1).getDistance(), Double.valueOf(12));
		Assert.assertEquals(seeParser.parseCenterFlags().get(1).getDirection(), Double.valueOf(5));
	}

	@Test
	public void parseOuterFlagType() {
		SeeParser seeParser = new SeeParser(SEE_STRING);
		Assert.assertEquals(seeParser.parseOuterFlagType('t', 'l', "50"), FlagType.TOP_LEFT_50);
		Assert.assertEquals(seeParser.parseOuterFlagType('l', 't', "30"), FlagType.LEFT_TOP_30);
	}

	@Test
	public void parseOuterFlags() {
		SeeParser seeParser = new SeeParser(SEE_STRING);
		Assert.assertEquals(seeParser.parseOuterFlags().size(), 2);
		Assert.assertEquals(seeParser.parseOuterFlags().get(0).getType(), FlagType.TOP_LEFT_50);
		Assert.assertEquals(seeParser.parseOuterFlags().get(0).getDistance(), Double.valueOf(30));
		Assert.assertEquals(seeParser.parseOuterFlags().get(0).getDirection(), Double.valueOf(10));
		Assert.assertEquals(seeParser.parseOuterFlags().get(1).getType(), FlagType.LEFT_TOP_30);
		Assert.assertEquals(seeParser.parseOuterFlags().get(1).getDistance(), Double.valueOf(20));
		Assert.assertEquals(seeParser.parseOuterFlags().get(1).getDirection(), Double.valueOf(12));
	}

	@Test
	public void parsePenaltyFlags() {
		SeeParser seeParser = new SeeParser(SEE_STRING_2);
		Assert.assertEquals(seeParser.parsePenaltyFlags().size(), 2);
		Assert.assertEquals(seeParser.parsePenaltyFlags().get(0).getType(), FlagType.PENALTY_LEFT_CENTER);
		Assert.assertEquals(seeParser.parsePenaltyFlags().get(0).getDistance(), Double.valueOf(54.1));
		Assert.assertEquals(seeParser.parsePenaltyFlags().get(0).getDirection(), Double.valueOf(-29));
		Assert.assertEquals(seeParser.parsePenaltyFlags().get(1).getType(), FlagType.PENALTY_LEFT_BOTTOM);
		Assert.assertEquals(seeParser.parsePenaltyFlags().get(1).getDistance(), Double.valueOf(50));
		Assert.assertEquals(seeParser.parsePenaltyFlags().get(1).getDirection(), Double.valueOf(-29));
	}
}
