//package com.example.controller;
//
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.conn.ssl.NoopHostnameVerifier;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.ssl.SSLContextBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.client.BufferingClientHttpRequestFactory;
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.http.client.SimpleClientHttpRequestFactory;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.ResponseErrorHandler;
//import org.springframework.web.client.RestTemplate;
//
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.SSLContext;
//import java.io.*;
//import java.net.URI;
//import java.nio.charset.StandardCharsets;
//import java.security.*;
//import java.security.cert.CertificateException;
//
//@Slf4j
//@Component
//public class RestTemplateClient implements ResponseErrorHandler {
//
//    private static final String CONTENT_TYPE = "Content-Type";
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//
//    @Bean
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate(
//                new BufferingClientHttpRequestFactory(
//                        getRequestFactory()
//                ));
//        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
//        restTemplate.setErrorHandler(this);
//        return restTemplate;
//    }
//
//    private SimpleClientHttpRequestFactory getRequestFactory() {
//        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
//        simpleClientHttpRequestFactory.setConnectTimeout(30000);
//        simpleClientHttpRequestFactory.setReadTimeout(30000);
//        return simpleClientHttpRequestFactory;
//    }
//
//    public <T> T post(String url, Object data, Class<T> type) {
//        try {
//             return this.post(url, data, type, null);
//        } catch (Exception e) {
//            throw new RestTemplateClientException(url, e, data);
//        }
//    }
//
//    public <T> T post(String url, Object data, Class<T> type, String expectBodyContentType) {
//        log.info("------postForObject,request:{}", JSON.toJSONString(data));
//        return restTemplate.postForObject(url, new HttpEntity<>(data, createCommonHeaders(expectBodyContentType)), type);
//    }
//
//    public <T> T post(String url, HttpEntity httpEntity, Class<T> type) {
//        log.info("------postForObject,request:{}====url:{}", JSON.toJSONString(httpEntity),url);
//        return restTemplate.postForObject(url, httpEntity, type);
//    }
//
//    public <T> T get(String url, Class<T> type) {
//        try {
//            return get(url, type, null);
//        } catch (Exception e) {
//            throw new RestTemplateClientException(url, e);
//        }
//    }
//
//    public <T> T get(String url, Class<T> type, String expectBodyContentType) {
//        log.info("------postForObject,request:{}",url);
//        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(createCommonHeaders(expectBodyContentType)), type).getBody();
//    }
//
//    @Override
//    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
//        return false;
//    }
//
//    @Override
//    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
//
//    }
//
//    public <T> T post(String certificateType, String certificateFilePath, String certificatePassword, String url, Object data, Class<T> type, String expectBodyContentType) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
//
//        KeyStore keyStore = KeyStore.getInstance(certificateType);
//        FileInputStream fileInputStream = new FileInputStream(new File(certificateFilePath));
//        keyStore.load(fileInputStream, certificatePassword.toCharArray());
//        SSLContext sslContext = SSLContextBuilder.create().loadKeyMaterial(keyStore, certificatePassword.toCharArray()).build();
//        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"},
//                null, hostnameVerifier);
//        CloseableHttpClient httpclient = HttpClients.custom()
//                .setSSLSocketFactory(sslsf)
//                .build();
//
//        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpclient);
//
//        RestTemplate sslRestTemplate = new RestTemplate();
//        sslRestTemplate.setRequestFactory(clientHttpRequestFactory);
//        sslRestTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
//
//        return sslRestTemplate.postForObject(url, new HttpEntity<>(data, createCommonHeaders(expectBodyContentType)), type);
//    }
//
//    @Override
//    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
//
//        InputStream body = response.getBody();
//        log.error("url:{},responseBody:{}",url.getPath(),convertStreamToString(body));
//    }
//
//    private HttpHeaders createCommonHeaders(String contentType) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(CONTENT_TYPE, contentType == null ? MediaType.APPLICATION_JSON_VALUE : contentType);
//        return headers;
//    }
//
//    private String convertStreamToString(InputStream is) {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        StringBuilder sb = new StringBuilder();
//
//        String line = null;
//        try {
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//        } catch (IOException e) {
//            log.error("转换json失败，返回的字符串不是规则json",e);
//        } finally {
//            try {
//                is.close();
//            } catch (IOException e) {
//                log.error("数据流错误",e);
//            }
//        }
//
//        return sb.toString();
//    }
//
//}
