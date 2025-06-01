package pattern.Visitors;

import pattern.Menu.Menu;
import pattern.Menu.MenuItem;

public interface MenuVisitor {
    void visit(Menu menu);
    void visit(MenuItem menuItem);
}
