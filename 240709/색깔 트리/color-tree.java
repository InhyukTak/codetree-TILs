import java.io.*;
import java.util.*;

public class Main {
    static class Node {
        int mId;
        int pId;
        int color;
        int maxDepth;
        public Node(int mId, int pId, int color, int maxDepth) {
            this.mId = mId;
            this.pId = pId;
            this.color = color;
            this.maxDepth = maxDepth;
        }
    }

    // 노드 추가 100 m_id p_id color max_depth
    // 색깔 변경 200 m_id color
    // 색깔 조회 300 m_id
    // 점수 조회 400

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        int q = Integer.parseInt(br.readLine());
        int mId = 0;
        int pId = 0;
        int color = 0;
        int maxDepth = 0;

        Node[] tree = new Node[100001];
        List<Integer>[] children = new List[100001];
        Deque<Node> deque = new ArrayDeque<>();
        List<Integer> roots = new ArrayList<>();

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int action = Integer.parseInt(st.nextToken());
            switch (action) {
                case 100:
                    mId = Integer.parseInt(st.nextToken());
                    pId = Integer.parseInt(st.nextToken());
                    color = Integer.parseInt(st.nextToken());
                    maxDepth = Math.min(Integer.parseInt(st.nextToken()), pId == -1 ? 100000 : tree[pId].maxDepth - 1);
                    if (maxDepth < 1) {
                        break;
                    }
                    tree[mId] = new Node(mId, pId, color, maxDepth);
                    if (pId == -1) {
                        roots.add(mId);
                    } else {
                        if (children[pId] == null) {
                            children[pId] = new ArrayList<>();
                        }
                        children[pId].add(mId);
                    }
                    break;
                case 200:
                    mId = Integer.parseInt(st.nextToken());
                    color = Integer.parseInt(st.nextToken());
                    deque.add(tree[mId]);
                    while (!deque.isEmpty()) {
                        Node node = deque.poll();
                        node.color = color;
                        if (children[node.mId] != null) {
                            for (int child : children[node.mId]) {
                                deque.add(tree[child]);
                            }
                        }
                    }
                    break;
                case 300:
                    mId = Integer.parseInt(st.nextToken());
                    System.out.println(tree[mId].color);
                    break;
                case 400:
                    int totalValue = 0;
                    for (int j = 1; j <= 100000; j++) {
                        if (tree[j] != null) {
                            boolean[] colors = new boolean[6];
                            int value = 0;
                            deque.add(tree[j]);
                            while (!deque.isEmpty() && value < 5) {
                                Node node = deque.poll();
                                if (!colors[node.color]) {
                                    colors[node.color] = true;
                                    value++;
                                }
                                if (children[node.mId] != null) {
                                    for (int child : children[node.mId]) {
                                        deque.add(tree[child]);
                                    }
                                }
                            }
                            deque.clear();
                            totalValue += value * value;
                        }
                    }
                    System.out.println(totalValue);
                    break;
                default:
                    break;
            }
        }
    }
}