/**
 * Mule Google Api Commons
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */


package com.google.gdata.data.acl;

import com.google.gdata.data.AttributeGenerator;
import com.google.gdata.data.AttributeHelper;
import com.google.gdata.data.ExtensionDescription;
import com.google.gdata.data.ExtensionPoint;
import com.google.gdata.data.ExtensionProfile;
import com.google.gdata.util.ParseException;

import java.util.List;

/**
 * Describes the key granting a role in an access control list.
 *
 * 
 */
@ExtensionDescription.Default(
    nsAlias = AclNamespace.gAclAlias,
    nsUri = AclNamespace.gAcl,
    localName = AclWithKey.XML_NAME)
public class AclWithKey extends ExtensionPoint {

  /** XML element name */
  static final String XML_NAME = "withKey";

  /** XML "key" attribute name */
  private static final String KEY = "key";

  /** A key granting a role */
  private String key = null;

  /**
   * Default mutable constructor.
   */
  public AclWithKey() {
    super();
  }

  /**
   * Immutable constructor.
   *
   * @param key a key granting a role.
   * @param role a role.
   */
  public AclWithKey(String key, AclRole role) {
    super();
    setKey(key);
    setRole(role);
    setImmutable(true);
  }

  @Override
  public void declareExtensions(ExtensionProfile extProfile) {
    extProfile.declare(AclWithKey.class, AclRole.class);
    extProfile.declare(AclWithKey.class, AdditionalRole.getDefaultDescription(false, true));
  }

  /**
   * Returns the key granting a role.
   *
   * @return a key
   */
  public String getKey() {
    return key;
  }

  /**
   * Sets the key granting a role.
   *
   * @param key a key or <code>null</code> to reset
   */
  public void setKey(String key) {
    throwExceptionIfImmutable();
    this.key = key;
  }

  /**
   * Returns whether it has a key.
   *
   * @return whether it has a key
   */
  public boolean hasKey() {
    return getKey() != null;
  }

  /**
   * Returns the role.
   *
   * @return a role
   */
  public AclRole getRole() {
    return getExtension(AclRole.class);
  }

  /**
   * Sets the role.
   *
   * @param role the role or <code>null</code> to reset
   */
  public void setRole(AclRole role) {
    throwExceptionIfImmutable();
    if (role == null) {
      removeExtension(AclRole.class);
    } else {
      setExtension(role);
    }
  }

  /**
   * Returns whether it has the role.
   *
   * @return whether it has the role
   */
  public boolean hasRole() {
    return hasExtension(AclRole.class);
  }

  /**
   * Returns the additional roles.
   */
  public List<AdditionalRole> getAdditionalRoles() {
    return getRepeatingExtension(AdditionalRole.class);
  }

  /**
   * Adds an additional role.
   */
  public void addAdditionalRole(AdditionalRole role) {
    addRepeatingExtension(role);
  }

  /**
   * Clears the additional roles.
   */
  public void clearAdditionalRoles() {
    getAdditionalRoles().clear();
  }

  @Override
  protected void putAttributes(AttributeGenerator generator) {
    generator.put(KEY, key);
  }

  @Override
  protected void consumeAttributes(AttributeHelper helper) throws ParseException {
    key = helper.consume(KEY, false);
  }

  @Override
  public String toString() {
    return "{AclWithKey key=" + key + " " + super.toString() + "}";
  }
}
