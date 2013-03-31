package com.example.manage_fragment;

import java.util.LinkedList;

import com.example.framecomics.R;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentSwitcherFor4Comics {
	private LinkedList<Class<?>> stack;
	private FragmentActivity activity;
	private int root;

	/**
	 * コンストラクタ
	 * @param activity
	 * @param fragmentClass 最初に表示するフラグメント
	 */
	public FragmentSwitcherFor4Comics(int root, FragmentActivity activity, Class<?> fragmentClass) {
		this.root = root;
		this.activity = activity;
		stack = new LinkedList<Class<?>>();
		init(fragmentClass);
	}

	/**
	 * フラグメントを切り替える
	 * @param fragmentClass
	 */
	public void init(Class<?> fragmentClass) {
		if(stack.size()>0 && stack.getFirst().equals(fragmentClass)) {
			return;
		}
		stack.clear();
		stack.addFirst(fragmentClass);
		replace(fragmentClass, false, false);
	}
	
	/**
	 * スタックに積んでフラグメントを切り替える
	 * @param fragmentClass
	 */
	public void forward(Class<?> fragmentClass) {
		if(stack.size()>0 && stack.getFirst().equals(fragmentClass)) {
			return;
		}
		stack.addFirst(fragmentClass);
		replace(fragmentClass, true, true);
	}

	/**
	 * 一つ前のフラグメントに戻す
	 */
	public void back() {
		if (stack.size() > 1) {
			stack.removeFirst();
			Class<?> fragmentClass = stack.getFirst();
			replace(fragmentClass, true, false);
		}
	}
//////////このメソッドはいる？？///////////
	private void replace(Class<?> fragmentClass, boolean isAnimated, boolean isLeft) {
		FragmentManager fm = activity.getSupportFragmentManager();
		Fragment fragment = Fragment.instantiate(activity, fragmentClass.getName());
		FragmentTransaction ft = fm.beginTransaction();
		if (isAnimated) {
			if (isLeft) {
				ft.setCustomAnimations(R.anim.fragment_right_in_4comics, R.anim.fragment_left_out_4comics);
			} else {
				ft.setCustomAnimations(R.anim.fragment_left_in_4comics, R.anim.fragment_right_out_4comics);
			}
		}
		ft.replace(root, fragment);
		ft.commit();
	}
}
