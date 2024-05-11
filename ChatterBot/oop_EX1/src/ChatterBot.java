import java.util.*;

/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 * @author Dan Nirel
 */
class ChatterBot {
	static final String REQUEST_PREFIX = "say ";
	static final String PLACEHOLDER_FOR_REQUESTED_PHRASE = "<phrase>";
	static final String PLACEHOLDER_FOR_ILLEGAL_REQUEST = "<request>";
	Random rand = new Random();
	String[] repliesToIllegalRequest;
	String[] legalRequestsReplies;
	String name;

	ChatterBot(String name, String[] repliesToIllegalRequest, String[] repliesToLegalRequest) {
		//init name
		this.name = name;
		//init illegal response
		this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
		for(int i = 0 ; i < repliesToIllegalRequest.length ;i++) {
			this.repliesToIllegalRequest[i] = repliesToIllegalRequest[i];
		}
		//init Legal response
		this.legalRequestsReplies = new String[repliesToLegalRequest.length];
		for(int i = 0 ; i < repliesToLegalRequest.length ;i++) {
			this.legalRequestsReplies[i] = repliesToLegalRequest[i];
		}
	}

	String replyTo(String statement) {
		if(statement.startsWith(REQUEST_PREFIX)) {
			//we don’t repeat the request prefix, so delete it from the reply
			 String phrase = statement.replaceFirst(REQUEST_PREFIX, "");
			 return replyToLegalRequest(phrase);
		}
		return replyToIllegalRequest(statement);
	}

	String replacePlaceholderInARandomPattern(String[] possibleReplies, String placeHolder, String statement){
		int randomIndex = rand.nextInt(possibleReplies.length);
		String phrase = possibleReplies[randomIndex];
		return phrase.replaceAll(placeHolder, statement);
	}
	String replyToLegalRequest(String statement) {

		return replacePlaceholderInARandomPattern(legalRequestsReplies,
				ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE, statement);

	}
	String replyToIllegalRequest(String statement) {
		return replacePlaceholderInARandomPattern(repliesToIllegalRequest,
				ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST, statement);
	}

	String getName(){
		return this.name;
	}
}
