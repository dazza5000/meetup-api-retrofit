package zeroturnaround.org.jrebel4androidgettingstarted;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by shelajev on 16/12/15.
 */
interface MeetupService {
    @GET("/{group_urlname}/events/")
    Call<List<Event>> getEvents(@Path("group_urlname") String group_urlname);

    @GET("/{group_urlname}/events/{event_id}")
    Call<Event> getEvent(@Path("group_urlname") String group_urlname,
                               @Path("event_id") String event_id);

//    @GET("/2/open_events?key=781b7f155a7cb96457745d35a1241&sign=true&photo-host=public&zip=78731&text=pizza&text=provide&text=provided&page=300")
    @GET("/2/open_events?key=781b7f155a7cb96457745d35a1241&sign=true&photo-host=public&zip=78731&text=pizza&text=provided&page=300")
    Call<Results> getEventsWithPizza();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.meetup.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // https://api.meetup.com/2/open_events?key=781b7f155a7cb96457745d35a1241&sign=true&photo-host=public&zip=78731&text=pizza&text=provide&text=provided&page=300

}


