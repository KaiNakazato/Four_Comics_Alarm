package com.example.control_four_comics;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.base_fourcomics_alarm.MainActivity;
import com.example.framecomics.R;

public class FourComicsSelectFragment extends Fragment {
	ListView lv;
	ImageButton[] image_button;

	private enum ID {
		CORN(0, R.id.img1), TOMATO(1, R.id.img2), EGG_PLANT(2, R.id.img3), CARROT(
				3, R.id.img4);
		public int index;
		public int resource_id;

		private ID(int index, int resource_id) {
			this.index = index;
			this.resource_id = resource_id;
		}
	}

	private ID[] id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MainActivity.back_bt.setVisibility(View.GONE);
		return inflater.inflate(R.layout.tab_comics_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		MainActivity.theme.setText("よんこま漫画");

		image_button = new ImageButton[ID.values().length];

		id = ID.values();

		for (ID _id : id) {
			image_button[_id.index] = (ImageButton) getActivity().findViewById(
					_id.resource_id);

			setLisners(image_button[_id.index]);

		}

	}

	private void changeView() {
		FragmentTransaction fragmentTransaction = getFragmentManager()
				.beginTransaction();
		fragmentTransaction.addToBackStack(null);
		// class名を変えよう
		fragmentTransaction
				.replace(R.id.fragment, new FourComicsListFragment());
		fragmentTransaction.commit();
	}

	private void setLisners(ImageButton imange_bt) {
		imange_bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeView();
			}
		});


		imange_bt.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					((ImageButton) v).setColorFilter(Color.argb(100, 0, 0, 0));
					break;
				case MotionEvent.ACTION_UP:
					((ImageButton) v).setColorFilter(null);
					break;
				case MotionEvent.ACTION_MOVE:
					((ImageButton) v).setColorFilter(Color.argb(100, 0, 0, 0));
					break;
				case MotionEvent.ACTION_CANCEL:
					((ImageButton) v).setColorFilter(null);
					break;
				}
				return false;
			}
		});
	}

}
