package com.own.service.springboot.api;

/**
 * Created by Spring on 2017/7/4.
 */

import com.google.common.collect.ImmutableMap;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;

/**
 * 自定义MessageConverter; 在返回值里统一加一层data : {"data": response}
 */
public class CustomHttpMessageConverter extends FastJsonHttpMessageConverter4 {
    private static final Logger logger = LoggerFactory.getLogger(CustomHttpMessageConverter.class);

    @Override
    protected void writeInternal(Object obj, Type type, HttpOutputMessage outputMessage)
            throws IOException,HttpMessageNotWritableException {
        if(obj instanceof ErrorApiResponse){
            super.writeInternal(wrap("error",obj),type,outputMessage);
        }else if(obj instanceof PagingApiResponse){
            super.writeInternal(paging(((PagingApiResponse)obj)),type,outputMessage);
        }else if(obj instanceof String){
            HttpHeaders headers=outputMessage.getHeaders();
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            byte[] bytes=((String)obj).getBytes();
            headers.setContentLength(bytes.length);
            outputStream.write(bytes);
            OutputStream out=outputMessage.getBody();
            outputStream.writeTo(out);
            outputStream.close();
        }else {
            super.writeInternal(wrap("data",obj),type,outputMessage);
        }
    }

    private ImmutableMap<Object,Object> wrap(String key,Object obj){
        return ImmutableMap.builder()
                .put(key,obj)
                .build();
    }

    private static ImmutableMap<Object,Object> paging(PagingApiResponse pagingApiResponse){
        ImmutableMap<String,Object> paging=ImmutableMap.<String,Object>builder()
                .put("page",pagingApiResponse.getPage())
                .put("pageSize",pagingApiResponse.getPageSize())
                .put("total", pagingApiResponse.getTotal())
                .put("hasMore", pagingApiResponse.isHasMore())
                .build();
        return ImmutableMap.builder()
                .put("data",pagingApiResponse.getData())
                .put("paging",paging)
                .build();
    }
}
