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

package com.catalize.backend.model.introductionApi.model;

/**
 * Model definition for Introduction.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the introductionApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Introduction extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String aContact;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String aName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean aReplied;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean aText;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String acceptCode;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean active;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String bContact;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String bName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean bReplied;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean bText;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String body;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean complete;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String date;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String email;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean expired;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String introducerId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String phone;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String uid;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAContact() {
    return aContact;
  }

  /**
   * @param aContact aContact or {@code null} for none
   */
  public Introduction setAContact(java.lang.String aContact) {
    this.aContact = aContact;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAName() {
    return aName;
  }

  /**
   * @param aName aName or {@code null} for none
   */
  public Introduction setAName(java.lang.String aName) {
    this.aName = aName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getAReplied() {
    return aReplied;
  }

  /**
   * @param aReplied aReplied or {@code null} for none
   */
  public Introduction setAReplied(java.lang.Boolean aReplied) {
    this.aReplied = aReplied;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getAText() {
    return aText;
  }

  /**
   * @param aText aText or {@code null} for none
   */
  public Introduction setAText(java.lang.Boolean aText) {
    this.aText = aText;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAcceptCode() {
    return acceptCode;
  }

  /**
   * @param acceptCode acceptCode or {@code null} for none
   */
  public Introduction setAcceptCode(java.lang.String acceptCode) {
    this.acceptCode = acceptCode;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getActive() {
    return active;
  }

  /**
   * @param active active or {@code null} for none
   */
  public Introduction setActive(java.lang.Boolean active) {
    this.active = active;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getBContact() {
    return bContact;
  }

  /**
   * @param bContact bContact or {@code null} for none
   */
  public Introduction setBContact(java.lang.String bContact) {
    this.bContact = bContact;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getBName() {
    return bName;
  }

  /**
   * @param bName bName or {@code null} for none
   */
  public Introduction setBName(java.lang.String bName) {
    this.bName = bName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getBReplied() {
    return bReplied;
  }

  /**
   * @param bReplied bReplied or {@code null} for none
   */
  public Introduction setBReplied(java.lang.Boolean bReplied) {
    this.bReplied = bReplied;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getBText() {
    return bText;
  }

  /**
   * @param bText bText or {@code null} for none
   */
  public Introduction setBText(java.lang.Boolean bText) {
    this.bText = bText;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getBody() {
    return body;
  }

  /**
   * @param body body or {@code null} for none
   */
  public Introduction setBody(java.lang.String body) {
    this.body = body;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getComplete() {
    return complete;
  }

  /**
   * @param complete complete or {@code null} for none
   */
  public Introduction setComplete(java.lang.Boolean complete) {
    this.complete = complete;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDate() {
    return date;
  }

  /**
   * @param date date or {@code null} for none
   */
  public Introduction setDate(java.lang.String date) {
    this.date = date;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEmail() {
    return email;
  }

  /**
   * @param email email or {@code null} for none
   */
  public Introduction setEmail(java.lang.String email) {
    this.email = email;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getExpired() {
    return expired;
  }

  /**
   * @param expired expired or {@code null} for none
   */
  public Introduction setExpired(java.lang.Boolean expired) {
    this.expired = expired;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getIntroducerId() {
    return introducerId;
  }

  /**
   * @param introducerId introducerId or {@code null} for none
   */
  public Introduction setIntroducerId(java.lang.String introducerId) {
    this.introducerId = introducerId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPhone() {
    return phone;
  }

  /**
   * @param phone phone or {@code null} for none
   */
  public Introduction setPhone(java.lang.String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUid() {
    return uid;
  }

  /**
   * @param uid uid or {@code null} for none
   */
  public Introduction setUid(java.lang.String uid) {
    this.uid = uid;
    return this;
  }

  @Override
  public Introduction set(String fieldName, Object value) {
    return (Introduction) super.set(fieldName, value);
  }

  @Override
  public Introduction clone() {
    return (Introduction) super.clone();
  }

}
