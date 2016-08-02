package zeroturnaround.org.jrebel4androidgettingstarted;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

import zeroturnaround.org.jrebel4androidgettingstarted.components.ApplicationComponent;
import zeroturnaround.org.jrebel4androidgettingstarted.components.DaggerApplicationComponent;
import zeroturnaround.org.jrebel4androidgettingstarted.modules.AustinFeedsMeApplicationModule;

/**
 * Created by daz on 8/1/16.
 */

public class MeetupApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        applicationComponent = DaggerApplicationComponent.builder()
                .austinFeedsMeApplicationModule(new AustinFeedsMeApplicationModule(this))
                .build();
    }

    public ApplicationComponent component() {
        return applicationComponent;
    }
}
