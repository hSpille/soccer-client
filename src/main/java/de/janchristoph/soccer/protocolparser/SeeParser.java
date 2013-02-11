package de.janchristoph.soccer.protocolparser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.janchristoph.soccer.model.Ball;
import de.janchristoph.soccer.model.Flag;
import de.janchristoph.soccer.model.FlagType;
import de.janchristoph.soccer.model.Goal;
import de.janchristoph.soccer.model.GoalType;

public class SeeParser {
	private static final Pattern OUTER_FLAG_PATTERN = Pattern.compile("\\(\\(flag ([rltb]) ([rltb]) ([0-9]0)\\) ([0-9-.]+) ([0-9-.]+)\\)");
	private static final Pattern CENTER_FLAG_PATTERN = Pattern.compile("\\(\\(flag ([lcr])\\) ([0-9-.]+) ([0-9-.]+)\\)");
	private static final Pattern GOAL_FLAG_PATTERN = Pattern.compile("\\(\\(flag g ([lr]) ([tb])\\) ([0-9-.]+) ([0-9-.]+)\\)");
	private static final Pattern EDGE_FLAG_PATTERN = Pattern.compile("\\(\\(flag ([lcr]) ([tb])\\) ([0-9-.]+) ([0-9-.]+)\\)");
	private static final Pattern GOAL_PATTERN = Pattern.compile("\\(\\([gG]oal\\ ([rl])\\) ([0-9-.]+) ([0-9-.]+)\\)");
	private static final Pattern CYCLE_PATTERN = Pattern.compile("\\(see ([0-9]+) ");
	private static final Pattern BALL_PATTERN = Pattern.compile("\\(\\([bB]all\\) ([0-9-.]+) ([0-9-.]+)( ([0-9-.]+) ([0-9-.]+))?\\)");
	private String seeString;
	public static final String PROTOCOL_SEE_START = "(see";

	public SeeParser(String seeString) {
		this.seeString = seeString;
	}

	public Integer parseCycleNumber() {
		Integer cycle = 0;
		try {
			Matcher m = CYCLE_PATTERN.matcher(seeString);
			m.find();
			cycle = Integer.valueOf(m.group(1));
		} catch (IllegalStateException ex) {
			System.out.println("Caught Illegal State on: " + seeString);
		}
		return cycle;
	}

	public Ball parseBall() {
		Matcher m = BALL_PATTERN.matcher(seeString);

		if (!m.find()) {
			return null;
		}

		Ball ball = new Ball();
		ball.setDistance(Double.valueOf(m.group(1)));
		ball.setDirection(Double.valueOf(m.group(2)));
		return ball;
	}

	public List<Goal> parseGoals() {
		List<Goal> goals = new ArrayList<Goal>();
		Matcher m = GOAL_PATTERN.matcher(seeString);

		while (m.find()) {
			Goal goal = new Goal();

			if ("r".equals(m.group(1))) {
				goal.setType(GoalType.RIGHT);
			} else if ("l".equals(m.group(1))) {
				goal.setType(GoalType.LEFT);
			}

			goal.setDistance(Double.valueOf(m.group(2)));
			goal.setDirection(Double.valueOf(m.group(3)));

			goals.add(goal);
		}

		return goals;
	}

	public List<Flag> parseEdgeFlags() {
		List<Flag> flags = new ArrayList<Flag>();
		Matcher m = EDGE_FLAG_PATTERN.matcher(seeString);

		while (m.find()) {
			Flag flag = new Flag();

			char typeChar1 = m.group(1).charAt(0);
			char typeChar2 = m.group(2).charAt(0);

			flag.setType(parseEdgeFlagType(typeChar1, typeChar2));

			flag.setDistance(Double.valueOf(m.group(3)));
			flag.setDirection(Double.valueOf(m.group(4)));

			flags.add(flag);
		}

		return flags;
	}

	FlagType parseEdgeFlagType(char typeChar1, char typeChar2) {
		FlagType type = null;
		if (typeChar1 == 'l' && typeChar2 == 't') {
			type = FlagType.LEFT_TOP;
		} else if (typeChar1 == 'c' && typeChar2 == 't') {
			type = FlagType.CENTER_TOP;
		} else if (typeChar1 == 'r' && typeChar2 == 't') {
			type = FlagType.RIGHT_TOP;
		} else if (typeChar1 == 'l' && typeChar2 == 'b') {
			type = FlagType.LEFT_BOTTOM;
		} else if (typeChar1 == 'c' && typeChar2 == 'b') {
			type = FlagType.CENTER_BOTTOM;
		} else if (typeChar1 == 'r' && typeChar2 == 'b') {
			type = FlagType.RIGHT_BOTTOM;
		}
		return type;
	}

