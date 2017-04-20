package a8a.wwl.com.a8a_android.services;


import java.io.Serializable;

public class BaseResponse implements Serializable {
    public int status_code;
    public String message;


    @Override
    public String toString() {
        return "BaseRespose{" +
                "code='" + status_code + '\'' +
                ", msg='" + message + '\'' +
                '}';
    }

    public boolean isSuccess(){
        if (status_code >= 200 && status_code < 300)
            return true;
        else
            return false;
    }
//    public String getErrorString() {
//        if (code >= 300 || code < 200) {
//            if (TextUtils.isEmpty(message))
//                return "错误！";
//            else
//                return message;
//        }
//
//        if (data == null) {
//            if (TextUtils.isEmpty(message))
//                return "错误！";
//            else
//                return message;
//        }
//
//        return null;
//    }
}

