package com.example.base_fourcomics_alarm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.framecomics.R;

public class TabRootFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_root, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FrameLayout Layout = (FrameLayout) getActivity().findViewById(R.id.fragment);
        ((MainActivity)getActivity()).setPaddingBottom(Layout);
        // 引数から初期のFragmentを取得
        Bundle args = getArguments();
        String rootClass = args.getString("root");

        // 初期Fragmentを設定
        initFragment(rootClass);

    }

    /**
     * BackStack取り出し.
     * BackStackがあった場合はtrue, なかった場合はfalseを返す
     *
     * @return true:BackStackあり false:BackStackなし
     */
    public boolean popBackStack() {
        return getChildFragmentManager().popBackStackImmediate();
    }

    /**
     * BackStackをクリア
     */
    public void clearBackStack() {
        getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * 初期Fragmentを設定.
     * @param rootClass 初期Fragmentクラス名
     */
    private void initFragment(String rootClass) {

        FragmentManager fragmentManager = getChildFragmentManager();
        if (fragmentManager.findFragmentById(R.id.fragment) != null) {
            // すでにFragmentを設定してある場合は何もしない
            return;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = Fragment.instantiate(getActivity(), rootClass);
        fragmentTransaction.replace(R.id.fragment, fragment);

        fragmentTransaction.commit();
    }
    
}