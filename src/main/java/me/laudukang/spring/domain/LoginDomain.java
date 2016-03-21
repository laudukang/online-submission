package me.laudukang.spring.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/21
 * <p>Time: 13:22
 * <p>Version: 1.0
 */
public class LoginDomain {
    @NotEmpty
    @Email
    private String account;
    @NotEmpty
    private String password;
    //@NotEmpty(message = "验证码不能为空")
    private String verificationCode;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
