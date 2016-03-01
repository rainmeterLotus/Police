package com.police.momo.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.police.momo.R;
import com.police.momo.adapter.AddUserPopAdapter;

import java.util.ArrayList;
import java.util.List;


public class AnswerDialog extends Dialog {
    android.widget.AdapterView.OnItemClickListener mOnItemClick;
    Context mContext;
    ListView lv;
    List<String> list;
    private AddUserPopAdapter adapter;
    TextView title;

    public AnswerDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public AnswerDialog(Context context, int theme) {

        super(context, theme);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_companyproduct);
        // 使dialog全局
        getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        initViews();
    }

    private void initViews() {
        lv = (ListView) findViewById(R.id.product_search_dialog_lv);
        title = (TextView) findViewById(R.id.product_search_dialog_title);
        list = new ArrayList<String>();
    }

    /**
     * listView item 点击事件
     */
    public void setListOnItemClickListener(android.widget.AdapterView.OnItemClickListener l) {
        this.mOnItemClick = l;
        lv.setOnItemClickListener(mOnItemClick);
    }

    /**
     * 设置company list 数据, 并刷新adapter
     *
     * @param list
     */
    public void setListAdapterData(List<String> list) {
        adapter = new AddUserPopAdapter(mContext, list);
        this.list.clear();
        this.list.addAll(list);
        adapter.setData(list);
        lv.setAdapter(adapter);
    }

    /**
     * 设置dialog title
     *
     * @param text
     */
    public void setTitleText(String text) {
        title.setText(text);
    }
}
