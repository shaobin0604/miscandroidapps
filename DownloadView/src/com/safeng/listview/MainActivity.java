package com.safeng.listview;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.itcast.net.download.DownloadProgressListener;
import cn.itcast.net.download.DownloadThread;
import cn.itcast.net.download.FileDownloader;
import cn.itcast.service.FileService;

public class MainActivity extends Activity {

	private Map<Integer,DownloadFile> list = new HashMap<Integer, DownloadFile>();
	private LinearLayout listview;
	private ProgressBar progressBar	;
	private Map<String,Boolean>  downlist = new HashMap<String,Boolean>();
	private Map<String,FileDownloader> filelist = new HashMap<String, FileDownloader>();
	FileService fileService ;
	LayoutInflater mInflater;
	private String posNew;
	private String currPos;//记录当前点击的条目
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		fileService = new FileService(MainActivity.this);
		mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		
		DownloadFile file1 = new DownloadFile("http://www.jpskb.com/Down/JpBus.rar", "JpBus.rar");
		DownloadFile file2 = new DownloadFile("http://ttplayer.qianqian.com/download/ttpsetup_5711.exe", "ttpsetup_5711.exe");
		DownloadFile file3 = new DownloadFile("http://ime.sogou.com/dl/sogou_pinyin_52_5338.exe", "sogou_pinyin_52_5338.exe");
		list.put(1,file1);
		list.put(2,file2);
		list.put(3,file3);
		listview = (LinearLayout) findViewById(R.id.mylist);
		for(int i= 1; i<=list.size();i++){
			listview.addView(getView(i));
		}
	}
	
	public RelativeLayout getView(final int position){
		RelativeLayout layout = (RelativeLayout) mInflater.inflate(R.layout.list_item, null);
		ImageView startDownload = (ImageView) layout.findViewById(R.id.but_img);
		startDownload.setTag(position+"1");
		ProgressBar progressBar = (ProgressBar)layout.findViewById(R.id.my_progressBar);
		progressBar.setTag(position+"2");
		TextView percent = (TextView) layout.findViewById(R.id.percent);
		percent.setTag(position+"3");
		
		startDownload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currPos = "";
				Message msg = new Message();
				msg.what=2;
				msg.getData().putString("position", position+"");
				handler.sendMessage(msg);
			}
		});
		
		ImageView delDownload = (ImageView) layout.findViewById(R.id.del_img);
		delDownload.setTag(position+"4");
		delDownload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currPos = position+"";
				Message msg = new Message();
				msg.what=3;
				msg.getData().putString("position", position+"");
				handler.sendMessage(msg);
			}
		});
		
		TextView name = (TextView) layout.findViewById(R.id.sort_name);
		ImageView img = (ImageView) layout.findViewById(R.id.img);
		img.setImageResource(R.drawable.aaaaa);
		name.setText(list.get(position).getName());
		layout.setTag(position+"");
		return layout;
	}

	private void download(final String path, final File dir,final String pos){
		progressBar = (ProgressBar) listview.findViewWithTag(pos+"2");
    	new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("run..............");
				try {
					FileDownloader loader = new FileDownloader(MainActivity.this, path, dir, 3);
					filelist.put(pos, loader); //将下载对象保存起来
					int length = loader.getFileSize();//获取文件的长度
					progressBar.setMax(length);
					loader.download(new DownloadProgressListener(){
						@Override
						public void onDownloadSize(int size) {//可以实时得到文件下载的长度
							Message msg = new Message();
							msg.what = 1;
							msg.getData().putInt("size", size);							
							msg.getData().putString("pos", pos);							
							handler.sendMessage(msg);
						}});
				} catch (Exception e) {
					e.printStackTrace();
					Message msg = new Message();
					msg.what = -1;
					msg.getData().putString("error", "下载失败");
					handler.sendMessage(msg);
				}
			}
		}).start();
    	
    }

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				int size = msg.getData().getInt("size");
				String pos = msg.getData().getString("pos");
				if(!pos.equals(currPos)){
					ProgressBar progressBar = (ProgressBar)listview.findViewWithTag(pos+"2");
					if(progressBar==null)break;
					progressBar.setProgress(size);
					float result = (float)progressBar.getProgress()/ (float)progressBar.getMax();
					int p = (int)(result*100);
					TextView viewpercent = (TextView) listview.findViewWithTag(pos+"3");
					viewpercent.setText(p+"%");
					if(progressBar.getProgress()==progressBar.getMax()){
						ImageView startImg= (ImageView) listview.findViewWithTag(pos+"1");
						startImg.setImageResource(R.drawable.start);
					}
				}
				break;
			case -1:
				Toast.makeText(MainActivity.this, "下载失败", 1).show();
				break;
			case 2:
				String position = msg.getData().getString("position");
				ImageView startDown = (ImageView) listview.findViewWithTag(position+"1");
				//判断该任务是否已经在下载
				if(!downlist.containsKey(position)){
					String path = list.get(Integer.parseInt(position)).getPath();
					if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
						File dir = Environment.getExternalStorageDirectory();//文件保存目录
						download(path,dir,position);
						downlist.put(position,true);
						startDown.setImageResource(R.drawable.pause);
					}else{
						Toast.makeText(MainActivity.this, "sdcard is error!", 1).show();
					}
				}else{
					if(downlist.get(position)){
						FileDownloader loader = filelist.get(position+"");
						loader.exit();
						startDown.setImageResource(R.drawable.start);
						downlist.put(position,false);
					}else{
						download(list.get(Integer.parseInt(position)).getPath(), Environment.getExternalStorageDirectory(),position+"");
						startDown.setImageResource(R.drawable.pause);
						downlist.put(position,true);
					}
				}
				break;
			case 3:
				String delpos = msg.getData().getString("position");
				ImageView delDown = (ImageView) listview.findViewWithTag(delpos+"4");
				ImageView startImg= (ImageView) listview.findViewWithTag(delpos+"1");
				startImg.setImageResource(R.drawable.start);
				TextView viewpercent = (TextView) listview.findViewWithTag(delpos+"3");
				viewpercent.setText("");
				if(!downlist.containsKey(delpos)){
				}else{
					//删除数据库中改下载文件的记录
					fileService.delete(list.get(Integer.parseInt(delpos)).getPath());
					FileDownloader loader = filelist.get(delpos);
					if(loader!=null)
						loader.exit();
					downlist.put(delpos,false);
				}
				ProgressBar progressBar = (ProgressBar)listview.findViewWithTag(delpos+"2");
			    progressBar.setProgress(0);
				String name = list.get(Integer.parseInt(delpos)).getName();
				File path = new File(Environment.getExternalStorageDirectory()+"/"+name);
				if(path!=null)
					path.delete();
				list.remove(delpos);
//				listview.removeView(listview.findViewWithTag(delpos));
				break;
			}
			
		}    	
    };
}