package libraryMGMTT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LibraryMgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryMgmtApplication.class, args);
	}

}
