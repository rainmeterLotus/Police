package com.police.momo.query;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.police.momo.R;
import com.police.momo.base.BaseActivity;
import com.police.momo.base.ViewHolder;
import com.police.momo.bean.QuestionParent;
import com.police.momo.manager.DbManager;

import java.util.List;

/**
 * 询问
 * 作者： momo on 2015/9/26.
 * 邮箱：wangzhonglimomo@163.com
 */
public class QueryActivity extends BaseActivity {
    ListAdapter listAdapter;
    private DbManager dbManager;
    private List<QuestionParent> prarents;
    private List<QuestionParent> gridPrarents;
    private GridView home_grid;
    private ListView home_list;
    private GridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        initView();
        getListData();
    }

    private void initView() {
        home_grid = (GridView) this.findViewById(R.id.home_grid);
        home_list = (ListView) this.findViewById(R.id.home_list);
        home_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        home_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getGridData(prarents.get(position).getCatId());
                listAdapter.notifyDataSetChanged();
            }
        });
        home_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                QueryListActivity.action(mContext, gridPrarents.get(position));
                onFinish(gridPrarents.get(position).getCatId());
            }
        });
    }

    public void onFinish(String typeCat) {
        Intent intent = new Intent();
        intent.putExtra("typeCat", typeCat);
        setResult(RESULT_OK,intent);
        finish();
    }

    private void getListData() {
        dbManager = DbManager.sharedInstance(this);
        dbManager.openDatabase();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                openProgressDialog("请稍后...");
            }

            @Override
            protected Void doInBackground(Void... params) {
                prarents = dbManager.getAllParentTypeCatEcxeptBasic();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //更新界面
                closeProgressDialog();
                home_list.setAdapter(listAdapter = new ListAdapter());
                getGridData(prarents.get(0).getCatId());
                home_list.setItemChecked(0, true);
                listAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    private void getGridData(final String parent_id) {
        dbManager = DbManager.sharedInstance(this);
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
                home_grid.setAdapter(gridAdapter = new GridAdapter(gridPrarents));
                gridAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return prarents == null ? 0 : prarents.size();
        }

        @Override
        public Object getItem(int position) {
            return prarents.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.item_home_list_content, parent, false);
            }
            TextView item_home_grid_tv = ViewHolder.get(convertView,
                    R.id.item_home_grid_tv);
            item_home_grid_tv.setText(prarents.get(position).getName());
            updateBackground(position, convertView);
            return convertView;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void updateBackground(int position, View view) {
        int backgroundId;
        if (home_list.isItemChecked(position)) {
            backgroundId = R.color.text_color_9da9b9;
        } else {
            backgroundId = R.color.background_all;
        }
        Drawable background = mContext.getResources().getDrawable(backgroundId);
        view.setBackground(background);
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
