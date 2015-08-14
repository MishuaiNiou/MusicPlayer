package com.example.musicplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.utils.DataUtils;
import com.example.utils.MyDBHelper;

public class MainActivity extends Activity implements OnItemClickListener{

	private ListView listview;
//	private MusicAdapter adapter;
	
//	private List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	
	private List<Map<String,String>> list = DataUtils.getList();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();

		
	}
	
	
	private void init() {
		// TODO Auto-generated method stub
		listview = (ListView)this.findViewById(R.id.listView1);
		listview.setOnItemClickListener(this);
		
		MyDBHelper dbHelper = new MyDBHelper(this);
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query("music", null, null, null, null, null, null);
		/*if(cursor!=null&&cursor.getCount()>0){
			
			while(cursor.moveToNext()){
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String artist = cursor.getString(cursor.getColumnIndex("artist"));
				String path = cursor.getString(cursor.getColumnIndex("path"));
				Map<String,String> map = new HashMap<String,String>();
				map.put("name", name);
				map.put("artist", artist);
				map.put("path", path);
				list.add(map);
				listview.setAdapter(new MusicAdapter());
			}
			
		}else{*/
			ContentResolver resolver = getContentResolver();
			Log.i("TAG", MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString());
			cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
			while(cursor.moveToNext()){
				String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
				String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
				String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
				Log.i("TAG", name+"_"+ artist + "_" + path);
				
				ContentValues values = new ContentValues();
				values.put("name", name);
				values.put("artist", artist);
				values.put("path", path);
				database.insert("music", null, values);
				
				Map<String,String> map = new HashMap<String,String>();
				map.put("name", name);
				map.put("artist", artist);
				map.put("path", path);
				list.add(map);
				listview.setAdapter(new MusicAdapter());
//			}
		}
		
		
	}


	class MusicAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView == null){
				convertView = View.inflate(MainActivity.this, R.layout.all_music_item, null);
			}
			
			TextView name = (TextView) convertView.findViewById(R.id.item_tv_music_name);
			TextView artist = (TextView) convertView.findViewById(R.id.item_tv_music_artist);
			Map<String,String> map = list.get(position);
			name.setText(map.get("name"));
			artist.setText(map.get("artist"));
			
			return convertView;
		}
		
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MainActivity.this,MusicPlayActivity.class);
		intent.putExtra("position", position);
		startActivity(intent);
		
	}
	
	
	
}
