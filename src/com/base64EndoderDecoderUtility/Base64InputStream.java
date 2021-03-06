
package com.base64EndoderDecoderUtility;

import java.io.IOException;
import java.io.InputStream;

/**
 * The Class Base64InputStream.
 */
public class Base64InputStream extends InputStream {


	/** The input stream. */
	private InputStream inputStream;


	/** The buffer. */
	private int[] buffer;


	/** The buffer counter. */
	private int bufferCounter = 0;


	/** The eof. */
	private boolean eof = false;

	/**
	 * <p>
	 * It builds a base64 decoding input stream.
	 * </p>
	 * 
	 * @param inputStream
	 *            The underlying stream, from which the encoded data is read.
	 */
	public Base64InputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/* (non-Javadoc)
	 * @see java.io.InputStream#read()
	 */
	public int read() throws IOException {
		if (buffer == null || bufferCounter == buffer.length) {
			if (eof) {
				return -1;
			}
			acquire();
			if (buffer.length == 0) {
				buffer = null;
				return -1;
			}
			bufferCounter = 0;
		}
		return buffer[bufferCounter++];
	}

	/**
	 * Reads from the underlying stream, decodes the data and puts the decoded
	 * bytes into the buffer.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void acquire() throws IOException {
		char[] four = new char[4];
		int i = 0;
		do {
			int b = inputStream.read();
			if (b == -1) {
				if (i != 0) {
					throw new IOException("Bad base64 stream");
				} else {
					buffer = new int[0];
					eof = true;
					return;
				}
			}
			char c = (char) b;
			if (Shared.chars.indexOf(c) != -1 || c == Shared.pad) {
				four[i++] = c;
			} else if (c != '\r' && c != '\n') {
				throw new IOException("Bad base64 stream");
			}
		} while (i < 4);
		boolean padded = false;
		for (i = 0; i < 4; i++) {
			if (four[i] != Shared.pad) {
				if (padded) {
					throw new IOException("Bad base64 stream");
				}
			} else {
				if (!padded) {
					padded = true;
				}
			}
		}
		int l;
		if (four[3] == Shared.pad) {
			if (inputStream.read() != -1) {
				throw new IOException("Bad base64 stream");
			}
			eof = true;
			if (four[2] == Shared.pad) {
				l = 1;
			} else {
				l = 2;
			}
		} else {
			l = 3;
		}
		int aux = 0;
		for (i = 0; i < 4; i++) {
			if (four[i] != Shared.pad) {
				aux = aux | (Shared.chars.indexOf(four[i]) << (6 * (3 - i)));
			}
		}
		buffer = new int[l];
		for (i = 0; i < l; i++) {
			buffer[i] = (aux >>> (8 * (2 - i))) & 0xFF;
		}
	}

	/* (non-Javadoc)
	 * @see java.io.InputStream#close()
	 */
	public void close() throws IOException {
		inputStream.close();
	}
}