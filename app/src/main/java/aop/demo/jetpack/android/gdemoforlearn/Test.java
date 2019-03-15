package aop.demo.jetpack.android.gdemoforlearn;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        ArrayList<String> objects = new ArrayList<>();
        objects.add("dfd");
        ArrayList arrayList = objects;
        objects.add("");
        System.out.println(arrayList == objects);

    }
}
