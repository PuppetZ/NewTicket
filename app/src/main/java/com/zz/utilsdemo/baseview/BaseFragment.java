package com.zz.utilsdemo.baseview;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhangjing on 2016/8/2.
 * Fragment基类
 * 解决程序在崩溃或者来回切换中 fragment重叠问题
 */

public abstract class BaseFragment extends Fragment {
    public static final String STATE_IS_HIDDEN = "state_is_hidden";
    public LayoutInflater inflater;
    public Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isHidden = savedInstanceState.getBoolean(STATE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //手动去保存隐藏的状态
        outState.putBoolean(STATE_IS_HIDDEN, isHidden());
    }

    public abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(getLayoutId(), container, false);
        init(view);
        return view;
    }

    private final void init(View view) {
        initView(view);
        initData();
    }

    /**
     * 初始化UI
     */
    public abstract void initView(View view);


    /**
     * 初始化数据
     */
    public void initData() {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
