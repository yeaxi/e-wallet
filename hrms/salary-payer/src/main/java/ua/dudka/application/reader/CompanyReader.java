package ua.dudka.application.reader;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ua.dudka.domain.model.Company;

import java.util.List;

/**
 * @author Rostislav Dudka
 */
@FeignClient(value = "hrm-service", fallback = CompanyReaderFallback.class)
public interface CompanyReader {

    @GetMapping("/api/companies")
    List<Company> readAll();
}