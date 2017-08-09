/** * <p>Base64编码是基于64个字符(字符分别为：ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxzy0123456789+/)的编码方式， *
 *
 */
//1100011 分成Base64分组后为：011000 010110 001001 100011 即24 22 9 35，对应Base64编码的 YWJj</li> * <li>除3余1：ab=01100001 01100010，分成Base64分组后为：011000 010110 0010，0010不够6bit，需要补0为：001000，得到YWI，因为4个Base编码为一组，最后再补上'='补齐一组，即：YWI=</li> * <li>除3余2：a=011000010，分成Base64分组后为：011000 01，01不够6bit，需要补0为：010000，得到YQ，因为4个Base编码为一组，最后再补上'='补齐一组，即：YQ==</li> * </p> * @author chmod400 * */
public class BASE64 {
    private static String codeStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxzy0123456789+/";
    private static char[] code = codeStr.toCharArray();
    /** * 对字符串进行Base64编码
     * * @param str
     * * @return
     * */
    public static String encode(String str) {
        if(str == null) {
            throw new NullPointerException();
        }
        StringBuffer result = new StringBuffer();
        // 字符串转为二进制
        String binStr = str2Bin(str);
        // 6bit 为一个单元，需要补0的位数
        int tail = binStr.length() % 6;
        if(tail != 0) {
            //最后剩2bit，需要补4位，剩4位需要补2bit
            tail = 6 - tail;
        }
        for(int i = 0; i < tail; i++) {
            binStr += "0";
        }
        for(int i = 0; i < binStr.length() / 6; i++) {
            int beginIndex = i * 6;
            String s = binStr.substring(beginIndex, beginIndex+6);
            // 二进制转十进制
            int codePoint = Integer.valueOf(s, 2);
            // 对应的字符
            char c = code[codePoint];
            result.append(c);
        }
        // 需要补=的位数
        int groupNum = binStr.length() / 6;
        // 6bit为一组
        if((groupNum % 4) != 0) {
            tail = 4 - groupNum % 4;
        }
        for(int i = 0; i < tail; i++) {
            result.append("=");
        }
        return result.toString();
    }
    /**
     * * base64解码 * @param str * @return
     * */
    public static String decode(String str) {
        if(str == null) {
            throw new NullPointerException();
        }
        StringBuffer result = new StringBuffer();
        // 去除末尾的'='
        int index = str.indexOf("=");
        if (index >= 0) {
            str = str.substring(0, index);
        }
        // base64字符串转换为二进制
        String binStr = base64Str2Bin(str);
        // 将二进制按8bit一组还原成原字符
        for(int i = 0; i < binStr.length() / 8; i++) {
            int beginIndex = i * 8;
            String s = binStr.substring(beginIndex, beginIndex+8);
            String c = bin2Str(s); result.append(c);
        }
        return result.toString();
    }
    /**
     * * 字符串转换为二进制字符串
     * * @param str
     * * @return
     * */
    private static String str2Bin(String str) {
        StringBuffer sb = new StringBuffer();
        // 字符串转为字符数组
        char[] c = str.toCharArray();
        for(int i = 0; i < c.length; i++) {
            // 将每个字符转换为二进制
            String s = Integer.toBinaryString(c[i]);
            // 需要补0的长度
            int len = 8 - s.length();
            for(int j = 0; j < len; j++) {
                s = "0" + s;
            } sb.append(s);
        }
        return sb.toString();
    }
    /**
     * * Base64字符串转换为二进制字符串
     * * @param str * @return
     * */
    private static String base64Str2Bin(String str) {
        StringBuffer sb = new StringBuffer();
        // 字符串转为字符数组
        char[] c = str.toCharArray();
        for(int i = 0; i < c.length; i++) {
            // 将每个字符转换为二进制
            int index = codeStr.indexOf(c[i]);
            String s = Integer.toBinaryString(index);
            // 需要补0的长度
            int len = 6 - s.length();
            for(int j = 0; j < len; j++) {
                s = "0" + s;
            }
            sb.append(s);
        } return sb.toString();
    }
    /**
     * * 二进制转换为字符串
     * * @param binStr
     * * @return
     * */
    private static String bin2Str(String binStr) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < binStr.length() / 8; i++) {
            int beginIndex = i * 8;
            String s = binStr.substring(beginIndex, beginIndex+8);
            // 二进制转十进制
            int codePoint = Integer.valueOf(s, 2);
            // 对应的字符
            char c = Character.toChars(codePoint)[0]; sb.append(c);
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        System.out.println(str2Bin("ab"));
        //
        System.out.println(bin2Str("000001000001000001000000"));
        System.out.println(encode("a"));
        System.out.println(encode("ab"));
        System.out.println(encode("abc"));
        System.out.println(encode(""));
        System.out.println(encode(""));
        System.out.println(encode(codeStr));
        System.out.println(decode("YQ=="));
        System.out.println(decode("YWI="));
        System.out.println(decode("YWJj"));
        System.out.println(decode("QUJDREVGR0hJSktMTU5PUFFSU1RVVldYWVphYmNkZWZnaGlqa2xtbm9wcXJydHV2d3h6eTAxMjM0NTY3ODkrLw=="));
        System.out.println(decode(""));
        System.out.println(decode(null));
    }
}


