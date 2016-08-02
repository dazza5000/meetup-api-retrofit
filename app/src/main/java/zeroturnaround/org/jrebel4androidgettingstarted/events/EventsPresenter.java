package zeroturnaround.org.jrebel4androidgettingstarted.events;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import zeroturnaround.org.jrebel4androidgettingstarted.Event;
import zeroturnaround.org.jrebel4androidgettingstarted.data.EventsDataSource;
import zeroturnaround.org.jrebel4androidgettingstarted.data.EventsRepository;
import zeroturnaround.org.jrebel4androidgettingstarted.data.FirebaseEventsDataSource;

/**
 * Created by darrankelinske on 5/2/16.
 */
public class EventsPresenter implements EventsContract.UserActionsListener {

    @Inject
    DatabaseReference firebase;
    @Inject
    EventsRepository repository;

    EventsContract.View view;

    public EventsPresenter(EventsContract.View view, Activity activity) {
        this.view = view;
//        ((MeetupApplication) activity.getApplication()).component().inject(this);
        FirebaseEventsDataSource eventsDataSource =
                new FirebaseEventsDataSource(FirebaseDatabase.getInstance().getReference("events"));
        repository = new EventsRepository(eventsDataSource);
    }

    @Override
    public void loadEvents() {
        repository.getEvents(new EventsDataSource.LoadEventsCallback() {
            @Override
            public void onEventsLoaded(List<Event> events) {

                Iterator<Event> iter = events.iterator();

                while (iter.hasNext()) {
                    Event nextEvent = iter.next();
                    if (!nextEvent.isFood())
                        iter.remove();
                }

                view.showEvents(events);
            }

            @Override
            public void onError(String error) {
                Log.e("OOPS", "We have an errorrrrr");

            }
        });

    }

    @Override
    public void openEventDetails(Event clickedEvent) {

    }
}
