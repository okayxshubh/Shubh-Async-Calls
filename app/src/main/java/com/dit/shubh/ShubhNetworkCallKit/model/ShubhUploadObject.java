package com.dit.shubh.ShubhNetworkCallKit.model;

import android.util.Log;

import com.dit.shubh.ShubhNetworkCallKit.econstants.HttpTaskType;

import java.io.Serializable;
import java.util.List;

public class ShubhUploadObject implements Serializable {

    /**
     * 🔧 How to Configure ShubhUploadObject for Each HTTP Type
     * --------------------------------------------------------
     * ➤ GET:
     *    - url         ✅ (base URL)
     *    - methodName  ✅ (endpoint path)
     *    - param       🔁 (optional query string, e.g., "?id=123")
     *    - body        ❌ (ignored)
     *
     * ➤ POST:
     *    - url         ✅ (base URL)
     *    - methodName  ✅ (endpoint path)
     *    - param       🔁 (optional, usually null)
     *    - body        ✅ (JSON string)
     *
     * ➤ PUT:
     *    - url         ✅ (base URL)
     *    - methodName  ✅ (endpoint path)
     *    - param       🔁 (optional, usually null)
     *    - body        ✅ (JSON string)
     *
     * ➤ DELETE:
     *    - url         ✅ (base URL)
     *    - methodName  ✅ (endpoint path)
     *    - param       🔁 (optional query string)
     *    - body        ❌ (ignored)
     *
     * Common:
     *    - httpTaskType   ✅ (GET / POST / PUT / DELETE)
     *    - appTaskType    ✅ (For internal use e.g. tracking which call)
     *    - imagePath/imagePathsList ❌ (Optional, ignored unless used in future)
     */


    private String url;
    private HttpTaskType httpTaskType;
    private String methodName;
    private String param;
    private String body;
    private String imagePath;
    private List<String> imagePathsList;

    // Getters & Setters
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public HttpTaskType getHttpTaskType() { return httpTaskType; }
    public void setHttpTaskType(HttpTaskType httpTaskType) { this.httpTaskType = httpTaskType; }

    public String getMethodName() { return methodName; }
    public void setMethodName(String methodName) { this.methodName = methodName; }

    public String getParam() { return param; }
    public void setParam(String param) { this.param = param; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public List<String> getImagePathsList() { return imagePathsList; }
    public void setImagePathsList(List<String> imagePathsList) { this.imagePathsList = imagePathsList; }

    public void printDetails() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n\n\n╔══════════════════════════════════════════════════════╗\n");
        sb.append("║              🚀 ShubhUploadObject Details             ║\n");
        sb.append("╠══════════════════════════════════════════════════════╣\n");

        if (url != null)         sb.append("║ URL            : ").append(url).append("\n");
        if (methodName != null)  sb.append("║ Method         : ").append(methodName).append("\n");
        if (httpTaskType != null)sb.append("║ HttpTaskType   : ").append(httpTaskType.name()).append("\n");
        if (param != null)       sb.append("║ Params         : ").append(param).append("\n");
        if (body != null)        sb.append("║ Body           : ").append(body).append("\n");
        if (imagePath != null)   sb.append("║ ImagePath      : ").append(imagePath).append("\n");

        if (imagePathsList != null && !imagePathsList.isEmpty()) {
            sb.append("║ ImagePathsList : ").append(imagePathsList.toString()).append("\n");
        }

        sb.append("╚══════════════════════════════════════════════════════╝\n\n\n");

        Log.e("UPLOAD OBJECT", sb.toString());
    }

}
