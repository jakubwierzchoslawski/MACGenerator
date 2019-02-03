# Message Authentication Code generator


To generate MAC two methods are avaliable:
a) 	public String encodeMessage(String strength, String message) - generate  HMAC for any message
b) 	public String encodeMessage(String strength, String ccid, String uuid, String date) -this one can be used to generate HMAC hash to get document from CSI Web.


HMAC plays two roles:
- authentication
- message integrity 


