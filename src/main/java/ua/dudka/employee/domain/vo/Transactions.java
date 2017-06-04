package ua.dudka.employee.domain.vo;

import com.google.common.collect.ForwardingList;
import ua.dudka.employee.domain.Transaction;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static ua.dudka.employee.domain.Transaction.Type.REFILL;

/**
 * @author Rostislav Dudka
 */
public class Transactions extends ForwardingList<Transaction> {

    private static final Comparator<Transaction> COMPARATOR = (o1, o2) -> {
        LocalDateTime firstDate = o1.getDate();
        LocalDateTime secondDate = o2.getDate();
        int result = secondDate.compareTo(firstDate);

        return result != 0 ? result : o2.getType() == REFILL ? 1 : -1;
    };

    private List<Transaction> delegate;

    public Transactions(List<Transaction> transactions) {
        this.delegate = transactions;
        delegate.sort(COMPARATOR);
    }

    @Override
    protected List<Transaction> delegate() {
        return delegate;
    }
}