package com.sinosoft.sinoep.sso.filter;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

class TrustAnyHostnameVerifier implements HostnameVerifier {
 TrustAnyHostnameVerifier() {
 }

 public boolean verify(String hostname, SSLSession session) {
     return true;
 }
}
