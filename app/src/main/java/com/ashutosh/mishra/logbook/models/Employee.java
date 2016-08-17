package com.ashutosh.mishra.logbook.models;

/**
 * Created by Ashutosh on 17-08-2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employee {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("role_id")
    @Expose
    private Integer roleId;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("forgot_pass_key")
    @Expose
    private String forgotPassKey;
    @SerializedName("verification_key")
    @Expose
    private String verificationKey;
    @SerializedName("verified")
    @Expose
    private Integer verified;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("terms_condition")
    @Expose
    private Integer termsCondition;
    @SerializedName("deleted")
    @Expose
    private Integer deleted;
    @SerializedName("notification")
    @Expose
    private Integer notification;
    @SerializedName("add_date")
    @Expose
    private String addDate;
    @SerializedName("upd_date")
    @Expose
    private String updDate;
    @SerializedName("acc_active_date")
    @Expose
    private Object accActiveDate;
    @SerializedName("acc_expiration_date")
    @Expose
    private Object accExpirationDate;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The roleId
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     *
     * @param roleId
     * The role_id
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     *
     * @return
     * The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     *
     * @param fullName
     * The full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     *
     * @return
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The forgotPassKey
     */
    public String getForgotPassKey() {
        return forgotPassKey;
    }

    /**
     *
     * @param forgotPassKey
     * The forgot_pass_key
     */
    public void setForgotPassKey(String forgotPassKey) {
        this.forgotPassKey = forgotPassKey;
    }

    /**
     *
     * @return
     * The verificationKey
     */
    public String getVerificationKey() {
        return verificationKey;
    }

    /**
     *
     * @param verificationKey
     * The verification_key
     */
    public void setVerificationKey(String verificationKey) {
        this.verificationKey = verificationKey;
    }

    /**
     *
     * @return
     * The verified
     */
    public Integer getVerified() {
        return verified;
    }

    /**
     *
     * @param verified
     * The verified
     */
    public void setVerified(Integer verified) {
        this.verified = verified;
    }

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The termsCondition
     */
    public Integer getTermsCondition() {
        return termsCondition;
    }

    /**
     *
     * @param termsCondition
     * The terms_condition
     */
    public void setTermsCondition(Integer termsCondition) {
        this.termsCondition = termsCondition;
    }

    /**
     *
     * @return
     * The deleted
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     *
     * @param deleted
     * The deleted
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     *
     * @return
     * The notification
     */
    public Integer getNotification() {
        return notification;
    }

    /**
     *
     * @param notification
     * The notification
     */
    public void setNotification(Integer notification) {
        this.notification = notification;
    }

    /**
     *
     * @return
     * The addDate
     */
    public String getAddDate() {
        return addDate;
    }

    /**
     *
     * @param addDate
     * The add_date
     */
    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    /**
     *
     * @return
     * The updDate
     */
    public String getUpdDate() {
        return updDate;
    }

    /**
     *
     * @param updDate
     * The upd_date
     */
    public void setUpdDate(String updDate) {
        this.updDate = updDate;
    }

    /**
     *
     * @return
     * The accActiveDate
     */
    public Object getAccActiveDate() {
        return accActiveDate;
    }

    /**
     *
     * @param accActiveDate
     * The acc_active_date
     */
    public void setAccActiveDate(Object accActiveDate) {
        this.accActiveDate = accActiveDate;
    }

    /**
     *
     * @return
     * The accExpirationDate
     */
    public Object getAccExpirationDate() {
        return accExpirationDate;
    }

    /**
     *
     * @param accExpirationDate
     * The acc_expiration_date
     */
    public void setAccExpirationDate(Object accExpirationDate) {
        this.accExpirationDate = accExpirationDate;
    }

}