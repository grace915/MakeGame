package com.example.makegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    List<Integer> imgBatch = new ArrayList<>();
    Button[] btnArray;
    Button btnRestart;
    RelativeLayout rl;
    Button btnNext;
    TextView tvScore;
    TextView tvClicked;

    boolean[] btnFliped;
    int flipedBtnCount;
    int flipedBtn[] = new int[2];
    int score;
    int clickNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data_Setting();
        Restart_Setting();

        Play_Game();
    }

    private void Data_Setting() {
        imgBatch.clear();
        imgBatch.add(R.drawable.img0);imgBatch.add(R.drawable.img1);imgBatch.add(R.drawable.img2);
        imgBatch.add(R.drawable.img0);imgBatch.add(R.drawable.img1);imgBatch.add(R.drawable.img2);

        btnArray = new Button[]{findViewById(R.id.btn201),findViewById(R.id.btn202),findViewById(R.id.btn203),
                findViewById(R.id.btn204),findViewById(R.id.btn205),findViewById(R.id.btn206)};

        rl = findViewById(R.id.rl2);
        btnNext = findViewById(R.id.btn_next2);
        tvScore = findViewById(R.id.tv_score2);
        tvClicked = findViewById(R.id.tv_clicked2);

        btnFliped = new boolean[]{false, false, false, false, false, false};

        flipedBtnCount = 0;

        score = 0;
        tvScore.setText("점수 : "+score+"점");
        clickNum = 0;
        tvClicked.setText("클릭 수 : "+clickNum+"번");

        Collections.shuffle(imgBatch);

        for(int i=0;i<6;i++)
            btnArray[i].setBackgroundColor(0xffeaded2);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Game3Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Restart_Setting() {
        btnRestart = findViewById(R.id.btn_restart2);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data_Setting();
                Play_Game();
            }
        });
    }

    private void Play_Game() {
        for(int i=0;i<6;i++) {
            final int pos = i;
            btnArray[pos].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!btnFliped[pos] && flipedBtnCount < 2) {
                        btnFliped[pos] = true;
                        flipedBtn[flipedBtnCount] = pos;
                        flipedBtnCount++;
                        btnArray[pos].setBackgroundResource(imgBatch.get(pos));

                        if(flipedBtnCount == 2) {
                            clickNum++;
                            tvClicked.setText("클릭 수 : "+clickNum+"번");

                            if(imgBatch.get(flipedBtn[0]) == imgBatch.get(flipedBtn[1])) {
                                flipedBtnCount = 0;
                                btnArray[flipedBtn[0]].setOnClickListener(null);
                                btnArray[flipedBtn[1]].setOnClickListener(null);

                                score += 10;
                                tvScore.setText("점수 : "+score+"점");

                                if(score == 30) {
                                    Toast.makeText(getApplicationContext(), "게임 끝. 클릭 횟수 : " + clickNum + "번", Toast.LENGTH_SHORT).show();
                                    rl.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Timer delayTimer = new Timer();
                                TimerTask delayTask = new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                flipedBtnCount = 0;
                                                btnFliped[flipedBtn[0]] = false;
                                                btnArray[flipedBtn[0]].setBackgroundColor(0xffeaded2);
                                                btnFliped[flipedBtn[1]] = false;
                                                btnArray[flipedBtn[1]].setBackgroundColor(0xffeaded2);
                                            }
                                        });
                                    }
                                };

                                delayTimer.schedule(delayTask, 500);
                            }
                        }
                    } else {
                        btnFliped[pos] = false;
                        flipedBtnCount--;
                        btnArray[pos].setBackgroundColor(0xffeaded2);
                    }
                }
            });
        }
    }
}