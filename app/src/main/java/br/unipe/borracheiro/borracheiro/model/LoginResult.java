package br.unipe.borracheiro.borracheiro.model;

/**
 * Created by -Vinícius on 04/06/2017.
 */

public class LoginResult {
    private boolean success;
    private String message;
    private String token;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String toString() {
        return message + " - " + token;
    }
}
