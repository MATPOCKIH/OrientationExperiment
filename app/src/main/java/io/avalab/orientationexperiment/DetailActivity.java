package io.avalab.orientationexperiment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "ActivityOrientationTest";
    private int mNaturalOrientation = Configuration.ORIENTATION_PORTRAIT;

    private SimpleOrientationListener mOrientationListener;
    private TextView value;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        value = findViewById(R.id.value);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mOrientationListener = new SimpleOrientationListener(this) {

            @Override
            public void onSimpleOrientationChanged(int orientation) {
                if(orientation == Configuration.ORIENTATION_LANDSCAPE){
                    mNaturalOrientation = Configuration.ORIENTATION_LANDSCAPE;
                    Log.d("OrientationTAG", "Orientation landscape[Detail]: " + orientation);
                }else if(orientation == Configuration.ORIENTATION_PORTRAIT){
                    Log.d("OrientationTAG", "Orientation portrait[Detail]: " + orientation);
                    if(mNaturalOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                        finish();
                    }
                    mNaturalOrientation = Configuration.ORIENTATION_LANDSCAPE;
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
        overridePendingTransition(0, 0);
    }
}
