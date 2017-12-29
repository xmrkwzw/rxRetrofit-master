package com.xmjj.rxretrofit_master.activity;

import android.view.View;
import android.widget.EditText;

import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：
 * Created by wzw
 * 2017/12/22
 */

public class ComparedActivity extends BaseActivity {

    @BindView(R.id.et_data1)
    EditText etData1;
    @BindView(R.id.et_data2)
    EditText etData2;
    @BindView(R.id.et_show)
    EditText etShow;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_compared;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {


    }

    @OnClick({R.id.tv_make, R.id.tv_go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_make:
                clean();
                break;


            case R.id.tv_go:
                make();
                break;
        }

    }


    public Set<String> difference(Set<String> setA, Set<String> setB) {
        Set<String> setDifference = new HashSet<>();
        String s;
        Iterator<String> iterator = setA.iterator();
        while (iterator.hasNext()) {
            s = iterator.next();
            if (!setB.contains(s)) {
                setDifference.add(s);
            }
        }
        return setDifference;
    }

    /*整理数据*/
    public void clean() {
        String dataA = etData1.getText().toString();
        String dataB = etData2.getText().toString();

        dataA = dataA.replace("\n", ",");
        dataB = dataB.replace("\n", ",");

        etData1.setText(dataA);
        etData2.setText(dataB);
    }

    /*计算差异*/
    public void make() {
        clean();
        String dataA = etData1.getText().toString();
        String dataB = etData2.getText().toString();
        dataA = dataA.replace("\r", "");
        dataB = dataB.replace("\r", "");

        String[] arrayA = dataA.split(",");
        String[] arrayB = dataB.split(",");


        Set<String> setA = new HashSet<>(),
                setB = new HashSet<>(),
                setC;


        /**将数组元素加到集合*/
        for (int i = 0; i < arrayA.length; i++) {
            setA.add(arrayA[i]);
        }
        for (int i = 0; i < arrayB.length; i++) {
            setB.add(arrayB[i]);
        }

        /**求差集结果*/
        setC = setA.size() > setB.size() ? difference(setA, setB) : difference(setB, setA);

        Iterator<String> iterator = setC.iterator();
        StringBuffer sb = new StringBuffer("差异:\n");
        while (iterator.hasNext()) {
            sb.append(iterator.next()).append("\n");
        }
        etShow.setText(sb.toString());
    }


}
