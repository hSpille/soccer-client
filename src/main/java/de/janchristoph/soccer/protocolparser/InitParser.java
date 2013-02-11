package de.janchristoph.soccer.protocolparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.janchristoph.soccer.model.PlayMode;
import de.janchristoph.soccer.model.Side;

public class InitParser {
	private static final String PROTOCOL_RIGHT_SIDE = "r";
	private static final String PROTOCOL_LEFT_SIDE = "l";
	private static final Pattern INIT_PATTERN = Pattern.compile("\\(init ([lr]) ([0-9]+) ([a-zA-Z_]+)\\)");
	private String initString;
	public static final String PROTOCOL_INIT_START = "(init";

	private Matcher m;

	public InitParser(String initString) {
		this.initString = initString;
	}

	public Side parseSide() {
		if (PROTOCOL_LEFT_SIDE.equals(getM().group(1)))
			return Side.LEFT;
		if (PROTOCOL_RIGHT_SIDE.equals(getM().group(1)))
			return Side.RIGHT;
		System.err.println("InitParser->parseSide(): keine gültige Seite gelesen");
		return null;
	}

	public Integer parseUNum() {
		return Integer.valueOf(getM().group(2));
	}

	public PlayMode parsePlayMode() {
		return PlayMode.valueOf(getM().group(3));
	}

	public Matcher getM() {
		System.out.println(initString);
		if (m == null) {
			m = INIT_PATTERN.matcher(initString);
			m.find();
		}
		return m;
	}
}
