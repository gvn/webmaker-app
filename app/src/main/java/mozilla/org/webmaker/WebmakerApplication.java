package mozilla.org.webmaker;

import android.app.Application;
import android.content.res.Resources;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import mozilla.org.webmaker.activity.Element;
import mozilla.org.webmaker.activity.Page;
import mozilla.org.webmaker.activity.Play;
import mozilla.org.webmaker.activity.Project;
import mozilla.org.webmaker.activity.ProjectSettings;
import mozilla.org.webmaker.activity.Tinker;
import mozilla.org.webmaker.router.Router;
import mozilla.org.webmaker.storage.MemStorage;

public class WebmakerApplication extends Application {

    private WebmakerApplication singleton;
    private GoogleAnalytics analytics;
    private Tracker tracker;

    public WebmakerApplication getInstance() { return singleton; }
    public GoogleAnalytics getAnalytics() { return analytics; }
    public Tracker getTracker() { return tracker; }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        Resources res = getResources();

        // Dry run allows you to debug Google Analytics locally without sending data to any servers.
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setDryRun(BuildConfig.DEBUG);

        tracker = analytics.newTracker(R.xml.app_tracker);
        tracker.setAppId(res.getString(R.string.ga_appId));
        tracker.setAppName(res.getString(R.string.ga_appName));
        tracker.enableAutoActivityTracking(true);
        tracker.enableExceptionReporting(true);

        MemStorage.getMemStorage().setContext(getApplicationContext());

        Router.sharedRouter().setContext(getApplicationContext());
        Router.sharedRouter().map("/main", MainActivity.class);
        Router.sharedRouter().map("/main/:tab", MainActivity.class);
        Router.sharedRouter().map("/projects/:project", Project.class);
        Router.sharedRouter().map("/projects/:project/pages/:page", Page.class);
        Router.sharedRouter().map("/projects/:project/pages/:page/elements/:element/editor/:editor", Element.class);
        Router.sharedRouter().map("/projects/:project/pages/:page/elements/:element/attributes/:attribute/editor/:editor", Tinker.class);
        Router.sharedRouter().map("/projects/:project/settings", ProjectSettings.class);
        Router.sharedRouter().map("/projects/:project/:mode", Play.class);

        // @todo Restore state
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
