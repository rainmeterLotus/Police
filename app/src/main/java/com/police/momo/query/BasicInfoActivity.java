package com.police.momo.query;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.police.momo.MainApplication;
import com.police.momo.R;
import com.police.momo.base.BaseActivity;
import com.police.momo.bean.BasicInfo;
import com.police.momo.util.DateUtils;
import com.police.momo.util.DialogUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 基本信息
 * 作者： momo on 2015/9/26.
 * 邮箱：wangzhonglimomo@163.com
 */
public class BasicInfoActivity extends BaseActivity {

    private EditText info_et_count;
    private EditText info_et_add;
    private EditText info_et_id;
    private EditText info_et_name;
    private EditText info_et_name_old;
    private EditText info_et_age;
    private EditText info_et_census_add;
    private EditText info_et_now_add;
    private EditText info_et_company_add;
    private EditText info_et_tel;
    private TextView info_tv_category;
    private TextView info_tv_suspicion;
    private TextView info_tv_type;
    private TextView info_tv_start_time;
    private TextView info_tv_end_time;
    private TextView info_tv_sex;
    private TextView info_tv_birthday;
    private TextView info_tv_education;
    private TextView info_tv_nation;
    private TextView info_tv_politics;
    private Button btn_next_ask;

    int selectIndex = 0;
    private boolean hasSaved = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        initView();
    }

    private void initView() {
        info_et_count = (EditText) this.findViewById(R.id.info_et_count);
        info_et_add = (EditText) this.findViewById(R.id.info_et_add);
        info_et_id = (EditText) this.findViewById(R.id.info_et_id);
        info_et_name = (EditText) this.findViewById(R.id.info_et_name);
        info_et_name_old = (EditText) this.findViewById(R.id.info_et_name_old);
        info_et_age = (EditText) this.findViewById(R.id.info_et_age);
        info_et_census_add = (EditText) this.findViewById(R.id.info_et_census_add);
        info_et_now_add = (EditText) this.findViewById(R.id.info_et_now_add);
        info_et_company_add = (EditText) this.findViewById(R.id.info_et_company_add);
        info_et_tel = (EditText) this.findViewById(R.id.info_et_tel);

        info_tv_category = (TextView) this.findViewById(R.id.info_tv_category);
        info_tv_suspicion = (TextView) this.findViewById(R.id.info_tv_suspicion);
        info_tv_type = (TextView) this.findViewById(R.id.info_tv_type);
        info_tv_start_time = (TextView) this.findViewById(R.id.info_tv_start_time);
        info_tv_end_time = (TextView) this.findViewById(R.id.info_tv_end_time);
        info_tv_sex = (TextView) this.findViewById(R.id.info_tv_sex);
        info_tv_birthday = (TextView) this.findViewById(R.id.info_tv_birthday);
        info_tv_education = (TextView) this.findViewById(R.id.info_tv_education);
        info_tv_nation = (TextView) this.findViewById(R.id.info_tv_nation);
        info_tv_politics = (TextView) this.findViewById(R.id.info_tv_politics);
        btn_next_ask = (Button) this.findViewById(R.id.btn_next_ask);
        info_tv_category.setOnClickListener(this);
        info_tv_suspicion.setOnClickListener(this);
        info_tv_type.setOnClickListener(this);
        info_tv_start_time.setOnClickListener(this);
        info_tv_end_time.setOnClickListener(this);
        info_tv_sex.setOnClickListener(this);
        info_tv_birthday.setOnClickListener(this);
        info_tv_education.setOnClickListener(this);
        info_tv_nation.setOnClickListener(this);
        info_tv_politics.setOnClickListener(this);
        btn_next_ask.setOnClickListener(this);
        info_tv_start_time.setText(DateUtils.getCurrentDate(DateUtils.dateFormatYMDHMS));

        if ("edit".equals(getIntent().getStringExtra("edit"))) {
            btn_next_ask.setVisibility(View.GONE);
        }
        updateView();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.info_tv_category:
                selecteCategory();
                break;
            case R.id.info_tv_suspicion:
                selecteSuspicion();
                break;
            case R.id.info_tv_type:
                selectTypes();
                break;
            case R.id.info_tv_start_time:
                selecteStartTime();
                break;
            case R.id.info_tv_end_time:
                selecteEndTime();
                break;
            case R.id.info_tv_sex:
                selecteSexy();
                break;
            case R.id.info_tv_birthday:
                selecteBirthday();
                break;
            case R.id.info_tv_education:
                selecteEducation();
                break;
            case R.id.info_tv_nation:
                selecteNation();
                break;
            case R.id.info_tv_politics:
                selectePolitics();
                break;
            case R.id.btn_next_ask:
//                startActivity(QueryActivity.class);
//                startActivity(BasicQueryActivity.class);QueryListActivity
//                QueryListActivity.action(this, info_tv_category.getText().toString().trim());
                onSave();
                break;
            default:
                break;
        }
    }

    /**
     * 选择到案方式
     */
    private void selectTypes() {
        selectIndex = 0;
        final String[] info_daoan_type = getResources().getStringArray(R.array.info_daoan_type);
        new AlertDialog.Builder(getActivity()).setSingleChoiceItems(info_daoan_type, 0, new
                DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectIndex = which;
                    }

                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                info_tv_type.setText(info_daoan_type[selectIndex]);
            }
        }).setNegativeButton("取消", null).setTitle("到案方式").show();
    }

    /**
     * 选择开始时间
     */
    private void selecteStartTime() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        int day_ofweek = c.get(Calendar.DAY_OF_WEEK);
        final int hours = c.get(Calendar.HOUR_OF_DAY);
        final int minutes = c.get(Calendar.MINUTE);
        final int second = c.get(Calendar.SECOND);
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog
                .OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String date = String.format("%02d-%02d-%02d %02d:%02d:%02d", year, month + 1,
                        day, hourOfDay, minute, second);
                info_tv_start_time.setText(date);
            }
        }, hours, minutes, true);
        dialog.setTitle("选择开始时间");
        dialog.show();
    }

    /**
     * 选择结束时间
     */
    private void selecteEndTime() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        int day_ofweek = c.get(Calendar.DAY_OF_WEEK);
        final int hours = c.get(Calendar.HOUR_OF_DAY);
        final int minutes = c.get(Calendar.MINUTE);
        final int second = c.get(Calendar.SECOND);
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog
                .OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String date = String.format("%02d-%02d-%02d %02d:%02d:%02d", year, month + 1,
                        day, hourOfDay, minute, second);
                info_tv_end_time.setText(date);
            }
        }, hours, minutes, true);
        dialog.setTitle("选择结束时间");
        dialog.show();
    }

    /**
     * 选择出生日期
     */
    private void selecteBirthday() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int
                            dayOfMonth) {
                        String date = String.format("%02d-%02d-%02d", year, monthOfYear + 1,
                                dayOfMonth);
                        info_tv_birthday.setText(date);
                        //顺便把年龄算出来
                        Date date1 = DateUtils.getDateByFormat(date, DateUtils.dateFormatYMD);
                        try {
                            int age = DateUtils.getAge(date1);
                            info_et_age.setText(age + "");
                        } catch (Exception e) {
                            showToast("年龄有误");
                            e.printStackTrace();
                        }
                    }
                }, year, month, day);
        dialog.setTitle("选择结束时间");
        dialog.show();
    }

    /**
     * 选择嫌疑人
     */
    private void selecteSuspicion() {
        selectIndex = 0;
        final String[] info_person_types = getResources().getStringArray(R.array.info_person_types);
        new AlertDialog.Builder(getActivity()).setSingleChoiceItems(info_person_types, 0, new
                DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectIndex = which;
                    }

                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                info_tv_suspicion.setText(info_person_types[selectIndex]);
            }
        }).setNegativeButton("取消", null).setTitle("人员类型").show();
    }

    /**
     * 笔录格式
     */
    private void selecteCategory() {
        selectIndex = 0;
        final String[] info_categorys = getResources().getStringArray(R.array.info_categorys);
        new AlertDialog.Builder(getActivity()).setSingleChoiceItems(info_categorys, 0, new
                DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectIndex = which;
                    }

                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                info_tv_category.setText(info_categorys[selectIndex]);
            }
        }).setNegativeButton("取消", null).setTitle("笔录格式").show();
    }

    /**
     * 选择性别
     */
    private void selecteSexy() {
        selectIndex = 0;
        final String[] info_sexys = getResources().getStringArray(R.array.info_sexs);
        new AlertDialog.Builder(getActivity()).setSingleChoiceItems(info_sexys, 0, new
                DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectIndex = which;
                    }

                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                info_tv_sex.setText(info_sexys[selectIndex]);
            }
        }).setNegativeButton("取消", null).setTitle("选择性别").show();
    }

    /**
     * 选择教育水平
     */
    private void selecteEducation() {
        selectIndex = 0;
        final String[] info_educations = getResources().getStringArray(R.array.info_educations);
        new AlertDialog.Builder(getActivity()).setSingleChoiceItems(info_educations, 0, new
                DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectIndex = which;
                    }

                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                info_tv_education.setText(info_educations[selectIndex]);
            }
        }).setNegativeButton("取消", null).setTitle("选择教育水平").show();
    }

    /**
     * 选择政治面貌
     */
    private void selectePolitics() {
        selectIndex = 0;
        final String[] info_politics = getResources().getStringArray(R.array.info_politics);
        new AlertDialog.Builder(getActivity()).setSingleChoiceItems(info_politics, 0, new
                DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectIndex = which;
                    }

                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                info_tv_politics.setText(info_politics[selectIndex]);
            }
        }).setNegativeButton("取消", null).setTitle("选择政治面貌").show();
    }

    /**
     * 选择民族
     */
    private void selecteNation() {
        selectIndex = 0;
        final String[] info_nations = getResources().getStringArray(R.array.info_nations);
        new AlertDialog.Builder(getActivity()).setSingleChoiceItems(info_nations, 0, new
                DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectIndex = which;
                    }

                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                info_tv_nation.setText(info_nations[selectIndex]);
            }
        }).setNegativeButton("取消", null).setTitle("选择民族").show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            onSave();
            return true;
        }
