package pattern.Iterators;

import java.util.Iterator;
import pattern.Menu.MenuComponent;

public class NullIterator implements Iterator<MenuComponent> {
    public MenuComponent next() {
        return null;
    }
    
    public boolean hasNext() {
        return false;
    }
}
