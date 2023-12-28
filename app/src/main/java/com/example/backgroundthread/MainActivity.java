package com.example.backgroundthread;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button startThread;
    private Button stopThread;

    private boolean stop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startThread = findViewById(R.id.startThread);
        startThread.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startThread();
            }
        });

        stopThread = findViewById(R.id.stopThread);
        stopThread.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                stopThread();
            }
        });
    }

    private void startThread() {
        stop = false;
        /*
        BackgroundThreads bthread = new BackgroundThreads(10);
        bthread.start();
        */

        BackgroundRunnable brun = new BackgroundRunnable(10);
        new Thread(brun).start();
    }

    private void stopThread() {
        stop = true;
    }

    private class BackgroundThreads extends Thread {
        private int _time;

        BackgroundThreads(int time) {
            _time = time;
        }

        @Override
        public void run() {
            for (int i = 0; i < _time; i++) {
                if (stop) return;;
                try {
                    Log.i(TAG, "startThread: " + i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private class BackgroundRunnable implements Runnable {
        private int _time;

        BackgroundRunnable(int time) {
            _time = time;
        }

        @Override
        public void run() {
            for (int i = 0; i < _time; i++) {
                if (stop) return;
                if (i == 5) {
                    /*
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startThread.setText("50%");
                        }
                    });
                     */

                    /*
                    startThread.post(new Runnable() {
                        @Override
                        public void run() {
                            startThread.setText("50%");
                        }
                    });
                     */

                    Handler threadHandler = new Handler(Looper.getMainLooper());
                    threadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            startThread.setText("50%");
                        }
                    });
                }
                try {
                    Log.i(TAG, "startThread: " + i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}