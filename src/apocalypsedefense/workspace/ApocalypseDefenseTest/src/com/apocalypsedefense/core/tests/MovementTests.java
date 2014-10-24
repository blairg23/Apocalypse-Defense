package com.apocalypsedefense.core.tests;

import com.apocalypsedefense.core.Map;
import com.apocalypsedefense.core.Movement;
import com.apocalypsedefense.core.Point;
import org.junit.Assert;
import org.junit.Before;

import java.util.ArrayList;

/**
 *
 * User: Pat
 */
public class MovementTests {
    final ArrayList<Point> noBlockedSpaces =
            new ArrayList<Point>();
    int height=2;
    int width =2;
    private Map map;

    @Before
    public void setUp() throws Exception {
        map = new Map(noBlockedSpaces, height, width);
    }

    @org.junit.Test
    public void testDirectedWalk_2x2NoBlockedFrom00To11_Returns11() throws Exception {
        Movement target = new Movement(new Map(noBlockedSpaces,2,2));

        final Point point = target.directedWalk(new Point(0, 0), new Point(1, 1));

        Assert.assertEquals(1, point.x);
        Assert.assertEquals(1, point.y);
    }

    @org.junit.Test
    public void testDirectedWalk_2x2NoBlockedStayInPlace_Returns11() throws Exception {
        Movement target = new Movement(new Map(noBlockedSpaces,2,2));

        final Point point = target.directedWalk(new Point(1,1), new Point(1, 1));

        Assert.assertEquals(1, point.x);
        Assert.assertEquals(1, point.y);
    }

    @org.junit.Test
    public void testDirectedWalk_3x3NoBlockedFrom00To22_Returns11() throws Exception {
        Movement target = new Movement(new Map(noBlockedSpaces,3,3));

        final Point point = target.directedWalk(new Point(0, 0), new Point(2,2));

        Assert.assertEquals(1, point.x);
        Assert.assertEquals(1, point.y);
    }

    @org.junit.Test
    public void testGetNewPosition_3x3NoBlockedFrom00To22RunTwice_Returns22() throws Exception {
        Movement target = new Movement(new Map(noBlockedSpaces,3,3));
        Point dest = new Point(2, 2);

        final Point newPosition = target.getNewPosition(new Point(0,0), "Directed", dest);
        final Point finalPos = target.getNewPosition(newPosition, "Directed", dest);

        Assert.assertEquals(2, finalPos.x);
        Assert.assertEquals(2, finalPos.y);
    }

    @org.junit.Test
    public void testGetNewPosition_2x2NoBlockedStayInPlace_Returns11() throws Exception {
        Movement target = new Movement(new Map(noBlockedSpaces,2,2));

        final Point point = target.getNewPosition(new Point(1,1), "Directed", new Point(1, 1));

        Assert.assertEquals(1, point.x);
        Assert.assertEquals(1, point.y);
    }

    @org.junit.Test
    public void testGetNewPosition_2x2IsBlocked_Returns00() throws Exception {
        ArrayList<Point> blockedSpaces = new ArrayList<Point>();
        final Point end = new Point(1, 1);
        blockedSpaces.add(end);
        Movement target = new Movement(new Map(blockedSpaces,2,2));

        final Point point = target.getNewPosition(new Point(0, 0), "Directed", end);

        Assert.assertEquals(0, point.x);
        Assert.assertEquals(0, point.y);
    }
}
