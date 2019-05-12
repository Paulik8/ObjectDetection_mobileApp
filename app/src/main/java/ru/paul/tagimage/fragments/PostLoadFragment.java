package ru.paul.tagimage.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.paul.tagimage.OpenFragmentCallback;
import ru.paul.tagimage.R;
import ru.paul.tagimage.model.ApiResponse;
import ru.paul.tagimage.repository.PostRepository;
import ru.paul.tagimage.service.Service;


public class PostLoadFragment extends Fragment {

    public static final String TAG = "PostLoadFragment";

    private static final int GALLERY_REQUEST = 1;

    @Nullable
    private final OpenFragmentCallback openFragmentCallback;

    @BindView(R.id.load_button)
    Button button;
    @BindView(R.id.send_button)
    Button sendButton;
    @BindView(R.id.load_image)
    AppCompatImageView imageView;
    File savingFile;
    File file2;
    String outMimeType;

    public PostLoadFragment(@Nullable OpenFragmentCallback openFragmentCallback) {
        this.openFragmentCallback = openFragmentCallback;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        button.setOnClickListener(view -> {

            Intent loadImage = new Intent(Intent.ACTION_PICK);
            loadImage.setType("image/*");
            startActivityForResult(loadImage, GALLERY_REQUEST);

        });
        sendButton.setOnClickListener(v -> {
            Service service = PostRepository.getInstance().getService();
            byte[] encoded = ("paulik" + ":" + "kek").getBytes(StandardCharsets.UTF_8);
            String base64 = "Basic " + Base64.encodeToString(encoded, Base64.NO_WRAP);
            Map<String, RequestBody> map = new HashMap<>();
            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse("image/*"),
                            savingFile
                    );
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("photo", savingFile.getName(), requestFile);
//            RequestBody filePart = RequestBody.create(MediaType.parse("image/*"), savingFile);
            RequestBody author = RequestBody.create(MediaType.parse("text/plain"), "paulik");
            RequestBody caption = RequestBody.create(MediaType.parse("text/plain"), "Nature");
            RequestBody date = RequestBody.create(MediaType.parse("text/plain"), getDate());
//            map.put("file\"; filename=\"pp.png\"", filePart);
//            map.put("photo", filePart);
//            map.put("author", author);
//            map.put("caption", caption);
//            map.put("data", date);
            service.loadPost(base64, body, author, caption, date).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    Log.i("load", String.valueOf(response.body()));
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Log.i("load", "err");
                }
            });
        });
        logMemory();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.post_load_fragment, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;

        if (requestCode == GALLERY_REQUEST) {
            Uri selectedImage = data.getData();
            Bitmap bitmapImage = getImage(selectedImage);
            file2 = new File(selectedImage.getPath());
            try {
                savingFile = saveImage(bitmapImage, "im");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                readImage(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//
//            imageView.setImageURI(selectedImage);
            logMemory();
        }

    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
        Date date = new Date();
        return dateFormat.format(date);
    }

    private File saveImage(Bitmap bitmap, String name) throws IOException {
        File f = new File(getActivity().getCacheDir(), name);
        f.createNewFile();

//Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        return f;
        }

    private Bitmap getImage(Uri uri){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream ims = null;
        try {
            ims = getContext().getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BitmapFactory.decodeStream(ims,null,  options);
        try {
            ims.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Вычисляем inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth,
//                reqHeight);

        try {
            ims = getContext().getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Читаем с использованием inSampleSize коэффициента
        options.inJustDecodeBounds = false;
        Bitmap image = BitmapFactory.decodeStream(ims, null, options);
        try {
            ims.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        outMimeType = options.outMimeType;
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.i("size", String.format("Height %s, Width %s, Mime %s", height, width, outMimeType));
        return image;
    }

    private void logMemory() {
        Log.i("log", String.format("Total memory = %s",
                (int) (Runtime.getRuntime().totalMemory() / 1024)));
    }

    private void readImage(Uri image) throws FileNotFoundException {
        int px = getResources().getDimensionPixelSize(R.dimen.image_size);

        InputStream ims = getContext().getContentResolver().openInputStream(image);
        Bitmap bitmap = decodeSampledBitmapFromResource(image);
        imageView.setImageBitmap(bitmap);
    }

    private Bitmap decodeSampledBitmapFromResource(Uri uri) {

        // Читаем с inJustDecodeBounds=true для определения размеров
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream ims = null;
        try {
            ims = getContext().getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BitmapFactory.decodeStream(ims,null,  options);
        try {
            ims.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Вычисляем inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth,
//                reqHeight);

        try {
            ims = getContext().getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Читаем с использованием inSampleSize коэффициента
        options.inJustDecodeBounds = false;
        Bitmap image = BitmapFactory.decodeStream(ims, null, options);
        try {
            ims.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private int calculateInSampleSize(BitmapFactory.Options options,
                                      int reqWidth, int reqHeight) {
        // Реальные размеры изображения
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Вычисляем наибольший inSampleSize, который будет кратным двум
            // и оставит полученные размеры больше, чем требуемые
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
