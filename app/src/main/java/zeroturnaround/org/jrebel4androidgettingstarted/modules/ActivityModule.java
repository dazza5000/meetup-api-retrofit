package zeroturnaround.org.jrebel4androidgettingstarted.modules;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import zeroturnaround.org.jrebel4androidgettingstarted.PerActivity;

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides @PerActivity
    Activity activity() {
        return activity;
    }
}
