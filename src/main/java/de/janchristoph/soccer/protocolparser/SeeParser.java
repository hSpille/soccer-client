package de.janchristoph.soccer.protocolparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.janchristoph.soccer.model.Ball;

public class SeeParser {
	private String seeString;

	public SeeParser(String seeString) {
		this.seeString = seeString;
	}

	public Integer parseCycleNumber() {
		Pattern cyclePatter = Pattern.compile("\\(see ([0-9]+) ");
		Matcher m = cyclePatter.matcher(seeString);
		m.find();
		Integer cycle = Integer.valueOf(m.group(1));
		return cycle;
	}

	public Ball parseBall() {
		Pattern ballPattern = Pattern.compile("\\(\\(ball\\) ([0-9-.]+) ([0-9-.]+) ([0-9-.]+) ([0-9-.]+)\\)");
		Matcher m = ballPattern.matcher(seeString);

		if (!m.find()) {
			return null;
		}

		Ball ball = new Ball();
		ball.setDistance(Double.valueOf(m.group(1)));
		ball.setDirection(Double.valueOf(m.group(2)));
		return ball;
	}
}
