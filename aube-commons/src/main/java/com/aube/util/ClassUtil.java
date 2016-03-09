package com.aube.util;

import java.lang.reflect.Modifier;

public class ClassUtil {
	/**
	 * Helper method that checks if given class is a concrete one; that is, not
	 * an interface or abstract class.
	 */
	public static boolean isConcrete(Class<?> type) {
		int mod = type.getModifiers();
		return (mod & (Modifier.INTERFACE | Modifier.ABSTRACT)) == 0;
	}
}
