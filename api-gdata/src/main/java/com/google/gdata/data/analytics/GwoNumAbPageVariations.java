/**
 * Mule Google Api Commons
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */


package com.google.gdata.data.analytics;

import com.google.gdata.data.ExtensionDescription;
import com.google.gdata.data.ValueConstruct;

/**
 * Number of page variations in an A/B experiment.
 *
 * 
 */
@ExtensionDescription.Default(
    nsAlias = AnalyticsNamespace.GWO_ALIAS,
    nsUri = AnalyticsNamespace.GWO,
    localName = GwoNumAbPageVariations.XML_NAME)
public class GwoNumAbPageVariations extends ValueConstruct {

  /** XML element name */
  static final String XML_NAME = "numAbPageVariations";

  /**
   * Default mutable constructor.
   */
  public GwoNumAbPageVariations() {
    this(null);
  }

  /**
   * Constructor (mutable or immutable).
   *
   * @param value immutable value or <code>null</code> for a mutable value
   */
  public GwoNumAbPageVariations(String value) {
    super(AnalyticsNamespace.GWO_NS, XML_NAME, null, value);
    setRequired(false);
  }

  /**
   * Returns the extension description, specifying whether it is required, and
   * whether it is repeatable.
   *
   * @param required   whether it is required
   * @param repeatable whether it is repeatable
   * @return extension description
   */
  public static ExtensionDescription getDefaultDescription(boolean required,
      boolean repeatable) {
    ExtensionDescription desc =
        ExtensionDescription.getDefaultDescription(
        GwoNumAbPageVariations.class);
    desc.setRequired(required);
    desc.setRepeatable(repeatable);
    return desc;
  }

  @Override
  public String toString() {
    return "{GwoNumAbPageVariations value=" + getValue() + "}";
  }

}
