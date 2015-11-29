package ca.ualberta.cs.xpertsapp.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ca.ualberta.cs.xpertsapp.model.es.SearchHit;

/**
 * Manages the downloading of images from the server
 */
public class ImageManager {
	private Map<String, Bitmap> images = new HashMap<String, Bitmap>();

	/**
	 * @param id the id of the image to look for
	 * @return the image or null if it doesn't exist
	 */
	public Bitmap getImage(String id) {
		// If image is loaded
		if (this.images.containsKey(id)) {
			return this.images.get(id);
		}
		// TODO:
		try {
			// Load the base64 encoded image
			SearchHit<ImageContainer> loadedImage = IOManager.sharedManager().fetchData(Constants.serverImageExtension() + id, new TypeToken<SearchHit<ImageContainer>>() {
			});
			if (loadedImage.isFound()) {
				this.loadImage(id, loadedImage.getSource().b64Data);
				return this.getImage(id);
			} else {
				// TODO:
				return null;
			}
		} catch (JsonIOException e) {
			throw new RuntimeException(e);
		} catch (JsonSyntaxException e) {
			throw new RuntimeException(e);
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		}
	}

	/** Converts a base64 encoded image to a bitmap */
	private void loadImage(String id, String b64Image) {
		byte[] decodedString = Base64.decode(b64Image, Base64.DEFAULT);
		Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
		this.images.put(id, decodedImage);
	}

	/** Registers the image and returns its ID */
	String registerImage(Bitmap image1) {
		// First resize
		Bitmap image = image1;
		while (image.getByteCount() > 65536) {
			// Shrink
			image = Bitmap.createScaledBitmap(image, (int)(image.getWidth() * 0.9), (int)(image.getHeight() * 0.9), false);
		}
		// Register
		String id = UUID.randomUUID().toString();
		this.images.put(id, image);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		byte[] b = byteArrayOutputStream.toByteArray();
		String encodedImage = Base64.encodeToString(b, 2);
		IOManager.sharedManager().storeData(new ImageContainer(encodedImage), Constants.serverImageExtension() + id);
		return id;
	}

	private class ImageContainer {
		String b64Data;
		ImageContainer(String b64Data) {
			this.b64Data = b64Data;
		}
	}

	/**
	 * clear the loaded services
	 */
	public void clearCache() {
		this.images.clear();
		IOManager.sharedManager().clearCache();
	}

	// Singleton
	private static ImageManager instance = new ImageManager();

	private ImageManager() {
	}

	/** Returns the singleton instance of the Service manager */
	public static ImageManager sharedManager() {
		return ImageManager.instance;
	}
}