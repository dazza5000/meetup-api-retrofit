package zeroturnaround.org.jrebel4androidgettingstarted;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daz on 8/1/16.
 */

public class Results {

    @SerializedName("results")
    @Expose
    private List<Event> quotes;

    public Results(){
        quotes = new ArrayList<>();
    }

    /**
     * @return The quotes
     */
    public List<Event> getEvents() {
        return quotes;
    }

//    /**
//     * @param quotes The quotes
//     */
//    public void setQuotes(List<Event> quotes) {
//        this.quotes = quotes;
//    }
//
//    public static Results parseJSON(String response) {
//        Gson gson = new GsonBuilder().create();
//        Results contents = gson.fromJson(response, Results.class);
//        return contents;
//    }
}
