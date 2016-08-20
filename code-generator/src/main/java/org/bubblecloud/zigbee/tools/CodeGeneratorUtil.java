package org.bubblecloud.zigbee.tools;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

/**
 * Created by tlaukkan on 4/10/2016.
 */
public class CodeGeneratorUtil {
    public static String toHex(int profileId) {
        return "0x" + Integer.toHexString(profileId);
    }

    public static Integer fromHex(String headerIdString) {
        return Integer.valueOf(StringUtils.substringAfter(headerIdString, "0x"), 16);
    }

    public static String labelToEnumerationValue(String dataType) {
        return dataType.trim().toUpperCase().replace(" ", "_").replace("-", "_").replace("/", "_").replace("(", "_").replace(")", "_");
    }

    public static String labelToUpperCamelCase(String value) {
        return WordUtils.capitalizeFully(splitCamelCase(value).replace("-", " ").replace("/", " ").replace("(", " ").replace(")", " "), new char[]{' '}).replaceAll(" ", "");
    }

    public static String upperCamelCaseToLowerCamelCase(String value) {
        return value.substring(0, 1).toLowerCase() + value.substring(1);
    }

    public static String splitCamelCase(String value) {
        return value.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }
}
