package pattern;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TreeClientTest {
    @Test
    void testSingleInstanceOfDeciduousTree() throws Exception {
        TreeFactory factory = new TreeFactory();
        TreeClient client = new TreeClient(factory);
        client.addTree("deciduous", 1, 1);
        client.addTree("deciduous", 2, 2);
        Tree t1 = factory.getTree("deciduous");
        Tree t2 = factory.getTree("deciduous");
        assertSame(t1, t2, "DeciduousTree instances should be the same (flyweight)");
    }

    @Test
    void testSingleInstanceOfConiferTree() throws Exception {
        TreeFactory factory = new TreeFactory();
        TreeClient client = new TreeClient(factory);
        client.addTree("conifer", 3, 3);
        client.addTree("conifer", 4, 4);
        Tree t1 = factory.getTree("conifer");
        Tree t2 = factory.getTree("conifer");
        assertSame(t1, t2, "ConiferTree instances should be the same (flyweight)");
    }

    @Test
    void testDifferentTypesAreDifferentInstances() throws Exception {
        TreeFactory factory = new TreeFactory();
        TreeClient client = new TreeClient(factory);
        client.addTree("deciduous", 1, 1);
        client.addTree("conifer", 2, 2);
        Tree deciduous = factory.getTree("deciduous");
        Tree conifer = factory.getTree("conifer");
        assertNotSame(deciduous, conifer, "Different tree types should be different instances");
    }
}
