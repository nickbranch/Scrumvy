package com.wescrum.scrumvy.dto;

import com.wescrum.scrumvy.validation.FieldMatch;
import com.wescrum.scrumvy.validation.ValidEmail;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "passwordConfirm", message = "The password fields must be the same")
})
public class UserDto {

    @NotNull(message = "required")
    @Size(min = 1, message = "required")
    private String userName;

    @NotNull(message = "required")
    @Size(min = 1, message = "required")
    private String password;

    @NotNull(message = "required")
    @Size(min = 1, message = "required")
    private String passwordConfirm;

    @NotNull(message = "required")
    @Size(min = 1, message = "required")
    private String firstName;

    @NotNull(message = "required")
    @Size(min = 1, message = "required")
    private String lastName;

    @ValidEmail
    @NotNull(message = "required")
    @Size(min = 1, message = "required")
    private String email;

    public UserDto() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
