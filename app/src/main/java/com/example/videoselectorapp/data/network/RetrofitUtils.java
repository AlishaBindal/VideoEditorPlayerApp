package com.example.videoselectorapp.data.network;

import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Retrofit utility class
 */
public final class RetrofitUtils {

    private static final String TAG = RetrofitUtils.class.getSimpleName();

    /**
     * Prevent instantiation
     */
    private RetrofitUtils() {
    }

    /**
     * Forms request body from string
     *
     * @param value content which need to convert into request body
     * @return formed request body object
     */
    static RequestBody getRequestBodyFromString(final String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }


    /**
     * Forms a part body from file
     *
     * @param key  parameter name
     * @param file the file to include as part of request body
     * @return formed part body
     */
    public static MultipartBody.Part getPartBodyFromFile(final String key, final File file) {

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse(getMimeType(file)), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(key, file.getName(), requestFile);

    }


    /**
     * Get the mime type
     *
     * @param file file for which mime type is required
     * @return the mimeType of the passed file
     */
    static String getMimeType(final File file) {
        String mimeType = "image/png";
        try {
            Uri selectedUri = Uri.fromFile(file);
            String fileExtension
                    = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
            mimeType
                    = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
        } catch (Exception e) {
            Log.e(TAG, e.toString());

        }
        return mimeType;
    }

    /**
     * Gets header map.
     *
     * @return the header map
     */
    public static HashMap<String, String> getHeaderMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Authorization", "bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJQUk9GSUxFOjQiLCJpYXQiOjE2MTQ1ODY3ODUsImV4cCI6MTYxNjU4Njc4NX0.YoJQRpUiGTwdpyu_RhQuMeI2HieGdVkWsx7_jM00POoTeYHksq0-lDzODKyuMVfkmiBqKh5eDs4W6VEFmUAsAQ");
        return hashMap;
    }
}