package com.barcan.virgil.insidenav;

import android.app.Activity;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.barcan.virgil.insidenav.backend.dataprocessing.step.AndroidStepDetection;
import com.barcan.virgil.insidenav.backend.dataprocessing.step.SimpleStepDetection;
import com.barcan.virgil.insidenav.common.interfaces.observer.Observer;

public class MainActivity extends Activity implements Observer {

    private SimpleStepDetection simpleStepDetection;
    private AndroidStepDetection androidStepDetection;

    private TextView numberOfStepsCustomAlgorithmTextView;
    private TextView numberOfStepsAndroidAlgorithmTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the number of steps text views
        numberOfStepsCustomAlgorithmTextView = (TextView) findViewById(R.id.no_of_steps_custom_algorithm_text_view);
        numberOfStepsAndroidAlgorithmTextView = (TextView) findViewById(R.id.no_of_steps_android_algorithm_text_view);

        //create the simple step algorithm object
        //simpleStepDetection = new SimpleStepDetection(this);

        //create the android step algorithm object
        androidStepDetection = new AndroidStepDetection(this);

        //register this activity to the step algorithm
        register();

        //register the step algorithm to the sensors data
        //simpleStepDetection.register();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //register the step algorithm from the sensors data
        //simpleStepDetection.register();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //unregister the step algorithm from the sensors data
        //simpleStepDetection.unregister();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //unregister the step algorithm from the sensors data
        //simpleStepDetection.unregister();

        //unregister this activity from the step algorithm
        unregister();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //unregister the step algorithm from the sensors data
        //simpleStepDetection.unregister();

        //unregister this activity from the step algorithm
        unregister();
    }

    //region Observer methods
    @Override
    public boolean register() {
        return //simpleStepDetection.registerObserver(this) &&
                androidStepDetection.registerObserver(this);
    }

    @Override
    public boolean unregister() {
        return //simpleStepDetection.unregisterObserver(this) &&
                androidStepDetection.unregisterObserver(this);
    }

    @Override
    public boolean update(Object data) {
        if (data instanceof SensorEvent) {
            numberOfStepsAndroidAlgorithmTextView.setText(((SensorEvent) data).values[0] + " steps");
        }
        else {
            numberOfStepsCustomAlgorithmTextView.setText(((Integer) data).longValue() + " steps");
        }

        return true;
    }
    //endregion Observer methods
}
