package pattern.Menu;

import java.util.Iterator;
import java.util.ArrayList;
import pattern.Iterators.CompositeIterator;
import pattern.Visitors.MenuVisitor;

public class Menu extends MenuComponent {
    Iterator<MenuComponent> iterator = null;
    ArrayList<MenuComponent> menuComponents = new ArrayList<MenuComponent>();
    String name;
    String description;
    
    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public void add(MenuComponent menuComponent) {
        menuComponents.add(menuComponent);
    }
    
    public void remove(MenuComponent menuComponent) {
        menuComponents.remove(menuComponent);
    }
    
    public MenuComponent getChild(int i) {
        return menuComponents.get(i);
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public Iterator<MenuComponent> createIterator() {
        if (iterator == null) {
            iterator = new CompositeIterator(menuComponents.iterator());
        }
        return iterator;
    }
    
    @Override
    public void accept(MenuVisitor visitor) {
        visitor.visit(this);
        
        // Visit all children
        Iterator<MenuComponent> iterator = menuComponents.iterator();
        while (iterator.hasNext()) {
            MenuComponent menuComponent = iterator.next();
            menuComponent.accept(visitor);
        }
    }
    
    public void print() {
        System.out.print("\n" + getName());
        System.out.println(", " + getDescription());
        System.out.println("---------------------");
        
        Iterator<MenuComponent> iterator = menuComponents.iterator();
        while (iterator.hasNext()) {
            MenuComponent menuComponent = iterator.next();
            menuComponent.print();
        }
    }
}
