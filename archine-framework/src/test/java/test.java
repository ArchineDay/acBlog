import java.util.ArrayList;
import java.util.Iterator;

public class test {
    public static void main(String[] args) {
        ArrayList<String> removelist = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        list.add("你");
        list.add("好");
        list.add("再");
        list.add("见");
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String element = it.next();
            if (element.equals("再")) {
                removelist.add(element);
            }
        }
        list.removeAll(removelist);
        System.out.println(list);
    }
}
