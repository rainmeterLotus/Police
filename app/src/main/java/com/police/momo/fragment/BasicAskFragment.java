package com.police.momo.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.police.momo.R;
import com.police.momo.base.BaseFragment;
import com.police.momo.base.ViewHolder;
import com.police.momo.bean.QuestionParent;
import com.police.momo.manager.DbManager;

import java.util.List;

/**
 * 作者： momo on 2015/11/15.
 * 邮箱：wangzhonglimomo@163.com
 */
public class BasicAskFragment extends BaseFragment {

    private GridView gridView;
    private DbManager dbManager;
    private GridAdapter gridAdapter;
    private List<QuestionParent> gridPrarents;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_ask, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        gridView = (GridView) view.findViewById(R.id.home_grid);
        getGridData("0");
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                QueryListActivity.action(mContext, gridPrarents.get(position));
            }
        });
}
    private void getGridData(final String parent_id) {
        dbManager = DbManager.sharedInstance(mContext);
        dbManager.openDatabase();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                openProgressDialog("请稍后...");
            }

            @Override
            protected Void doInBackground(Void... params) {
                gridPrarents = dbManager.getTypeCatByParentTypeCat(parent_id);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //更新界面
                closeProgressDialog();
                gridView.setAdapter(gridAdapter = new GridAdapter(gridPrarents));
                gridAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    class GridAdapter extends BaseAdapter {
        List<QuestionParent> gridPrarents;

        public GridAdapter(List<QuestionParent> gridPrarents) {
            this.gridPrarents = gridPrarents;
        }

        @Override
        public int getCount() {
            return gridPrarents == null ? 0 : gridPrarents.size();
        }

        @Override
        public Object getItem(int position) {
            return gridPrarents.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.item_home_grid_content, parent, false);
            }
            ImageView item_home_grid_iv = ViewHolder.get(convertView,
                    R.id.item_home_grid_iv);
            TextView item_home_grid_tv = ViewHolder.get(convertView,
                    R.id.item_home_grid_tv);
            item_home_grid_iv.setImageResource(R.mipmap.ic_xunwen);
            item_home_grid_tv.setText(gridPrarents.get(position).getName());
            return convertView;
        }
    }
}
