package fr.jlc.polytech.scheduler.core.timeline;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

public class TimelineControllerTest extends Application {
	
	@Override
	@Test
	public void start(Stage primaryStage) throws Exception {
		BorderPane bp_main = new BorderPane();
		
		TimelineController timeline = new TimelineController();
		bp_main.setCenter(timeline.getView());
		
		timeline.getTimeline().addTimeline();
		timeline.getTimeline().addTimeline();
		timeline.getTimeline().addTimeline();
		
		timeline.getTimeline().addEvent(0, new EventBuilder<String>()
				.setStart(0)
				.setEnd(10)
				.createEvent());
		timeline.getTimeline().addEvent(0, new EventBuilder<String>()
				.setStart(15)
				.setEnd(17)
				.createEvent());
		timeline.getTimeline().addEvent(0, new EventBuilder<String>()
				.setStart(19)
				.setEnd(19)
				.createEvent());
		timeline.getTimeline().addEvent(0, new EventBuilder<String>()
				.setStart(32)
				.setEnd(36)
				.createEvent());
		
		timeline.getTimeline().addEvent(1, new EventBuilder<String>()
				.setStart(5)
				.setEnd(9)
				.createEvent());
		timeline.getTimeline().addEvent(1, new EventBuilder<String>()
				.setStart(15)
				.setEnd(18)
				.createEvent());
		
		System.out.println(timeline.getTimeline().toString());
		
		primaryStage.setTitle("TimelineControllerTest");
		primaryStage.setScene(new Scene(bp_main));
		primaryStage.show();
		
		timeline.update();
	}
	
	@Test
	public static void main(String[] args) {
		launch(args);
	}
}
