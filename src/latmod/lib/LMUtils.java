package latmod.lib;

import latmod.lib.util.*;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.*;
import java.util.*;

/**
 * Made by LatvianModder
 */
public class LMUtils
{
	// Class / Object //
	
	private static final Comparator<Package> packageComparator = new Comparator<Package>()
	{
		public int compare(Package o1, Package o2)
		{ return o1.getName().compareTo(o2.getName()); }
	};
	
	@SuppressWarnings("all")
	public static <E> E newObject(Class<?> c, Object... o) throws Exception
	{
		if(c == null) return null;
		
		if(o != null && o.length > 0)
		{
			Class<?>[] params = new Class<?>[o.length];
			for(int i = 0; i < o.length; i++)
				params[i] = o.getClass();
			
			Constructor<?> c1 = c.getConstructor(params);
			return (E) c1.newInstance(o);
		}
		
		return (E) c.newInstance();
	}
	
	public static Package[] getAllPackages()
	{
		Package[] p = Package.getPackages();
		Arrays.sort(p, packageComparator);
		return p;
	}
	
	public static String classpath(Class<?> c)
	{ return (c == null) ? null : c.getName(); }
	
	public static List<Class<?>> addSubclasses(Class<?> c, List<Class<?>> al, boolean all)
	{
		if(c == null) return null;
		if(al == null) al = new ArrayList<>();
		ArrayList<Class<?>> al1 = new ArrayList<>();
		LMListUtils.addAll(al1, c.getDeclaredClasses());
		if(all && !al1.isEmpty()) for(Class<?> anAl1 : al1) al.addAll(addSubclasses(anAl1, null, true));
		al.addAll(al1);
		return al;
	}
	
	public static boolean areObjectsEqual(Object o1, Object o2, boolean allowNulls)
	{
		if(o1 == null && o2 == null) return allowNulls;
		return !(o1 == null || o2 == null) && (o1 == o2 || o1.equals(o2));
	}
	
	public static int hashCodeOf(Object o)
	{ return o == null ? 0 : o.hashCode(); }
	
	public static int hashCode(Object... o)
	{
		if(o == null || o.length == 0) return 0;
		if(o.length == 1) return hashCodeOf(o[0]);
		int h = 0;
		for(Object anO : o) h = h * 31 + hashCodeOf(anO);
		return h;
	}
	
	public static long longHashCode(Object... o)
	{
		if(o == null || o.length == 0) return 0;
		if(o.length == 1) return hashCodeOf(o[0]);
		long h = 0L;
		for(Object anO : o) h = h * 31L + hashCodeOf(anO);
		return h;
	}
	
	public static void throwException(Exception e) throws Exception
	{ if(e != null) throw e; }
	
	// Net //
	
	public static String getHostAddress()
	{
		try { return InetAddress.getLocalHost().getHostAddress(); }
		catch(Exception e) { }
		return null;
	}
	
	public static String getExternalAddress()
	{
		try { return LMStringUtils.readString(new URL("http://checkip.amazonaws.com").openStream()); }
		catch(Exception e) { }
		return null;
	}
	
	// Misc //
	
	public static boolean openURI(URI uri) throws Exception
	{
		Class<?> oclass = Class.forName("java.awt.Desktop");
		Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null);
		oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, uri);
		return true;
	}
	
	public static long millis()
	{ return System.currentTimeMillis(); }
	
	public static void moveBytes(InputStream is, OutputStream os, boolean close) throws Exception
	{
		byte[] buffer = new byte[1024];
		int len;
		while((len = is.read(buffer, 0, buffer.length)) > 0) os.write(buffer, 0, len);
		os.flush();
		
		if(close)
		{
			is.close();
			os.close();
		}
	}
	
	@SuppressWarnings("all")
	public static <T> T[] newArray(int length, Class<? extends T> typeClass)
	{ return (T[]) new Object[length]; }
	
	@SuppressWarnings("all")
	public static <T> T[] convertArray(Object[] array, Class<? extends T> typeClass)
	{
		if(array == null) return null;
		T[] t = newArray(array.length, typeClass);
		System.arraycopy(array, 0, t, 0, t.length);
		return t;
	}
	
	public static String getID(Object o)
	{
		if(o == null) return null;
		else if(o instanceof FinalIDObject) return ((FinalIDObject) o).ID;
		else if(o instanceof IDObject) return ((IDObject) o).ID;
		else return o.toString();
	}
}