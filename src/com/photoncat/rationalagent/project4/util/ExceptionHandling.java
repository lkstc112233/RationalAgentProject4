package com.photoncat.rationalagent.project4.util;
/**
 * Methods for exception handling.
 * @author Xu Ke
 *
 */
class ExceptionHandling {
	/**
	 * Throws an exception when panic.
	 * @throws IllegalStateException When called.
	 */
	static void panic(String errMsg) {
		throw new IllegalStateException(errMsg);
	}
}
