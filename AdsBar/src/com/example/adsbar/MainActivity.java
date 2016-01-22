package com.example.adsbar;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity {

	private Context mContext;
	private ViewPager pager;
	
	List<ImageView> imgs;
	
	List<ImageView> points;
	
	private int[] imgIds=new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h,R.drawable.i,R.drawable.j,R.drawable.k,R.drawable.l,R.drawable.m};
	private String[] descs=new String[]{"the day you went away!","昨天看了大旗门","铁中棠","水灵光","一个很好看的故事","坚韧机智世无双","铁血柔情未彷徨","文武传遍后人心","固将一世英名扬","很好看~","特喜欢~~","为什么央视版给了我们一个悲惨的结局","希望有完美结局的版本~"};
	private MyPagerAdapter adapter;
	private boolean isRunning;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        imgs=new ArrayList<ImageView>();
        points=new ArrayList<ImageView>();
        pager = (ViewPager) findViewById(R.id.vp);
        llPoints = (LinearLayout) findViewById(R.id.ll_points);
        
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        adapter = new MyPagerAdapter();
        initView();
        isRunning=true;
        mHandler.sendEmptyMessageDelayed(100, 3000);
    }

	private void initView() {
		
		for (int i = 0; i < imgIds.length; i++) {
			/**
			 * 添加图片到级集合中
			 */
			ImageView img=new ImageView(mContext);
			
			img.setBackgroundResource(imgIds[i]);
			
			imgs.add(img);
			
			/**
			 * 添加小圆点到集合中
			 */
			ImageView point=new ImageView(mContext);
			point.setBackgroundResource(R.drawable.point_bg);
			points.add(point);
			if (i==0) {
				point.setEnabled(true);
			}else {
				point.setEnabled(false);
				tvDesc.setText(descs[i]);
			}
			
			LayoutParams params=new LinearLayout.LayoutParams(8, 8);
			llPoints.addView(point,params);
		}
		pager.setAdapter(adapter);

		regListener();
	}

	private int lastCount;
	private void regListener() {

		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				int pageCount=position%imgs.size();
				
				points.get(lastCount).setEnabled(false);
				tvDesc.setText(descs[pageCount]);
				points.get(pageCount).setEnabled(true);
				lastCount=pageCount;
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
	}

	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			if (isRunning) {
				
				int currCount = pager.getCurrentItem();
				pager.setCurrentItem(currCount+1);
				
				mHandler.sendEmptyMessageDelayed(100, 3000);
			}
		};
		
	};
	private LinearLayout llPoints;
	private TextView tvDesc;
	private class MyPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			
			return Integer.MAX_VALUE;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			
			int imgCount=position%imgIds.length;
			
			ImageView img=imgs.get(imgCount);
			
			container.addView(img);
			return img;
		}
		@Override
		public boolean isViewFromObject(View view, Object object) {
			
			if (view==object) {
				return true;
			}
			return false;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			
			System.out.println("销毁了第 "+position+" 个view");

			container.removeView((View)object);
		}
		
	}

}
