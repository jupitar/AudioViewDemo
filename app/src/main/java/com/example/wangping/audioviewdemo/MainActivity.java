package com.example.wangping.audioviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import view.StepArcView;

public class MainActivity extends AppCompatActivity {
    private StepArcView stepArcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
//        stepArcView=findViewById(R.id.stepView);
//        stepArcView.setCurrentCount(7000,3000);
    }
}
