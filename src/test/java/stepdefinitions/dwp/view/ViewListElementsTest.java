package stepdefinitions.dwp.view;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

public class ViewListElementsTest {
    @Test
    public void testListIndicesHell() throws Exception {
        String members[] = {"Chris", "Bob", "Dmitry", "Pascal", "Bob", "Fernando"};
        List<String> team = Arrays.asList(members);
        for(String member: members) {
            List<Integer> indices = collectIndicesOf(team, member);
            assertTrue(team.indexOf(member) + 1 == indices.get(0));
        }
    }

    private List<Integer> collectIndicesOf(List<String> team, String mate) {
        AtomicInteger index = new AtomicInteger(1);
        List<Integer> indices = IntStream.range(1, team.size() + 1).filter(i ->
            index.compareAndSet(i, i + 1) &
                team.get(i - 1).contains(mate))
            .boxed()
            .collect(Collectors.toList());
        return indices;
    }
}
