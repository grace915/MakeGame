package com.example.makegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    TextView tvScore;
    TextView tvClick;

    int score; //점수
    int clickNum; //클릭 횟수

    boolean[] btnFliped; //현재 버튼이 뒤집혀있는지 저장하는 변수. (btnFliped[0] = true : 0번 버튼이 뒤집힘)
    int flipedBtnCount; // 현재 뒤집혀있는 버튼의 갯수를 저장하는 변수 0 or 1
    int flipedBtn[] = new int[2];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameSetting();
        PlayGame();

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameSetting();
                PlayGame();

            }
        });

    }

    //게임을 시작시키는 함수
    private void GameSetting(){
        // imgBatch에 img0~img9까지 2번씩 넣음.
        imgBatch.clear();

        imgBatch.add(R.drawable.img0);
        imgBatch.add(R.drawable.img1);
        imgBatch.add(R.drawable.img2);
        imgBatch.add(R.drawable.img3);
        imgBatch.add(R.drawable.img4);
        imgBatch.add(R.drawable.img5);
        imgBatch.add(R.drawable.img6);
        imgBatch.add(R.drawable.img7);
        imgBatch.add(R.drawable.img8);
        imgBatch.add(R.drawable.img9);
        imgBatch.add(R.drawable.img0);
        imgBatch.add(R.drawable.img1);
        imgBatch.add(R.drawable.img2);
        imgBatch.add(R.drawable.img3);
        imgBatch.add(R.drawable.img4);
        imgBatch.add(R.drawable.img5);
        imgBatch.add(R.drawable.img6);
        imgBatch.add(R.drawable.img7);
        imgBatch.add(R.drawable.img8);
        imgBatch.add(R.drawable.img9);

        btnFliped = new boolean[]{false, false, false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};

        // btnArray(버튼 배열)에 우리가 만든 button1~button20까지 집어넣음
        btnArray = new Button[]{findViewById(R.id.button1),findViewById(R.id.button2),findViewById(R.id.button3),
                findViewById(R.id.button4),findViewById(R.id.button5),findViewById(R.id.button6),findViewById(R.id.button7),
                findViewById(R.id.button8),findViewById(R.id.button9),findViewById(R.id.button10),findViewById(R.id.button11),findViewById(R.id.button12),
                findViewById(R.id.button13),findViewById(R.id.button14),findViewById(R.id.button15),findViewById(R.id.button16),findViewById(R.id.button17),
                findViewById(R.id.button18),findViewById(R.id.button19),findViewById(R.id.button20)};

        // imgBatch를 섞어줌.
        Collections.shuffle(imgBatch);


        //btnRestart, tvScore, tvClick 선언
        btnRestart = findViewById(R.id.btn_restart);
        tvScore = findViewById(R.id.tv_score);
        tvClick = findViewById(R.id.tv_click);

        score = 0;
        clickNum = 0;
        flipedBtnCount = 0;

        tvScore.setText("점수 : 0점");
        tvClick.setText("클릭 수 : 0번");

        for(int i = 0; i<20; i++){
            btnArray[i].setBackgroundColor(0xffeaded2);
            btnArray[i].setBackgroundTintList(null);
        }

    }

    //게임에 관한 처리가 들어가있는 함수
    private void PlayGame(){
        for(int i = 0; i<20; i++){
            final int pos = i;

            btnArray[pos].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!btnFliped[pos] && flipedBtnCount<2){ //현재 버튼이 뒤집힌 상태가 아니고 뒤집혀있는 버튼의 갯수가 1개이하일때때

                        //클릭한 버튼을 뒤집고, 그에 맞는 처리를 함.
                        btnFliped[pos] = true;
                        flipedBtn[flipedBtnCount] = pos;
                        flipedBtnCount++;
                        btnArray[pos].setBackgroundResource(imgBatch.get(pos)); //버튼의 이미지 변경


                        if(flipedBtnCount == 2){ //뒤집힌 버튼이 2개인 경우
                            // click 횟수 1 증가시킴킴
                           clickNum++;
                            tvClick.setText("클릭 수 : " + clickNum + "번");


                            if(imgBatch.get(flipedBtn[0]) == imgBatch.get(flipedBtn[1])){ //짝 찾았을 때!
                                flipedBtnCount = 0;
                                btnArray[flipedBtn[0]].setOnClickListener(null);
                                btnArray[flipedBtn[1]].setOnClickListener(null);

                                //점수 표시
                                score += 10;
                                tvScore.setText(("점수 : " + score + "점"));

                                //100점! 끝!
                                if(score == 100){
                                    Toast.makeText(getApplicationContext(), "게임 끝", Toast.LENGTH_SHORT).show();
                                }
                            } else{ //짝 못찾았을 때
                                //딜레이 넣기
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
                                //0.5초초 이후에 delayTask 진행
                                delayTimer.schedule(delayTask, 500);

                            }
                        }
                   }
                    else{
                        // 뒤집혀있는 애를 다시 클릭 했을 경우 -> 버튼을 안 뒤집힌 상태로 되돌리기
                        btnFliped[pos] = false;
                        flipedBtnCount--;
                        btnArray[pos].setBackgroundColor(0xffeaded2);
                    }
                }
            });
        }

    }
}