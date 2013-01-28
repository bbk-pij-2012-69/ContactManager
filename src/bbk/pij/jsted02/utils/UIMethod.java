/**
 * UIMethod - Interface tag used to tag methods that should be included in the UI,
 * 		this increases code portability as all "invokable" methods can then be
 * 		inferred from the annotation, simplifies the implementation as a mapping
 * 		of "friendly" names against methods does not need to be maintained.
 */

package bbk.pij.jsted02.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface UIMethod {
	/**
	 * name of method to be printed in UI. 
	 */
	public String name();
}
