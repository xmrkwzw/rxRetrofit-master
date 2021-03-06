package com.xmjj.rxretrofit_master.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.tencent.smtt.sdk.TbsVideo;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.activity.AnimationActivity;
import com.xmjj.rxretrofit_master.activity.ClockActivity;
import com.xmjj.rxretrofit_master.activity.ComparedActivity;
import com.xmjj.rxretrofit_master.activity.FileLookActivity;
import com.xmjj.rxretrofit_master.activity.LayImActivity;
import com.xmjj.rxretrofit_master.activity.SpanActivity;
import com.xmjj.rxretrofit_master.activity.TbsWebViewActivity;
import com.xmjj.rxretrofit_master.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/11
 */

public class OtherFragment extends BaseFragment {

    @BindView(R.id.btn_animation)
    Button btnAnimation;
    @BindView(R.id.btn_known)
    Button btnKnown;
    @BindView(R.id.btn_webview)
    Button btnWebview;
    @BindView(R.id.btn_video)
    Button btnVideo;
    @BindView(R.id.btn_clock)
    Button btnClock;
    @BindView(R.id.btn_span)
    Button btnSpan;
    @BindView(R.id.btn_tool)
    Button btnTool;
    Unbinder unbinder;
    Unbinder unbinder1;

    private String URL = "http://baobab.wdjcdn.com/145076769089714.mp4";


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_view;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.btn_layim, R.id.btn_tool, R.id.btn_animation, R.id.btn_known, R.id.btn_webview, R.id.btn_video, R.id.btn_clock, R.id.btn_span})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_animation:
                startActivity(new Intent(activity, AnimationActivity.class));
                break;

            case R.id.btn_known:
                startActivity(new Intent(activity, FileLookActivity.class));
                break;

            case R.id.btn_webview:
                startActivity(new Intent(activity, TbsWebViewActivity.class));
                break;

            case R.id.btn_video:
                if (TbsVideo.canUseTbsPlayer(activity)) {
                    TbsVideo.openVideo(activity, URL);
                }
                break;
            case R.id.btn_clock:
                startActivity(new Intent(activity, ClockActivity.class));
                break;
            case R.id.btn_span:
                startActivity(new Intent(activity, SpanActivity.class));
                break;
            case R.id.btn_layim:
                startActivity(new Intent(activity, LayImActivity.class));
                break;
            case R.id.btn_tool:
                startActivity(new Intent(activity, ComparedActivity.class));
                break;
        }

    }


}