	public List<Flag> parseGoalFlags() {
		List<Flag> flags = new ArrayList<Flag>();
		Matcher m = GOAL_FLAG_PATTERN.matcher(seeString);

		while (m.find()) {
			Flag flag = new Flag();

			char typeChar1 = m.group(1).charAt(0);
			char typeChar2 = m.group(2).charAt(0);

			flag.setType(parseGoalFlagType(typeChar1, typeChar2));

			flag.setDistance(Double.valueOf(m.group(3)));
			flag.setDirection(Double.valueOf(m.group(4)));

			flags.add(flag);
		}

		return flags;
	}

	FlagType parseGoalFlagType(char typeChar1, char typeChar2) {
		FlagType type = null;
		if (typeChar1 == 'l' && typeChar2 == 't') {
			type = FlagType.GOAL_LEFT_TOP;
		} else if (typeChar1 == 'r' && typeChar2 == 't') {
			type = FlagType.GOAL_RIGHT_TOP;
		} else if (typeChar1 == 'l' && typeChar2 == 'b') {
			type = FlagType.GOAL_LEFT_BOTTOM;
		} else if (typeChar1 == 'r' && typeChar2 == 'b') {
			type = FlagType.GOAL_RIGHT_BOTTOM;
		}
		return type;
	}

	public static boolean isSeeMessage(String line) {
		if (line.startsWith(SeeParser.PROTOCOL_SEE_START)) {
			return true;
		}
		return false;
	}

	public List<Flag> parseCenterFlags() {
		List<Flag> flags = new ArrayList<Flag>();
		Matcher m = CENTER_FLAG_PATTERN.matcher(seeString);
		while (m.find()) {
			Flag flag = new Flag();
			flag.setType(parseCenterFlagType(m.group(1).charAt(0)));
			flag.setDistance(Double.valueOf(m.group(2)));
			flag.setDirection(Double.valueOf(m.group(3)));
			flags.add(flag);
		}
		return flags;
	}

	private FlagType parseCenterFlagType(char typeChar) {
		if (typeChar == 'l')
			return FlagType.LEFT_0;
		else if (typeChar == 'r')
			return FlagType.RIGHT_0;
		else if (typeChar == 'c')
			return FlagType.CENTER;
		return null;
	}

	public List<Flag> parseOuterFlags() {
		List<Flag> flags = new ArrayList<Flag>();

		Matcher m = OUTER_FLAG_PATTERN.matcher(seeString);
		while (m.find()) {
			Flag flag = new Flag();
			FlagType type = parseOuterFlagType(m.group(1).charAt(0), m.group(2).charAt(0), m.group(3));
			if (type == null)
				continue;
			flag.setType(type);
			flag.setDistance(Double.valueOf(m.group(4)));
			flag.setDirection(Double.valueOf(m.group(5)));
			flags.add(flag);
		}

		return flags;
	}

	FlagType parseOuterFlagType(char typeChar1, char typeChar2, String num) {
		FlagType type = null;
		String typeString = "";

		if (typeChar1 == 'l')
			typeString += "LEFT_";
		else if (typeChar1 == 'r')
			typeString += "RIGHT_";
		else if (typeChar1 == 't')
			typeString += "TOP_";
		else if (typeChar1 == 'b')
			typeString += "BOTTOM_";

		if (typeChar2 == 'l')
			typeString += "LEFT_";
		else if (typeChar2 == 'r')
			typeString += "RIGHT_";
		else if (typeChar2 == 't')
			typeString += "TOP_";
		else if (typeChar2 == 'b')
			typeString += "BOTTOM_";

		typeString += num;

		try {
			type = FlagType.valueOf(typeString);
		} catch (IllegalArgumentException e) {
			System.err.println("SeeParser->parseOuterFlagType(): FlagType konnte nicht erkannt werden. (" + type + ")");
			return null;
		}
		return type;
	}
}
