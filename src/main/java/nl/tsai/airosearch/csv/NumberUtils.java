package nl.tsai.airosearch.csv;

public class NumberUtils {

    /**
     * <p>Convert a {@code String} to a {@code Double}.</p>
     * <p>Returns {@code null} if the string is {@code null} or empty.</p>
     *
     * @param str a {@code String} to convert, may be null or empty
     *
     * @return converted {@code Double} (or null if the input is null)
     *
     * @throws NumberFormatException if the value cannot be converted
     */
    public static Double createDouble(final String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return Double.parseDouble(str);
    }

    /**
     * <p>Convert a {@code String} to a {@code Float}.</p>
     * <p>Returns {@code null} if the string is {@code null} or empty.</p>
     *
     * @param str a {@code String} to convert, may be null or empty
     *
     * @return converted {@code Float} (or null if the input is null)
     *
     * @throws NumberFormatException if the value cannot be converted
     */
    public static Float createFloat(final String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return Float.parseFloat(str);
    }

    /**
     * <p>Convert a {@code String} to a {@code Integer}.</p>
     * <p>Returns {@code null} if the string is {@code null} or empty.</p>
     *
     * @param str a {@code String} to convert, may be null or empty
     *
     * @return converted {@code Integer} (or null if the input is null)
     *
     * @throws NumberFormatException if the value cannot be converted
     */
    public static Integer createInteger(final String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return Integer.parseInt(str);
    }
}
