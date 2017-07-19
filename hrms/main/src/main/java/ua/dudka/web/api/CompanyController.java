package ua.dudka.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dudka.domain.model.Company;
import ua.dudka.repository.CompanyRepository;

import java.util.Collection;

/**
 * @author Rostislav Dudka
 */
@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyRepository companyRepository;

    @GetMapping()
    public Collection<Company> getCompanies() {
        return companyRepository.findAll();
    }
}
