package com.lambdaschool.schools.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@Component
public class CustomErrorDetails extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);

        Map<String, Object> newErrorAttributes = new LinkedHashMap<>();
        newErrorAttributes.put("message", errorAttributes.get("error"));
        newErrorAttributes.put("status", errorAttributes.get("status"));
        List<String> details = new ArrayList<>();
        details.add(errorAttributes.get("message").toString());
        newErrorAttributes.put("details", details);
        newErrorAttributes.put("timestamp", new Date());
        if (errorAttributes.get("exceptionType") != null)
            newErrorAttributes.put("exceptionType", errorAttributes.get("exceptionType"));

        return newErrorAttributes;
    }
}
