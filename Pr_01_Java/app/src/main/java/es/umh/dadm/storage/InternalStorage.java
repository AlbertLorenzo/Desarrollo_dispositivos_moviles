package es.umh.dadm.storage;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

import es.umh.dadm.appcontext.App;
import es.umh.dadm.category.Category;

public class InternalStorage {
    private static final String FILE_NAME = "categorias.txt";
    private final StringBuilder fileData = new StringBuilder();

    public InternalStorage() {
        if (fileExists() && !fileIsEmpty()) {
            loadData();
        }
    }

    /**
     * Lee línea por línea un fichero y lo añade a un string builder.
     */
    private void loadData() {
        try {
            BufferedReader fin = new BufferedReader(new InputStreamReader(App.getContext().openFileInput(FILE_NAME)));
            String line;
            while ((line = fin.readLine()) != null) {
                fileData.append(line).append("\n");
            }
            fin.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Añade una nueva línea al final del archivo.
     * Si este fichero no existe, se crea, si existe sólo la aplicación podrá leerlo.
     * @param data objeto categoría en formato string,
     */
    public void appendLine(String data) {
        try {
            OutputStreamWriter fout = new OutputStreamWriter(App.getContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE | Context.MODE_APPEND));
            fout.append(data).append("\n");
            fout.flush();
            fout.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Como el borrado y/o actualizado de este fichero se hace de forma manual, al cambiarlo, se vuelca el arraylist entero de nuevo
     * borrando el fichero antiguo y creando uno con el arraylist modificado.
     *
     * @param data arraylist de categorías en un string.
     */
    public void writeNewData(String data) {
        try {
            OutputStreamWriter fout = new OutputStreamWriter(App.getContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            fout.write(data);
            fout.flush();
            fout.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean fileIsEmpty() {
        File file = App.getContext().getFileStreamPath(FILE_NAME);
        return file.length() == 0;
    }

    private boolean fileExists() {
        File file = App.getContext().getFileStreamPath(FILE_NAME);
        return file.exists();
    }

    /**
     * Convierte un strinbuilder en un array de string cogiendo como delimitador el salto de línea
     * @return devuelve un array de tipo string
     */
    public Category[] filDataAsArray() {
        Gson gson = new Gson();
        return gson.fromJson(Arrays.toString(this.fileData.toString().split("\\n")), Category[].class);
    }

    public ArrayList<Category> fileDataAsArrayList() {
        return new ArrayList<>(
                Arrays.asList(filDataAsArray())
        );
    }
}
