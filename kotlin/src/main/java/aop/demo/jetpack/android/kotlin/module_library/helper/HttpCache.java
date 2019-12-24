package aop.demo.jetpack.android.kotlin.module_library.helper;

import java.io.File;

import aop.demo.jetpack.android.kotlin.module_library.util.AppUtils;
import okhttp3.Cache;

public class HttpCache {

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024;

    public static Cache getCache() {
        return new Cache(new File(AppUtils.INSTANCE.getContext().getExternalCacheDir().getAbsolutePath() + File
                .separator + "data/NetCache"),
                HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}