package stepdefinitions.dwp.autocrat.flow;

import com.essent.automation.autocrat.Model;

import java.util.function.Predicate;

public interface FlowAwarePredicate<T> extends Predicate<T> {
    Model.Execution build(T input);
}
