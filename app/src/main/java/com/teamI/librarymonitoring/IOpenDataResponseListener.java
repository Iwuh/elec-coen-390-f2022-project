package com.teamI.librarymonitoring;

// interacts with OpenDataApiHelper
// use this to inform the parent activity when OpenDataApiHelper has retrieved the needed data
// (or exited in error)
// for more information: https://stackoverflow.com/questions/33535435/how-to-create-a-proper-volley-listener-for-cross-class-volley-method-calling
// or search Listener design pattern


public interface IOpenDataResponseListener {
    void onError(String message);
    void onResponse();
}