//        if (id == R.id.action_clear) {
//            onClear();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 清空
     */
    private void onClear() {
//        MainApplication.getInstance().setInfo(new BasicInfo());
        MainApplication.getInstance().setInfo(null);
        updateView();
        showToast("已经清空！");
    }

    /**
     * 更新View
     */
    public void updateView() {
        BasicInfo info = MainApplication.getInstance().getInfo();
        info_et_count.setText(info.getCount() == 0 ? "1" : info.getCount() + "");
        info_et_add.setText(info.getAddrass());
        info_et_id.setText(info.getIdCard());
        info_et_name.setText(info.getName());
        info_et_name_old.setText(info.getOldName());
        info_et_age.setText(info.getAge() == 0 ? "" : info.getAge() + "");
        info_et_census_add.setText(info.getCensusAdd());
        info_et_now_add.setText(info.getNowAdd());
        info_et_company_add.setText(info.getCompanyAdd());
        info_et_tel.setText(info.getTel());
        info_tv_category.setText(TextUtils.isEmpty(info.getCategory()) ? "治安询问" : info
                .getCategory());
        info_tv_suspicion.setText(TextUtils.isEmpty(info.getSuspicion()) ? "嫌疑人" : info
                .getSuspicion());
        info_tv_type.setText(TextUtils.isEmpty(info.getType()) ? "口头传唤" : info.getType());
        info_tv_start_time.setText(TextUtils.isEmpty(info.getStartTime()) ? DateUtils
                .getCurrentDate(DateUtils.dateFormatYMDHMS) : info.getStartTime());
        info_tv_end_time.setText(info.getEndTime());
        info_tv_sex.setText(TextUtils.isEmpty(info.getSex()) ? "男" : info.getSex());
        info_tv_birthday.setText(info.getBirthday());
        info_tv_education.setText(TextUtils.isEmpty(info.getEducation()) ? "初中" : info
                .getEducation());
        info_tv_nation.setText(TextUtils.isEmpty(info.getNation()) ? "汉族" : info.getNation());
        info_tv_politics.setText(TextUtils.isEmpty(info.getPolitics()) ? "普通公民" : info
                .getPolitics());
    }

    /**
     * 保存数据
     */
    private void onSave() {
        BasicInfo info = MainApplication.getInstance().getInfo();
        String count = info_et_count.getText().toString().trim();
        String et_add = info_et_add.getText().toString().trim();
        String et_id = info_et_id.getText().toString().trim();
        String et_name = info_et_name.getText().toString().trim();
        String et_name_old = info_et_name_old.getText().toString().trim();
        String et_age = info_et_age.getText().toString().trim();
        String et_census_add = info_et_census_add.getText().toString().trim();
        String et_now_add = info_et_now_add.getText().toString().trim();
        String et_company_add = info_et_company_add.getText().toString().trim();
        String et_tel = info_et_tel.getText().toString().trim();
        String tv_category = info_tv_category.getText().toString().trim();
        String tv_suspicion = info_tv_suspicion.getText().toString().trim();
        String tv_type = info_tv_type.getText().toString().trim();
        String tv_start_time = info_tv_start_time.getText().toString().trim();
        String tv_end_time = info_tv_end_time.getText().toString().trim();
        String tv_sex = info_tv_sex.getText().toString().trim();
        String tv_birthday = info_tv_birthday.getText().toString().trim();
        String tv_education = info_tv_education.getText().toString().trim();
        String tv_nation = info_tv_nation.getText().toString().trim();
        String tv_politics = info_tv_politics.getText().toString().trim();
        info.setCount(TextUtils.isEmpty(count) ? 0 : Integer.parseInt(count));
        info.setAddrass(et_add);
        info.setIdCard(et_id);
        info.setName(et_name);
        info.setOldName(et_name_old);
        info.setAge(TextUtils.isEmpty(et_age) ? 0 : Integer.parseInt(et_age));
        info.setCensusAdd(et_census_add);
        info.setNowAdd(et_now_add);
        info.setCompanyAdd(et_company_add);
        info.setTel(et_tel);
        info.setCategory(tv_category);
        info.setSuspicion(tv_suspicion);
        info.setType(tv_type);
        info.setStartTime(tv_start_time);
        info.setEndTime(tv_end_time);
        info.setSex(tv_sex);
        info.setBirthday(tv_birthday);
        info.setEducation(tv_education);
        info.setNation(tv_nation);
        info.setPolitics(tv_politics);
//        showToast("保存成功！");
        hasSaved = true;
        if ("edit".equals(getIntent().getStringExtra("edit"))) {
            Intent intent = new Intent(this, AnswerListActivity.class);
            startActivity(intent);
        } else {
            QueryListActivity.action(this, info_tv_category.getText().toString().trim());
        }
    }

    @Override
    public void onBackPressed() {
        if (hasSaved) {//已经保存过
            finish();
        } else {
            DialogUtil.show(BasicInfoActivity.this, "温馨提示", "确定不保存退出吗？", "确定", new
                    DialogInterface
                            .OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }, "取消", null);
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        MainApplication.getInstance().setInfo(null);
//    }
}
