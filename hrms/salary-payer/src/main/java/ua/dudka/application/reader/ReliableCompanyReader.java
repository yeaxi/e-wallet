package ua.dudka.application.reader;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.dudka.domain.model.Company;

import java.util.Collections;
import java.util.List;

/**
 * @author Rostislav Dudka
 */
@Component
@RequiredArgsConstructor
public class ReliableCompanyReader {

    private final CompanyReader companyReader;

    @HystrixCommand(fallbackMethod = "reliable")
    public List<Company> readAll() {
        return companyReader.readAll();
    }

    private List<Company> reliable() {
        return Collections.emptyList();
    }
}