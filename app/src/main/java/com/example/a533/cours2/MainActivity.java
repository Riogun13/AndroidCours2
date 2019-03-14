package com.example.a533.cours2;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       mediaPlayer = MediaPlayer.create(this,R.raw.terminer);
       mediaPlayer.setLooping(false);
       final Handler handler = new Handler();
       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               SeekBar seekBarMedia = findViewById(R.id.seekBar_media);
               seekBarMedia.setProgress(mediaPlayer.getCurrentPosition()*100/mediaPlayer.getDuration());
               handler.postDelayed(this, 1000);
           }
       },1000);
       setListener();
       mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
           @Override
           public void onCompletion(MediaPlayer mp) {
               Toast.makeText(getApplicationContext(), "fini",  Toast.LENGTH_SHORT).show();
           }
       });
    }

    public void setListener(){
        findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMediaPlayer();
            }
        });
        findViewById(R.id.btn_pause).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pauseMedaiPlayer();
            }
        });
        setSeekBarListenner();
        setSeekBarVolume();
    }

    private void setSeekBarVolume(){
        SeekBar seekBarVolume = findViewById(R.id.seekBar_volume);
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    seekInVolume(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setSeekBarListenner(){
        SeekBar seekBarMedia = findViewById(R.id.seekBar_media);
        seekBarMedia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    seekInMedia(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void seekInMedia(int progress){
        mediaPlayer.seekTo(progress * mediaPlayer.getDuration()/100);
    }

    private void seekInVolume(int level){
        int maxVolume = 100;
        float log1=(float)(Math.log(maxVolume-level)/Math.log(maxVolume));
        mediaPlayer.setVolume(1-log1,1-log1);
    }

    private void playMediaPlayer(){
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    private void pauseMedaiPlayer(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }
}
