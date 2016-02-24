package br.com.fieldrent;

import br.com.fieldrent.model.Company;
import br.com.fieldrent.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FieldrentApplication {

	public static void main(String[] args) {
		SpringApplication.run(FieldrentApplication.class, args);
	}
}
