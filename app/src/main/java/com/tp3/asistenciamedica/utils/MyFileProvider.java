package com.tp3.asistenciamedica.utils;

import android.content.Context;
import android.net.Uri;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * Created by fernando on 11/17/17.
 */

public class MyFileProvider extends FileProvider {

    public static final String AUTHORITY = "com.tp3.asistenciamedica.utils.MyFileProvider";

    public static Uri getUriForFile(Context context, File file) {
        return getUriForFile(context, AUTHORITY, file);
    }

}
