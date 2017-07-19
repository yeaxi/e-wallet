package ua.dudka.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rostislav Dudka
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private int id;
    private Salary salary;
}