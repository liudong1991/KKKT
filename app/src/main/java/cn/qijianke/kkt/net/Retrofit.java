package cn.qijianke.kkt.net;

import android.graphics.Bitmap;
import android.os.Environment;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.qijianke.kkt.KKTApplication;
import cn.qijianke.kkt.common.Constants;
import cn.qijianke.kkt.net.interceptors.AddHeaderInterceptor;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {

    private static kktService kktService;

    static {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new AddHeaderInterceptor())
                .build();

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(Constants.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        kktService = retrofit.create(kktService.class);
    }

    public static kktService getService() {
        return kktService;
    }

    public static <T> RequestBody convert(T t) {
        String json = new Gson().toJson(t);
        return RequestBody.create(MediaType.parse("application/json"), json);
    }

    public static List<MultipartBody.Part> convertImgUpload(String imgPath) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        try {
            File file = new Compressor(KKTApplication.getInstance())
                    .setMaxWidth(720)
                    .setMaxHeight(1080)
                    .setQuality(80)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(new File(imgPath));
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("imageFile", file.getName(), requestFile);
            parts.add(part);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parts;
    }
}
