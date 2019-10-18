package site.zhongkai.ask.utils;

import lombok.extern.log4j.Log4j2;
import site.zhongkai.ask.config.Constant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

@Log4j2
public class HttpUtils {

    /**
     * HTTP请求
     *
     * @param requestUrl          请求的URL
     * @param requestMethod       请求的方式
     * @param requestParameter    请求参数
     * @param requestCharsetName  请求的参数编码
     * @param responseCharsetName 响应的参数编码
     * @return ResponseUtils
     */
    public static ResponseResult<String> httpRequest(String requestUrl, String requestMethod, String requestParameter, Charset requestCharsetName, Charset responseCharsetName) {
        log.info("发起POST请求-[" + requestUrl + "?" + requestParameter + "]");
        byte[] parameterBytes = String.valueOf(requestParameter).getBytes(requestCharsetName);
        int responseCode = 50000;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setDoOutput(true);
            if (requestParameter.startsWith("{") && requestParameter.endsWith("}")) {
                connection.setRequestProperty("Content-type", "application/json");
            } else {
                connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            }
            connection.setRequestProperty("Contend-Length", String.valueOf(parameterBytes.length));
            // 获取输出流
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(parameterBytes);
            // 获取响应码
            responseCode = connection.getResponseCode();
            // 读取返回的数据
            String tempLine;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), responseCharsetName));
            while ((tempLine = bufferedReader.readLine()) != null) {
                if ("".equals(tempLine)) continue;
                stringBuilder.append(tempLine);
            }
            stringBuilder.trimToSize();
            // 关闭相关资源
            outputStream.close();
            bufferedReader.close();
            connection.disconnect();
        } catch (Exception e) {
            log.error(e.toString());
        }
        if (responseCode == 200) {
            return new ResponseResult<>(true, Constant.STATE_SUCCESS, Constant.EXPLAIN_SUCCESS, stringBuilder.toString());
        } else if (responseCode == 50000) {
            return new ResponseResult<>(false, Constant.STATE_FAIL, Constant.EXPLAIN_FAIL, "POST请求无法连接服务端，请检查网络连接是否正常");
        }
        return new ResponseResult<>(false, Constant.STATE_FAIL, Constant.EXPLAIN_FAIL, "服务端响应的状态码[" + responseCode + "]");
    }

}
