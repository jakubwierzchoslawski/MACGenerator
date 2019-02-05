import jw.workshop.messaging.MACGenerator;
import jw.workshop.messaging.mac.MACAlgorithms;

public class TestMACGenerator {

	public static void main(String[] args) throws Exception {
		
		MACGenerator gen = new MACGenerator("7367523e34564a2461347470463e383659333836266d6f552b7326443b3b3334");
		String ccid = "Metropolis";
		String uuid = "302345612";
		String date = "2/5/2019 8:48:15 AM";
		String message = "METROPOLIS02/05/2019302345612";
		
		System.out.println("-----------");
		//System.out.println("SHA256: " + gen.encodeMessage(MACAlgorithms.ENCODING_STRENGTH_SHA256, ccid, uuid, date));
		//System.out.println("SHA512: " + gen.encodeMessage(MACAlgorithms.ENCODING_STRENGTH_SHA512, ccid, uuid, date));
		System.out.println("MD5 full message: " + gen.encodeMessage(MACAlgorithms.ENCODING_STRENGTH_MD5, message));
		System.out.println("MD5 composite message: " + gen.encodeMessage(MACAlgorithms.ENCODING_STRENGTH_MD5, ccid, uuid, date));
	}

}
