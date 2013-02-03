package de.janchristoph.soccer.protocolparser;

import de.neusta.soccerclient.provided.Client;
import de.neusta.soccerclient.provided.Client.GAMESTATE;

public class StateParser {
	private static final String BEFORE_KICK_OFF_PROTOCOL = "before_kick_off";
	private static final String KICK_OFF_L = "kick_off_l";
	private static final String KICK_OFF_R = "kick_off_r";
	private static final String PLAY_ON = "play_on";
	public static String GAMESTATE_BEGIN_INIT = "(init";
	public static String GAMESTATE_BEGIN_HEAR = "(hear";

	public static boolean isStateMessage(String data) {
		if (data.startsWith(GAMESTATE_BEGIN_INIT)
				|| data.startsWith(GAMESTATE_BEGIN_HEAR)) {
			return true;
		}
		return false;
	}

	public static Client.GAMESTATE getState(String line) {
		if (line.contains(BEFORE_KICK_OFF_PROTOCOL)) {
			return Client.GAMESTATE.BEFORE_KICKOFF;
		}
		if (line.contains(KICK_OFF_L)) {
			return Client.GAMESTATE.KICK_OFF_L;
		}
		if (line.contains(KICK_OFF_R)) {
			return Client.GAMESTATE.KICK_OFF_R;
		}
		if (line.contains(PLAY_ON)) {
			return Client.GAMESTATE.PLAY_ON;
		}
		
		System.out.println("Dunno this Gamestate: " + line);
		return GAMESTATE.ELSE;
	}

}
