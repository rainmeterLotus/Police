package com.police.momo.query;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.police.momo.MainApplication;
import com.police.momo.R;
import com.police.momo.base.BaseActivity;
import com.police.momo.bean.Question;
import com.police.momo.util.AnswerDialog;
import com.police.momo.util.DialogUtil;
import com.police.momo.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 作者： momo on 2015/10/12.
 * 邮箱：wangzhonglimomo@163.com
 */
public class EditQuestionActivity extends BaseActivity {
    public static final String QUESTION = "question";
    public Question question;
    private EditText edit_query_title;
    private EditText edit_query_et;
    private Button add_answer;
    private int position;
    AnswerDialog dialog;

    public static void action(Context context, Question question) {
        Intent intent = new Intent(context, EditQuestionActivity.class);
        intent.putExtra(QUESTION, question);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_edit_question);
        if (getIntent() != null) {
            question = (Question) getIntent().getSerializableExtra(QUESTION);
            position = getIntent().getIntExtra("position", -1);
        }
        if (question == null) {
            onBackPressed();
        }
        initView();
        bindDate(question);
    }

    private void initView() {
        edit_query_title = (EditText) this.findViewById(R.id.edit_query_title);
        edit_query_et = (EditText) this.findViewById(R.id.edit_query_et);
        add_answer = (Button) this.findViewById(R.id.add_answer);
        add_answer.setOnClickListener(this);
    }

    private void bindDate(Question question) {
//        edit_query_title.setText(question.getNum() + " : " + question.getContent());
        edit_query_title.setText(question.getContent());
        edit_query_et.setText(question.getAnswer());
        Selection.setSelection(edit_query_et.getText(), edit_query_et.getText()
                .length());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_and_clear, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            onSave();
            return true;
        }
        if (id == R.id.action_clear) {
            onClear();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (TextUtils.isEmpty(edit_query_et.getText())) {
            DialogUtil.show(EditQuestionActivity.this, "温馨提示", "确定不回答这个问题吗？", "确定", new
                    DialogInterface
                            .OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }, "取消", null);
        } else {
            onSave();
        }
    }

    private void onSave() {
        String answer = edit_query_et.getText().toString().trim();
        String content = edit_query_title.getText().toString().trim();
        if (!TextUtils.isEmpty(answer)) {
            question.setAnswer(answer);
            question.setContent(content);
            showToast("保存成功");
            Intent intent = new Intent();
            intent.putExtra("question", question);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            showToast("请输入答案");
        }
    }

    private void onClear() {
//        edit_query_et.setText("");
//        question.setAnswer("");
//        showToast("答案已经清空");
//        for (Question q : MainApplication.getInstance().getQuestions()) {
//            if (q.getType_cat() == question.getType_cat()) {
//                if (MainApplication.getInstance().getQuestions().remove(q)) {
//                    finish();
//                    return;
//                }
//            }
//        }
//        Intent intent = new Intent();
//        setResult(RESULT_OK, intent);

        if (position >=0) {
            MainApplication.getInstance().getQuestions().remove(position);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.add_answer) {
            List<String> lists = Arrays.asList(StringUtils.spitString(question.getDefaultAnswer()));
            showCustomDialog(lists, "选择答案");
        }
    }

    /**
     * 底部弹出dialog
     */
    private void showCustomDialog(final List<String> custList, String text) {
        if (dialog == null) {
            dialog = new AnswerDialog(EditQuestionActivity.this, R.style.popup_dialog_style);
        }
        Window win = dialog.getWindow();
        win.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        WindowManager mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        win.setWindowManager(mWindowManager, null, null);
        dialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
        win.setWindowAnimations(R.style.ContactAnimationPreview);
        dialog.show();
        dialog.setListAdapterData(custList);
        dialog.setTitleText(text);
        dialog.setListOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != custList && custList.size() > 0) {
                    edit_query_et.setText(custList.get(position));
                }
                dialog.dismiss();
            }
        });
    }

}
