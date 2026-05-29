import org.apache.commons.codec.binary.Base64;

public class MD5Utils {
    public static String getMD5Str(String strValue) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String((byte[])md5.digest(strValue.getBytes()));
    }

    public static void main(String[] args) {
        try {
            String md5 = MD5Utils.getMD5Str("lkmc2");
            System.out.println(md5);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
