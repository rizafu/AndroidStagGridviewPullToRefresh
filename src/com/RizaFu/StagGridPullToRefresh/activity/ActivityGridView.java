package com.RizaFu.StagGridPullToRefresh.activity;

import java.util.ArrayList;

import com.RizaFu.StagGridPullToRefresh.adapter.SampleAdapterGridView;
import com.RizaFu.StagGridPullToRefresh.adapter.SampleData;
import com.RizaFu.StagGridPullToRefresh.customview.PullToRefreshStaggeredGridView;
import com.etsy.android.grid.StaggeredGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.montazze.rnd.simplz.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

@SuppressLint({ "NewApi", "InflateParams" })
public class ActivityGridView extends Activity implements OnScrollListener, OnItemClickListener{
	
	private SampleAdapterGridView adapter;	
	private PullToRefreshStaggeredGridView gridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gridview);
		
		/**
		 * create footer layout
		 */
		LayoutInflater inflater=getLayoutInflater();		
		View footer=inflater.inflate(R.layout.footter_gridview, null);
		Button loadMore=(Button)footer.findViewById(R.id.buttonLoadMore);
		
		adapter=new SampleAdapterGridView(this, R.id.txt_line1);
		
		/**
		 * set gridview from View
		 * and set gridview event
		 */
		gridView=(PullToRefreshStaggeredGridView)findViewById(R.id.PTRsGridView);	
		gridView.getRefreshableView().addFooterView(footer);
		gridView.getRefreshableView().setAdapter(adapter);
		gridView.getRefreshableView().setOnScrollListener(this);
		gridView.getRefreshableView().setOnItemClickListener(this);
		
		/**
		 * event when pulldown the grid view
		 */
		gridView.setOnRefreshListener(new OnRefreshListener<StaggeredGridView>() {

			@Override
			public void onRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {
				Toast.makeText(ActivityGridView.this, "Pull Down!", Toast.LENGTH_SHORT).show();
				new GetDataTask().execute();
			}
		});
		
		/**
		 * when button loadMore clicked
		 */
		loadMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {			
				Toast.makeText(ActivityGridView.this, "Load More", Toast.LENGTH_SHORT).show();
				new GetDataTask().execute();
			}
		});
		
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * when item on gridview clicked
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Toast.makeText(ActivityGridView.this, "click item : " + Integer.toString(arg2).toString(), Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * @author RizaFu
	 *	call GetDataTask when load data from sample data and set data to adapter.
	 */
	private class GetDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);								
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			ArrayList<String> list = SampleData.generateSampleData();
			adapter.addAll(list);
			adapter.notifyDataSetChanged();

			// Call onRefreshComplete when the list has been refreshed.
			gridView.onRefreshComplete();
		}
	}


}
