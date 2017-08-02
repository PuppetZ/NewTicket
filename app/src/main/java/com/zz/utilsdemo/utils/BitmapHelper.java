package com.zz.utilsdemo.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by zhangjing on 2017/8/2.
 * 图像压缩
 */

public class BitmapHelper {
    private static final String TAG = "BitmapHelper";

    /**转bitmap
     * @param url  url
     * @return    bt
     */
    public static Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }
    /**
     * 图片压缩到规定尺寸
     *
     * @param data      文件名
     * @param newWidth  规定的宽
     * @param newHeight 规定的高
     * @return bt
     */
    public static Bitmap createThumbnail(byte[] data, int newWidth, int newHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//对图片进行二次采样，生成缩略图。防止加载过大图片出现内存溢出
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        int oldWidth = options.outWidth; //只获取图片的宽，并没有占用内存
        int oldHeight = options.outHeight;

        int ratioWidth = 0;   //宽的比例
        int ratioHeight = 0;

        if (newWidth != 0 && newHeight == 0) {
            ratioWidth = oldWidth / newWidth;
            options.inSampleSize = ratioWidth;
        } else if (newWidth == 0 && newHeight != 0) {
            ratioHeight = oldHeight / newHeight;
            options.inSampleSize = ratioHeight;
        } else {
            ratioHeight = oldHeight / newHeight;
            ratioWidth = oldWidth / newWidth;
            options.inSampleSize = ratioHeight > ratioWidth ? ratioHeight : ratioWidth;//压缩比例
        }
        options.inPreferredConfig = Bitmap.Config.RGB_565;//占用内存少
        options.inJustDecodeBounds = false;//重新设置为false，否则bt为null
        Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        return bm;
    }

    /**
     * 图片压缩到规定尺寸
     *
     * @param pathName  文件名
     * @param newWidth  规定的宽
     * @param newHeight 规定的高
     * @return bt
     */
    public static Bitmap createThumbnail(String pathName, int newWidth, int newHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        int oldWidth = options.outWidth;
        int oldHeight = options.outHeight;

        int ratioWidth = 0;
        int ratioHeight = 0;

        if (newWidth != 0 && newHeight == 0) {
            ratioWidth = oldWidth / newWidth;
            options.inSampleSize = ratioWidth;
        } else if (newWidth == 0 && newHeight != 0) {
            ratioHeight = oldHeight / newHeight;
            options.inSampleSize = ratioHeight;
        } else {
            ratioHeight = oldHeight / newHeight;
            ratioWidth = oldWidth / newWidth;
            options.inSampleSize = ratioHeight > ratioWidth ? ratioHeight : ratioWidth;
        }
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(pathName, options);
        return bm;
    }

    /**
     * 获取视频文件的典型帧作为封面
     *
     * @param filePath file路径
     * @return bt
     */
    public static Bitmap createVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        } catch (Exception ex) {
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
            }
        }
        return bitmap;
    }

    /**
     * 获取音乐文件中内置的专辑图片
     *
     * @param filePath file路径
     * @return bt
     */
    //
    public static Bitmap createAlbumThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            byte[] art = retriever.getEmbeddedPicture();
            bitmap = BitmapFactory.decodeByteArray(art, 0, art.length);
        } catch (Exception ex) {
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
            }
        }
        return bitmap;
    }


    /**
     * 图片规定尺寸并且不大于200kb
     * @param path        path
     * @param newWidth    规定宽
     * @param newHeight   规定高
     * @return            bt
     */
    public static Bitmap compressByQuality(String path, int newWidth, int newHeight) {
        Bitmap bitmap = createThumbnail(path, newWidth, newHeight);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//流的压缩 100代表不压缩
        int options = 90;
        while (baos.toByteArray().length / 1024 > 200) { //循环判断如果压缩后图片是否大于200kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        byte[] bytes = baos.toByteArray();
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    /**
     * 判断bitmap对象是否为空
     *
     * @param src 源图片
     * @return true: 是  false: 否
     */
    private static boolean isEmptyBitmap(Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }


    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 旋转图片  一般三星手机拍完照上传会自动旋转90°
     *
     * @param angle  角度
     * @param bitmap 图片
     * @return bitma
     */

    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 解决小米手机上获取图片路径为null的情况
     *
     * @param intent 返回的数据 data
     * @return uri
     */
    public Uri geturi(android.content.Intent intent, Context context) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns._ID}, buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }
}
