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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.paul.tagimage.R;

import static android.app.Activity.RESULT_OK;

public class PostLoadFragment extends Fragment {

    public static final String TAG = "PostLoadFragment";

    private static final int GALLERY_REQUEST = 1;

    @BindView(R.id.load_button)
    Button button;
    @BindView(R.id.load_image)
    AppCompatImageView imageView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        button.setOnClickListener(view -> {

            Intent loadImage = new Intent(Intent.ACTION_PICK);
            loadImage.setType("image/*");
            startActivityForResult(loadImage, GALLERY_REQUEST);

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

    private void logMemory() {
        Log.i("log", String.format("Total memory = %s",
                (int) (Runtime.getRuntime().totalMemory() / 1024)));
    }

    private void readImage(Uri image) throws FileNotFoundException {
        int px = getResources().getDimensionPixelSize(R.dimen.image_size);

        InputStream ims = getContext().getContentResolver().openInputStream(image);
//        File file = new File(getPath(image));
        Bitmap bitmap = decodeSampledBitmapFromResource(ims, px, px);
//        Log.d("log", String.format("Required size = %s, bitmap size = %sx%s, byteCount = %s",
//                px, bitmap.getWidth(), bitmap.getHeight(), bitmap.getByteCount()));
        imageView.setImageBitmap(bitmap);
    }

    private Bitmap decodeSampledBitmapFromResource(InputStream is,
                                                   int reqWidth, int reqHeight) {

        // Читаем с inJustDecodeBounds=true для определения размеров
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is,null,  options);

        // Вычисляем inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Читаем с использованием inSampleSize коэффициента
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, options);
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

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }
}