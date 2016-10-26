package com.example.innuy.services_sample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_start_sticky)
    Button startStickyButton;

    Boolean bStickyService = false;

    @BindView(R.id.main_start_not_sticky)
    Button startNotStickyButton;

    @BindView(R.id.main_bind_service)
    Button bindServiceButton;

    @BindView(R.id.main_open_bound_activity)
    Button openBoundActivityButton;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(getApplicationContext(), R.string.main_activity_bound, Toast.LENGTH_SHORT).show();
            bindServiceButton.setText(R.string.main_bound_service);
            bindServiceButton.setEnabled(false);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getApplicationContext(), R.string.main_activity_unbound, Toast.LENGTH_SHORT).show();
            bindServiceButton.setText(R.string.main_bind_service);
            bindServiceButton.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        final Intent stickyServiceIntent = new Intent(this, StickyService.class);
        final Intent notStickyServiceIntent = new Intent(this, NotStickyService.class);
        final Intent boundServiceIntent = new Intent(this, BoundService.class);
        final Intent boundActivityIntent = new Intent(this, BoundActivity.class);

        startStickyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bStickyService = !bStickyService;
                if (bStickyService) {
                    startStickyButton.setText(R.string.main_make_it_stop);
                    startService(stickyServiceIntent);

                } else {
                    startStickyButton.setText(R.string.main_start_sticky);
                    stopService(stickyServiceIntent);
                }
            }
        });

        startNotStickyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(notStickyServiceIntent);
            }
        });


        bindServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(boundServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });

        openBoundActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(boundActivityIntent);
            }
        });
    }
}
