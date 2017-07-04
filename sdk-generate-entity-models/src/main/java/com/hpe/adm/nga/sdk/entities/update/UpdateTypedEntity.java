/*
 * Copyright 2017 Hewlett-Packard Enterprise Development Company, L.P.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hpe.adm.nga.sdk.entities.update;

import com.hpe.adm.nga.sdk.entities.TypedEntityList;
import com.hpe.adm.nga.sdk.model.TypedEntityModel;
import com.hpe.adm.nga.sdk.network.OctaneHttpClient;
import com.hpe.adm.nga.sdk.network.OctaneRequest;

/**
 * This class hold the UpdateEntities object of one entity
 */
public abstract class UpdateTypedEntity<T extends TypedEntityModel, E extends UpdateTypedEntity>
        extends TypedEntityList.TypedEntityRequest<T> {

    private T entityModel;
    private final OctaneRequest octaneRequest;

    protected UpdateTypedEntity(final Class<T> typedEntityModelClass, final OctaneHttpClient octaneHttpClient, final String urlDomain, final int iEntityId) {
        super(typedEntityModelClass);
        octaneRequest = new OctaneRequest(octaneHttpClient, urlDomain, iEntityId);
    }

    /**
     * 1. UpdateEntities Request execution with json data 2. Parse response to
     * a new EntityModel object
     */
    public final T execute() {
        return getEntityInstance(UpdateHelper.getInstance().updateEntityModel(entityModel.getWrappedEntityModel(), octaneRequest));
    }

    /**
     * set a new entity for updating
     *
     * @param entityModel The entity model to be updated
     * @return an update object with new entity
     */
    public final E entity(final T entityModel) {
        this.entityModel = entityModel;
        return (E) this;
    }
}
