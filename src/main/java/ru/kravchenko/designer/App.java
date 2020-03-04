package ru.kravchenko.designer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ru.kravchenko.designer.api.IBootstrapService;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		IBootstrapService bootstrapService = SpringApplication.run(App.class, args).getBean(IBootstrapService.class);
		bootstrapService.init();
	}

}
