/*
 * Copyright (c) 2000 World Wide Web Consortium,
 * (Massachusetts Institute of Technology, Institut National de
 * Recherche en Informatique et en Automatique, Keio University). All
 * Rights Reserved. This program is distributed under the W3C's Software
 * Intellectual Property License. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 */

package org.w3c.dom.html;

/**
 * Document base URI. See the BASE element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/WD-DOM-Level-1-20000929'>Document Object Model (DOM) Level 1 Specification (Second Edition)</a>.
 */
public interface HTMLBaseElement extends HTMLElement {
    /**
     * The base URI. See the href attribute definition in HTML 4.0.
     */
    public String getHref();
    public void setHref(String href);

    /**
     * The default target frame. See the target attribute definition in HTML 
     * 4.0.
     */
    public String getTarget();
    public void setTarget(String target);

}
