package com.map.reading;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.map.osm.FeatureCollection;

/**
 * This snippet shows how to write the object to a file (serialize) and read it
 * back (de-serialize).
 * 
 * @author mvohra
 * 
 */
public class ObjectSerializer {

	// file where serialized object would be stored
	private static final String OBJECT_FILE_NAME = "/sdcard/Others/object.dat";

	/**
	 * De-serialize the Object from the file
	 */
	public static FeatureCollection deserialize(ObjectInputStream in) {

		FeatureCollection deserializedObject = null;
		try {
			deserializedObject = (FeatureCollection) in.readObject();
			in.close();
			System.out.println("Object : " + deserializedObject.getClass()
					+ " de-serialized successfully");
		} catch (Exception ex) {
			System.out.println("Error Reading Object to File :"
					+ ex.getMessage());
			ex.printStackTrace();

		}

		return deserializedObject;

	}

	/**
	 * Serializes the object to a file
	 * 
	 * @param objToSerialize
	 */
	public static void serialize(FeatureCollection objToSerialize) {

		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(OBJECT_FILE_NAME));
			out.writeObject(objToSerialize);
			out.close();
			System.out.println("Object : " + objToSerialize.getClass()
					+ " serialized successfully");
			// System.out.println("Object : " + objToSerialize.getClass()
			// + " serialized successfully");
		} catch (Exception ex) {
			System.out.println("Error Saving Object to File :"
					+ ex.getMessage());
			ex.printStackTrace();
		}
	}
}