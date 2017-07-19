package ua.dudka.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rostislav Dudka
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    private int id;

    private List<Employee> employees = new ArrayList<>();
}