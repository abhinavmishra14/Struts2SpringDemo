
package com.base64EndoderDecoderUtility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Base64 encoding and decoding utility,both for binary and textual formats
 */
public class Base64 {


	/**
	 * Encode a string.
	 * Before the string is encoded in Base64, it is converted in a binary
	 * sequence using the system default charset.
	 * @param str the str
	 * @return the string
	 * @throws RuntimeException the runtime exception
	 */
	public static String encode(String str) throws RuntimeException {
		byte[] bytes = str.getBytes();
		byte[] encoded = encode(bytes);
		try {
			return new String(encoded, "ASCII");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("ASCII is not supported!", e);
		}
	}

	/**
	 * Encodes a string.
	 * Before the string is encoded in Base64, it is converted in a binary
	 * sequence using the supplied charset.
	 */
	public static String encode(String str, String charset)
	throws RuntimeException {
		byte[] bytes;
		try {
			bytes = str.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unsupported charset: " + charset, e);
		}
		byte[] encoded = encode(bytes);
		try {
			return new String(encoded, "ASCII");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("ASCII is not supported!", e);
		}
	}

	/**
	 * Decodes the supplied string.
	 * The supplied string is decoded into a binary sequence, and then the
	 * sequence is encoded with the system default charset and returned.
	 */
	public static String decode(String str) throws RuntimeException {
		byte[] bytes;
		try {
			bytes = str.getBytes("ASCII");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("ASCII is not supported!", e);
		}
		byte[] decoded = decode(bytes);
		return new String(decoded);
	}

	/**
	 * Decodes the supplied string.
	 * The supplied string is decoded into a binary sequence, and then the
	 * sequence is encoded with the supplied charset and returned.
	 */
	public static String decode(String str, String charset)
	throws RuntimeException {
		byte[] bytes;
		try {
			bytes = str.getBytes("ASCII");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("ASCII is not supported!", e);
		}
		byte[] decoded = decode(bytes);
		try {
			return new String(decoded, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unsupported charset: " + charset, e);
		}
	}

	/**
	 * Encodes a binary sequence.
	 * If data are large, i.e. if you are working with large binary files,
	 * consider to use a {@link Base64OutputStream} instead of loading too much
	 * data in memory.
	 */
	public static byte[] encode(byte[] bytes) throws RuntimeException {
		return encode(bytes, 0);
	}

	/**
	 * Encodes a binary sequence, wrapping every encoded line every
	 * <em>wrapAt</em> characters. A <em>wrapAt</em> value less than 1 disables
	 * wrapping.
	 * If data are large, i.e. if you are working with large binary files,
	 * consider to use a {@link Base64OutputStream} instead of loading too much
	 * data in memory.
	 */
	public static byte[] encode(byte[] bytes, int wrapAt)
	throws RuntimeException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			encode(inputStream, outputStream, wrapAt);
		} catch (IOException e) {
			throw new RuntimeException("Unexpected I/O error", e);
		} finally {
			try {
				inputStream.close();
			} catch (Throwable t) {
				;
			}
			try {
				outputStream.close();
			} catch (Throwable t) {
				;
			}
		}
		return outputStream.toByteArray();
	}

	/**
	 * Decodes a binary sequence.
	 * If data are large, i.e. if you are working with large binary files,
	 * consider to use a {@link Base64InputStream} instead of loading too much
	 * data in memory.
	 */
	public static byte[] decode(byte[] bytes) throws RuntimeException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			decode(inputStream, outputStream);
		} catch (IOException e) {
			throw new RuntimeException("Unexpected I/O error", e);
		} finally {
			try {
				inputStream.close();
			} catch (Throwable t) {
				;
			}
			try {
				outputStream.close();
			} catch (Throwable t) {
				;
			}
		}
		return outputStream.toByteArray();
	}

	/**
	 * Encodes data from the given input stream and writes them in the given
	 * output stream.
	 * The supplied input stream is read until its end is reached, but it's not
	 * closed by this method.
	 * The supplied output stream is nor flushed neither closed by this method.
	 */
	public static void encode(InputStream inputStream, OutputStream outputStream)
	throws IOException {
		encode(inputStream, outputStream, 0);
	}

	/**
	 * Encodes data from the given input stream and writes them in the given
	 * output stream, wrapping every encoded line every <em>wrapAt</em>
	 * characters. A wrapAt value less than 1 disables wrapping.
	 * The supplied input stream is read until its end is reached, but it's not
	 * closed by this method.
	 * The supplied output stream is nor flushed neither closed by this method.
	 */
	public static void encode(InputStream inputStream,
			OutputStream outputStream, int wrapAt) throws IOException {
		Base64OutputStream aux = new Base64OutputStream(outputStream, wrapAt);
		copy(inputStream, aux);
		aux.commit();
	}

	/**
	 * Decodes data from the given input stream and writes them in the given
	 * output stream.
	 * The supplied input stream is read until its end is reached, but it's not
	 * closed by this method.
	 * The supplied output stream is nor flushed neither closed by this method.
	 */
	public static void decode(InputStream inputStream, OutputStream outputStream)
	throws IOException {
		copy(new Base64InputStream(inputStream), outputStream);
	}

	/**
	 * Encodes data from the given source file contents and writes them in the
	 * given target file, wrapping every encoded line every <em>wrapAt</em>
	 * characters. A wrapAt value less than 1 disables wrapping.
	 */
	public static void encode(File source, File target, int wrapAt)
	throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(source);
			outputStream = new FileOutputStream(target);
			Base64.encode(inputStream, outputStream, wrapAt);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Throwable t) {
					;
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Throwable t) {
					;
				}
			}
		}
	}

	/**
	 * Encodes data from the given source file contents and writes them in the
	 * given target file.
	 */
	public static void encode(File source, File target) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(source);
			outputStream = new FileOutputStream(target);
			Base64.encode(inputStream, outputStream);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Throwable t) {
					;
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Throwable t) {
					;
				}
			}
		}
	}

	/**
	 * Decodes data from the given source file contents and writes them in the
	 * given target file.
	 */
	public static void decode(File source, File target) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(source);
			outputStream = new FileOutputStream(target);
			decode(inputStream, outputStream);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Throwable t) {
					;
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Throwable t) {
					;
				}
			}
		}
	}

	/**
	 * Copies data from a stream to another.
	 */
	private static void copy(InputStream inputStream, OutputStream outputStream)
	throws IOException {
		// 1KB buffer
		byte[] b = new byte[1024];
		int len;
		while ((len = inputStream.read(b)) != -1) {
			outputStream.write(b, 0, len);
		}
	}

}
