package dh.distributed.systems.List_Service;

import org.springframework.boot.SpringApplication;

public class TestListServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(ListServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
