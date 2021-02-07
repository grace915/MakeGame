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

public class Game5Activity extends AppCompatActivity {
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
        setContentView(R.layout.activity_game5);

        Data_Setting();
        Restart_Setting();

        Play_Game();
    }

    private void Data_Setting() {
        imgBatch.clear();
        imgBatch.add(R.drawable.imga0);imgBatch.add(R.drawable.imga1);imgBatch.add(R.drawable.imga2);
        imgBatch.add(R.drawable.imga3);imgBatch.add(R.drawable.imga4);imgBatch.add(R.drawable.imga5);
        imgBatch.add(R.drawable.imga6);imgBatch.add(R.drawable.imga7);imgBatch.add(R.drawable.imga8);
        imgBatch.add(R.drawable.imga9);imgBatch.add(R.drawable.imga10);imgBatch.add(R.drawable.imga11);
        imgBatch.add(R.drawable.imga12);imgBatch.add(R.drawable.imga13);imgBatch.add(R.drawable.imga14);
        imgBatch.add(R.drawable.imga0);imgBatch.add(R.drawable.imga1);
        imgBatch.add(R.drawable.imga2);imgBatch.add(R.drawable.imga3);imgBatch.add(R.drawable.imga4);
        imgBatch.add(R.drawable.imga5);imgBatch.add(R.drawable.imga6);imgBatch.add(R.drawable.imga7);
        imgBatch.add(R.drawable.imga8);imgBatch.add(R.drawable.imga9);imgBatch.add(R.drawable.imga10);
        imgBatch.add(R.drawable.imga11);imgBatch.add(R.drawable.imga12);imgBatch.add(R.drawable.imga13);imgBatch.add(R.drawable.imga14);

        btnArray = new Button[]{findViewById(R.id.btn501),findViewById(R.id.btn502),findViewById(R.id.btn503),
                findViewById(R.id.btn504),findViewById(R.id.btn505),findViewById(R.id.btn506),
                findViewById(R.id.btn507),findViewById(R.id.btn508),findViewById(R.id.btn509),
                findViewById(R.id.btn510),findViewById(R.id.btn511),findViewById(R.id.btn512),findViewById(R.id.btn513)
                ,findViewById(R.id.btn514),findViewById(R.id.btn515),findViewById(R.id.btn516),findViewById(R.id.btn517)
                ,findViewById(R.id.btn518),findViewById(R.id.btn519),findViewById(R.id.btn520),findViewById(R.id.btn521)
                ,findViewById(R.id.btn522),findViewById(R.id.btn523),findViewById(R.id.btn524),findViewById(R.id.btn525)
                ,findViewById(R.id.btn526),findViewById(R.id.btn527),findViewById(R.id.btn528),findViewById(R.id.btn529)
                ,findViewById(R.id.btn530)};

        rl = findViewById(R.id.rl5);
        btnNext = findViewById(R.id.btn_next5);
        tvScore = findViewById(R.id.tv_score5);
        tvClicked = findViewById(R.id.tv_clicked5);

        btnFliped = new boolean[]{false, false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false};

        flipedBtnCount = 0;

        score = 0;
        tvScore.setText("점수 : "+score+"점");
        clickNum = 0;
        tvClicked.setText("클릭 수 : "+clickNum+"번");

        Collections.shuffle(imgBatch);

        for(int i=0;i<30;i++)
            btnArray[i].setBackgroundColor(0xffeaded2);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Game5Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Restart_Setting() {
        btnRestart = findViewById(R.id.btn_restart5);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data_Setting();
                Play_Game();
            }
        });
    }

    private void Play_Game() {
        for(int i=0;i<30;i++) {
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

                                if(score == 150) {
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