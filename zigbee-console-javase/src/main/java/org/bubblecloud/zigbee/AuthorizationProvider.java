package org.bubblecloud.zigbee;

/**
 * Authorization provider interface.
 *
 * @author Tommi Laukkanen
 */
public interface AuthorizationProvider {

    /**
     * Gets access level for given access token.
     * @param accessToken the access token
     * @return the access level
     */
    AccessLevel getAccessLevel(final String accessToken);

}
