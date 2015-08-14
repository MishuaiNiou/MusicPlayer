package com.example.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.utils.DataUtils;
import com.example.utils.TimeFormatUtils;

public class MusicPlayActivity extends Activity implements OnClickListener {

	
	private TextView music_name;
	private TextView music_position;
	private TextView music_duration;
//	private TextView music_lyrsic;
	
	private SeekBar seekbar;
	
	private Button lastBtn;
	private Button playBtn;
	private Button nextBtn;
	
	
	
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			if(what == 1){
				updateUI();
			}
		}

		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_play);
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		
		music_name = (TextView)this.findViewById(R.id.music_name);
		music_position = (TextView)this.findViewById(R.id.music_position);
		music_duration = (TextView)this.findViewById(R.id.music_duration);
		
		seekbar = (SeekBar)this.findViewById(R.id.seekbar);
		
		lastBtn = (Button)this.findViewById(R.id.btn_last);
		playBtn = (Button)this.findViewById(R.id.btn_play);
		nextBtn = (Button)this.findViewById(R.id.btn_next);
		
//		TimeFormatUtils formatUtils = new TimeFormatUtils();
		
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				int progress = seekbar.getProgress();
				MusicService.mediaPlayer.seekTo(progress);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		lastBtn.setOnClickListener(this);
		playBtn.setOnClickListener(this);
		nextBtn.setOnClickListener(this);
		
		int musicIndex = getIntent().getIntExtra("position", 0);
		if(musicIndex!=MusicService.musicIndex){
			MusicService.musicIndex = musicIndex;
			MusicService.stopMusic();
			MusicService.playStatus=0;
		}
		play();
		
	}

	private void play() {
		// TODO Auto-generated method stub
		playBtn.setBackgroundResource(R.drawable.pause_select);
		startService(new Intent(MusicPlayActivity.this, MusicService.class));
		handler.sendEmptyMessage(1);
	}

	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==lastBtn){
			PlayLast();
		}else if(v==playBtn){
			if(MusicService.playStatus==1){
				pause();
			}else{
				play();
			}
		}else if(v==nextBtn){
			PlayNext();
		}
	}

	private void pause() {
		// TODO Auto-generated method stub
		MusicService.pause();
		playBtn.setBackgroundResource(R.drawable.play_select);
		handler.removeMessages(1);
	}

	private void PlayNext() {
//		positon1 = new Random(DataUtils.getList().size()).nextInt();    //Ëæ»ú²¥·Å
		
		MusicService.stopMusic();
//		MusicService.PlayNext();
		MusicService.nextIndex();
		play();
	}
	

	private void PlayLast() {
		// TODO Auto-generated method stub
		MusicService.stopMusic();
		MusicService.preIndex();
		play();
		
	}
	
	
	protected void updateUI() {
		// TODO Auto-generated method stub
		music_name.setText(DataUtils.getMusicMap(MusicService.musicIndex).get("name"));
		music_position.setText(TimeFormatUtils.getTimeString(MusicService.mediaPlayer.getCurrentPosition()));
		music_duration.setText(TimeFormatUtils.getTimeString(MusicService.mediaPlayer.getDuration()));
		seekbar.setProgress(MusicService.mediaPlayer.getCurrentPosition());
		seekbar.setMax(MusicService.mediaPlayer.getDuration());
		handler.sendEmptyMessageDelayed(1, 1000);
	};
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();		
		handler.removeMessages(1);
	}
	

}
