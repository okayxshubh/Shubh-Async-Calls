//package com.dit.shubh.ShubhNetworkCallKit.network;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.dit.shubh.ShubhNetworkCallKit.econstants.AppTaskTypes;
//import com.dit.shubh.ShubhNetworkCallKit.interfaces.ShubhAllAsyncTasksListener;
//import com.dit.shubh.ShubhNetworkCallKit.model.ShubhUploadObject;
//
//public class ShubhAsyncCalls extends AsyncTask<ShubhUploadObject, Void, ShubhResponseWrapper> {
//
//    ProgressDialog dialog;
//    Context context;
//    ShubhAllAsyncTasksListener shubhAllAsyncTasksListener;
//    AppTaskTypes appTaskTypes;
//
//    public ShubhAsyncCalls(Context context, ShubhAllAsyncTasksListener listener, AppTaskTypes appTaskTypes) {
//        this.context = context;
//        this.shubhAllAsyncTasksListener = listener;
//        this.appTaskTypes = appTaskTypes;
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        dialog = ProgressDialog.show(context, "Loading", "Connecting to Server .. Please Wait", true);
//        dialog.setCancelable(false);
//    }
//
//    @Override
//    protected ShubhResponseWrapper doInBackground(ShubhUploadObject... shubhUploadObjects) {
//        ShubhUploadObject uploadObject = shubhUploadObjects[0];
//        return new ShubhHttpManager().makeNetworkCall(uploadObject);
//    }
//
//    @Override
//    protected void onPostExecute(ShubhResponseWrapper result) {
//        super.onPostExecute(result);
//        try {
//            if (shubhAllAsyncTasksListener != null) {
//                shubhAllAsyncTasksListener.onTaskCompleted(result, appTaskTypes);
//            }
//        } catch (Exception e) {
//            Log.e("AsyncCallback", "Exception: " + e.getMessage());
//        }
//
//        if (dialog != null && dialog.isShowing()) dialog.dismiss();
//    }
//}
