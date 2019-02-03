package jw.workshop.messaging;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import jw.workshop.messaging.mac.HMACException;
import jw.workshop.messaging.mac.MACAlgorithms;
import jw.workshop.messaging.mac.MACConfiguration;

/**
 * 
 * @author jotwu
 *
 */
public class MACGenerator {

	private String HMAC_KEY;

	/**
	 * 
	 * @param key
	 */
	public MACGenerator(String key) {
		this.HMAC_KEY = key;
	}

	/**
	 * 
	 * @param strength
	 * @param message
	 * @return
	 */
	public String encodeMessage(String strength, String message) {
		String encodedMessage = null;
		Mac mac;

		if (strength == null) {
			strength = MACAlgorithms.ENCODING_STRENGTH_SHA512;
		}

		try {
			mac = Mac.getInstance(strength);
			SecretKeySpec secret_key;
			secret_key = new SecretKeySpec(this.HMAC_KEY.getBytes(MACConfiguration.HMAC_KEY_ENCODING), strength);
			mac.init(secret_key);
			// Base64.encodeBase64String(sha256_HMAC.doFinal(data.getBytes(MACConfiguration.MESSAGE_ENCODING)));
			encodedMessage = Hex.encodeHexString(mac.doFinal(message.getBytes(MACConfiguration.MESSAGE_ENCODING)));

		} catch (NoSuchAlgorithmException e) {
			throw new HMACException("No such algorithm", e);
		} catch (IllegalStateException e) {
			throw new HMACException("Illegal state exception", e);
		} catch (UnsupportedEncodingException e) {
			throw new HMACException("Unsupported encoding exception", e);
		} catch (InvalidKeyException e) {
			throw new HMACException("Unsupported encoding exception", e);
		}

		return encodedMessage;

	}

	/**
	 * 
	 * @param strength
	 * @param ccid
	 * @param uuid
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public String encodeMessage(String strength, String ccid, String uuid, String date) throws Exception {
		return this.encodeMessage(strength, createMessage(ccid, uuid, date));
	}

	/**
	 * Message builded based on CSI WEB following parameters:
	 * 
	 * @param ccid
	 * @param uuid
	 * @param date
	 * @return
	 */
	private String createMessage(String ccid, String uuid, String date) {
		return ccid + uuid + date;
	}

}
