package helix.utils;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ClassUtils {
	

	public static Set<String> getClasses(String path) {
		File currentDir = new File(path);
		Set<String> classesList = new HashSet<String>();
		
		for(String p : currentDir.list())
			classesList.addAll(getClasses(classesList, path + "/" + p));
		return classesList;
	}
	
	public static Set<String> getClasses(Set<String> classesList, String path) {
		// Add all current classes
		for(String clazz : classesList)
			classesList.add(clazz);
		
		File currentDir = new File(path);
		if(!currentDir.exists()) {
			System.err.println("premature return");
			return classesList;
		}
		
		if(currentDir.isDirectory()) {
			for(String p : currentDir.list()) {
				File next = new File(path + "/" + p);
				if(next.isDirectory()) {
					classesList.addAll(getClasses(classesList, path + "/" + p));
				} else {
					classesList.add(getFullyQualifiedName(path + "/" + p));
				}
			}
			
		} else if(!currentDir.isDirectory()) {
			String qualifiedName = getFullyQualifiedName(path);
			classesList.add(qualifiedName);
		}
		
		return classesList;
	}
	
	public static String getFullyQualifiedName(String path) {
		int index = path.indexOf("java/main");
		
		// Remove everything before incl. "java/" and then remove ".java" from the end
		path = path.substring(index).substring(5).replaceAll("/", ".");
		return path.substring(0, path.length() - 5);
	}
}
