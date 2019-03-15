package aop.demo.jetpack.android.gdemoforlearn;

class User {


    public String firstName;
    public int userId;

    public boolean isloading;
    public User(String name, String firstName, int userId,boolean isloading) {
        this.firstName = firstName;
        this.userId = userId;
        this.isloading = isloading;
    }

    public String getFirstName() {


        return firstName == null ? "" : firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getUserId() {

        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isIsloading() {

        return isloading;
    }

    public void setIsloading(boolean isloading) {
        this.isloading = isloading;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
