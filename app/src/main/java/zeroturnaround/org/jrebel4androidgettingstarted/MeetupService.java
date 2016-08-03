package zeroturnaround.org.jrebel4androidgettingstarted;

import java.util.List;

import retrofit2.Call;
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


    @GET("/2/open_events?key="+Constants.MEETUP_KEY+"&sign=true&photo-host=public&zip=78731&text=pizza&text=provided&page=300")
    Call<Results> getEventsWithPizza();

}


