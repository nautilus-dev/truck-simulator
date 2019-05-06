package com.hortonworks.solution;

import akka.actor.UntypedActor;
import com.hortonworks.simulator.impl.domain.transport.MobileEyeEvent;
import com.hortonworks.simulator.impl.domain.transport.Truck;
import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;
import com.mongodb.DBObject;



public class MongoDBSensorEventCollector extends UntypedActor {

  private MongoClient mongoClntObj = null;
  private DB dbObj = null;
  private DBCollection collectionObj = null;

  private static final String TOPIC = "truck";
  private int qos = 2;
  private String host = Lab.host;
  private String port = Lab.port;
  private String db = Lab.db;
  private String collection = Lab.collection;

  private Logger logger = Logger.getLogger(this.getClass());

  public MongoDBSensorEventCollector() throws MongoException {

      logger.info("Connecting to MongoDB : " + host + ":" + port + " " + db + " " + collection);
      mongoClntObj = new MongoClient(host, Integer.parseInt(port));
      dbObj = mongoClntObj.getDB(db);
      collectionObj = dbObj.getCollection(collection);
      logger.info("Connected succesfully");
  }

  @Override
  public void onReceive(Object event) throws Exception {
    MobileEyeEvent mee = (MobileEyeEvent) event;
    String eventToPass = null;
    if (Lab.format.equals(Lab.JSON)) {
        eventToPass = mee.toJSON();
    } else {
        logger.error("Cannot send Non-JSON to MongoDB");
    }
    DBObject dbObject = (DBObject) JSON.parse(eventToPass);

    try {
        logger.debug("Publishing message to MongoDB: "+eventToPass);
        collectionObj.insert(dbObject);
    } catch (MongoException e) {
      logger.error("Error sending event[" + eventToPass + "] to MongoDB topic", e);
    }
  }
}
