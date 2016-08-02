package zeroturnaround.org.jrebel4androidgettingstarted.events;


import java.util.List;

import zeroturnaround.org.jrebel4androidgettingstarted.Event;

/**
 * Created by darrankelinske on 5/2/16.
 */
interface EventsContract {

    interface View {

        void showEvents(List<Event> events);

    }

    interface UserActionsListener {

        void loadEvents();

        void openEventDetails(Event clickedEvent);

    }

}
