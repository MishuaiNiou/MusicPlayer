package com.example.musicplayer;

import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.utils.DataUtils;

public class MusicService extends Service {

	public static int musicIndex ;
	public static MediaPlayer mediaPlayer ;
	public static int playStatus;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
/*		mediaPlayer =  new MediaPlayer();
//		mediaPlayer = MediaPlayer.create(getApplicationContext(), MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);

		
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					mediaPlayer.reset();
					PlayNext();
				}
			});*/
	}

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		play();
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	
	private void play() {
		if(playStatus==0){
			Map<String,String> map = DataUtils.getMusicMap(musicIndex);
			String path = map.get("path");
			mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(path));
			mediaPlayer.start();
			playStatus = 1;
		}else if(playStatus==2){
			playStatus = 1;
			mediaPlayer.start();
		}else{
			
		}
		
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				PlayNext();
			}
		});
	}

	
	
	
	/*private void play() {
		// TODO Auto-generated method stub
		if(playStatus==0){
			Map<String,String> map = DataUtils.getMusicMap(musicIndex);
			String path = map.get("path");
			Log.i("---->>PATH--->>", path);
//			Toast.makeText(getApplicationContext(), path, 1).show();
			
			try {
				mediaPlayer.reset();
				mediaPlayer.setDataSource(path);
				mediaPlayer.prepareAsync();
				mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
					
					@Override
					public void onPrepared(MediaPlayer mp) {
						// TODO Auto-generated method stub
						if(mediaPlayer!=null){
							mediaPlayer.start();
							playStatus=1;
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(playStatus==2){
			playStatus=1;
			mediaPlayer.start();
		}else{
			
		}
	}*/
	
	
	
	

	protected void PlayNext(){
		mediaPlayer.stop();
		nextIndex();
		playStatus=0;
		play();
	}
	
	public static void nextIndex(){
		musicIndex = musicIndex<DataUtils.getList().size()-1 ? musicIndex+1 : 0;
	}
	
	public static void preIndex(){
		musicIndex = musicIndex>0 ? musicIndex-1 : DataUtils.getList().size()-1 ;
	}

	public static void stopMusic() {
		// TODO Auto-generated method stub
		if(playStatus==1){
			mediaPlayer.stop();
			playStatus=0;
		}
	}

	public static void pause() {
		// TODO Auto-generated method stub
		if(playStatus==1){
			mediaPlayer.pause();
			playStatus=2;
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopMusic();
		mediaPlayer.release();
		musicIndex=0;
	}
}
