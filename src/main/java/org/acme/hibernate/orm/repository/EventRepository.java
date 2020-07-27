package org.acme.hibernate.orm.repository;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.*;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;
import org.acme.hibernate.orm.domain.Event;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


@ApplicationScoped
public class EventRepository {
    @Inject
    EntityManager entityManager;

    public List<Event> findAll() {
        List<Event> events = entityManager.createNamedQuery("Events.findAll", Event.class)
                .getResultList();
        events.sort(Comparator.comparing(o -> o.getStartDate()));
        return events ;
    }

    public Event findEventById(int id) {
        Event event = entityManager.find(Event.class, id);
        if (event == null) {
            throw new WebApplicationException("Event with id of " + id + " does not exist.", 404);
        }
        return event;
    }

    @Transactional
    public Event updateEvent(Event event) {
        Event eventToUpdate = findEventById(event.getId());
        eventToUpdate.setDescription(event.getDescription());
        eventToUpdate.setName(event.getName());
        eventToUpdate.setEndDate(event.getEndDate());
        eventToUpdate.setStartDate(event.getStartDate());
        eventToUpdate.setImage(event.getImage());
        eventToUpdate.setStatus(event.getStatus());
        eventToUpdate.setType(event.getType());
        entityManager.flush();
        return eventToUpdate;
    }

    @Transactional
    public void createEvent(Event event) {
      entityManager.persist(event);
    }

    @Transactional
    public void deleteEvent(int id) {
        Event c = findEventById(id);
        entityManager.remove(c);
    }

    @GET
    @Path("/getbyType/{type}")
    public Event[] getByType(@PathParam String type) {
        Event[] events = entityManager.createQuery("select event from Event event " +
                "where event.type = '" + type +  "'", Event.class).getResultList().toArray(new Event[0]);
        return events ;
    }

    public String subscribeFirebaseEvent(int id){



        FileInputStream refreshToken = null;
        TopicManagementResponse response = null;
        try {

            FileInputStream serviceAccount =
                    new FileInputStream("D://FireBase/slido-5ecb3-firebase-adminsdk-ix36v-ae66a94177.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://slido-5ecb3.firebaseio.com")
                    .build();


            if(FirebaseApp.getApps().isEmpty()) { //<--- check with this line
                FirebaseApp.initializeApp(options);
            }

            List<String> registrationTokens = Arrays.asList("token");
            response = FirebaseMessaging.getInstance().subscribeToTopic(
                    registrationTokens, "event-" + id );
            System.out.println(response.getSuccessCount() + " tokens were subscribed successfully");
            return String.valueOf(response.getSuccessCount());

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("aaaa " + e.getLocalizedMessage());
        } catch (FirebaseMessagingException e) {
            System.out.println("stream " + e.getMessage());
        }
        return "aaaaa";
    }

}
