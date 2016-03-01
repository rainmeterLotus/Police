package com.police.momo.query;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.police.momo.MainApplication;
import com.police.momo.R;
import com.police.momo.base.BaseListActivity;
import com.police.momo.base.ViewHolder;
import com.police.momo.bean.Question;
import com.police.momo.manager.DbManager;

import java.util.List;

/**
 * 作者： momo on 2015/10/11.
 * 邮箱：wangzhonglimomo@163.com
 */
public class QueryListActivity extends BaseListActivity {
    public static final String TYPECAT = "typecate";
    private DbManager dbManager;
    private String type;
    private List<Question> questions;
    private int mPosition = -1;
    private MyAdapter adapter;
    private static final int REQUESTCODE = 100;


    public static void action(Context context, String type) {
        Intent intent = new Intent(context, QueryListActivity.class);
        intent.putExtra(TYPECAT, type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            type = getIntent().getStringExtra(TYPECAT);
            setTitle(type);
            getData(type);
        }
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.notifyDataSetChanged();
                Question question = questions.get(position);
                mPosition = position;
                toEditQuestion(question);
            }
        });
    }


    /**
     * 修改问题界面
     *
     * @param question
     */
    private void toEditQuestion(Question question) {
        Intent intent = new Intent(this, AnswerListActivity.class);
        if (!MainApplication.getInstance().getQuestions().contains(question)) {
            MainApplication.getInstance().getQuestions().add(question);
        }
//        else {
//            MainApplication.getInstance().getQuestions().remove(question);
//        }
        startActivity(intent);
    }

    private void getData(final String type) {
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
                String typeTemp = dbManager.getParentTypeCatByType(type);
                questions = dbManager.getQuestionsByTypeCat(typeTemp);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //更新界面
                closeProgressDialog();
                adapter = new MyAdapter(mContext, questions);
                setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != dbManager) {
            dbManager.closeDatabase();
        }
        MainApplication.getInstance().getQuestions().clear();
    }

    public class MyAdapter extends BaseAdapter {
        private Context context;
        private List<Question> questions;

        public MyAdapter(Context context, List<Question> questions) {
            this.context = context;
            this.questions = questions;
        }

        @Override
        public int getCount() {
            return questions == null ? 0 : questions.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = View.inflate(context, R.layout.list_item_question, null);
            }
            Question question = questions.get(position);
            TextView item_question_tv = ViewHolder.get(convertView, R.id.item_question_tv);
            item_question_tv.setText(question.getNum() + " : " + question.getContent());
            if ("0".equals(question.getParentTypeId())) {
                item_question_tv.setBackgroundResource(R.color.background_all);
            } else {
                item_question_tv.setBackgroundResource(R.drawable.list_item_bg);
            }
            updateBackground(position, convertView);
            return convertView;
        }

    }

    public void updateBackground(int position, View view) {
        int backgroundId;
        if (listView.isItemChecked(position)) {
            backgroundId = R.color.turquoise;
        } else {
            backgroundId = R.color.white;
        }
        Drawable background = mContext.getResources().getDrawable(backgroundId);
        view.setBackground(background);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_join, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_join) {
            Intent intent = new Intent(this, QueryActivity.class);
            startActivityForResult(intent, REQUESTCODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (REQUESTCODE == requestCode) {
                String join_typeCat = data.getStringExtra("typeCat");
                //加入别的询问信息
                getExtraData(join_typeCat);
            }
        }
    }

    //选择加入后 的数据
    private void getExtraData(final String typeCat) {
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
                List<Question> extraQquestions = dbManager.getQuestionsByTypeCat(typeCat);
                questions.addAll(extraQquestions);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //更新界面
                closeProgressDialog();
                adapter.notifyDataSetChanged();
                showToast("加入成功！");
            }
        }.execute();
    }
}
