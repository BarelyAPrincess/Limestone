/**
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * <p>
 * Limestone Copyright (C) 2018 Amelia Dewitt <theameliadewitt@gmail.com>
 * Glowstone Copyright (C) 2015-2018 The Glowstone Project.
 * Glowstone Copyright (C) 2011-2014 Tad Hardesty.
 * Lightstone Copyright (C) 2010-2011 Graham Edgecombe.
 * <p>
 * All Rights Reserved.
 */
import com.google.common.base.Strings;

import net.limestone.client.Limestone;

import java.io.File;
import java.lang.reflect.Field;

public class DevStart
{
	private static void hackNatives()
	{
		String paths = System.getProperty( "java.library.path" );
		String nativesDir = "/home/ameliad/.gradle/caches/minecraft/net/minecraft/natives/1.12.2";

		if ( Strings.isNullOrEmpty( paths ) )
			paths = nativesDir;
		else
			paths += File.pathSeparator + nativesDir;

		System.setProperty( "java.library.path", paths );

		// hack the classloader now.
		try
		{
			final Field sysPathsField = ClassLoader.class.getDeclaredField( "sys_paths" );
			sysPathsField.setAccessible( true );
			sysPathsField.set( null, null );
		}
		catch ( Throwable t )
		{
		}
	}

	public static void main( String[] args ) throws Throwable
	{
		hackNatives();

		new Limestone().boot();
	}
}
