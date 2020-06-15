package com.baselet.element.old.custom;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.baselet.control.util.Path;
import com.baselet.diagram.draw.TextSplitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileClassLoader extends ClassLoader {
	private static final Logger log = LoggerFactory.getLogger(FileClassLoader.class);

	public FileClassLoader() {
		super();
	}

	public FileClassLoader(ClassLoader parent) {
		super(parent);
	}

	@Override
	protected Class<?> findClass(String className) throws ClassNotFoundException {
		Class<?> c = null;
		try {
			byte[] data = loadClassData(className);
			c = defineClass(className, data, 0, data.length);
			if (c == null) {
				throw new ClassNotFoundException(className);
			}
		} catch (IOException e) {
			throw new ClassNotFoundException(className, e);
		}
		return c;
	}

	private byte[] loadClassData(String className) throws IOException {
		File f = new File(Path.temp() + className + ".class");
		byte[] buff = new byte[(int) f.length()];
		FileInputStream fis = null;
		DataInputStream dis = null;
		try {
			fis = new FileInputStream(f);
			dis = new DataInputStream(fis);
			dis.readFully(buff);
			f.deleteOnExit();
		} catch(Exception ex) {
			log.error(ex.getMessage());
		} finally {
			if (dis != null) {
				dis.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return buff;
	}
}
