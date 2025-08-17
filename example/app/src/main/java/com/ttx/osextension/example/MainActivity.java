package com.ttx.osextension.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.ttx.osextension.AosAgentManager;
import com.ttx.osextension.api.v1_0.core.IManager;
import com.ttx.osextension.api.v1_0.core.exception.AosAgentException;
import com.ttx.osextension.api.v1_0.system.ISystemManager;

/**
 * This class is used as main class for Aos Agent Lib Example application.
 * It contains implementation for a minimalistic UI to test the API.
 */
public class MainActivity extends Activity {

    private static final String LOG_TAG = "MainActivity";

    // Application context
    private MainActivity mApplicationContext;

    // Manager object for accessing Aos Agent managers and functionality
    private AosAgentManager mainManager;
    // System Manager object
    private ISystemManager systemManager;

    // UI elements
    private Button btnGetSerialNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApplicationContext = this;

        // Initialize UI widgets
        btnGetSerialNumber = findViewById(R.id.btnGetSerialNumber);

        // Setup Manager components
        setupManagerComponents();

        setupSystemSettings();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Sets up Aos Agent API manager usage
     */
    private void setupManagerComponents() {
        // Setup Aos Agent API access
        mainManager = new AosAgentManager(this);

        // Get managers
        systemManager = (ISystemManager) getManager(ISystemManager.class);
    }

    /**
     * Gets requested manager from the Aos Agent Api.
     * If manager is not available, application will be force stopped.
     *
     * @param myClass Class of manager to get.
     * @return Requested manager.
     */
    private IManager getManager(Class myClass) {
        try {
            return mainManager.getManager(myClass);
        } catch (AosAgentException e) {
            Log.e(LOG_TAG, "Error getting " + myClass.getSimpleName() + ". Closing application!");
            finish();
            System.exit(-1);
        }
        return null;
    }

    /**
     * Sets up time settings UI components
     */
    private void setupSystemSettings() {

        btnGetSerialNumber.setOnClickListener(view -> {
            String serial = "failed";
            try {
                serial = systemManager.getSerialNumber();
            } catch (AosAgentException ignored) {
            }

            String finalSerial = serial;
            mApplicationContext.runOnUiThread(() -> {
                Toast.makeText(mApplicationContext, "Device serial number is:" + finalSerial, Toast.LENGTH_SHORT).show();
            });
        });
    }
}
