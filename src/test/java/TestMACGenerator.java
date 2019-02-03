import jw.workshop.messaging.MACGenerator;
import jw.workshop.messaging.mac.MACAlgorithms;

public class TestMACGenerator {

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

}
