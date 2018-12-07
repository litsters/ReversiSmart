package main;

import main.stability.StackSet;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class StackSetTest {

    @Test
    public void push() {
        Stack<Integer> test = new StackSet<>();
        assertTrue(test.size() == 0);

        test.push(1);
        assertTrue(test.size() == 1);

        test.push(1);
        assertTrue(test.size() == 1);

        test.push(2);
        assertTrue(test.size() == 2);

        assertTrue(test.pop() == 2);
        assertTrue(test.size() == 1);

        assertTrue(test.pop() == 1);
        assertTrue(test.size() == 0);
    }
}