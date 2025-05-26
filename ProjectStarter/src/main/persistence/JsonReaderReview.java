package persistence;

import org.json.JSONObject;

import model.Event;
import model.EventLog;
import model.Review;

// reader that reads reviews of given recipe JSON
public class JsonReaderReview extends JsonReader {

    //EFFECTS: contructs reader to read into source
    public JsonReaderReview(String sourceFile) {
        super(sourceFile);
    }

    //EFFECT: parses review from json object and returns it
    public Review parseReview(JSONObject jsonObject) {
        Review review = updateReview(jsonObject);
        EventLog.getInstance().logEvent(new Event("Review Loaded from File."));
        return review;
    }
    
    //EFFECT: Recreates review in given recipe 
    private Review updateReview(JSONObject jsonObject) {
        String reviewString = jsonObject.getString(("reviewNote"));
        String dateString = jsonObject.getString("dateOfReview");

        Boolean isTried = jsonObject.optBooleanObject("isTried");

        Review review = new Review();
        if (isTried) {
            review.triedBefore();
        }
        review.updateReview(reviewString);
        review.updateDate(dateString);

        return review;
    }
}
