package zeroturnaround.org.jrebel4androidgettingstarted.components;

import android.app.Activity;

import dagger.Component;
import zeroturnaround.org.jrebel4androidgettingstarted.PerActivity;
import zeroturnaround.org.jrebel4androidgettingstarted.modules.ActivityModule;

/**
 * A base component upon which fragment's components may depend.  Activity-level components
 * should extend this component.
 */
@PerActivity // Subtypes of AbstractActivityComponent should be decorated with @PerActivity.
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface AbstractActivityComponent {
    Activity activity(); // Expose the activity to sub-graphs.
}
