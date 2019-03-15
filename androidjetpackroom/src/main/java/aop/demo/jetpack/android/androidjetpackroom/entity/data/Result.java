package aop.demo.jetpack.android.androidjetpackroom.entity.data;

public class Result<T> {

    private int errorCode;

    private String errorMsg;

    private T data;



    public T getData() {

        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {

        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {


        return errorMsg == null ? "" : errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
