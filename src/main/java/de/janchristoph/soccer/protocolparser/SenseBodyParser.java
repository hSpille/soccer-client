package de.janchristoph.soccer.protocolparser;

public class SenseBodyParser {
	public static String SENSE_BODY_PROTCOL_BEGIN = "(sense_body";

	public static boolean isSenseMessage(String data) {
		if(data.startsWith(SENSE_BODY_PROTCOL_BEGIN)){
			return true;
		}
		return false;
	}

}
