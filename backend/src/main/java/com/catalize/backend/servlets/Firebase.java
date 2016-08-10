package com.catalize.backend.servlets;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by Marcus on 8/2/16.
 */
public class Firebase extends HttpServlet {


    @Override
    public void init() throws ServletException {
        super.init();
        FirebaseOptions options = null;

        InputStream resourceStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("firebase2.json");
        String json =  "{\n" +
                "  \"type\": \"service_account\",\n" +
                "  \"project_id\": \"catalize-1470601187382\",\n" +
                "  \"private_key_id\": \"755aed7555546a7b2ccd4e0d3386d908cc0f523a\",\n" +
                "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCjwzBkvE6CRVDv\\nHo7pcE/ZP8YWO9Yek9m0MzwJZx56C7o+Dzvq5DjIJt9qg7G6f2Hx4lo1up44/cyD\\nk8/+ruCVshn77mcWKbWP9VIxexHzQiKkTzPaK9N4+8rNEZnHjRHEfZjDnMak13sS\\n7bKP+zDnzTtlj5WTheTvUX2QiXnahOp1H5QqGmab+F8VXNHOPo1tkjKsB3yvhNbR\\n4hpskhxdlWEpAx9jo42OdBfjPIn0jb4OWpl9vBRbQ6JaxxeWBE29ezXnvEpymJjd\\nkAisdbUd6F6shCHUMpKl++oKouEH+no+0kTuFPOmeYl1K/nCcjx9RLi4ZOME/7y2\\nySU0EEUBAgMBAAECggEAKgeKgBAkRiLsJrK9EwSUObm4aTRqO+bOVQVhPziMBd7E\\nqfDW++4e9J+lb3zlPGw9AwuJA4kl2pKozow9MaxIT6MqMBJ7uqBxzMS1l6VycfBj\\nW7BIKpJc8AwPfJor0Z9PFBL4BhxSjzQOjkkbLwDVCGkP+XzkcrxGiXVsuI/QCljw\\nzN/w4VjL7c6tB+sU2uBd8/9QbnbtKXCpyadWeq9CLYP3Ciz6jYsdEDS1MZ9EkJBj\\nyAsGY7D9Q3WmosDc2qqbae47it5JUVklh6EFZQ05Va6xNMVnrKyB+wjYcYU7gmJ0\\nFrpX1+69O8U+JWnHvlJE2aKAtAIs9MNMouUI1RINEQKBgQDmgwh9/wmUTuWeGiKp\\nEnbutqz9AxNuMZZgX8AnSltzNS/fJ843lrNZw9v4oOtmHKdoZ9NlzL6+tNUlLrEv\\nHrbrfCnY30CKmkbB0/uVyXGtZ+ok1QOPST8WdO/xE90RLzcwbtiSYYgsga97It03\\n3/4zcv3WHWOi9M77xlvCqvvLWwKBgQC13rgWVM3F9rS2SbMdn6J4ee9uT+VUUMqt\\nUwF/rLBYuUIROPjtfCATOyJb670bkPXK9aEZiFTN8SXTzFRBTAZdven3h5KjLRZ/\\nyOL34LksIjy0pQ3OmX+msVauG0ZvesuXWvzyIkZxoU/XPuNY/KlwiZYtFZK9ENsy\\nNAoYDBxL0wKBgGxXvB8URxlY992WpSHYVuQeIjKNRo2tauODPZp5/X+pyQVW/Trt\\nbwDK6NH0OI/2+cw9uIOJd0HFVsUyK/9crVcDNdwEGp7KomUp0H73fqGlTMzg/dpT\\n7/4LGleyIV6ZVZRDFTG/+7QHhEYY/ebsNdtSufHwQEtwSvTVp31Yxh4vAoGBAIcq\\nXsBiCIi/YPE1dwRmNva5EOR4RqVSYj9MuIluC6X1EZABOqgtnx0Lzu5oFqTdKH/N\\n7Aww0W45cfamHlxlJzoWQkOwI6BYiycvYtf80UJms51CKi/IzyFRHW3KYZ/KsHLt\\nsZ43MQmUbOU5HPhu13MHHnClXuhbFb2LBZF3trP3AoGAOEyhJyORQpbqe9rUfOMD\\nVqbRZ/LieYf+2WViyoV2Dvu0cuzNNLNUujgnMaklGaAxAhopz5vTCdbx2QeRRpEE\\nFPBSdLWFL4TsllAFQZ/Hx5WePYrAKobx9/84EbwWue6Ufyjq05Eeq4NZjiIxB/CC\\nCCAwO56L0gIUnZRLWCt15ts=\\n-----END PRIVATE KEY-----\\n\",\n" +
                "  \"client_email\": \"firebase@catalize-1470601187382.iam.gserviceaccount.com\",\n" +
                "  \"client_id\": \"109247923124619202464\",\n" +
                "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                "  \"token_uri\": \"https://accounts.google.com/o/oauth2/token\",\n" +
                "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase%40catalize-1470601187382.iam.gserviceaccount.com\"\n" +
                "}\n";
        InputStream stream = new ByteArrayInputStream(json.getBytes(UTF_8));


            options = new FirebaseOptions.Builder()
                    .setServiceAccount(stream)
                    .setDatabaseUrl("https://catalize-1470601187382.firebaseio.com")
                    .build();

        FirebaseApp.initializeApp(options);
    }
}
