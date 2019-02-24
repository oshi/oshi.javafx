/**
 * OSHI (https://github.com/oshi/oshi)
 *
 * Copyright (c) 2010 - 2019 The OSHI Project Team:
 * https://github.com/oshi/oshi/graphs/contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package oshi.javafx.scene.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HeaderController {

	@FXML
	private Label oshiVersionLabel;

	@FXML
	private Label javaVersionLabel;

	@FXML
	private void initialize() {

		// Set Java version
		javaVersionLabel.setText(System.getProperty("java.version") + " " + System.getProperty("java.vendor"));

		// Set OSHI version in the background in case searching the classpath takes
		// longer than expected
		new Thread(new Task<String>() {

			{
				setOnSucceeded(event -> oshiVersionLabel.setText(getValue()));
			}

			@Override
			public String call() throws Exception {
				return readOshiVersion();
			}

		}).start();
	}

	/**
	 * Read the OSHI version string from its manifest. <br>
	 * <br>
	 * Implementation note: this method searches each classpath manifest for the
	 * entry: {@code Implementation-Vendor-Id: com.github.oshi}.
	 * 
	 * @return The OSHI version or {@code null} if not found
	 * @throws IOException If some classpath manifest could not be read
	 */
	private String readOshiVersion() throws IOException {
		Iterator<URL> it = getClass().getClassLoader().getResources("META-INF/MANIFEST.MF").asIterator();
		while (it.hasNext()) {
			try (InputStream in = it.next().openStream()) {
				Attributes attributes = new Manifest(in).getMainAttributes();
				if ("com.github.oshi".equals(attributes.getValue("Implementation-Vendor-Id"))) {
					return attributes.getValue("Implementation-Version");
				}
			}
		}

		return null;
	}
}