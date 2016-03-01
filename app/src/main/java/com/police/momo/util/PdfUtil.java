package com.police.momo.util;

import android.text.TextUtils;
import android.util.Log;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Header;
import com.itextpdf.text.Phrase;
import com.police.momo.bean.BasicChunk;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by rain on 2015/10/30.
 */
public class PdfUtil {
    private static final String TAG = "PdfUtil";
    private static final Chunk CHUNK_TIME = new Chunk("时间");
    private static final Chunk CHUNK_YEAR = new Chunk("年");
    private static final Chunk CHUNK_MON = new Chunk("月");
    private static final Chunk CHUNK_DAY = new Chunk("日");
    private static final Chunk CHUNK_HOUR = new Chunk("时");
    private static final Chunk CHUNK_MIN = new Chunk("分");
    private static final Chunk CHUNK_TO = new Chunk("至");

    /**
     * 创建时间短语
     * @param startTime
     * @param endTime
     * @return
     */
    public static Element createTimePhrase(String startTime, String endTime,Font font) {
        Phrase elements = new Phrase();
        try {
            if(TextUtils.isEmpty(startTime)){
                startTime = System.currentTimeMillis()+"";
            }

            if(TextUtils.isEmpty(endTime)){
                endTime = startTime;
            }

            Log.i(TAG, "====startTime===" + startTime + "====endTime===" + endTime);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.dateFormatYMDHMS);
            simpleDateFormat.format(DateUtils.getDateByFormat(startTime, DateUtils.dateFormatYMDHMS));

            Calendar startCalendar = simpleDateFormat.getCalendar();
            Chunk chunkYear = getSuitChunk(font, startCalendar.get(Calendar.YEAR) + "", 4);
            Chunk chunkMon = getSuitChunk(font,  getFormat00Float(startCalendar.get(Calendar.MONTH)),2);
            Chunk chunkDay = getSuitChunk(font, getFormat00Float(startCalendar.get(Calendar.DAY_OF_MONTH)),2);
            Chunk chunkHour = getSuitChunk(font, getFormat00Float(startCalendar.get(Calendar.HOUR_OF_DAY)),2);
            Chunk chunkMin = getSuitChunk(font, getFormat00Float(startCalendar.get(Calendar.MINUTE)),2);

            elements.add(CHUNK_TIME);
            //年
            elements.add(chunkYear);
            elements.add(CHUNK_YEAR);
            //月
            elements.add(chunkMon);
            elements.add(CHUNK_MON);
            //日
            elements.add(chunkDay);
            elements.add(CHUNK_DAY);
            //时
            elements.add(chunkHour);
            elements.add(CHUNK_HOUR);
            //分
            elements.add(chunkMin);
            elements.add(CHUNK_MIN);
            //至
            elements.add(CHUNK_TO);

            simpleDateFormat.format(DateUtils.getDateByFormat(startTime, DateUtils.dateFormatYMDHMS));
            Calendar endCalendar = simpleDateFormat.getCalendar();


            Chunk chunkYear_ = getSuitChunk(font, endCalendar.get(Calendar.YEAR) + "",4);
            Chunk chunkMon_ = getSuitChunk(font,  getFormat00Float(endCalendar.get(Calendar.MONTH)),2);
            Chunk chunkDay_ = getSuitChunk(font, getFormat00Float(endCalendar.get(Calendar.DAY_OF_MONTH)),2);
            Chunk chunkHour_ = getSuitChunk(font, getFormat00Float(endCalendar.get(Calendar.HOUR_OF_DAY)), 2);
            Chunk chunkMin_ = getDateChunk(font, getFormat00Float(endCalendar.get(Calendar.MINUTE)), 2);

            //年
            elements.add(chunkYear_);
            elements.add(CHUNK_YEAR);
            //月
            elements.add(chunkMon_);
            elements.add(CHUNK_MON);
            //日
            elements.add(chunkDay_);
            elements.add(CHUNK_DAY);
            //时
            elements.add(chunkHour_);
            elements.add(CHUNK_HOUR);
            //分
            elements.add(chunkMin_);
            elements.add(CHUNK_MIN);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return elements;
        }
    }
    /**
     * 返回一个通用的组合短语
     * @param mainStr
     * @param secondStr
     * @return
     */
    public static Phrase createNormalPhrase(Document document,Font font,String mainStr , String secondStr , int strNum) {
        Phrase elements = new Phrase();
//        Chunk mainChunk = new Chunk(mainStr);
        Element element = textChunk(document, font, mainStr);
        Chunk secondChunk = getSuitChunk(font, secondStr, strNum);
        elements.add(element);
        elements.add(secondChunk);
        return elements;
    }
    public static Element createNormalPhrase1(String mainStr , String secondStr , int strNum) {
        return null;
    }




    /**
     * 组合一个合适的chunk
     *
     * @param font
     * @param str
     * @param strNum
     * @return
     */
    private static Chunk getSuitChunk(Font font, String str, int strNum) {
        if(TextUtils.isEmpty(str)){
            str = getNumString(strNum);
        }else{
            str = " " + str + " ";
        }
        Chunk chunk = new Chunk(str,font);
        return chunk;
    }

    private static String getSuitString(String str, int strNum) {
        if(TextUtils.isEmpty(str)){
            str = getNumString(strNum);
        }else{
            str = " " + str + " ";
        }
        return str;
    }

    /**
     * 组合一个合适的chunk
     *
     * @param font
     * @param str
     * @param strNum
     * @return
     */
    private static Chunk getDateChunk(Font font, String str, int strNum) {
        if(TextUtils.isEmpty(str)){
            str = getNumString(strNum);
        }else{
            str = " "+str;
        }
        Chunk chunk = new Chunk(str,font);
        return chunk;
    }


    /**
     * 将多个element组合为一个phrase
     * @param phrase
     * @param chunks
     * @return
     */
    public static Phrase groupChunkToPhrase(Phrase phrase,List<Element> chunks){
        if(chunks == null || chunks.size() <= 0){
            return null;
        }

        for(Element chunk:chunks){
            phrase.add(chunk);
        }
        return phrase;
    }

    /**
     * 获取一个空字符的字符
     * @param strNum
     * @return
     */
    private static String getNumString(int strNum) {
        StringBuffer strBuffer = new StringBuffer();
        for(int i=0;i<strNum;i++){
            strBuffer.append(" ");
        }
        return strBuffer.toString();
    }


    /**
     * 将多个element组合为一个element
     * @param elementArgs
     * @return
     */
    public static Element createMoreElements(Element... elementArgs){
        Phrase elements = new Phrase();
        for (Element element:elementArgs){
            elements.add(element);
        }
        return elements;
    }

    public static Element createSignPhrase(Font font) {
        Chunk chunk1 = new Chunk("(口头传唤/被扭送/自动投案的被询问人于");
        Element chunkArr =  getSignTime(font, "到达，");
        Element chunkLeave =  getSignTime(font, "离开，");
        Element chunkSuffix =  getTemplateXX_XX(font, "本人签名:", ")", "", 6);
        Element moreElements = createMoreElements(chunk1, chunkArr, chunkLeave, chunkSuffix);
        return moreElements;
    }

    private static Element getSignTime(Font font,String append) {
        Phrase elements = new Phrase();

        Chunk monChunk = getSuitChunk(font, "", 3);
        Chunk dayChunk = getSuitChunk(font, "", 3);
        Chunk hourChunk = getSuitChunk(font, "", 3);
        Chunk minChunk = getSuitChunk(font, "", 3);
        Chunk appendChunk = new Chunk(append);

        elements.add(monChunk);
        elements.add(CHUNK_MON);

        elements.add(dayChunk);
        elements.add(CHUNK_DAY);

        elements.add(hourChunk);
        elements.add(CHUNK_HOUR);

        elements.add(minChunk);
        elements.add(CHUNK_MIN);

        elements.add(appendChunk);
        return elements;
    }


    /**
     * 获取类似“本人签名_______。”格式的chunk
     * @param starStr
     * @param endStr
     * @param middleNum
     * @return
     */
    public static Phrase getTemplateXX_XX(Font font,String starStr, String endStr,String middleStr, int middleNum) {
        Phrase elements = new Phrase();
        Chunk starChunk = new Chunk(starStr);
        Chunk middleChunk = getSuitChunk(font, middleStr, middleNum);
        Chunk endChunk = new Chunk(endStr);
        elements.add(starChunk);
        elements.add(middleChunk);
        elements.add(endChunk);
        return elements;
    }

    public static Element createHeaderFooter(String value){
        Header header = new Header("header1",value);
        return header;
    }

    @Deprecated
    public static Chunk getUnderlineChunk(Document document, String str, Font font){
        float englishSize = getEnglishSize(document,font);
        float fontSizeHalf = font.getCalculatedSize() / 2;
        Chunk chunk = new Chunk(str);
        chunk.setFont(font);
        float widthPoint = getFormatFloat(chunk.getWidthPoint());//整个块的实际长度
        float absWidth = widthPoint%englishSize;//该块满一行之后剩余的长度
   //     Log.i("PdfUtil","===absWidth=="+absWidth);
        if(absWidth == 0.0f){
            return chunk;
        }

        float fillSize = englishSize - absWidth;//该块再补充多少就能满足一行
        int strNum = (int) (fillSize / fontSizeHalf);
        float shouldSize = getFormatFloat((float) (strNum * fontSizeHalf + absWidth));
//        Log.i("PdfUtil", "===shouldSize==" + shouldSize + "===getUnderlineChunk===widthPoint===" + widthPoint + "========fillSize=="
//                + fillSize + "====strNum==" + strNum + "=====strNum * 7.5 + absWidth===" + (strNum * fontSizeHalf + absWidth)
//                + "=====(widthPoint - absWidth)==" + (widthPoint - absWidth));
        if(shouldSize > englishSize){
            while(shouldSize > englishSize){
                strNum --;
                shouldSize = (float)(strNum * fontSizeHalf+absWidth);
         //       Log.i("PdfUtil","=if===while=="+strNum+"===shouldSize==="+shouldSize);
            }
        }else{
            while(shouldSize > englishSize){
                strNum ++;
                shouldSize = (float)(strNum * fontSizeHalf+absWidth);
      //          Log.i("PdfUtil","=else===while=="+strNum+"===shouldSize==="+shouldSize);
            }
        }
        String suitString = getNumString(strNum);
        chunk.append(suitString);
        return chunk;
    }





    /**
     * 将一个字符串，分割成多个带下划线的chunk，每个chunk的内容长度都满足document的宽度，不足的chunk用下划线填充
     * 即形如“____________”
     * @param document
     * @param value
     * @param font
     * @return
     */
    public static List<Chunk> textLineChunks(Document document, String value, Font font){
        if(TextUtils.isEmpty(value)){
            return null ;
        }
        value = replaceNR(value);
        float englishSize = getEnglishSize(document, font);
        float fontSize = font.getCalculatedSize();

        List<Chunk> chunkList = new ArrayList<Chunk>();
        char[] chars = value.toCharArray();
        StringBuffer bufferTemp = new StringBuffer();
        for(int i=0;i<chars.length;i++){
            char aChar = chars[i];
            bufferTemp.append(aChar);
            float width = getStringWidth(bufferTemp.toString(), font);

            float abs = getFormatFloat(Math.abs(englishSize - width));
     //       Log.i("PdfUtil", "==width==" + width+"===abs==="+abs);
            if(abs == fontSize/2){

       //         Log.i("PdfUtil", "==else=======before===" + bufferTemp.toString() + "===length==" + bufferTemp.length());
                bufferTemp.append(" ");
                chunkList.add(new Chunk(bufferTemp.toString(),font));
                bufferTemp.delete(0, bufferTemp.length());
            }else if(abs == 0.0f){
     //           Log.i("PdfUtil", "=if=======before===" + bufferTemp.toString() + "===length==" + bufferTemp.length());
                chunkList.add(new Chunk(bufferTemp.toString(),font));
                bufferTemp.delete(0, bufferTemp.length());
            }
        }

        if(bufferTemp.length() >0){
            float width = getStringWidth(bufferTemp.toString(), font);
            float abs = getFormatFloat(Math.abs(englishSize - width));
    //        Log.i("PdfUtil", "==bufferTemp.length() >0=====abs==" + abs+"===length=="+bufferTemp.length());
            while(abs != 0.0f){
                bufferTemp.append(" ");
                width = getStringWidth(bufferTemp.toString(), font);
                abs = getFormatFloat(Math.abs(englishSize - width));
    //            Log.i("PdfUtil", "==while=====abs==" + abs);
            }
            chunkList.add(new Chunk(bufferTemp.toString(), font));
        }
  //      Log.i("PdfUtil","=====end=================================================================");
        return chunkList;

    }


    public static Element textChunk(Document document,Font font,String value){
        if(TextUtils.isEmpty(value)){
            return null ;
        }
        value = replaceNR(value);
        float englishSize = getEnglishSize(document, font);
        float fontSize = font.getCalculatedSize();

        List<Element> chunkList = new ArrayList<Element>();
        char[] chars = value.toCharArray();
        StringBuffer bufferTemp = new StringBuffer();
        for(int i=0;i<chars.length;i++){
            bufferTemp.append(chars[i]);
            float width = getStringWidth(bufferTemp.toString(), font);
            float abs = getFormatFloat(Math.abs(englishSize - width));
            //       Log.i("PdfUtil", "==width==" + width+"===abs==="+abs);
            if(abs == fontSize/2){

                //         Log.i("PdfUtil", "==else=======before===" + bufferTemp.toString() + "===length==" + bufferTemp.length());
                bufferTemp.append(" ");
                chunkList.add(new Chunk(bufferTemp.toString()));
                bufferTemp.delete(0, bufferTemp.length());
            }else if(abs == 0.0f){
                //           Log.i("PdfUtil", "=if=======before===" + bufferTemp.toString() + "===length==" + bufferTemp.length());
                chunkList.add(new Chunk(bufferTemp.toString()));
                bufferTemp.delete(0, bufferTemp.length());
            }
        }

        if(bufferTemp.length() >0){
            chunkList.add(new Chunk(bufferTemp.toString()));
        }

        Element element = groupChunkToPhrase(new Phrase(), chunkList);
        return element;
    }

    public static List<Element> textLineIrregularChunk(Document document,Font font,String value){
        if(TextUtils.isEmpty(value)){
            return null ;
        }
        value = replaceNR(value);
        float englishSize = getEnglishSize(document, font);
        float fontSize = font.getCalculatedSize();

        List<Element> chunkList = new ArrayList<Element>();
        char[] chars = value.toCharArray();
        StringBuffer bufferTemp = new StringBuffer();
        for(int i=0;i<chars.length;i++){
            bufferTemp.append(chars[i]);
            float width = getStringWidth(bufferTemp.toString(), font);
            float abs = getFormatFloat(Math.abs(englishSize - width));
            //       Log.i("PdfUtil", "==width==" + width+"===abs==="+abs);
            if(abs == fontSize/2){

                //         Log.i("PdfUtil", "==else=======before===" + bufferTemp.toString() + "===length==" + bufferTemp.length());
                bufferTemp.append(" ");
                chunkList.add(new Chunk(bufferTemp.toString(),font));
                bufferTemp.delete(0, bufferTemp.length());
            }else if(abs == 0.0f){
                //           Log.i("PdfUtil", "=if=======before===" + bufferTemp.toString() + "===length==" + bufferTemp.length());
                chunkList.add(new Chunk(bufferTemp.toString(),font));
                bufferTemp.delete(0, bufferTemp.length());
            }
        }

        if(bufferTemp.length() >0){
            chunkList.add(new Chunk(bufferTemp.toString(),font));
        }

        return chunkList;
    }

    /**
     * 将一个字符串中的换行符，回车符替换掉
     * @param value
     * @return
     */
    private static String replaceNR(String value) {
        value = value.replace("\n","");//替换换行符
        value = value.replace("\r","");//替换回车符
        return value;
    }


    public static List<Element> textChunk_Chunk(Document document, Font font, String mainStr, String secondStr,int strNum) {


        List<Element> chunks = new ArrayList<>();
        secondStr = getSuitString(secondStr,strNum);
        secondStr = replaceNR(secondStr);
        float englishSize = getEnglishSize(document, font);
        float mainStrWidth = getStringWidth(mainStr, font);
        float secondStrWidth = getStringWidth(secondStr, font);

        float minusStrWidth = Math.abs(englishSize - mainStrWidth);
        Log.i("PdfUtil", "==========minusStrWidth===" + minusStrWidth + "===mainStrWidth==" + mainStrWidth);
        float secondAbs = minusStrWidth - secondStrWidth;
        StringBuffer secondBuffer = null;
        List<Element> chunkS = null;

        if(secondAbs >= 0){
            secondBuffer = new StringBuffer(secondStr);
        }else{

            chunkS = new ArrayList<>();
            String subStr = substringChunkStr(font, secondStr, minusStrWidth);
            Log.i("PdfUtil", minusStrWidth+"===secondBuffer.toString()===678===" + subStr+"===secondStr==="+secondStr);
            chunkS.add(new Chunk(subStr, font));
            String substring = secondStr.substring(subStr.length()-1, secondStr.length());
            secondBuffer = new StringBuffer(substring);
            chunkS.addAll(textLineIrregularChunk(document, font, substring));
        }


        Log.i("PdfUtil", "===secondBuffer.toString()===678===" + secondBuffer.toString());

        chunks.add(textChunk(document,font,mainStr));
        if(chunkS != null && chunkS.size()>0){
            Phrase elements = new Phrase();
            for(Element chunk:chunkS){
                elements.add(chunk);
                elements.add(Chunk.NEWLINE);
            }

            elements.remove(elements.size()-1);
            chunks.add(elements);
        }else{
            chunks.add(new Chunk(secondBuffer.toString(),font));
        }

        return chunks;
    }

    /**
     * 获取如下形式的2个chunk组合
     * “询问人______”
     * @param document
     * @param font
     * @param mainStr
     * @param secondStr
     * @return
     */
    public static List<Element> textChunk_lineChunk(Document document, Font font, String mainStr, String secondStr) {

        List<Element> chunks = new ArrayList<>();
        secondStr = " "+secondStr+" ";
        secondStr = replaceNR(secondStr);
        float englishSize = getEnglishSize(document, font);
        float mainStrWidth = getStringWidth(mainStr, font);
        float secondStrWidth = getStringWidth(secondStr, font);

        float minusStrWidth = Math.abs(englishSize - mainStrWidth);
        Log.i("PdfUtil", "==========minusStrWidth===" + minusStrWidth + "===mainStrWidth==" + mainStrWidth);
        float secondAbs = minusStrWidth - secondStrWidth;
        StringBuffer secondBuffer = null;
        List<Chunk> chunkS = null;

        if(secondAbs >= 0){
            secondBuffer = new StringBuffer(secondStr);
            float abs = getFormatFloat(Math.abs(minusStrWidth - secondStrWidth));


            while(abs != 0.0){
                secondBuffer.append(" ");
                float bufferWidth = getStringWidth(secondBuffer.toString(), font);
                abs = getFormatFloat(Math.abs(minusStrWidth - bufferWidth));
            }
        }else{

            chunkS = new ArrayList<>();
            String subStr = substringChunkStr(font, secondStr, minusStrWidth);
            Log.i("PdfUtil", minusStrWidth+"===secondBuffer.toString()===678===" + subStr+"===secondStr==="+secondStr);
            chunkS.add(new Chunk(subStr, font));
            String substring = secondStr.substring(subStr.length(), secondStr.length());
            secondBuffer = new StringBuffer(substring);
            chunkS.addAll(textLineChunks(document, substring, font));
        }


        Log.i("PdfUtil", "===secondBuffer.toString()===678===" + secondBuffer.toString());

        chunks.add(textChunk(document,font,mainStr));
        if(chunkS != null && chunkS.size()>0){
            Phrase elements = new Phrase();
            for(Chunk chunk:chunkS){
                elements.add(chunk);
                elements.add(Chunk.NEWLINE);
            }

            elements.remove(elements.size()-1);
            chunks.add(elements);
        }else{
            chunks.add(new Chunk(secondBuffer.toString(),font));
        }

        return chunks;
    }

    /**
     * 获取如下形式的2个chunk组合
     * “询问人______”
     * @param document
     * @param font
     * @param mainStr
     * @param secondStr
     * @param leftWidth
     * @return
     */
    public static List<Element> textChunk_lineChunk(Document document, Font font, String mainStr, String secondStr, float leftWidth) {

        List<Element> chunks = new ArrayList<>();
        secondStr = " "+secondStr+" ";
        secondStr = replaceNR(secondStr);
        float englishSize = getEnglishSize(document, font);
        float mainStrWidth = getStringWidth(mainStr, font);
        float secondStrWidth = getStringWidth(secondStr, font);

        float minusStrWidth = Math.abs(englishSize - leftWidth);
        Log.i("PdfUtil", (englishSize  - leftWidth)+"==========minusStrWidth===" + minusStrWidth + "===mainStrWidth==" + mainStrWidth+"=====leftWidth===="+leftWidth);
        float secondAbs = minusStrWidth - secondStrWidth;
        StringBuffer secondBuffer = null;
        List<Chunk> chunkS = null;

        if(secondAbs >= 0){
            secondBuffer = new StringBuffer(secondStr);
            float abs = getFormatFloat(Math.abs(minusStrWidth - secondStrWidth));

            Log.i("PdfUtil", "===secondAbs >= 0==abs===="+abs);
            while(abs != 0.0){
                secondBuffer.append(" ");
                float bufferWidth = getStringWidth(secondBuffer.toString(), font);
                abs = getFormatFloat(Math.abs(minusStrWidth - bufferWidth));
                Log.i("PdfUtil", abs+"===bufferWidth===" + bufferWidth+"===secondStr==="+secondBuffer.toString()+"===");
            }
        }else{

            chunkS = new ArrayList<>();
            String subStr = substringChunkStr(font, secondStr, minusStrWidth);
            Log.i("PdfUtil", minusStrWidth+"===secondBuffer.toString()===678===" + subStr+"===secondStr==="+secondStr);
            chunkS.add(new Chunk(subStr, font));
            String substring = secondStr.substring(subStr.length(), secondStr.length());
            secondBuffer = new StringBuffer(substring);
            chunkS.addAll(textLineChunks(document, substring, font));
        }


        Log.i("PdfUtil", "===secondBuffer.toString()===end===" + secondBuffer.toString() + "====");


        chunks.add(textChunk(document, font, mainStr));
        if(chunkS != null && chunkS.size()>0){
            Phrase elements = new Phrase();
            for(Chunk chunk:chunkS){
                elements.add(chunk);
                elements.add(Chunk.NEWLINE);
            }

            elements.remove(elements.size()-1);
            chunks.add(elements);
        }else{
            chunks.add(new Chunk(secondBuffer.toString(),font));
        }

        return chunks;
    }


    private static String substringChunkStr(Font font,String value,float length){
        if(getStringWidth(value, font) <= length){
            return value;
        }
        char[] chars = value.toCharArray();
        float fontSize = font.getCalculatedSize();
      //  length = checkMultiple(fontSize/2,length);
        Log.i("PdfUtil", "=====substringChunkStr==substringChunkStr===" + length);

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < chars.length;i++){
            buffer.append(chars[i]);
            float stringWidth = getStringWidth(buffer.toString(), font);
            float abs = getFormatFloat(Math.abs(length - stringWidth));
            Log.i("PdfUtil", abs+"=====substringChunkStr==substringChunkStr===" + stringWidth);
            if(abs == fontSize/2 ){
                buffer.append(" ");
                Log.i("PdfUtil", "==substring==if===" );
                break;
            }else if(abs == 0.0f){
                Log.i("PdfUtil", "===substring==else===" );
                break;
            }


        }
        Log.i("PdfUtil", "=====substringChunkStr==buffer.toString()===" + buffer.toString());
        return buffer.toString();
    }
    private static String substringChunkLeftStr(Font font,String value,float length){
        if(getStringWidth(value, font) <= length){
            return value;
        }


         char[] chars = value.toCharArray();
         float fontSize = font.getCalculatedSize();
         //  length = checkMultiple(fontSize/2,length);
         Log.i("PdfUtil", "=====substringChunkStr==substringChunkStr===" + length);

         StringBuffer buffer = new StringBuffer();
         int startIndex = 0;
         for (int i = 0; i < chars.length;i++){
             buffer.append(chars[i]);
             float stringWidth = getStringWidth(buffer.toString(), font);
             float abs = getFormatFloat(Math.abs(length - stringWidth));
             if(abs == fontSize/2 ){
                 break;
             }else if(abs == 0.0f){
                 break;
             }


         }
         String valueSub = value.substring(buffer.toString().length(), value.length());
         return valueSub;
    }


    /**
     * 将多个形如“询问人______”形式的chunk组合在一起
     * 即组合后“询问人______询问人______询问人______”
     * @param document
     * @param font
     * @param chunkList
     * @return
     */
    public static List<Element> textChunks_lineChunks(Document document, Font font, List<BasicChunk> chunkList){
        if(chunkList == null || chunkList.size() <= 0){
        }

        float englishSize = getEnglishSize(document, font);

        List<Element> elements = new ArrayList<>();
        float togetherWidth = 0;
        StringBuffer buffer = new StringBuffer();
        for(int i=0;i<chunkList.size();i++){
            BasicChunk basicChunk = chunkList.get(i);
            Phrase phrase = null;
            if(i == chunkList.size()-1){

                float reasonableMod = 0 ;
                if(buffer.toString().length() > 0){
                    reasonableMod = getReasonableMod(document, font, buffer.toString());
                }

                Log.i("PdfUtil", "=====phrase.getContent()====reasonableMod===="+reasonableMod);
                float v = //getReasonableMod(document,font,phrase.getContent());
                       // togetherWidth % englishSize;
                        reasonableMod;
                float keyWidth = getStringWidth(basicChunk.getChunkKey(), font);
                float absWidth = englishSize - v - keyWidth;

                float leftLength = 0;
                if(absWidth > 0){
                    leftLength = v + keyWidth;
                }else if(absWidth == 0){
                   leftLength = 0;
                }else {
                    float v1 = (englishSize - v);
                    String s = substringChunkLeftStr(font, basicChunk.getChunkKey(), v1);
                    leftLength= getStringWidth(s, font);
                    Log.i("PdfUtil", "=====phrase.getContent()====v1===="+v1+"====s=="+s);
                }





                Log.i("PdfUtil", "=====phrase.getContent()==keyWidth===" + keyWidth+"===v==="+v+"===leftLength=="+leftLength);
                List<Element> chunks = textChunk_lineChunk(document, font, basicChunk.getChunkKey(), basicChunk.getChunkValue(), leftLength);
                elements.addAll(chunks);
            }else{
                List<Element> elements1 = textChunk_Chunk(document, font, basicChunk.getChunkKey(), basicChunk.getChunkValue(), basicChunk.getRatio());
                phrase = groupChunkToPhrase(new Phrase(), elements1);
                togetherWidth += getStringWidth(phrase.getContent(), font);
                buffer.append(basicChunk.getChunkKey()+getSuitString(basicChunk.getChunkValue(), basicChunk.getRatio()));
//                buffer.append(phrase.getContent());
                Log.i("PdfUtil", "=====phrase.getContent()===" + phrase.getContent() + "==togetherWidth==="+togetherWidth);
                elements.add(phrase);
            }

        }



        return elements;
    }

    /**
     * 对一个字符串合理的取余后获取长度
     * @param document
     * @param font
     * @param value
     * @return
     */
    private static float getReasonableMod(Document document,Font font,String value){
        Log.i("PdfUtil", "====getReasonableMod==="+value+"====s==");
        if(TextUtils.isEmpty(value)){
            return 0 ;
        }

        value = replaceNR(value);
        value.lastIndexOf("擦啊");
        float englishSize = getEnglishSize(document, font);
        float fontSize = font.getCalculatedSize();

        List<Chunk> chunkList = new ArrayList<Chunk>();
        char[] chars = value.toCharArray();
        StringBuffer bufferTemp = new StringBuffer();
        for(int i=0;i<chars.length;i++){

            char aChar = chars[i];
            bufferTemp.append(aChar);
            float width = getStringWidth(bufferTemp.toString(), font);
            float abs = getFormatFloat(Math.abs(englishSize - width));
            Log.i("PdfUtil", "====getReasonableMod==width===="+width+"===abs=="+abs);
            if(abs == fontSize/2 ){

                bufferTemp.append(" ");
                Log.i("PdfUtil", "===if=getReasonableMod==width====" + bufferTemp.toString() + "===abs==" + abs);
                bufferTemp.delete(0, bufferTemp.length());
                Log.i("PdfUtil", "===if=getReasonableMod==end====" + bufferTemp.toString() +"==");
            }else if(abs == 0.0f){
                Log.i("PdfUtil", "===else=getReasonableMod==width====" + bufferTemp.toString() + "===abs==" + abs);
                bufferTemp.delete(0, bufferTemp.length());
                Log.i("PdfUtil", "===else=getReasonableMod==end====" + bufferTemp.toString() +"==");
            }

        }

        if(bufferTemp.length() >0){
            return getStringWidth(bufferTemp.toString(), font);

        }

        return 0;
    }

    /**
     * 判断srcFloat是否是multiFloat倍数，如果不是，就增加srcFloat使之是multiFloat的倍数
     * @param multiFloat
     * @param srcFloat
     * @return
     */
    private static float checkMultiple(float multiFloat,float srcFloat){
        float v = srcFloat % multiFloat;
        Log.i("PdfUtil", "=====checkMultiple==="+v);
        while (v != 0.0){
            srcFloat += multiFloat -v;
            v = srcFloat % multiFloat;
        }
        return srcFloat;
    }

    private static int getMultiple(float srcFloat,float desFloat){
        return (int) Math.ceil(srcFloat / desFloat);
    }


    private static Chunk getChunkStr(String value,Font font){
        Chunk chunk = new Chunk(value, font);
        return chunk;
    }

    /**
     * 获取一个字符串的长度
     * @param s
     * @param font
     * @return
     */
    private static float getStringWidth(String s, Font font) {
        if(font != null){
            float widthPoint = font.getCalculatedBaseFont(true).getWidthPoint(s, font.getCalculatedSize());
            return getFormatFloat(widthPoint);
        }
        return -1;
    }


    /**
     * 获取小数点一位的float例如123.58
     * @param decimal
     * @return
     */
    public static float getFormatFloat(float decimal){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);

        float parseFloat = Float.parseFloat(decimalFormat.format(decimal));
        return parseFloat;
    }
    /**
     * 获取小数点一位的float例如123.58
     * @param decimal
     * @return
     */
    public static String getFormat00Float(int decimal){
        DecimalFormat decimalFormat = new DecimalFormat("00");
        return decimalFormat.format(decimal);
    }


    /**
     * 去除小数点，不用四舍五入
     * @param decimal
     * @return
     */
    public static int getFormatInteger(float decimal){
        DecimalFormat decimalFormat = new DecimalFormat("0");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);

        Integer parseInt = Integer.parseInt(decimalFormat.format(decimal));
        return parseInt;
    }


    /**
     * 获取一个Document中剩余多少空间可以填充文字和字符
     * 即可用长度
     * 测试可知，一个数字，字母或者英文字符大小是font字体大小的一半
     * 一个中文或者中文字符大小是是font字体大小
     * @param document
     *  @param font
     * @return
     */
    private static float getEnglishSize(Document document, Font font) {
        if(document != null && font != null){
            float fontSize = font.getCalculatedSize()/2;
            float pageWidth = document.getPageSize().getWidth();
            float marginLeftRight = document.leftMargin()+document.rightMargin();
            float size = getFormatFloat(((int) ((pageWidth - marginLeftRight) / fontSize)) * fontSize);
            Log.i("PdfUtil", "=====getEnglishSize==="+size);
            return size;
        }else{
            return -1;
        }


    }


    /**
     * 获取一个Document中剩余多少空间可以填充文字和字符
     * 即可用长度
     * 测试可知，一个数字，字母或者英文字符大小是font字体大小的一半
     * 一个中文或者中文字符大小是是font字体大小
     * @param document
     *  @param font
     * @return
     */
    private static float getChineseSize(Document document, Font font) {
        if(document != null && font != null){
            float fontSize = font.getCalculatedSize();
            float pageWidth = document.getPageSize().getWidth();
            float marginLeftRight = document.leftMargin()+document.rightMargin();
            float size = getFormatFloat(((int) ((pageWidth - marginLeftRight) / fontSize)) * fontSize);
            Log.i("PdfUtil", "=====getChineseSize==="+size);
            return size;
        }else{
            return -1;
        }


    }



}
