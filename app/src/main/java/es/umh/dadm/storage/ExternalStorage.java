package es.umh.dadm.storage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public abstract class ExternalStorage {

    public static void BitmapToSDCard(Bitmap bmp, Context context, String name) {
        if (!isExternalStorageAvailable()) {
            Log.i("ESE:", "Almacenamiento externo no disponible.");
        } else if (isExternalStorageReadOnly()) {
            Log.i("ESE:", "Almacenamiento externo en modo lectura.");
        } else {
            File saveImage = new File(context.getExternalFilesDir(null), name);
            try {
                OutputStream outputStream = new FileOutputStream(saveImage);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    public static Bitmap loadBitmapFromSDCard(String imageName, Context context) {
        if (isExternalStorageAvailable()) {
            String photoPath = context.getExternalFilesDir(null) + "/" + imageName + ".jpg";
            return BitmapFactory.decodeFile(photoPath);
        } else {
            return null;
        }
    }

    public static void deleteBitmapFromSDCard(String imageName, Context context) {
        String photoPath = context.getExternalFilesDir(null) + "/" + imageName + ".jpg";
        File file = new File(photoPath);
        if (isExternalStorageAvailable() && file.exists()) {
            file.delete();
        }
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }
}
