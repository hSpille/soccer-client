package de.janchristoph.soccer.protocolparser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.janchristoph.soccer.model.Ball;
import de.janchristoph.soccer.model.Goal;
import de.janchristoph.soccer.model.GoalType;

public class SeeParser {
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
}
