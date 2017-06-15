package com.standard.demo.dwhelloworld.da;

import com.standard.demo.dwhelloworld.da.entity.Actor;
import com.standard.test.base.BasePersistenceTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.Handle;

import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * test cases for ActorDao
 */
public class ActorDaoTest extends BasePersistenceTest {

  private ActorDao dao;
  private Handle handle;

  /**
   * See flyway migration for parent objects
   */
  @Before
  public void before() {
    handle = jdbiResource.getHandle();
  }

  @After
  public void after() {
    handle.close();
  }

  @Test
  public void testRead() {
    dao = handle.attach(ActorDao.class);

    final Long actorId = 1L;
    final String username = "jsmith";

    Actor actor = dao.read(actorId);
    assertNotNull(actor);

    assertThat(actor.getUsername(), equalTo(username));
    assertThat(actor.getNumberOfLogins(), equalTo(0));
    assertThat(actor.getRecordVersion(), equalTo(0));
  }

  @Test
  public void testCreate() {
    dao = handle.attach(ActorDao.class);

    Actor actor = new Actor(
        "chewbacca",
        "test-user",
        LocalDateTime.now()
    );

    Long actorId = dao.create(actor);
    assertNotNull(actorId);
    assertThat(actorId, equalTo(3L));

    Actor persistedActor = dao.read(actorId);
    assertNotNull(persistedActor);
    assertThat(persistedActor.getUsername(), equalTo("chewbacca"));
    assertThat(persistedActor.getNumberOfLogins(), equalTo(0));
    assertThat(persistedActor.getRecordVersion(), equalTo(0));
  }

  @Test
  public void testUpdate() {
    dao = handle.attach(ActorDao.class);

    final Long actorId = 1L;
    final String username = "jsmith";

    Actor actor = dao.read(actorId);
    assertNotNull(actor);

    assertThat(actor.getUsername(), equalTo(username));
    assertThat(actor.getNumberOfLogins(), equalTo(0));
    assertThat(actor.getRecordVersion(), equalTo(0)); // rec ver = 0

    actor.setNumberOfLogins(1);
    actor.setUpdatedBy("testUpdate test");
    actor.setUpdatedTimestamp(LocalDateTime.now());

    int numberOfFecordsUpdated = dao.update(actor);
    assertThat(numberOfFecordsUpdated, equalTo(1));

    Actor updatedActor = dao.readByUsername(username);

    assertThat(updatedActor.getId(), equalTo(1L));
    assertThat(updatedActor.getUsername(), equalTo(username));
    assertThat(updatedActor.getNumberOfLogins(), equalTo(1));
    assertThat(updatedActor.getRecordVersion(), equalTo(1)); // rec ver = 1

  }
}
