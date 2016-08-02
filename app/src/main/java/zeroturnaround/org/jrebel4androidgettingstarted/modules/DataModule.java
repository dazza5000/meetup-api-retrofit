package zeroturnaround.org.jrebel4androidgettingstarted.modules;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import zeroturnaround.org.jrebel4androidgettingstarted.data.EventsDataSource;
import zeroturnaround.org.jrebel4androidgettingstarted.data.FirebaseEventsDataSource;

@Module
public class DataModule {
    private static final String FIREBASE_URL = "https://pizza-meetup.firebaseio.com/events";

    @Provides @Singleton
    DatabaseReference firebase() {
        return FirebaseDatabase.getInstance().getReferenceFromUrl(FIREBASE_URL);
    }

    @Provides @Singleton
    EventsDataSource eventsDataSource(FirebaseEventsDataSource firebaseEventsDataSource) {
        return firebaseEventsDataSource;
    }
}
