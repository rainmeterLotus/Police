package com.police.momo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.police.momo.base.BaseActivity;
import com.police.momo.base.ViewHolder;
import com.police.momo.newask.EmptyQueryActivity;
import com.police.momo.query.BasicInfoActivity;
import com.police.momo.setting.SettingActivity;
import com.police.momo.widget.MyGridView;


public class MainActivity extends BaseActivity {
    private int gridContents[] = {R.mipmap.ic_xunwen,
            R.mipmap.ic_null
    };
    private String gridStringContents[] = {"询问", "新建空白"};
    GridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar.setDisplayHomeAsUpEnabled(false);
        initView();
    }

    private void initView() {
        MyGridView home_grid = (MyGridView) this.findViewById(R.id.home_grid);
        home_grid.setAdapter(adapter = new GridAdapter());
        home_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(BasicInfoActivity.class);
                        break;
                    case 1:
                        startActivity(EmptyQueryActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return gridContents.length;
        }

        @Override
        public Object getItem(int position) {
            return gridContents[position];
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
            item_home_grid_iv.setImageResource(gridContents[position]);
            item_home_grid_tv.setText(gridStringContents[position]);
            return convertView;

        }
    }

    private long exitTime = 0l;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showToast("再按一次返回键退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.getAppManager().finishAllActivity();
            //清除掉本地生成的PDF文件
            Constant.deleteFile();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(SettingActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainApplication.getInstance().setInfo(null);
        MainApplication.getInstance().setQuestions(null);
    }
}
