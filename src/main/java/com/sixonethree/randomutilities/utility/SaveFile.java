package com.sixonethree.randomutilities.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SaveFile  {
	public File file;
	public String name;
	public String path;

	public ArrayList<String> data = new ArrayList<String>();

	public SaveFile(String name, String path) {
		this.name = name;
		this.path = path;
		file = new File(path + name);
	}
	
	protected SaveFile() {}

	public void createFile() {
		if (file.exists()) { return; }
		File pathFile = new File(path);
		pathFile.mkdirs();

		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		createFile();
		try {
			PrintWriter out = new PrintWriter(file);
			for (String line : data) {
				for (int i = 0; i < line.length(); i++) {
					char character = line.charAt(i);
					out.print(character);
				}
				out.println();
				out.flush();
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		createFile();
		clear();
		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNext()) {
				data.add(scan.nextLine());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		data.clear();
	}

	public boolean exists() {
		return file.exists();
	}

	public String getSingleData(String name) {
		for (String aData : data) {
			if (aData.contains(name)) { return aData; }
		}
		return null;
	}

	public boolean isBoolean(String name) {
		return (name.contains("true") || name.contains("false")) ? true : false;
	}

	public boolean getBoolean(String name){
		String aData = getSingleData(name);
		String[] split = null;
		if (isBoolean(aData)) {
			split = aData.split("=");
		}
		return Boolean.parseBoolean(split[1]);
	}
	
	public String getString(String name) {
		String aData = getSingleData(name);
		String[] split = aData.split("=");
		return split[1];
	}
}