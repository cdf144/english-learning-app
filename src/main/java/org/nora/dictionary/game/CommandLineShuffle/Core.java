package org.nora.dictionary.game.CommandLineShuffle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Core {
    public static List<Character> shuffle(String s) {
        List<Character> list = new ArrayList<>();
        for (char c : s.toCharArray()) {
            list.add(c);
        }
        Collections.shuffle(list);
        return list;
    }
}
