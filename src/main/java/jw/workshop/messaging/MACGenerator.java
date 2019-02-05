package jw.workshop.messaging;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
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
	private String dateFormat = "MM/dd/yyyy";

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
			//secret_key = new SecretKeySpec(this.HMAC_KEY.getBytes(MACConfiguration.HMAC_KEY_ENCODING), strength);
			
			secret_key = new SecretKeySpec( tokenize(this.HMAC_KEY,true), strength);
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
		String patternToHash = ccid.toUpperCase() + getDateFormatted(date, this.dateFormat) + uuid;
		return patternToHash;
	}

	/**
	 * 
	 * @param dateStr
	 * @return
	 */
	private String getDateFormatted(String dateStr, String dateFormat) {
		SimpleDateFormat dt = new SimpleDateFormat(dateFormat);
		Date date = null;
		String formattedDate = null;

		try {
			date = dt.parse(dateStr);
			formattedDate = dt.format(date);
		} catch (ParseException e) {
			throw new HMACException("Date formattting exception", e);
		}

		return formattedDate;
	}

	/**
	 * 
	 * @param str
	 * @param length
	 * @param car
	 * @return
	 */
	public static String lPad(String str, Integer length, char car) {
		return (str + String.format("%" + length + "s", "").replace(" ", String.valueOf(car))).substring(0, length);
	}

	/**
	 * 
	 * @param str
	 * @param length
	 * @param car
	 * @return
	 */
	public static String rPad(String str, Integer length, char car) {
		return (String.format("%" + length + "s", "").replace(" ", String.valueOf(car)) + str).substring(str.length(),
				length + str.length());
	}

	/**
	 * 
	 * @param key
	 * @param keyIsHexString
	 * @return
	 */
	public byte[] tokenize(String key, boolean keyIsHexString) {
		if (null == key) {
			return null;
		}

		if (key.length() == 0) {
			return null;
		}

		byte[] bytes;
		if (keyIsHexString) {
			if ((key.length() % 2) != 0) {
				return null;
			}

			bytes = new byte[key.length() / 2];

			StringBuilder buf = new StringBuilder(2);
			for (int i = 0; i < key.length(); i++) {

				buf.append(key.charAt(i));
				if (buf.length() > 1) {
					byte[] array;
					try {
						array = Hex.decodeHex(buf.toString());
					} catch (DecoderException e) {
						throw new HMACException("Cannot convert string value: " + buf.toString() + " to byte.", e);
					}
					bytes[(int) Math.floor((double) i / 2)] = array[0];

					buf.setLength(0);
					array = null;
				}
			}
		} else {
			bytes = new byte[key.length()];
			for (int i = 0; i < key.length(); i++)
				bytes[i] = (byte) key.charAt(i);
		}
		return bytes;
	}

	/**
	 * 
	 * @param args
	 * @throws DecoderException
	 */
	public static void main(String[] args) throws DecoderException {
		MACGenerator macg = new MACGenerator("7367523e34564a2461347470463e383659333836266d6f552b7326443b3b3334");

		byte[] tablica = macg.tokenize("7367523e34564a2461347470463e383659333836266d6f552b7326443b3b3334", true);
		for (byte b : tablica) {
			System.out.println("-> " + b);
		}

	}

}
