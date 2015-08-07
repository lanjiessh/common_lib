package com.lanjie.lib.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;


public class Tools {

	
	/**
	 * 动态改变listView的高度
	 * @param lv
	 * @param adapter
	 */
	public void setPullLvHeight(ListView lv, Adapter adapter) {
		int totalHeight = 0;
		for (int i = 0, len = adapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = adapter.getView(i, null, lv);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = lv.getLayoutParams();
		params.height = totalHeight + (lv.getDividerHeight() * (lv.getCount() - 1));
		lv.setLayoutParams(params);
	}
}
