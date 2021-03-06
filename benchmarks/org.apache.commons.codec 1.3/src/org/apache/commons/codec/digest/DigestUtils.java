/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.codec.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;


/**
 * Operations to simplifiy common {@link java.security.MessageDigest} tasks.
 * This class is thread safe.
 * 
 * @author Apache Software Foundation
 * @version $Id: DigestUtils.java 480406 2006-11-29 04:56:58 +0000 (Mi, 29 Nov
 *          2006) bayard $
 */
                    public class DigestUtils {

	/**
	 * Returns a <code>MessageDigest</code> for the given
	 * <code>algorithm</code>.
	 * 
	 * @param algorithm
	 *            the name of the algorithm requested. See <a
	 *            href="http://java.sun.com/j2se/1.3/docs/guide/security/CryptoSpec.html#AppA">Appendix
	 *            A in the Java Cryptography Architecture API Specification &
	 *            Reference</a> for information about standard algorithm names.
	 * @return An MD5 digest instance.
	 * @see MessageDigest#getInstance(String)
	 * @throws RuntimeException
	 *             when a {@link java.security.NoSuchAlgorithmException} is
	 *             caught.
	 */
	public static                                 MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Returns an MD5 MessageDigest.
	 * 
	 * @return An MD5 digest instance.
	 * @throws RuntimeException
	 *             when a {@link java.security.NoSuchAlgorithmException} is
	 *             caught.
	 */
	 static private MessageDigest getMd5Digest() {
		return getDigest("MD5");
	}

	/**
	 * Returns an SHA-1 digest.
	 * 
	 * @return An SHA-1 digest instance.
	 * @throws RuntimeException
	 *             when a {@link java.security.NoSuchAlgorithmException} is
	 *             caught.
	 */
	 static private MessageDigest getShaDigest() {
		return getDigest("SHA");
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 16 element
	 * <code>byte[]</code>.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest
	 */
	 static public byte[] md5(byte[] data) {
		return getMd5Digest().digest(data);
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 16 element
	 * <code>byte[]</code>.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest
	 */
	 static public byte[] md5(String data) {
		return md5(data.getBytes());
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 32 character hex
	 * string.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest as a hex string
	 */
	 static public String md5Hex(byte[] data) {
		return new String(Hex.encodeHex(md5(data)));
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 32 character hex
	 * string.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest as a hex string
	 */
	 static public String md5Hex(String data) {
		return new String(Hex.encodeHex(md5(data)));
	}

	/**
	 * Calculates the SHA-1 digest and returns the value as a
	 * <code>byte[]</code>.
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA-1 digest
	 */
	 static public byte[] sha(byte[] data) {
		return getShaDigest().digest(data);
	}

	/**
	 * Calculates the SHA-1 digest and returns the value as a
	 * <code>byte[]</code>.
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA-1 digest
	 */
	 static public byte[] sha(String data) {
		return sha(data.getBytes());
	}

	/**
	 * Calculates the SHA-256 digest and returns the value as a
	 * <code>byte[]</code>.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA-256 digest
	 */
	 static public byte[] sha256(byte[] data) {
		return getSha256Digest().digest(data);
	}

	/**
	 * Calculates the SHA-256 digest and returns the value as a
	 * <code>byte[]</code>.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA-256 digest
	 */
	 static public byte[] sha256(String data) {
		return sha256(data.getBytes());
	}

	/**
	 * Calculates the SHA-256 digest and returns the value as a hex string.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA-256 digest as a hex string
	 */
	 static public String sha256Hex(byte[] data) {
		return new String(Hex.encodeHex(sha256(data)));
	}

	/**
	 * Calculates the SHA-256 digest and returns the value as a hex string.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA-256 digest as a hex string
	 */
	 static public String sha256Hex(String data) {
		return new String(Hex.encodeHex(sha256(data)));
	}

	/**
	 * Calculates the SHA-384 digest and returns the value as a
	 * <code>byte[]</code>.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA-384 digest
	 */
	 static public byte[] sha384(byte[] data) {
		// FIXME: check Sun docs for how to get a sha 384 digest
		return getSha384Digest().digest(data);
	}

	/**
	 * Calculates the SHA-384 digest and returns the value as a
	 * <code>byte[]</code>.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA-384 digest
	 */
	 static public byte[] sha384(String data) {
		return sha384(data.getBytes());
	}

	/**
	 * Calculates the SHA-384 digest and returns the value as a hex string.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA-384 digest as a hex string
	 */
	 static public String sha384Hex(byte[] data) {
		return new String(Hex.encodeHex(sha384(data)));
	}

	/**
	 * Calculates the SHA-384 digest and returns the value as a hex string.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA-384 digest as a hex string
	 */
	 static public String sha384Hex(String data) {
		return new String(Hex.encodeHex(sha384(data)));
	}

	/**
	 * Calculates the SHA-512 digest and returns the value as a
	 * <code>byte[]</code>.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA-512 digest
	 */
	 static public byte[] sha512(byte[] data) {
		return getSha512Digest().digest(data);
	}

	/**
	 * Calculates the SHA-512 digest and returns the value as a
	 * <code>byte[]</code>.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA-512 digest
	 */
	 static public byte[] sha512(String data) {
		return sha512(data.getBytes());
	}

	/**
	 * Calculates the SHA-512 digest and returns the value as a hex string.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA-512 digest as a hex string
	 */
	 static public String sha512Hex(byte[] data) {
		return new String(Hex.encodeHex(sha512(data)));
	}

	/**
	 * Calculates the SHA-512 digest and returns the value as a hex string.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA-512 digest as a hex string
	 */
	 static public String sha512Hex(String data) {
		return new String(Hex.encodeHex(sha512(data)));
	}

	/**
	 * Calculates the SHA-1 digest and returns the value as a hex string.
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA-1 digest as a hex string
	 */
	 static public String shaHex(byte[] data) {
		return new String(Hex.encodeHex(sha(data)));
	}

	/**
	 * Calculates the SHA-1 digest and returns the value as a hex string.
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA-1 digest as a hex string
	 */
	 static public String shaHex(String data) {
		return new String(Hex.encodeHex(sha(data)));
	}

	/**
			 * Returns an SHA-256 digest.
			 * <p>
			 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
			 * </p>
			 * 
			 * @return An SHA-256 digest instance.
			 * @throws RuntimeException
			 *             when a {@link java.security.NoSuchAlgorithmException} is
			 *             caught.
			 */
			 static private MessageDigest getSha256Digest() {
				return getDigest("SHA-256");
			}

	/**
			 * Returns an SHA-384 digest.
			 * <p>
			 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
			 * </p>
			 * 
			 * @return An SHA-384 digest instance.
			 * @throws RuntimeException
			 *             when a {@link java.security.NoSuchAlgorithmException} is
			 *             caught.
			 */
			 static private MessageDigest getSha384Digest() {
				return getDigest("SHA-384");
			}

	/**
			 * Returns an SHA-512 digest.
			 * <p>
			 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
			 * </p>
			 * 
			 * @return An SHA-512 digest instance.
			 * @throws RuntimeException
			 *             when a {@link java.security.NoSuchAlgorithmException} is
			 *             caught.
			 */
			 static private MessageDigest getSha512Digest() {
				return getDigest("SHA-512");
			}
}
