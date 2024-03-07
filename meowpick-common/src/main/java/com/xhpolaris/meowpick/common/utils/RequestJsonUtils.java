package com.xhpolaris.meowpick.common.utils;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class RequestJsonUtils {
    private static final ThreadLocal<JSONObject> data = new ThreadLocal<>();

    public static JSONObject getRequestJsonObject(HttpServletRequest request)
    throws IOException {
        String json = getRequestJsonString(request);
        if (data.get() == null) {
            data.set(JSONObject.parseObject(json));
        }
        return data.get();
    }

    @SneakyThrows
    public static void write(Object data) {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();

        assert response != null;
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        PrintWriter out = response.getWriter();
        out.write(Meowpick.toJson(data));
        out.close();
    }

    public static String getRequestJsonString(HttpServletRequest request)
    throws IOException {
        String submitMehtod = request.getMethod();
        // GET
        if (submitMehtod.equals("GET")) {
            return new String(request.getQueryString()
                                     .getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8).replace("%22", "\"");
            // POST
        } else {
            return getRequestPostStr(request);
        }
    }

    public static byte[] getRequestPostBytes(HttpServletRequest request)
    throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte[] buffer = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {

            int readlen = request.getInputStream().read(buffer, i, contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    public static String getRequestPostStr(HttpServletRequest request)
    throws IOException {
        byte[] buffer       = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }


}