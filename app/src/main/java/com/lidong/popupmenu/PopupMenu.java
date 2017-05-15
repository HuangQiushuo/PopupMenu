package com.lidong.popupmenu;

import android.app.Activity;
import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PopupMenu extends PopupWindow{

	private LinearLayoutCompat popView;
	private Context context;
	private List<Item> items;
	private List<View> itemViews;
	private OnItemClickListener onItemClickListener;

	public PopupMenu(Context context) {
		super(context);
		this.context = context;
		LayoutInflater inflater = LayoutInflater.from(context);
		popView = (LinearLayoutCompat) inflater.inflate(R.layout.popup_menu, null, false);// 加载菜单布局文件
		popView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				if(isIn(popView, motionEvent)) {
					if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
						for (int i = 0; i < itemViews.size(); i++) {
							View v = itemViews.get(i);
							if (isIn(v, motionEvent)) {
								if(onItemClickListener!=null)
									onItemClickListener.onClick(i, items.get(i));
								break;
							}
						}
					}
				}
				dismiss();
				return false;
			}
		});

		itemViews = new ArrayList<>();
		this.setContentView(popView);// 把布局文件添加到popupwindow中
		this.setFocusable(true);// 获取焦点
		this.setTouchable(true); // 设置PopupWindow可触摸
		this.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
		ColorDrawable dw = new ColorDrawable(0x00000000);
		this.setBackgroundDrawable(dw);
	}

	private boolean isIn(View view, MotionEvent ev){
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		RectF rectF =  new RectF(location[0], location[1], location[0] + view.getWidth(),
				location[1] + view.getHeight());

		return rectF.contains(ev.getRawX(), ev.getRawY());
	}

	/**
	 * 设置显示的位置
	 *
	 *
	 */
	public void showLocation(View view) {
		showAsDropDown(view, 0, 0);
	}

	// dip转换为px
	public int dip2px(float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	// 点击监听接口
	public interface OnItemClickListener {
		void onClick(int position, Item item);
	}

	// 设置监听
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public void setItems(List<Item> items) {
		this.items = items;
		createMenu();
	}

	private void createMenu(){
		popView.removeAllViews();

		for(int i=0;i<items.size();i++){
			View v = generateItemView(items.get(i));
			v.setTag(i);
			popView.addView(v);
			itemViews.add(v);
		}
	}

	private View generateItemView(Item item){
		View itemView = LayoutInflater.from(context).inflate(R.layout.item_popup_menu_item, popView, false);

		TextView textView = (TextView) itemView.findViewById(R.id.item_name);
		textView.setText(item.name);

		ImageView imageView = (ImageView) itemView.findViewById(R.id.item_icon);
		imageView.setImageResource(item.icon);
		return itemView;
	}

	public static class Item{
		// R.id of icon
		public int icon;

		public String name;

		public Item(int icon, String name){
			this.icon = icon;
			this.name = name;
		}
	}

}
