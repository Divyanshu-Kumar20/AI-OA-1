import java.util.*;

public class HanoiDFS {

    static class State {
        List<List<Integer>> pegs;

        State(List<List<Integer>> p) { pegs = p; }

        boolean isGoal() {
            return pegs.get(0).isEmpty() &&
                   pegs.get(1).isEmpty() &&
                   pegs.get(2).equals(Arrays.asList(3, 2, 1));
        }

        public String toString() { return pegs.toString(); }
    }

    static List<List<Integer>> copy(List<List<Integer>> pegs) {
        List<List<Integer>> newPegs = new ArrayList<>();
        for (List<Integer> peg : pegs) newPegs.add(new ArrayList<>(peg));
        return newPegs;
    }

    static List<State> nextStates(State s) {
        List<State> list = new ArrayList<>();

        for (int from = 0; from < 3; from++) {
            if (s.pegs.get(from).isEmpty()) continue;

            for (int to = 0; to < 3; to++) {
                if (from == to) continue;

                List<Integer> src = s.pegs.get(from);
                List<Integer> dst = s.pegs.get(to);

                int disk = src.get(src.size() - 1);

                if (!dst.isEmpty() && dst.get(dst.size() - 1) < disk) continue;

                List<List<Integer>> np = copy(s.pegs);
                np.get(from).remove(np.get(from).size() - 1);
                np.get(to).add(disk);
                list.add(new State(np));
            }
        }

        return list;
    }

    public static void DFS(State initial) {
        Stack<State> stack = new Stack<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>();

        stack.push(initial);
        parent.put(initial.toString(), null);

        while (!stack.isEmpty()) {
            State current = stack.pop();

            if (current.isGoal()) {
                printPath(current.toString(), parent);
                return;
            }

            if (visited.contains(current.toString())) continue;
            visited.add(current.toString());

            for (State nxt : nextStates(current)) {
                if (!visited.contains(nxt.toString())) {
                    parent.put(nxt.toString(), current.toString());
                    stack.push(nxt);
                }
            }
        }
    }

    static void printPath(String goal, Map<String, String> parent) {
        List<String> path = new ArrayList<>();
        while (goal != null) {
            path.add(goal);
            goal = parent.get(goal);
        }
        Collections.reverse(path);

        System.out.println("\nDFS Solution:");
        for (String p : path) System.out.println(p);
        System.out.println("Moves: " + (path.size() - 1));
    }

    public static void main(String[] args) {
        List<List<Integer>> init = Arrays.asList(
            new ArrayList<>(Arrays.asList(3,2,1)),
            new ArrayList<>(),
            new ArrayList<>()
        );
        DFS(new State(init));
    }
}
