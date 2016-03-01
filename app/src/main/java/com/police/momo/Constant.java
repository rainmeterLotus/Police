package com.police.momo;

import java.io.File;

/**
 * Created by momo on 2015/9/25.
 */
public class Constant {

    /**
     * 判断引导页是否显示，sp
     */
    public final static String VERSION_CODE = "version_code";

    public static final class DIRCONFIG {
        public static String ROOT_PATH = android.os.Environment.getExternalStorageDirectory()
                + File.separator + "police" + File.separator;
        public static String PDF_PATH = ROOT_PATH + File.separator + "pdf" + File.separator;
        public final static String ROOT_NAME = "police";
        public static String CRASH_PATH = "";
        public static String CACHE_PATH = "data/police/cache/";
        public static String DATABASE_PATH = "data/police/database/";
    }

    /**
     * startActivityForResult 请求码和返回码
     */
    public static final class REQUESTCODE {
        public static int EDIT_QUESTION = 1;//编辑问题的返回码
        public static int DELETE_QUESTION = 2;//编辑问题的返回码
    }

    /**
     * 删除SD卡文件夹目录
     */
    public static void deleteFile() {
        File dirFile = new File(DIRCONFIG.ROOT_PATH);
        deleteDir(dirFile);
    }
    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
