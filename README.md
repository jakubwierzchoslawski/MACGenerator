# Message Authentication Code generator


# To generate MAC two methods are avaliable:
-	public String encodeMessage(String strength, String message) - generate  HMAC for any message
- 	public String encodeMessage(String strength, String ccid, String uuid, String date) -this one can be used to generate HMAC hash to get document from CSI Web.

# HMAC plays two roles:
- authentication
- message integrity 


# Testing

	public static void main(String[] args) throws Exception {

		MACGenerator gen = new MACGenerator("some key");
		String ccid = "ccid";
		String uuid = "uuid";
		String date = "date";


		System.out.println(gen.encodeMessage(MACAlgorithms.ENCODING_STRENGTH_SHA256, ccid, uuid, date));
		System.out.println(gen.encodeMessage(MACAlgorithms.ENCODING_STRENGTH_SHA512, ccid, uuid, date));
		System.out.println(gen.encodeMessage(MACAlgorithms.ENCODING_STRENGTH_MD5, ccid, uuid, date));
		System.out.println(gen.encodeMessage(MACAlgorithms.ENCODING_STRENGTH_SHA1, ccid, uuid, date));
	}
