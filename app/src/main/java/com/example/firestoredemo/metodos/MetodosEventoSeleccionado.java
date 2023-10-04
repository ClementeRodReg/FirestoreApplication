package com.example.firestoredemo.metodos;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.TextView;

public class MetodosEventoSeleccionado {
    public static void limitadorDeEscritura2(TextView editText) {
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(2);
        editText.setFilters(filters);
    }

    public static void limitadorDeEscritura4(TextView editText) {
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(4);
        editText.setFilters(filters);
    }
}
