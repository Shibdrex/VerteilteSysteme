package dh.distributed.systems.TODO_Liste_Session;

import org.springframework.boot.SpringApplication;

public class TestTodoListeSessionApplication {

	public static void main(String[] args) {
		SpringApplication.from(TodoListeSessionApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
