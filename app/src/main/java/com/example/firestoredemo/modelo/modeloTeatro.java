package com.example.firestoredemo.modelo;

public class modeloTeatro {
        private int iconResId;
        private String name;

        public modeloTeatro(int iconResId, String name) {
            this.iconResId = iconResId;
            this.name = name;
        }

        public int getIconResId() {
            return iconResId;
        }

        public String getName() {
            return name;
        }
}
