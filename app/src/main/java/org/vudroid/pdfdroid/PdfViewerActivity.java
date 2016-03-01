package org.vudroid.pdfdroid;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.support.v4.print.PrintHelper;
import android.widget.Toast;

import com.police.momo.R;
import com.police.momo.query.BasicInfoActivity;
import com.poqop.document.BaseViewerActivity;
import com.poqop.document.DecodeService;
import com.poqop.document.DecodeServiceBase;
import com.umeng.fb.util.Log;

import org.vudroid.pdfdroid.codec.PdfContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfViewerActivity extends BaseViewerActivity {

    private String data;

    @Override
    protected DecodeService createDecodeService() {
        return new DecodeServiceBase(new PdfContext());
    }

    @Override
    protected void onPrint() {
        data = getIntent().getStringExtra("path");
        boolean b = PrintHelper.systemSupportsPrint();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && b) {//4.4版本以上才能使用
            printDocument();
        } else {
            Toast.makeText(mContext, "系统版本低,无法完成打印操作!!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 编辑
     */
    @Override
    protected void onEdit() {
//        startActivity(BasicInfoActivity.class);
        Intent intent = new Intent(this, BasicInfoActivity.class);
        intent.putExtra("edit", "edit");
        startActivity(intent);
    }

    @Override
    protected boolean canEdit() {
        if("emptyquery".equals(getIntent().getStringExtra("emptyquery"))){
            return false;
        }
        return true;
    }


    /**
     * 开始打印
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void printDocument() {
        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);
        String jobName = this.getString(R.string.app_name) +
                " Document";
        printManager.print(jobName, new MyPrintDocumentAdapter(this),
                null);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    class MyPrintDocumentAdapter extends PrintDocumentAdapter {
        Context context;
        private int pageHeight;
        private int pageWidth;
        public PdfDocument myPdfDocument;
        public int totalpages = 4;

        public MyPrintDocumentAdapter(Context context) {
            this.context = context;
        }

        @Override
        public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes,
                             CancellationSignal cancellationSignal, LayoutResultCallback
                                     callback, Bundle extras) {
            myPdfDocument = new PrintedPdfDocument(context, newAttributes);
            pageHeight = newAttributes.getMediaSize().getHeightMils() / 1000 * 72;
            pageWidth = newAttributes.getMediaSize().getWidthMils() / 1000 * 72;
            if (cancellationSignal.isCanceled()) {
                callback.onLayoutCancelled();
                return;
            }
            if (totalpages > 0) {
                PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                        .Builder("print_output.pdf")
                        .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT);
//                        .setPageCount(totalpages);
                PrintDocumentInfo info = builder.build();
                callback.onLayoutFinished(info, true);
            } else {
                callback.onLayoutFailed("Page count is zero.");
            }
        }

        @Override
        public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor destination,
                            CancellationSignal cancellationSignal, WriteResultCallback callback) {
//            for (int i = 0; i < totalpages; i++) {
//                if (pageInRange(pageRanges, i)) {
//                    PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth,
//                            pageHeight, i).create();
//                    PdfDocument.Page page = myPdfDocument.startPage(newPage);
//                    if (cancellationSignal.isCanceled()) {
//                        callback.onWriteCancelled();
//                        myPdfDocument.close();
//                        myPdfDocument = null;
//                        return;
//                    }
//                    drawPage(page, i);
//                    myPdfDocument.finishPage(page);
//                }
//            }
            try {
//                String path = Constant.DIRCONFIG.ROOT_PATH + "police.pdf";
                Log.e(TAG, data);
                destination = ParcelFileDescriptor.open(new File(data), ParcelFileDescriptor
                        .MODE_READ_WRITE);
                myPdfDocument.writeTo(new FileOutputStream(
                        destination.getFileDescriptor()));
            } catch (IOException e) {
                callback.onWriteFailed(e.toString());
                return;
            } finally {
                myPdfDocument.close();
                myPdfDocument = null;
            }
            callback.onWriteFinished(pageRanges);
        }

        private boolean pageInRange(PageRange[] pageRanges, int page) {
            for (int i = 0; i < pageRanges.length; i++) {
                if ((page >= pageRanges[i].getStart()) &&
                        (page <= pageRanges[i].getEnd()))
                    return true;
            }
            return false;
        }

        private void drawPage(PdfDocument.Page page,
                              int pagenumber) {
            Canvas canvas = page.getCanvas();

            pagenumber++; // Make sure page numbers start at 1

            int titleBaseLine = 72;
            int leftMargin = 54;

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(20);
            canvas.drawText(
                    "Test Print Document Page " + pagenumber,
                    leftMargin,
                    titleBaseLine,
                    paint);

            paint.setTextSize(14);
            canvas.drawText("This is some test content to verify that custom document printing " +
                    "works加安静地看好久家具的快乐 加开了多久了阿里肯定看见works加安静地看好久家具的快乐 "
                    + "加开了多久了阿里肯定看见works加安静地看好久家具的快乐 加开了多久了阿里肯定看见"
                    + "加开了多久了阿里肯定看见works加安静地看好久家具的快乐 加开了多久了阿里肯定看见"
                    + "加开了多久了阿里肯定看见works加安静地看好久家具的快乐 加开了多久了阿里肯定看见"
                    + "加开了多久了阿里肯定看见works加安静地看好久家具的快乐 加开了多久了阿里肯定看见"
                    + "加开了多久了阿里肯定看见works加安静地看好久家具的快乐 加开了多久了阿里肯定看见"
                    , leftMargin, titleBaseLine + 35, paint);

            if (pagenumber % 2 == 0)
                paint.setColor(Color.RED);
            else
                paint.setColor(Color.GREEN);

            PdfDocument.PageInfo pageInfo = page.getInfo();
            canvas.drawCircle(pageInfo.getPageWidth() / 2,
                    pageInfo.getPageHeight() / 2,
                    150,
                    paint);
        }

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onFinish() {
            super.onFinish();
        }
    }
}
