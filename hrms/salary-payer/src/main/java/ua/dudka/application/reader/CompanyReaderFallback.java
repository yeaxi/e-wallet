package ua.dudka.application.reader;

import org.springframework.stereotype.Component;
import ua.dudka.domain.model.Company;

import java.util.Collections;
import java.util.List;

/**
 * @author Rostislav Dudka
 */
@Component
public class CompanyReaderFallback implements CompanyReader {
    @Override
    public List<Company> readAll() {
        return Collections.emptyList();
    }
}