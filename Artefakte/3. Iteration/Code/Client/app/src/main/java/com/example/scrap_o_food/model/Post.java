package com.example.scrap_o_food.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post implements Serializable
{

    @SerializedName("idMeal")
    @Expose
    private String idMeal;
    @SerializedName("idUser")
    @Expose
    private String idUser;
    @SerializedName("idGroup")
    @Expose
    private String idGroup;
    @SerializedName("strMeal")
    @Expose
    private String strMeal;
    @SerializedName("strDescription")
    @Expose
    private String strDescription;
    @SerializedName("strLongitude")
    @Expose
    private String strLongitude;
    @SerializedName("adDate")
    @Expose
    private String adDate;
    @SerializedName("strLatitude")
    @Expose
    private String strLatitude;
    @SerializedName("strMealThumb")
    @Expose
    private String strMealThumb;
    @SerializedName("strCategory")
    @Expose
    private String strCategory;
    @SerializedName("strStoring")
    @Expose
    private String strStoring;
    @SerializedName("strCondition")
    @Expose
    private String strCondition;
    @SerializedName("strMHD")
    @Expose
    private String strMHD;
    @SerializedName("strMail")
    @Expose
    private String strMail;
    @SerializedName("strPhone")
    @Expose
    private String strPhone;

    private final static long serialVersionUID = -1954524627900315202L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Post() {
    }

    public Post(String idMeal, String idUser, String idGroup, String strMeal, String strDescription, String strLongitude, String adDate, String strLatitude, String strMealThumb, String strCategory, String strStoring, String strCondition, String strMHD, String strMail, String strPhone) {
        super();
        this.idMeal = idMeal;
        this.idUser = idUser;
        this.idGroup = idGroup;
        this.strMeal = strMeal;
        this.strDescription = strDescription;
        this.strLongitude = strLongitude;
        this.adDate = adDate;
        this.strLatitude = strLatitude;
        this.strMealThumb = strMealThumb;
        this.strCategory = strCategory;
        this.strStoring = strStoring;
        this.strCondition = strCondition;
        this.strMHD = strMHD;
        this.strMail = strMail;
        this.strPhone = strPhone;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(String idGroup) {
        this.idGroup = idGroup;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrLongitude() {
        return strLongitude;
    }

    public void setStrLongitude(String strLongitude) {
        this.strLongitude = strLongitude;
    }

    public String getAdDate() {
        return adDate;
    }

    public void setAdDate(String adDate) {
        this.adDate = adDate;
    }

    public String getStrLatitude() {
        return strLatitude;
    }

    public void setStrLatitude(String strLatitude) {
        this.strLatitude = strLatitude;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrStoring() {
        return strStoring;
    }

    public void setStrStoring(String strStoring) {
        this.strStoring = strStoring;
    }

    public String getStrCondition() {
        return strCondition;
    }

    public void setStrCondition(String strCondition) {
        this.strCondition = strCondition;
    }

    public String getStrMHD() {
        return strMHD;
    }

    public void setStrMHD(String strMHD) {
        this.strMHD = strMHD;
    }

    public String getStrMail() {
        return strMail;
    }

    public void setStrMail(String strMail) {
        this.strMail = strMail;
    }

    public String getStrPhone() {
        return strPhone;
    }

    public void setStrPhone(String strPhone) {
        this.strPhone = strPhone;
    }

}
