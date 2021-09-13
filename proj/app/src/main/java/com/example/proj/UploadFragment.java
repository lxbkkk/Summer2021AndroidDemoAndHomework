package com.example.proj;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadFragment extends Fragment {

    private static final int REQUEST_CODE_VIDEO = 102;
    private static final String VIDEO_TYPE = "video/*";
    public IApi api;
    public Uri coverImageUri;
    public Uri videoUri;
    public ImageView coverImage;
    public VideoView video;
    private static final long MAX_FILE_SIZE = 30 * 1024 *1024;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page2, container, false);
        initNetwork();
        coverImage = view.findViewById(R.id.imagecover);
        video = view.findViewById(R.id.videoview);

        return view;
    }

    private void initNetwork() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(IApi.class);
    }

    public void submit(){
        byte[] coverImageData = readDataFromUri(coverImageUri);
        byte[] videoData = readDataFromUri(videoUri);
        if(coverImageData == null || coverImageData.length == 0){
            Toast.makeText(getActivity(), "请选择封面", Toast.LENGTH_SHORT).show();
            return ;
        }
        if(videoData == null || videoData.length == 0){
            Toast.makeText(getActivity(), "请选择视频", Toast.LENGTH_SHORT).show();
            return ;
        }
        if ( coverImageData.length + videoData.length>= MAX_FILE_SIZE) {
            Toast.makeText(getActivity(), "文件过大", Toast.LENGTH_SHORT).show();
            return ;
        }

        MultipartBody.Part coverPart = MultipartBody.Part.createFormData("cover_image",
                coverImageUri.toString()+".png",
                RequestBody.create(MediaType.parse("multipart/form-data"),
                        coverImageData));

        MultipartBody.Part videoPart = MultipartBody.Part.createFormData("video",
                videoUri.toString()+".mp4",
                RequestBody.create(MediaType.parse("multipart/form-data"),
                        videoData));

        Call<UploadResponse> call = api.submitMessage(
                Constants.STUDENT_ID,
                Constants.USER_NAME,
                "",
                coverPart,
                videoPart,
                Constants.token);

        try{
            call.enqueue(new Callback<UploadResponse>() {
                @Override
                public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(getActivity(), "提交失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final UploadResponse finalupload = response.body();
                    if(!finalupload.success){
                        Toast.makeText(getActivity(), "提交失败", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        Toast.makeText(getActivity(), "提交完成", Toast.LENGTH_SHORT).show();
                        coverImageUri = null;
                        coverImage.setImageURI(coverImageUri);
                        coverImage.setVisibility(View.INVISIBLE);
                        videoUri = null;
                        video.setVideoURI(videoUri);
                        video.setVisibility(View.INVISIBLE);
                        return;
                    }
                }
                @Override
                public void onFailure(Call<UploadResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] readDataFromUri(Uri uri) {
        byte[] data = null;
        try {
            InputStream is = getActivity().getContentResolver().openInputStream(uri);
            data = Util.inputStream2bytes(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}