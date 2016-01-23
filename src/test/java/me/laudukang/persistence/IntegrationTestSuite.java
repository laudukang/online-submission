package me.laudukang.persistence;

import me.laudukang.persistence.hibernate.FooPaginationPersistenceIntegrationTest;
import me.laudukang.persistence.hibernate.FooSortingPersistenceServiceTest;
import me.laudukang.persistence.service.FooServiceBasicPersistenceIntegrationTest;
import me.laudukang.persistence.service.FooServicePersistenceIntegrationTest;
import me.laudukang.persistence.service.ParentServicePersistenceIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ // @formatter:off
    FooServiceBasicPersistenceIntegrationTest.class
    ,FooPaginationPersistenceIntegrationTest.class
    ,FooServicePersistenceIntegrationTest.class
    ,ParentServicePersistenceIntegrationTest.class
    ,FooSortingPersistenceServiceTest.class
}) // @formatter:on
public class IntegrationTestSuite {
    //
}
