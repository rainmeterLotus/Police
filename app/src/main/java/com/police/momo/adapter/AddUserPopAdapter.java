package com.police.momo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.police.momo.R;

import java.util.ArrayList;
import java.util.List;

/***
 * 新增用户pop数据适配
 *
 * @author sdj
 */
public class AddUserPopAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> list = new ArrayList<String>();

    public AddUserPopAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.list = list;
    }


    public void setData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (null != list && list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        if (null == convertView) {
            Holder holder = new Holder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout
                    .layout_addusertypeandrank_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.tv_text);
            convertView.setTag(holder);
        }
        Holder holder = (Holder) convertView.getTag();

        if (null != list && list.size() > 0) {
            holder.name.setText(list.get(position));
        }
        return convertView;
    }

    private class Holder {
        TextView name;
    }
}
