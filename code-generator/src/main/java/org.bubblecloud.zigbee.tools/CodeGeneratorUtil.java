package org.bubblecloud.zigbee.tools;

import org.apache.commons.lang.StringUtils;

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

    static String naturalNameToEnumerationValue(String dataType) {
        return dataType.trim().toUpperCase().replace(" ", "_").replace("-", "_").replace("/", "_").replace("(", "_").replace(")", "_");
    }
}
