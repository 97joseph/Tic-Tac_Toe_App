package com.likhith.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.input.InputManager;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    EditText p1n,p2n;
    public static String p1name,p2name;

    int currentplayer=0;//0=p1name and 1=p2name

    boolean gameisactive=true;

    int[] cstate={2,2,2,2,2,2,2,2,2};

    int[][] wc={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};


    public void play(View view)
    {
        ImageView counter =(ImageView) view;

        int s= Integer.parseInt(counter.getTag().toString());
        if(cstate[s]==2 && gameisactive)
        {
            cstate[s]=currentplayer;
            counter.setTranslationY(-1000f);
            if (currentplayer == 0)
            {
                counter.setImageResource(R.drawable.yellow);
                counter.animate().translationYBy(1000).rotation(360).setDuration(300);
                currentplayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                counter.animate().translationYBy(1000).rotation(360).setDuration(300);
                currentplayer = 0;
            }

            for(int[] wp:wc)
            {
                if(cstate[wp[0]]==cstate[wp[1]] && cstate[wp[1]]==cstate[wp[2]] &&cstate[wp[0]]!=2)
                {
                    gameisactive=false;
                    String winner="";

                    if(cstate[wp[0]]==0)
                    {
                        winner=p1name;
                    }
                    else if(cstate[wp[0]]==1)
                    {
                        winner=p2name;
                    }
                    VideoView winnervideo=findViewById(R.id.winnervideo);
                    String uriPath = "android.resource://"+getPackageName()+"/"+R.raw.winner;
                    Uri uri = Uri.parse(uriPath);
                    winnervideo.setVideoURI(uri);
                    winnervideo.requestFocus();
                    winnervideo.start();
                    TextView winnertext=findViewById(R.id.winnertext);
                    winnertext.setText(winner +" has won!");
                    final LinearLayout layout=findViewById(R.id.popup);
                    layout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            layout.setVisibility(View.VISIBLE);
                        }
                    },500);
                }
                else
                {
                    boolean gameover=true;
                    for(int counter1:cstate)
                    {
                        if(counter1==2)
                        {
                            gameover=false;
                        }
                    }
                    if(gameover)
                    {
                        TextView winnertext=findViewById(R.id.winnertext);
                        winnertext.setText("it's a draw");
                        final LinearLayout layout=findViewById(R.id.popup);
                        layout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                layout.setVisibility(View.VISIBLE);
                            }
                        },500);
                    }
                }

            }
        }
    }

    public void playagain(View view)
    {
        gameisactive=true;
        LinearLayout layout=findViewById(R.id.popup);
        layout.setVisibility(View.INVISIBLE);
        currentplayer=0;
        for(int i=0;i<cstate.length;i++)
        {
            cstate[i]=2;
        }
        GridLayout gl=findViewById(R.id.gl);
        for(int i=0;i<gl.getChildCount();i++)
        {
            ((ImageView) gl.getChildAt(i)).setImageResource(0);
        }
    }

    public void playstart(View view)
    {
        p1n =findViewById(R.id.p1n);
        p1name=p1n.getText().toString();
        p2n =findViewById(R.id.p2n);
        p2name=p2n.getText().toString();
        closekeyboard();
        RelativeLayout main=findViewById(R.id.main);
        GridLayout gl=findViewById(R.id.gl);
        main.setVisibility(View.INVISIBLE);
        gl.setVisibility(View.VISIBLE);

    }

    public void closekeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
