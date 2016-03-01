package com.police.momo.newask;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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

import com.police.momo.Constant;
import com.police.momo.R;
import com.police.momo.base.BaseActivity;
import com.police.momo.base.ViewHolder;
import com.police.momo.bean.CaseBean;
import com.police.momo.util.AssetsCopyer;

import org.vudroid.pdfdroid.PdfViewerActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 询问
 * 作者： momo on 2015/9/26.
 * 邮箱：wangzhonglimomo@163.com
 */
public class EmptyQueryActivity extends BaseActivity {
    ListAdapter listAdapter;
    private GridView home_grid;
    private ListView home_list;
    private GridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        initView();
        getListData();
//        copyFiles();
    }

    /**
     * 向手机上copyPDF文件
     */
    private void copyFileToPolice(final CaseBean caseBean) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                openProgressDialog("请稍后...");
            }

            @Override
            protected Void doInBackground(Void... params) {
                AssetsCopyer copyer = new AssetsCopyer(mContext);
                try {
                    copyer.copy(caseBean.getSourcePpath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //更新界面
                closeProgressDialog();
                Intent intent = new Intent(EmptyQueryActivity.this, PdfViewerActivity.class);
                intent.setData(Uri.fromFile(new File(caseBean.getTargetpath())));
                intent.putExtra("path", caseBean.getTargetpath());
                intent.putExtra("emptyquery", "emptyquery");
                EmptyQueryActivity.this.startActivity(intent);
            }
        }.execute();
    }

    private void initView() {
        home_grid = (GridView) this.findViewById(R.id.home_grid);
        home_list = (ListView) this.findViewById(R.id.home_list);
        home_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        home_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CaseBean item = gridAdapter.getItem(position);
                copyFileToPolice(item);
//                Intent intent = new Intent(EmptyQueryActivity.this, PdfViewerActivity.class);
//                intent.setData(Uri.fromFile(new File(item.getTargetpath())));
//                intent.putExtra("path", item.getTargetpath());
//                EmptyQueryActivity.this.startActivity(intent);
            }
        });
        home_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listAdapter.notifyDataSetChanged();
                String type = listAdapter.getItem(position);
                getGridData(type);
            }
        });
    }

    private void getListData() {
        List<String> cases = new ArrayList<>();
        cases.add("行政案件");
        cases.add("刑事案件");
        listAdapter = new ListAdapter(this, cases);
        home_list.setAdapter(listAdapter);
        home_list.setItemChecked(0, true);
        getGridData("行政案件");
    }

    private void getGridData(String type) {
        if ("行政案件".equals(type)) {
            List<CaseBean> grid_cases1 = new ArrayList<>();//行政案件
            CaseBean caseBean1 = new CaseBean("询问笔录", "pdf/xingzhenganjianxunwenbilu.pdf",
                    Constant.DIRCONFIG.PDF_PATH + "xingzhenganjianxunwenbilu.pdf");
            CaseBean caseBean2 = new CaseBean("询问笔录口头传唤", "pdf/xunwenbilukoutouchuanhuan.pdf",
                    Constant.DIRCONFIG.PDF_PATH + "xunwenbilukoutouchuanhuan.pdf");
            CaseBean caseBean3 = new CaseBean("公安行政案件权利义务告知书",
                    "pdf/gonganxingzhenganjianquanliyiwugaozhishu.pdf",
                    Constant.DIRCONFIG.PDF_PATH + "gonganxingzhenganjianquanliyiwugaozhishu.pdf");
            grid_cases1.add(caseBean1);
            grid_cases1.add(caseBean2);
            grid_cases1.add(caseBean3);
            gridAdapter = new GridAdapter(grid_cases1);
            home_grid.setAdapter(gridAdapter);
        } else if ("刑事案件".equals(type)) {
            List<CaseBean> grid_cases2 = new ArrayList<CaseBean>();//刑事案件
            CaseBean caseBean1 = new CaseBean("讯问笔录", "pdf/xingshianjianxunwenbilu.pdf",
                    Constant.DIRCONFIG.PDF_PATH + "xingshianjianxunwenbilu.pdf");
            CaseBean caseBean2 = new CaseBean("被害人诉讼权利义务告知书", "pdf/beihairensusong.pdf",
                    Constant.DIRCONFIG.PDF_PATH + "beihairensusong.pdf");
            CaseBean caseBean3 = new CaseBean("证人诉讼权利义务告知书", "pdf/zhengrensusong.pdf",
                    Constant.DIRCONFIG.PDF_PATH + "zhengrensusong.pdf");
            CaseBean caseBean4 = new CaseBean("犯罪嫌疑人诉讼权利义务告知书", "pdf/fanzuixianyiren.pdf",
                    Constant.DIRCONFIG.PDF_PATH + "fanzuixianyiren.pdf");
            CaseBean caseBean5 = new CaseBean("伤害案件当事人诉讼权利义务告知单", "pdf/shanghaianjiandanshiren.pdf",
                    Constant.DIRCONFIG.PDF_PATH + "shanghaianjiandanshiren.pdf");
            grid_cases2.add(caseBean1);
            grid_cases2.add(caseBean2);
            grid_cases2.add(caseBean3);
            grid_cases2.add(caseBean4);
            grid_cases2.add(caseBean5);
            gridAdapter = new GridAdapter(grid_cases2);
            home_grid.setAdapter(gridAdapter);
        }
    }

    class ListAdapter extends BaseAdapter {
        private Context context;
        private List<String> cases;

        public ListAdapter(Context context, List<String> cases) {
            this.context = context;
            this.cases = cases;
        }

        @Override
        public int getCount() {
            return cases == null ? 0 : cases.size();
        }

        @Override
        public String getItem(int position) {
            return cases.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.item_home_list_content, parent, false);
            }
            TextView item_home_grid_tv = ViewHolder.get(convertView,
                    R.id.item_home_grid_tv);
            item_home_grid_tv.setText(cases.get(position));
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
        List<CaseBean> grid_cases;

        public GridAdapter(List<CaseBean> grid_cases) {
            this.grid_cases = grid_cases;
        }

        @Override
        public int getCount() {
            return grid_cases == null ? 0 : grid_cases.size();
        }

        @Override
        public CaseBean getItem(int position) {
            return grid_cases.get(position);
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
            item_home_grid_tv.setText(grid_cases.get(position).getName());
            return convertView;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清除掉本地生成的PDF文件
        Constant.deleteFile();
    }
}
