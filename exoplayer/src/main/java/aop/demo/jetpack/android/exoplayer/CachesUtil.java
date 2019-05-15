package aop.demo.jetpack.android.exoplayer;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class CachesUtil {

     public static String VIDEO = "video";

    private static final String TAG = "CachesUtil";
    /**
     * 获取媒体缓存文件
     *
     * @param child
     * @return
     */
    public static File getMediaCacheFile(String child) {
        String directoryPath = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 外部储存可用
            directoryPath = DemoApplication.getContext().getExternalFilesDir(child).getAbsolutePath();
        } else {
            directoryPath = DemoApplication.getContext().getFilesDir().getAbsolutePath() + File.separator + child;
        }
        File file = new File(directoryPath);
        //判断文件目录是否存在
        if (!file.exists()) {
            file.mkdirs();
        }
        Log.d(TAG, "getMediaCacheFile ====> " + directoryPath);
        return file;
    }
}