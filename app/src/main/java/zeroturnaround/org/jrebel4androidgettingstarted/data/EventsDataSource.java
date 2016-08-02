package zeroturnaround.org.jrebel4androidgettingstarted.data;

import java.util.List;

import zeroturnaround.org.jrebel4androidgettingstarted.Event;

/**
 * Created by darrankelinske on 4/10/16.
 */
public interface EventsDataSource {

    interface LoadEventsCallback {

        void onEventsLoaded(List<Event> events);

        void onError(String error);
    }

    interface LoadEventCallback {

        void onEventLoaded(Event event);

        void onError(String error);

    }

    interface SaveEventCallback {

        void onEventSaved(boolean success);

        void onError(String error);

    }

    void getEvents(LoadEventsCallback callback);

    void getEvent(String eventId, LoadEventCallback callback);

    void saveEvent(Event eventToSave, SaveEventCallback callback);
}
