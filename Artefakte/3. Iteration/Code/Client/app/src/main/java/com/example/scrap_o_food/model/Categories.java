package com.example.scrap_o_food.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Categories implements Serializable {

    @SerializedName("categories")
    @Expose
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public static class Category implements Serializable {

        @SerializedName("idCategory")
        @Expose
        private String idCategory;
        @SerializedName("strCategory")
        @Expose
        private String strCategory;
        @SerializedName("strCategoryThumb")
        @Expose
        private String strCategoryThumb;
        @SerializedName("strCategoryDescription")
        @Expose
        private String strCategoryDescription;

        @SerializedName("ValueStorageOpen")
        @Expose
        private String ValueStorageOpen;

        @SerializedName("ValueStorageFridge")
        @Expose
        private String ValueStorageFridge;

        @SerializedName("ValueStorageFreezer")
        @Expose
        private String ValueStorageFreezer;

        @SerializedName("ValueStorageProtected")
        @Expose
        private String ValueStorageProtected;

        @SerializedName("ValueConditionOpen")
        @Expose
        private String ValueConditionOpen;

        @SerializedName("ValueConditionClosed")
        @Expose
        private String ValueConditionClosed;

        @SerializedName("ValueConditionFresh")
        @Expose
        private String ValueConditionFresh;

        public String getValueStorageOpen() {
            return ValueStorageOpen;
        }

        public void setValueStorageOpen(String valueStorageOpen) {
            ValueStorageOpen = valueStorageOpen;
        }

        public String getValueStorageFridge() {
            return ValueStorageFridge;
        }

        public void setValueStorageFridge(String valueStorageFridge) {
            ValueStorageFridge = valueStorageFridge;
        }

        public String getValueStorageFreezer() {
            return ValueStorageFreezer;
        }

        public void setValueStorageFreezer(String valueStorageFreezer) {
            ValueStorageFreezer = valueStorageFreezer;
        }

        public String getValueStorageProtected() {
            return ValueStorageProtected;
        }

        public void setValueStorageProtected(String valueStorageProtected) {
            ValueStorageProtected = valueStorageProtected;
        }

        public String getValueConditionOpen() {
            return ValueConditionOpen;
        }

        public void setValueConditionOpen(String valueConditionOpen) {
            ValueConditionOpen = valueConditionOpen;
        }

        public String getValueConditionClosed() {
            return ValueConditionClosed;
        }

        public void setValueConditionClosed(String valueConditionClosed) {
            ValueConditionClosed = valueConditionClosed;
        }

        public String getValueConditionFresh() {
            return ValueConditionFresh;
        }

        public void setValueConditionFresh(String valueConditionFresh) {
            ValueConditionFresh = valueConditionFresh;
        }

        public String getValueConditionFrozen() {
            return ValueConditionFrozen;
        }

        public void setValueConditionFrozen(String valueConditionFrozen) {
            ValueConditionFrozen = valueConditionFrozen;
        }

        @SerializedName("ValueConditionFrozen")
        @Expose
        private String ValueConditionFrozen;

        public String getIdCategory() {
            return idCategory;
        }

        public void setIdCategory(String idCategory) {
            this.idCategory = idCategory;
        }

        public String getStrCategory() {
            return strCategory;
        }

        public void setStrCategory(String strCategory) {
            this.strCategory = strCategory;
        }

        public String getStrCategoryThumb() {
            return strCategoryThumb;
        }

        public void setStrCategoryThumb(String strCategoryThumb) {
            this.strCategoryThumb = strCategoryThumb;
        }

        public String getStrCategoryDescription() {
            return strCategoryDescription;
        }

        public void setStrCategoryDescription(String strCategoryDescription) {
            this.strCategoryDescription = strCategoryDescription;
        }

    }

}
