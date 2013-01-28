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
	private static final Pattern GOAL_FLAG_PATTERN = Pattern.compile("\\(\\(flag g ([lr]) ([tb])\\) ([0-9-.]+) ([0-9-.]+)\\)");
	private static final Pattern EDGE_FLAG_PATTERN = Pattern.compile("\\(\\(flag ([lcr]) ([tb])\\) ([0-9-.]+) ([0-9-.]+)\\)");
	private static final Pattern GOAL_PATTERN = Pattern.compile("\\(\\([gG]oal\\ ([rl])\\) ([0-9-.]+) ([0-9-.]+)\\)");
	private static final Pattern CYCLE_PATTERN = Pattern.compile("\\(see ([0-9]+) ");
	private static final Pattern BALL_PATTERN = Pattern.compile("\\(\\([bB]all\\) ([0-9-.]+) ([0-9-.]+)( ([0-9-.]+) ([0-9-.]+))?\\)");
	private String seeString;

	public SeeParser(String seeString) {
		this.seeString = seeString;
	}

	public Integer parseCycleNumber() {
		Matcher m = CYCLE_PATTERN.matcher(seeString);
		m.find();
		Integer cycle = Integer.valueOf(m.group(1));
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

	private FlagType parseEdgeFlagType(char typeChar1, char typeChar2) {
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

	private FlagType parseGoalFlagType(char typeChar1, char typeChar2) {
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
}
