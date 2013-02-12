package de.janchristoph.soccer.protocolparser;

import org.testng.Assert;
import org.testng.annotations.Test;

import de.janchristoph.soccer.model.PlayMode;
import de.janchristoph.soccer.model.Side;

public class InitParserTest {
	public static final String INIT_STRING_1 = "(init l 2 before_kick_off)";
	public static final String INIT_STRING_2 = "(init r 1 before_kick_off)";

	@Test
	public void parseSideLeft() {
		InitParser parser = new InitParser(INIT_STRING_1);
		Assert.assertEquals(parser.parseSide(), Side.LEFT);
	}

	@Test
	public void parseSideRight() {
		InitParser parser = new InitParser(INIT_STRING_2);
		Assert.assertEquals(parser.parseSide(), Side.RIGHT);
	}

	@Test
	public void parseUNum() {
		InitParser parser = new InitParser(INIT_STRING_2);
		Assert.assertEquals(parser.parseUNum(), Integer.valueOf(1));
	}

	@Test
	public void parsePlayMode() {
		InitParser parser = new InitParser(INIT_STRING_2);
		Assert.assertEquals(parser.parsePlayMode(), PlayMode.BEFORE_KICK_OFF);
	}
}
