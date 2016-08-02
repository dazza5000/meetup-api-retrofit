package zeroturnaround.org.jrebel4androidgettingstarted.components;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import zeroturnaround.org.jrebel4androidgettingstarted.events.EventsPresenter;
import zeroturnaround.org.jrebel4androidgettingstarted.modules.AustinFeedsMeApplicationModule;
import zeroturnaround.org.jrebel4androidgettingstarted.modules.DataModule;

@Singleton
@Component(modules = {AustinFeedsMeApplicationModule.class, DataModule.class})
public interface ApplicationComponent {

    void inject(EventsPresenter eventsPresenter);

    // Exported for child-components.
    Application application();
}
