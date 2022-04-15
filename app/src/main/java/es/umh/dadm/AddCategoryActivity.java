package es.umh.dadm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import es.umh.dadm.category.Category;

public class AddCategoryActivity extends AppCompatActivity {

    private final Context context = this;
    private static final String FILE_NAME = "Categorias.txt";
    private static final String FILE_PATH = "CategoriasDir";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        Button btn_add_category = findViewById(R.id.btn_add_category);

        if (!isExternalStorageAvailable()) {
            btn_add_category.setEnabled(false);
        }
    }

    private String getSerializedData() {
        EditText input_id = findViewById(R.id.input_cat_id);
        EditText input_short_desc = findViewById(R.id.input_cat_short_desc);
        EditText input_long_desc = findViewById(R.id.input_cat_long_desc);
        EditText input_details = findViewById(R.id.input_cat_details);

        Category category = new Category(
                input_id.getText().toString(),
                input_short_desc.getText().toString(),
                input_long_desc.getText().toString(),
                input_details.getText().toString());

        Gson gson = new Gson();
        return gson.toJson(category);
    }

    public void saveExternal(View view) {
        String data = getSerializedData();

        File externalFile = new File(getExternalFilesDir(FILE_PATH), FILE_NAME);
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(externalFile);
            out.write(data.getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void load(View view) {
        BufferedReader fin = null;
        try {
            fin = new BufferedReader(new InputStreamReader(openFileInput(FILE_NAME)));
            String texto = fin.readLine();
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
