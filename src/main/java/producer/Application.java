package producer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is the Producer Application for the Amex coding challenge. It retrieves the supplied Json from resources and
 * sends each line as a separate Rest operation to the Consumer Application.
 * 
 * In return the consumer sends the location of the persisted event resource for future CRUD operations.
 * See the Consumer javadoc for more info. 
 * 
 * @author matt
 * 
 *
 */
@SpringBootApplication
public class Application {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  private static final String CONSUMER_URL = "http://localhost:8080/events";
  private static final String SOURCE_FILENAME = "source.txt";

  // ObjectMapper provides serializing and deserializing operations for converting
  // POJO to Json and vice versa
  private ObjectMapper mapper = new ObjectMapper();

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  private InputStream getSource(String fileName) {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream stream = classLoader.getResourceAsStream(fileName);
    return stream;
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

  @Bean
  public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
    return args -> {

      InputStream is = getSource(SOURCE_FILENAME);
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      String line;
      while ((line = br.readLine()) != null) {

        // use mapper to deserialize each line of the file into and EventLine class
        EventLine eventLine = mapper.readValue(line, EventLine.class);

        // we unwrap the root element of the Json as having a nested/inner wrapper
        // elements caused problems deserializing on the Consumer
        Events[] events = eventLine.getEvents();

        // location is the URL where you would retrieve the created record from the Consumer
        // for subsequent CRUD operations
        // this is one of the core principles of Hypermedia/HATEOAS Rest
        // which apparently should be the aspiration of any Rest service
        // https://spring.io/guides/tutorials/bookmarks/
        Object location = restTemplate.postForLocation(CONSUMER_URL, events[0]);

        // logging this out for inspection
        log.info("location is " + location.toString());
      }
      is.close();
    };
  }
}
