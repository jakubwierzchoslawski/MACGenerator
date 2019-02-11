# Message Authentication Code generator


# To generate MAC two methods are avaliable:
-	public String encodeMessage(String strength, String message) - generate  HMAC for any message
- 	public String encodeMessage(String strength, String ccid, String uuid, String date) -this one can be used to generate HMAC hash to get document from CSI Web.

# HMAC plays two roles:
- authentication
- message integrity 


# Testing

	public static void main(String[] args) throws Exception {
		
		MACGenerator gen = new MACGenerator("");
		String ccid = "";
		String uuid = "";
		String date = "";
		String message = "";
		
		System.out.println("-----------");
		//System.out.println("SHA256: " + gen.encodeMessage(MACAlgorithms.ENCODING_STRENGTH_SHA256, ccid, uuid, date));
		//System.out.println("SHA512: " + gen.encodeMessage(MACAlgorithms.ENCODING_STRENGTH_SHA512, ccid, uuid, date));
		System.out.println("MD5 full message: " + gen.encodeMessage(MACAlgorithms.ENCODING_STRENGTH_MD5, message));
		System.out.println("MD5 composite message: " + gen.encodeMessage(MACAlgorithms.ENCODING_STRENGTH_MD5, ccid, uuid, date));
	}
