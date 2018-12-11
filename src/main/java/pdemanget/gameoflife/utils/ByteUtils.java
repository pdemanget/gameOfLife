package pdemanget.gameoflife.utils;


/**
 * bytes transcoding manipulation.
 * @author pdemanget
 *
 */
public class ByteUtils {
	
	/**
	 * BigEndian transcoding
	 * @param long
	 * @return bytes
	 */
	public static byte[] longToBytesBE(long l) {
	    byte[] result = new byte[8];
//	    for (int i = 7; i >= 0; i--) {
	    for (int i = 0; i < 8; i++) {
	        result[i] = (byte)(l & 0xFF);
	        l >>= 8;
	    }
	    return result;
	}

	public static long bytesToLongBE(byte[] b) {
	    long result = 0;
	    for (int i = 7; i >= 0; i--) {
	        result <<= 8;
	        result |= (b[i] & 0xFF);
	    }
	    return result;
	}
	
	public static int unsignedByte(byte b) {
		return b & 0xFF;
	}

}
