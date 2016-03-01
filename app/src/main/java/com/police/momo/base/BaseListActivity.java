package com.police.momo.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.police.momo.R;

@SuppressLint("ResourceAsColor")
public class BaseListActivity extends BaseActivity {
    protected BaseAdapter adapter;
    protected ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listview);
        listView = (ListView) this.findViewById(R.id.listview);
    }

    protected void setAdapter(BaseAdapter adapter) {
        if (adapter == null) {
            return;
        }
        this.adapter = adapter;
        listView.setAdapter(adapter);
    }

    protected BaseAdapter getAdapter() {
        return adapter;
    }

    public void setBgColor(int id) {
        listView.setBackgroundColor(getResources().getColor(id));
    }

    protected void setEmptyView(String text, int colorId) {
        View empty = View.inflate(mContext, R.layout.empty_text, null);
        TextView tv = (TextView) empty.findViewById(R.id.empty_tv);
        tv.setTextColor(getResources().getColor(colorId));
        tv.setText(text);
        listView.setEmptyView(empty);
    }

    protected ListView getListView() {
        return listView;
    }

    protected void addHeardView(View view) {
        if (view == null)
            return;
        listView.addHeaderView(view);
    }

}
