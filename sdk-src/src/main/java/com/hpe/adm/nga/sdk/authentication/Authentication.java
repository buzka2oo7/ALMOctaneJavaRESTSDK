package com.hpe.adm.nga.sdk.authentication;

/**
 *
 * Interface of Authentication , hold contract functions. <br>
 * Provided implementations: {@link SimpleUserAuthentication}, {@link ClientAuthentication} <br>
 * Note that the default implementations keep the credentials in memory. <br>
 * It is recommended that you implement {@link #getAuthenticationString() getAuthenticationString} so that the credentials are not stored in memory.
 */
public interface Authentication {

    /**
     * Holds the string that will be sent in the body of the authentication post
     * @return The authentication string.  Either user/pass or client/secret
     */
    String getAuthenticationString();

    /**
     * Returns the HPECLIENTTYPE header that is added to all calls to the REST API.  See the REST API documentation for
     * further information
     * This will only be used if it is non-empty
     * @return The String that will be sent as the HPECLIENTTYPE.  If none is sent then this should be null
     */
    String getClientHeader();
}


