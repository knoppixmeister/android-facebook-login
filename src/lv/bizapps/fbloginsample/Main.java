package lv.bizapps.fbloginsample;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Main extends ActionBarActivity {
	protected AppEventsLogger logger;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);

    	if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    					.add(R.id.container, new PlaceholderFragment())
                    					.commit();
        }

    	logger = AppEventsLogger.newLogger(this);
    }

    public static class PlaceholderFragment extends Fragment {
    	private UiLifecycleHelper uiHelper;
    	private AppEventsLogger logger;

    	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
    	    if(state.isOpened()) {
    	        Log.e("AAA", "Logged in...");
    	    }
    	    else if(state.isClosed()) {
    	    	Log.e("AA", "Logged out...");
    	    }
    	    else Log.e("", "NOT DEFINED");
    	}

    	private Session.StatusCallback callback = new Session.StatusCallback() {
    	    public void call(Session session, SessionState state, Exception exception) {
    	    	Log.e("", "SESSION CALL");

    	    	logger.logEvent("CLICK_222");

    	        onSessionStateChange(session, state, exception);
    	    }
    	};

    	public PlaceholderFragment() {
    	}

    	public void onCreate(Bundle savedInstanceState) {
    		super.onCreate(savedInstanceState);
    		uiHelper = new UiLifecycleHelper(getActivity(), callback);
    		uiHelper.onCreate(savedInstanceState);

    		logger = uiHelper.getAppEventsLogger();

    		logger.logEvent("CLICK_1");
    	}

    	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    		View rootView = inflater.inflate(R.layout.fragment_main, container, false);

    		LoginButton authButton = (LoginButton)rootView.findViewById(R.id.authButton);
    		authButton.setFragment(this);

    		return rootView;
    	}

    	@Override
    	public void onResume() {
    		super.onResume();
    		uiHelper.onResume();
    	}
	}
}