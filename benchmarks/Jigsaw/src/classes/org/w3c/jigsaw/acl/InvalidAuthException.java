// InvalidAuthException.java
// $Id: InvalidAuthException.java,v 1.2 2000/08/16 21:37:33 ylafon Exp $
// (c) COPYRIGHT MIT, INRIA and Keio, 1999.
// Please first read the full copyright statement in file COPYRIGHT.html

package org.w3c.jigsaw.acl;

/**
 * @version $Revision: 1.2 $
 * @author  Beno�t Mah� (bmahe@w3.org)
 */
public class InvalidAuthException extends Exception {

    public InvalidAuthException(String msg) {
	super(msg);
    }
}
