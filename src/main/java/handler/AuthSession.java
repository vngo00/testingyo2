package handler;

public class AuthSession {
    private boolean isLoggedIn;

    private String userName;

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public String getUserName() {
        return userName;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
