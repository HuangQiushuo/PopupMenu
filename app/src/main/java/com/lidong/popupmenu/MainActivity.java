package com.lidong.popupmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

	private View iv_menu;
	private PopupMenu popupMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		popupMenu = new PopupMenu(getApplicationContext());

		iv_menu =  findViewById(R.id.hello);

		List<PopupMenu.Item> list = new ArrayList<>();
		list.add(new PopupMenu.Item(R.drawable.ic_launcher, "New Event"));
		list.add(new PopupMenu.Item(R.drawable.ic_launcher, "New Meeting"));
//		popupMenu.setItems(list);
		popupMenu.setOnItemClickListener(new PopupMenu.OnItemClickListener() {
			@Override
			public void onClick(int position, PopupMenu.Item item) {
				Toast.makeText(MainActivity.this, item.name, Toast.LENGTH_SHORT).show();
			}
		});

		iv_menu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				popupMenu.showLocation(iv_menu);
			}
		});
	}
}
