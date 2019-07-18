package io.avalab.orientationexperiment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SimpleOrientationListener mOrientationListener;

    private Button button;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDetailActivity();
            }
        });

        handler = new Handler();

        mOrientationListener = new SimpleOrientationListener(this) {

            @Override
            public void onSimpleOrientationChanged(int orientation) {
                if(orientation == Configuration.ORIENTATION_LANDSCAPE){
                    Log.d("OrientationTAG", "Orientation landscape[Main]: " + orientation);
                    toDetailActivity();
                }else if(orientation == Configuration.ORIENTATION_PORTRAIT){
                    Log.d("OrientationTAG", "Orientation portrait[Main]: " + orientation);
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mOrientationListener.canDetectOrientation()) {
            mOrientationListener.enable();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mOrientationListener.canDetectOrientation()) {
            mOrientationListener.disable();
        }
    }

    private void toDetailActivity(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        }, 500);

    }

}
