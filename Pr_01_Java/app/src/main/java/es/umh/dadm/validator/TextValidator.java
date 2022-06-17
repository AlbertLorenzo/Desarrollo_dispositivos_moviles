package es.umh.dadm.validator;

import android.widget.EditText;

import java.util.regex.Pattern;

public abstract class TextValidator {
    private static final Pattern DATE_PATERN = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");

    public static boolean checkDate(EditText etDate) {
        String date = etDate.getText().toString();
        boolean check = true;
        if (!DATE_PATERN.matcher(date).matches()) {
            etDate.setError("El formato de la fecha no es válido 00/00/0000");
            check = false;
        }
        return check;
    }

    public static boolean validate(EditText[] et) {
        boolean check = true;

        for (EditText item : et) {
            if (item.getText().toString().trim().isEmpty()) {
                item.setError("Este campo no puede estar vacío.");
                check = false;
            }
        }
        return check;
    }
}