package com.hpe.adm.nga.sdk.tests.crud;

import com.hpe.adm.nga.sdk.model.EntityModel;
import com.hpe.adm.nga.sdk.query.Query;
import com.hpe.adm.nga.sdk.tests.base.TestBase;
import com.hpe.adm.nga.sdk.utils.CommonUtils;
import com.hpe.adm.nga.sdk.utils.QueryUtils;
import com.hpe.adm.nga.sdk.utils.generator.DataGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

/**
 *
 * Created by Guy Guetta on 25/04/2016.
 */
public class TestDeleteEntity extends TestBase {
    public TestDeleteEntity() {
        entityName = "releases";
    }

    @Test
    public void testDeleteEntityById() throws Exception{
        Collection<EntityModel> generatedEntity = DataGenerator.generateEntityModel(octane, entityName);
        Collection<EntityModel> entityModels = entityList.create().entities(generatedEntity).execute();

        EntityModel entityModel = entityModels.iterator().next();
        String entityId = CommonUtils.getIdFromEntityModel(entityModel);

        entityList.at(entityId).delete().execute();

        Collection<EntityModel> getEntity = entityList.get().execute();

        List<String> entityIds = CommonUtils.getIdFromEntityModelCollection(getEntity);

        Assert.assertFalse(entityIds.contains(entityId));
    }

    @Test
    public void testDeleteEntitiesByQuery() throws Exception{
        Collection<EntityModel> generatedEntity = DataGenerator.generateEntityModelCollection(octane, entityName);
        Collection<EntityModel> entityModels = entityList.create().entities(generatedEntity).execute();
        List<String> entityIds = CommonUtils.getIdFromEntityModelCollection(entityModels);

        Query query = QueryUtils.getQueryForIds(entityIds);

        entityList.delete().query(query).execute();

        Collection<EntityModel> getEntity = entityList.get().execute();

        List<String> actualEntityIds = CommonUtils.getIdFromEntityModelCollection(getEntity);

        //check there are no common ids
        actualEntityIds.retainAll(entityIds);

        Assert.assertTrue(actualEntityIds.isEmpty());
    }
}
