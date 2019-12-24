package aop.demo.jetpack.android.myapplication.viewmodel;

import java.util.List;

public class InterceptData {


    /**
     * code : 1
     * data : ["ssohandler.html","order/status.html","/order/confirm.html","http://am.aculearn.com/aculearn-idm/v4/opr/studioclient.asp","http://am.aculearn.com/aculearn-idm/v4/opr/studioclient.asp","http://online.lzr.com/ot/video/player.html?cnum=10170985"]
     */

    private int code;
    private List<String> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public InterceptData(int code, List<String> data) {
        this.code = code;
        this.data = data;
    }
}
