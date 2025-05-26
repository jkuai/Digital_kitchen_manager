package model;

import org.json.JSONObject;

import persistence.Writable;

//represents a user review of selected recipe
public class Review implements Writable {
    private Boolean isTried;
    private String review;
    private String dateOfReview;

    //EFFECTS: constructs a Review with false as tried status, 
    // and empty string for review and date of review.
    public Review() {
        this.isTried = false;
        this.review = "";
        this.dateOfReview = "";
    }

    //EFFECTS: returns true if recipe has been executed before
    public Boolean getIsTried() {
        return isTried;
    }

    //EFFECTS: returns review
    public String getReview() {
        return review; 
    }

    //EFFECTS: returns date of review
    public String getDateOfReview() {
        return dateOfReview; 
    }

    //MODIFIES: this
    //EFFECTS: sets isTried to true
    public void triedBefore() {
        isTried = true;
    }

    //MODIFIES: this
    //EFFECTS: updates review based on string to true
    public void updateReview(String review) {
        this.review = review;
    }

    //MODIFIES: this
    //EFFECTS: updates date that recipe was executed
    public void updateDate(String date) {
        dateOfReview = date;
    }

    //EFFECT: converts object to JsonObject by assigning keys to values
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("reviewNote", review);
        json.put("isTried", isTried);
        json.put("dateOfReview", dateOfReview);

        return json;
    }

}
