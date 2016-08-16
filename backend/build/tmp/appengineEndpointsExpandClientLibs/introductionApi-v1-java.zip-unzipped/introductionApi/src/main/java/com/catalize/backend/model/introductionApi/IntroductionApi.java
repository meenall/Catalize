/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2016-07-08 17:28:43 UTC)
 * on 2016-08-16 at 05:16:22 UTC 
 * Modify at your own risk.
 */

package com.catalize.backend.model.introductionApi;

/**
 * Service definition for IntroductionApi (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link IntroductionApiRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class IntroductionApi extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.22.0 of the introductionApi library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://myApplicationId.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "introductionApi/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public IntroductionApi(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  IntroductionApi(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "bothText".
   *
   * This request holds the parameters needed by the introductionApi server.  After setting any
   * optional parameters, call the {@link BothText#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public BothText bothText() throws java.io.IOException {
    BothText result = new BothText();
    initialize(result);
    return result;
  }

  public class BothText extends IntroductionApiRequest<com.catalize.backend.model.introductionApi.model.Introduction> {

    private static final String REST_PATH = "test/both";

    /**
     * Create a request for the method "bothText".
     *
     * This request holds the parameters needed by the the introductionApi server.  After setting any
     * optional parameters, call the {@link BothText#execute()} method to invoke the remote operation.
     * <p> {@link
     * BothText#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected BothText() {
      super(IntroductionApi.this, "POST", REST_PATH, null, com.catalize.backend.model.introductionApi.model.Introduction.class);
    }

    @Override
    public BothText setAlt(java.lang.String alt) {
      return (BothText) super.setAlt(alt);
    }

    @Override
    public BothText setFields(java.lang.String fields) {
      return (BothText) super.setFields(fields);
    }

    @Override
    public BothText setKey(java.lang.String key) {
      return (BothText) super.setKey(key);
    }

    @Override
    public BothText setOauthToken(java.lang.String oauthToken) {
      return (BothText) super.setOauthToken(oauthToken);
    }

    @Override
    public BothText setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (BothText) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public BothText setQuotaUser(java.lang.String quotaUser) {
      return (BothText) super.setQuotaUser(quotaUser);
    }

    @Override
    public BothText setUserIp(java.lang.String userIp) {
      return (BothText) super.setUserIp(userIp);
    }

    @Override
    public BothText set(String parameterName, Object value) {
      return (BothText) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "emailTest".
   *
   * This request holds the parameters needed by the introductionApi server.  After setting any
   * optional parameters, call the {@link EmailTest#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public EmailTest emailTest() throws java.io.IOException {
    EmailTest result = new EmailTest();
    initialize(result);
    return result;
  }

  public class EmailTest extends IntroductionApiRequest<com.catalize.backend.model.introductionApi.model.Introduction> {

    private static final String REST_PATH = "test/email";

    /**
     * Create a request for the method "emailTest".
     *
     * This request holds the parameters needed by the the introductionApi server.  After setting any
     * optional parameters, call the {@link EmailTest#execute()} method to invoke the remote
     * operation. <p> {@link
     * EmailTest#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected EmailTest() {
      super(IntroductionApi.this, "POST", REST_PATH, null, com.catalize.backend.model.introductionApi.model.Introduction.class);
    }

    @Override
    public EmailTest setAlt(java.lang.String alt) {
      return (EmailTest) super.setAlt(alt);
    }

    @Override
    public EmailTest setFields(java.lang.String fields) {
      return (EmailTest) super.setFields(fields);
    }

    @Override
    public EmailTest setKey(java.lang.String key) {
      return (EmailTest) super.setKey(key);
    }

    @Override
    public EmailTest setOauthToken(java.lang.String oauthToken) {
      return (EmailTest) super.setOauthToken(oauthToken);
    }

    @Override
    public EmailTest setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (EmailTest) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public EmailTest setQuotaUser(java.lang.String quotaUser) {
      return (EmailTest) super.setQuotaUser(quotaUser);
    }

    @Override
    public EmailTest setUserIp(java.lang.String userIp) {
      return (EmailTest) super.setUserIp(userIp);
    }

    @Override
    public EmailTest set(String parameterName, Object value) {
      return (EmailTest) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getIntroduction".
   *
   * This request holds the parameters needed by the introductionApi server.  After setting any
   * optional parameters, call the {@link GetIntroduction#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetIntroduction getIntroduction(java.lang.Long id) throws java.io.IOException {
    GetIntroduction result = new GetIntroduction(id);
    initialize(result);
    return result;
  }

  public class GetIntroduction extends IntroductionApiRequest<com.catalize.backend.model.introductionApi.model.Introduction> {

    private static final String REST_PATH = "introduction/{id}";

    /**
     * Create a request for the method "getIntroduction".
     *
     * This request holds the parameters needed by the the introductionApi server.  After setting any
     * optional parameters, call the {@link GetIntroduction#execute()} method to invoke the remote
     * operation. <p> {@link GetIntroduction#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetIntroduction(java.lang.Long id) {
      super(IntroductionApi.this, "GET", REST_PATH, null, com.catalize.backend.model.introductionApi.model.Introduction.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetIntroduction setAlt(java.lang.String alt) {
      return (GetIntroduction) super.setAlt(alt);
    }

    @Override
    public GetIntroduction setFields(java.lang.String fields) {
      return (GetIntroduction) super.setFields(fields);
    }

    @Override
    public GetIntroduction setKey(java.lang.String key) {
      return (GetIntroduction) super.setKey(key);
    }

    @Override
    public GetIntroduction setOauthToken(java.lang.String oauthToken) {
      return (GetIntroduction) super.setOauthToken(oauthToken);
    }

    @Override
    public GetIntroduction setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetIntroduction) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetIntroduction setQuotaUser(java.lang.String quotaUser) {
      return (GetIntroduction) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetIntroduction setUserIp(java.lang.String userIp) {
      return (GetIntroduction) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetIntroduction setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetIntroduction set(String parameterName, Object value) {
      return (GetIntroduction) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertIntroduction".
   *
   * This request holds the parameters needed by the introductionApi server.  After setting any
   * optional parameters, call the {@link InsertIntroduction#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.catalize.backend.model.introductionApi.model.Introduction}
   * @return the request
   */
  public InsertIntroduction insertIntroduction(com.catalize.backend.model.introductionApi.model.Introduction content) throws java.io.IOException {
    InsertIntroduction result = new InsertIntroduction(content);
    initialize(result);
    return result;
  }

  public class InsertIntroduction extends IntroductionApiRequest<com.catalize.backend.model.introductionApi.model.Introduction> {

    private static final String REST_PATH = "introduction";

    /**
     * Create a request for the method "insertIntroduction".
     *
     * This request holds the parameters needed by the the introductionApi server.  After setting any
     * optional parameters, call the {@link InsertIntroduction#execute()} method to invoke the remote
     * operation. <p> {@link InsertIntroduction#initialize(com.google.api.client.googleapis.services.A
     * bstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param content the {@link com.catalize.backend.model.introductionApi.model.Introduction}
     * @since 1.13
     */
    protected InsertIntroduction(com.catalize.backend.model.introductionApi.model.Introduction content) {
      super(IntroductionApi.this, "POST", REST_PATH, content, com.catalize.backend.model.introductionApi.model.Introduction.class);
    }

    @Override
    public InsertIntroduction setAlt(java.lang.String alt) {
      return (InsertIntroduction) super.setAlt(alt);
    }

    @Override
    public InsertIntroduction setFields(java.lang.String fields) {
      return (InsertIntroduction) super.setFields(fields);
    }

    @Override
    public InsertIntroduction setKey(java.lang.String key) {
      return (InsertIntroduction) super.setKey(key);
    }

    @Override
    public InsertIntroduction setOauthToken(java.lang.String oauthToken) {
      return (InsertIntroduction) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertIntroduction setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertIntroduction) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertIntroduction setQuotaUser(java.lang.String quotaUser) {
      return (InsertIntroduction) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertIntroduction setUserIp(java.lang.String userIp) {
      return (InsertIntroduction) super.setUserIp(userIp);
    }

    @Override
    public InsertIntroduction set(String parameterName, Object value) {
      return (InsertIntroduction) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "sgTest".
   *
   * This request holds the parameters needed by the introductionApi server.  After setting any
   * optional parameters, call the {@link SgTest#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public SgTest sgTest() throws java.io.IOException {
    SgTest result = new SgTest();
    initialize(result);
    return result;
  }

  public class SgTest extends IntroductionApiRequest<Void> {

    private static final String REST_PATH = "test/sendGrid";

    /**
     * Create a request for the method "sgTest".
     *
     * This request holds the parameters needed by the the introductionApi server.  After setting any
     * optional parameters, call the {@link SgTest#execute()} method to invoke the remote operation.
     * <p> {@link
     * SgTest#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected SgTest() {
      super(IntroductionApi.this, "POST", REST_PATH, null, Void.class);
    }

    @Override
    public SgTest setAlt(java.lang.String alt) {
      return (SgTest) super.setAlt(alt);
    }

    @Override
    public SgTest setFields(java.lang.String fields) {
      return (SgTest) super.setFields(fields);
    }

    @Override
    public SgTest setKey(java.lang.String key) {
      return (SgTest) super.setKey(key);
    }

    @Override
    public SgTest setOauthToken(java.lang.String oauthToken) {
      return (SgTest) super.setOauthToken(oauthToken);
    }

    @Override
    public SgTest setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (SgTest) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public SgTest setQuotaUser(java.lang.String quotaUser) {
      return (SgTest) super.setQuotaUser(quotaUser);
    }

    @Override
    public SgTest setUserIp(java.lang.String userIp) {
      return (SgTest) super.setUserIp(userIp);
    }

    @Override
    public SgTest set(String parameterName, Object value) {
      return (SgTest) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "textTest".
   *
   * This request holds the parameters needed by the introductionApi server.  After setting any
   * optional parameters, call the {@link TextTest#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public TextTest textTest() throws java.io.IOException {
    TextTest result = new TextTest();
    initialize(result);
    return result;
  }

  public class TextTest extends IntroductionApiRequest<com.catalize.backend.model.introductionApi.model.Introduction> {

    private static final String REST_PATH = "test/text";

    /**
     * Create a request for the method "textTest".
     *
     * This request holds the parameters needed by the the introductionApi server.  After setting any
     * optional parameters, call the {@link TextTest#execute()} method to invoke the remote operation.
     * <p> {@link
     * TextTest#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected TextTest() {
      super(IntroductionApi.this, "POST", REST_PATH, null, com.catalize.backend.model.introductionApi.model.Introduction.class);
    }

    @Override
    public TextTest setAlt(java.lang.String alt) {
      return (TextTest) super.setAlt(alt);
    }

    @Override
    public TextTest setFields(java.lang.String fields) {
      return (TextTest) super.setFields(fields);
    }

    @Override
    public TextTest setKey(java.lang.String key) {
      return (TextTest) super.setKey(key);
    }

    @Override
    public TextTest setOauthToken(java.lang.String oauthToken) {
      return (TextTest) super.setOauthToken(oauthToken);
    }

    @Override
    public TextTest setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (TextTest) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public TextTest setQuotaUser(java.lang.String quotaUser) {
      return (TextTest) super.setQuotaUser(quotaUser);
    }

    @Override
    public TextTest setUserIp(java.lang.String userIp) {
      return (TextTest) super.setUserIp(userIp);
    }

    @Override
    public TextTest set(String parameterName, Object value) {
      return (TextTest) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link IntroductionApi}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link IntroductionApi}. */
    @Override
    public IntroductionApi build() {
      return new IntroductionApi(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link IntroductionApiRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setIntroductionApiRequestInitializer(
        IntroductionApiRequestInitializer introductionapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(introductionapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
