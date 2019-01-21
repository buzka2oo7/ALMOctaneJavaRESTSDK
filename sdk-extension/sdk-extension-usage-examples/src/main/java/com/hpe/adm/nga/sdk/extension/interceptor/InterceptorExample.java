package com.hpe.adm.nga.sdk.extension.interceptor;

import com.hpe.adm.nga.sdk.Octane;
import com.hpe.adm.nga.sdk.authentication.Authentication;
import com.hpe.adm.nga.sdk.authentication.SimpleUserAuthentication;
import com.hpe.adm.nga.sdk.extension.OctaneConnectionConstants;
import com.hpe.adm.nga.sdk.extension.OctaneExtensionUtil;
import com.hpe.adm.nga.sdk.extension.network.RequestInterceptor;
import com.hpe.adm.nga.sdk.extension.network.ResponseInterceptor;
import com.hpe.adm.nga.sdk.extension.network.google.InterceptorGoogleHttpClient;
import com.hpe.adm.nga.sdk.model.EntityModel;
import com.hpe.adm.nga.sdk.model.StringFieldModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class InterceptorExample {

    private static final Logger logger = LoggerFactory.getLogger(InterceptorGoogleHttpClient.class.getName());

    public static void main(String[] args){

        OctaneExtensionUtil.enable();

        Authentication authentication
                = new SimpleUserAuthentication(
                OctaneConnectionConstants.username,
                OctaneConnectionConstants.password);

        InterceptorGoogleHttpClient.addRequestInterceptor(new RequestInterceptor() {

            @Override
            public String url(String url) {
                String newUrl = url.replace("work_items","defects");
                logger.info("Intercepting request url: {} ->  {}", url, newUrl);
                return newUrl;
            }

            @Override
            public String content(String content) {
                logger.info("Intercepting request content: {}", content);
                return content;
            }

            @Override
            public Map<String, Object> headers( Map<String, Object> headers) {
                logger.info("Intercepting request headers: (printing)");
                headers.put("DUMMY", "HEADER");
                headers.forEach((key, value) -> logger.info(key +": " + value));
                return headers;
            }
        });

        InterceptorGoogleHttpClient.addResponseInterceptor(new ResponseInterceptor() {
            @Override
            public Map<String, Object> headers( Map<String, Object> headers) {
                logger.info("Intercepting response headers: (printing)");
                headers.put("DUMMY", "HEADER");
                headers.forEach((key, value) -> logger.info(key +": " + value));
                return headers;
            }
        });

        Octane octane =
                new Octane.Builder(authentication)
                        .Server(OctaneConnectionConstants.urlDomain)
                        .sharedSpace(OctaneConnectionConstants.sharedspaceId)
                        .workSpace(OctaneConnectionConstants.workspaceId)
                        .build();

        //Fetch defects as an example, and update one of them
        Collection<EntityModel> entities = octane.entityList("work_items").get().addFields("name").execute();

        if(entities.size() < 1) return;

        EntityModel entityModel = entities.iterator().next();

        StringFieldModel stringFieldModel = (StringFieldModel) entityModel.getValue("name");
        stringFieldModel.setValue("name", "entity_" + ZonedDateTime.now());
        entityModel.setValue(stringFieldModel);

        octane.entityList("work_items").update().entities(Collections.singletonList(entityModel)).execute();
    }

}
