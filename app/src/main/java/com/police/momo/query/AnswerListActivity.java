package com.police.momo.query;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.police.momo.Constant;
import com.police.momo.MainApplication;
import com.police.momo.R;
import com.police.momo.base.BaseListActivity;
import com.police.momo.base.ViewHolder;
import com.police.momo.bean.BasicChunk;
import com.police.momo.bean.BasicInfo;
import com.police.momo.bean.Question;
import com.police.momo.util.PdfUtil;

import org.vudroid.pdfdroid.PdfViewerActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者： momo on 2015/11/1.
 * 邮箱：wangzhonglimomo@163.com
 */
public class AnswerListActivity extends BaseListActivity {

    private List<Question> questions;
    private int mPosition = -1;
    private AnswerListAdapter adapter;
    private Font mNormalFont;//普通字体
    private Font mUnderLineFont;//下划线字体
    private Font mBoldFont;//粗体
    private int FONT_SIZE = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEmptyView("还没有问题", R.color.login_text_color);

        initItext();
    }

    @Override
    public void onResume() {
        super.onResume();
        questions = MainApplication.getInstance().getQuestions();
        if (null == adapter) {
            adapter = new AnswerListAdapter(questions, this);
        } else {
            adapter.notifyDataSetChanged();
        }
        setAdapter(adapter);
        listView.setDivider(null);
        listView.setBackgroundColor(getResources().getColor(R.color.white));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                Intent intent = new Intent(AnswerListActivity.this, EditQuestionActivity.class);
                intent.putExtra(EditQuestionActivity.QUESTION, questions.get(position));
                intent.putExtra("position", position);
                startActivityForResult(intent, Constant.REQUESTCODE.EDIT_QUESTION);
            }
        });
    }

    private void initItext() {
        try {
            BaseFont bfChinese = BaseFont.createFont("/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont
                    .NOT_EMBEDDED);
            //    Log.d("itext", "======createFont======" + createFont);
            mNormalFont = new Font(bfChinese, FONT_SIZE, Font.NORMAL);
            mUnderLineFont = new Font(bfChinese, FONT_SIZE, Font.UNDERLINE);
            mBoldFont = new Font(bfChinese, FONT_SIZE, Font.BOLD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            if (Constant.REQUESTCODE.EDIT_QUESTION == requestCode) {
                Question question = (Question) data.getSerializableExtra("question");
                //更新这条问题
                questions.get(mPosition).setAnswer(question.getAnswer());
                questions.get(mPosition).setContent(question.getContent());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_previse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_previse) {
            onPrevise();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 预览界面
     */
    private void onPrevise() {
        createPdf();
    }

    private void createPdf() {
        File file = new File(Constant.DIRCONFIG.ROOT_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        new Thread() {
            public void run() {
                Looper.prepare();
                // step 1: creation of a document-object
                Document document = new Document(PageSize.A4);
                String path = null;
                BasicInfo basicInfo = MainApplication.getInstance().getInfo();
                try {
                    // step 2:
                    // we create a writer that listens to the document
                    // and directs a PDF-stream to a file
                    /**
                     * left , right ,top,bottom
                     */
                    //为了保证这个目录下只能有一个文档，并且是打印完成就删除
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat
//                            ("yyyy-MM-dd-HH-mm-ss");
//                    String format = simpleDateFormat.format(new Date(System.currentTimeMillis()))
//                            + ".pdf";
                    String format = "police.pdf";
                    path = Constant.DIRCONFIG.ROOT_PATH + format;
                    File file_null = new File(path);
                    PdfWriter.getInstance(document, new FileOutputStream(file_null));
                    // step 3: we open the document
                    document.open();
                    // step 4: we add a paragraphTitle to the document
                    Log.i("PdfUtil", "==document.getPageSize();===" + document.getPageSize());
                    createHeader(basicInfo.getCount() + "", document, mNormalFont);
                    Paragraph paragraphTitle = new Paragraph();
                    paragraphTitle.setFont(mBoldFont);
                    paragraphTitle.add("询问笔录");
                    paragraphTitle.setAlignment(Paragraph.ALIGN_CENTER);
                    document.add(paragraphTitle);
                    createBasic(basicInfo, document, mNormalFont);
                    createMain(document, mUnderLineFont);
                } catch (Exception ioe) {
                    System.err.println(ioe.getMessage());
                } finally {
                    Toast.makeText(getApplicationContext(), "生成文档成功", Toast.LENGTH_SHORT).show();
                    // step 5: we close the document
                    try {
                        document.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(AnswerListActivity.this, PdfViewerActivity.class);
                    intent.setData(Uri.fromFile(new File(path)));
                    intent.putExtra("path", path);
                    AnswerListActivity.this.startActivity(intent);
                }
                Looper.loop();
            }


            ;
        }.start();
    }

    private void createHeader(String strNum, Document document, Font font) {
        try {
            Phrase headerFooter = PdfUtil.getTemplateXX_XX(mUnderLineFont, "第", "次", strNum, 6);
            Paragraph headerParagraph = new Paragraph();
            headerParagraph.setFont(mNormalFont);
            headerParagraph.add(headerFooter);
            headerParagraph.setAlignment(Paragraph.ALIGN_RIGHT);

            headerParagraph.setIndentationRight(100f);
            headerParagraph.setPaddingTop(10);
            document.add(headerParagraph);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 基本信息
     *
     * @param basicInfo
     * @param document
     * @param font
     * @throws DocumentException
     */
    private void createBasic(BasicInfo basicInfo, Document document, Font font) throws
            DocumentException {
        Paragraph paragraphBacic = new Paragraph();
        paragraphBacic.setFont(font);
        paragraphBacic.add("1.基本信息");
        paragraphBacic.setAlignment(Paragraph.ALIGN_LEFT);
        //   document.add(paragraphBacic);

        if (basicInfo == null) {
            return;
        }
        Element timeElement = PdfUtil.createTimePhrase(basicInfo.getStartTime(), basicInfo
                .getEndTime(), mUnderLineFont);

        ArrayList<BasicChunk> addressList = new ArrayList<>();
        addressList.add(new BasicChunk("地点", basicInfo.getAddrass(), 40));
        Element addressElement = PdfUtil.groupChunkToPhrase(new Phrase(), PdfUtil
                .textChunks_lineChunks(document, mUnderLineFont, addressList));


        ArrayList<BasicChunk> askList = new ArrayList<>();
//        askList.add(new BasicChunk("询问人",basicInfo.getName(),8));
        askList.add(new BasicChunk("询问人", "        ", 8));
        askList.add(new BasicChunk("工作单位", basicInfo.getCompanyAdd(), 40));
        Element askName_add_Element = PdfUtil.groupChunkToPhrase(new Phrase(), PdfUtil
                .textChunks_lineChunks(document, mUnderLineFont, askList));

        ArrayList<BasicChunk> recordList = new ArrayList<>();
//        recordList.add(new BasicChunk("记录人",basicInfo.getName(),8));
        recordList.add(new BasicChunk("记录人", "        ", 8));
        recordList.add(new BasicChunk("工作单位", basicInfo.getCompanyAdd(), 40));
        Element recordName_add_Element = PdfUtil.groupChunkToPhrase(new Phrase(), PdfUtil
                .textChunks_lineChunks(document, mUnderLineFont, recordList));

        ArrayList<BasicChunk> askedList = new ArrayList<>();
        askedList.add(new BasicChunk("被询问人", basicInfo.getName(), 8));
        askedList.add(new BasicChunk("性别", basicInfo.getSex(), 2));
        askedList.add(new BasicChunk("年龄", basicInfo.getAge() + "", 3));
        askedList.add(new BasicChunk("出生日期", basicInfo.getBirthday(), 40));
        Element askedName_add_Element = PdfUtil.groupChunkToPhrase(new Phrase(), PdfUtil
                .textChunks_lineChunks(document, mUnderLineFont, askedList));

        ArrayList<BasicChunk> cardIdList = new ArrayList<>();
        cardIdList.add(new BasicChunk("身份证号码种类及号码", basicInfo.getIdCard(), 8));
        cardIdList.add(new BasicChunk("政治面貌", basicInfo.getPolitics(), 2));
        Element cardId_politics_Element = PdfUtil.groupChunkToPhrase(new Phrase(), PdfUtil
                .textChunks_lineChunks(document, mUnderLineFont, cardIdList));

        ArrayList<BasicChunk> nowAddList = new ArrayList<>();
        nowAddList.add(new BasicChunk("现住址", basicInfo.getNowAdd(), 40));
        nowAddList.add(new BasicChunk("联系方式", basicInfo.getTel(), 2));
        Element nowAdd_telElement = PdfUtil.groupChunkToPhrase(new Phrase(), PdfUtil
                .textChunks_lineChunks(document, mUnderLineFont, nowAddList));

        ArrayList<BasicChunk> censusAddList = new ArrayList<>();
        censusAddList.add(new BasicChunk("户籍所在地", basicInfo.getCensusAdd(), 40));
        Element censusAddElement = PdfUtil.groupChunkToPhrase(new Phrase(), PdfUtil
                .textChunks_lineChunks(document, mUnderLineFont, censusAddList));

        Element signElement = PdfUtil.createSignPhrase(mUnderLineFont);

        Paragraph elements = new Paragraph(font.getSize() * 1.6f);
        elements.setAlignment(Paragraph.ALIGN_LEFT);
        elements.setFont(font);
        //时间
        elements.add(timeElement);
        elements.add(Chunk.NEWLINE);
        //地点
        elements.add(addressElement);
        elements.add(Chunk.NEWLINE);
        //询问人
        elements.add(askName_add_Element);
        elements.add(Chunk.NEWLINE);
        //记录人
        elements.add(recordName_add_Element);
        elements.add(Chunk.NEWLINE);
        //被询问人
        elements.add(askedName_add_Element);
        elements.add(Chunk.NEWLINE);

        elements.add(cardId_politics_Element);
        elements.add(Chunk.NEWLINE);
        elements.add(nowAdd_telElement);
        elements.add(Chunk.NEWLINE);
        elements.add(censusAddElement);
        elements.add(Chunk.NEWLINE);
        elements.add(signElement);
        document.add(elements);
    }

    public void createMain(Document document, Font font) throws DocumentException {
        Paragraph paragraphBacic = new Paragraph();
        paragraphBacic.setFont(font);
        paragraphBacic.add("2.出入境");
        paragraphBacic.setAlignment(Paragraph.ALIGN_LEFT);
        //  document.add(paragraphBacic);
        if (questions == null && questions.size() <= 0) {
            return;
        }
        Paragraph paragraphContent = new Paragraph(font.getSize() * 1.6f);
//        paragraphContent.setLeading(font.getSize() * 1.5f);
        paragraphContent.setAlignment(Paragraph.ALIGN_LEFT);
        StringBuffer contentBuffer = new StringBuffer();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String contentStr = "问：" + question.getContent();
            String answerStr = "答：" + question.getAnswer();
            insertChunkToPhraph(document, paragraphContent, font, contentStr);
            insertChunkToPhraph(document, paragraphContent, font, answerStr);
        }
        document.add(paragraphContent);

    }

    public void insertChunkToPhraph(Document document, Paragraph paragraph, Font font, String
            value) {
        try {
            List<Chunk> chunkS = PdfUtil.textLineChunks(document, value, font);
            if (chunkS == null || chunkS.size() <= 0) {
                return;
            }
            Phrase elements = new Phrase(font.getSize() * 1.6f);
            for (Chunk chunk : chunkS) {
                elements.add(chunk);
                elements.add(Chunk.NEWLINE);
            }
            //    elements.remove(elements.size()-1);
            document.add(elements);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void insertMoreChunkToPhrase(String... params) {

    }


    public class AnswerListAdapter extends BaseAdapter {
        private List<Question> questions;
        private Context context;

        public AnswerListAdapter(List<Question> questions, Context context) {
            this.questions = questions;
            this.context = context;
        }

        @Override
        public int getCount() {
            return questions == null ? 0 : questions.size();
        }

        @Override
        public Question getItem(int position) {
            return questions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = View.inflate(context, R.layout.answer_list_item, null);
            }
            TextView edit_query_title = ViewHolder.get(convertView, R.id.edit_query_title);
            TextView edit_query_content = ViewHolder.get(convertView, R.id.edit_query_content);
            Question question = questions.get(position);
            edit_query_title.setText((position + 1) + ": " + question.getContent());
            edit_query_content.setText(question.getAnswer());
            return convertView;
        }

    }
}
