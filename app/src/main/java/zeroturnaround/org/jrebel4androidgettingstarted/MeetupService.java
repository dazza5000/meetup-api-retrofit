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

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.meetup.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}


