package life.zwp.community.utils;

import java.util.Random;

public enum RandCodeEnumUtils {
	/**
	 * 混合字符串
	 */
	ALL_CHAR("123456789abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ"),
	/**
	 * 字符
	 */
	LETTER_CHAR("abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ"),
	/**
	 * 小写字母
	 */
	LOWER_CHAR("abcdefghjkmnpqrstuvwxyz"),
	/**
	 * 数字
	 */
	NUMBER_CHAR("123456789"),
	/**
	 * 大写字符
	 */
	UPPER_CHAR("ABCDEFGHJKMNPQRSTUVWXYZ");
	/**
	 * 待生成的字符串
	 */
	private String charStr;

	/**
	 * @param charStr
	 */
	private RandCodeEnumUtils(final String charStr) {
		this.charStr = charStr;
	}

	/**
	 * 生产随机验证码
	 *
	 * @param codeLength
	 *            验证码的长度
	 * @return 验证码
	 */
	public String generateStr(final int codeLength) {
		final StringBuffer sb = new StringBuffer();
		final Random random = new Random();
		final String sourseStr = getCharStr();

		for (int i = 0; i < codeLength; i++) {
			sb.append(sourseStr.charAt(random.nextInt(sourseStr.length())));
		}

		return sb.toString();
	}

	/**
	 * @return the {@link #charStr}
	 */
	public String getCharStr() {
		return charStr;
	}

	public static void main(String[] args) {
		String code = RandCodeEnumUtils.ALL_CHAR.generateStr(4);
		System.err.println(code);
	}
}
