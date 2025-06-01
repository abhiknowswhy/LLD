package pattern.interpreter;

import pattern.Duck;
import java.util.HashMap;
import java.util.Map;

public class Context {
    private Map<String, Duck> ducks = new HashMap<>();
    
    public void addDuck(String name, Duck duck) {
        ducks.put(name, duck);
    }
    
    public Duck getDuck(String name) {
        return ducks.get(name);
    }
    
    public boolean hasDuck(String name) {
        return ducks.containsKey(name);
    }
    
    public void listDucks() {
        System.out.println("Available ducks:");
        for (String name : ducks.keySet()) {
            System.out.println("- " + name + " (" + ducks.get(name).getClass().getSimpleName() + ")");
        }
    }
}
